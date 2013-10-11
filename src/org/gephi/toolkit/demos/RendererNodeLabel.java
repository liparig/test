package org.gephi.toolkit.demos;

import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import org.gephi.graph.api.Node;
import org.gephi.preview.api.Item;
import org.gephi.preview.api.PDFTarget;
import org.gephi.preview.api.PreviewModel;
import org.gephi.preview.api.PreviewProperties;
import org.gephi.preview.api.PreviewProperty;
import org.gephi.preview.api.RenderTarget;

import org.gephi.preview.api.SVGTarget;

import org.gephi.preview.plugin.items.NodeItem;
import org.gephi.preview.plugin.items.NodeLabelItem;
import org.gephi.preview.spi.ItemBuilder;
import org.gephi.preview.spi.MouseResponsiveRenderer;
import org.gephi.preview.spi.PreviewMouseListener;
import org.gephi.preview.spi.Renderer;
import org.gephi.preview.types.DependantColor;
import org.gephi.preview.types.DependantOriginalColor;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.w3c.dom.svg.SVGLocatable;
import org.w3c.dom.svg.SVGRect;

@ServiceProvider(service = Renderer.class)
public class RendererNodeLabel implements Renderer, MouseResponsiveRenderer{
    //Custom properties

    public static final String NODE_COLOR = "node.label.nodeColor";
    public static final String NODE_SIZE = "node.label.nodeSize";
    public static final String NODE_X = "node.x";
    public static final String NODE_Y = "node.y";
    public static final String FONT_SIZE = "node.label.fontSize";
    //Default values
    protected final boolean defaultShowLabels = true;
    protected final Font defaultFont = new Font("Arial", Font.PLAIN, 12);
    protected final boolean defaultShorten = false;
    protected final DependantOriginalColor defaultColor = new DependantOriginalColor(DependantOriginalColor.Mode.ORIGINAL);
    protected final int defaultMaxChar = 30;
    protected final boolean defaultProportinalSize = true;
    protected final float defaultOutlineSize = 4;
    protected final DependantColor defaultOutlineColor = new DependantColor(Color.WHITE);
    protected final int defaultOutlineOpacity = 40;
    protected final boolean defaultShowBox = false;
    protected final DependantColor defaultBoxColor = new DependantColor(DependantColor.Mode.PARENT);
    protected final int defaultBoxOpacity = 100;
    //Font cache
    protected Map<Integer, Font> fontCache;

    @Override
    public void preProcess(PreviewModel previewModel) {
        PreviewProperties properties = previewModel.getProperties();
        if (properties.getBooleanValue(PreviewProperty.NODE_LABEL_SHORTEN)) {
            //Shorten labels
            Item[] nodeLabelsItems = previewModel.getItems(Item.NODE_LABEL);

            int maxChars = properties.getIntValue(PreviewProperty.NODE_LABEL_MAX_CHAR);
            for (Item item : nodeLabelsItems) {
                String label = item.getData(NodeLabelItem.LABEL);
                if (label.length() >= maxChars + 3) {
                    label = label.substring(0, maxChars) + "...";
                    item.setData(NodeLabelItem.LABEL, label);
                }
            }
        }

        //Put parent color, size and position
        for (Item item : previewModel.getItems(Item.NODE_LABEL)) {
            Node node = (Node) item.getSource();
            Item nodeItem = previewModel.getItem(Item.NODE, node);
            item.setData(NODE_COLOR, nodeItem.getData(NodeItem.COLOR));
            item.setData(NODE_SIZE, nodeItem.getData(NodeItem.SIZE));
            item.setData(NODE_X, nodeItem.getData(NodeItem.X));
            item.setData(NODE_Y, nodeItem.getData(NodeItem.Y));
        }

        //Calculate font size and cache fonts
        fontCache = new HashMap<Integer, Font>();
        Font font = properties.getFontValue(PreviewProperty.NODE_LABEL_FONT);
        for (Item item : previewModel.getItems(Item.NODE_LABEL)) {
            Float nodeSize = item.getData(NODE_SIZE);
            Float fontSize = 1f;
            if (item.getData(NodeLabelItem.SIZE) != null) {
                fontSize = item.getData(NodeLabelItem.SIZE);
            }
            if (properties.getBooleanValue(PreviewProperty.NODE_LABEL_PROPORTIONAL_SIZE)) {
                fontSize *= nodeSize / 10f;
            }
            fontSize *= font.getSize();
            Font labelFont = font.deriveFont((float) fontSize);
            fontCache.put(labelFont.getSize(), labelFont);
            item.setData(FONT_SIZE, labelFont.getSize());
        }
    }

    @Override
    public void render(Item item, RenderTarget target, PreviewProperties properties) {
        Node node = (Node) item.getSource();
        //Label
        Color nodeColor = item.getData(NODE_COLOR);
        Color color = item.getData(NodeLabelItem.COLOR);
        DependantOriginalColor propColor = properties.getValue(PreviewProperty.NODE_LABEL_COLOR);
        color = propColor.getColor(nodeColor, color);
        String label = item.getData(NodeLabelItem.LABEL);
        Integer fontSize = item.getData(FONT_SIZE);
        Float x = item.getData(NODE_X);
        Float y = item.getData(NODE_Y);

        //Outline
        DependantColor outlineDependantColor = properties.getValue(PreviewProperty.NODE_LABEL_OUTLINE_COLOR);
        Float outlineSize = properties.getFloatValue(PreviewProperty.NODE_LABEL_OUTLINE_SIZE);
        outlineSize = outlineSize * (fontSize / 32f);
        int outlineAlpha = (int) ((properties.getFloatValue(PreviewProperty.NODE_LABEL_OUTLINE_OPACITY) / 100f) * 255f);
        if (outlineAlpha > 255) {
            outlineAlpha = 255;
        }
        Color outlineColor = outlineDependantColor.getColor(nodeColor);
        outlineColor = new Color(outlineColor.getRed(), outlineColor.getGreen(), outlineColor.getBlue(), outlineAlpha);

        //Box
        Boolean showBox = properties.getValue(PreviewProperty.NODE_LABEL_SHOW_BOX);
        DependantColor boxDependantColor = properties.getValue(PreviewProperty.NODE_LABEL_BOX_COLOR);
        Color boxColor = boxDependantColor.getColor(nodeColor);
        int boxAlpha = (int) ((properties.getFloatValue(PreviewProperty.NODE_LABEL_BOX_OPACITY) / 100f) * 255f);
        if (boxAlpha > 255) {
            boxAlpha = 255;
        }
        boxColor = new Color(boxColor.getRed(), boxColor.getGreen(), boxColor.getBlue(), boxAlpha);

        if (target instanceof G2DTarget) {
            renderG2D((G2DTarget) target, label, x, y, fontSize, color, outlineSize, outlineColor, showBox, boxColor);
        } else if (target instanceof SVGTarget) {
            renderSVG((SVGTarget) target, node, label, x, y, fontSize, color, outlineSize, outlineColor, showBox, boxColor);
        } else if (target instanceof PDFTarget) {
            renderPDF((PDFTarget) target, node, label, x, y, fontSize, color, outlineSize, outlineColor, showBox, boxColor);
        }
    }

    public void renderG2D(G2DTarget target, String label, float x, float y, int fontSize, Color color, float outlineSize, Color outlineColor, boolean showBox, Color boxColor) {
        Graphics2D graphics = target.getGraphics();

        Font font = fontCache.get(fontSize);
        graphics.setFont(font);

        FontMetrics fm = graphics.getFontMetrics();
        float posX = x - fm.stringWidth(label) / 2f;
        float posY = y + fm.getDescent();

        //Box
        if (showBox) {
            graphics.setColor(boxColor);
            Rectangle2D.Float rect = new Rectangle2D.Float();
            rect.setFrame(posX - outlineSize / 2f,
                    y - (fm.getAscent() + fm.getDescent()) / 2f - outlineSize / 2f,
                    fm.stringWidth(label) + outlineSize,
                    fm.getAscent() + fm.getDescent() + outlineSize);
            graphics.draw(rect);
        }

        //Outline
        if (outlineSize > 0) {
            FontRenderContext frc = graphics.getFontRenderContext();
            GlyphVector gv = font.createGlyphVector(frc, label);
            Shape glyph = gv.getOutline(posX, posY);
            graphics.setColor(outlineColor);
            graphics.setStroke(new BasicStroke(outlineSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            graphics.draw(glyph);
        }

        graphics.setColor(color);
        graphics.drawString(label, posX, posY);
    }

    public void renderSVG(SVGTarget target, Node node, String label, float x, float y, int fontSize, Color color, float outlineSize, Color outlineColor, boolean showBox, Color boxColor) {
        Text labelText = target.createTextNode(label);
        Font font = fontCache.get(fontSize);
        if (outlineSize > 0) {
            Text labelTextOutline = target.createTextNode(label);
            Element outlineElem = target.createElement("text");
            outlineElem.setAttribute("class", ""+node.getId());
            outlineElem.setAttribute("x", String.valueOf(x));
            outlineElem.setAttribute("y", String.valueOf(y));
            outlineElem.setAttribute("style", "text-anchor: middle; dominant-baseline: central;");
            outlineElem.setAttribute("fill", target.toHexString(color));
            outlineElem.setAttribute("font-family", font.getFamily());
            outlineElem.setAttribute("font-size", String.valueOf(fontSize));
            outlineElem.setAttribute("stroke", target.toHexString(outlineColor));
            outlineElem.setAttribute("stroke-width", (outlineSize * target.getScaleRatio()) + "px");
            outlineElem.setAttribute("stroke-linecap", "round");
            outlineElem.setAttribute("stroke-linejoin", "round");
            outlineElem.setAttribute("stroke-opacity", String.valueOf(outlineColor.getAlpha() / 255f));
            outlineElem.appendChild(labelTextOutline);
            target.getTopElement(SVGTarget.TOP_NODE_LABELS_OUTLINE).appendChild(outlineElem);

            //Trick to center text vertically on node:
            SVGRect rect = ((SVGLocatable) outlineElem).getBBox();
            outlineElem.setAttribute("y", String.valueOf(y + rect.getHeight() / 4f));
        }

        Element labelElem = target.createElement("text");
        labelElem.setAttribute("class", ""+node.getId());
        labelElem.setAttribute("x", String.valueOf(x));
        labelElem.setAttribute("y", String.valueOf(y));
        labelElem.setAttribute("style", "text-anchor: middle; dominant-baseline: central;");
        labelElem.setAttribute("fill", target.toHexString(color));
        labelElem.setAttribute("font-family", font.getFamily());
        labelElem.setAttribute("font-size", String.valueOf(fontSize));
        labelElem.appendChild(labelText);
        target.getTopElement(SVGTarget.TOP_NODE_LABELS).appendChild(labelElem);

        //Trick to center text vertically on node:
        SVGRect rect = ((SVGLocatable) labelElem).getBBox();
        labelElem.setAttribute("y", String.valueOf(y + rect.getHeight() / 4f));

        //Box
        if (showBox) {
            rect = ((SVGLocatable) labelElem).getBBox();
            Element boxElem = target.createElement("rect");
            boxElem.setAttribute("x", Float.toString(rect.getX() - outlineSize / 2f));
            boxElem.setAttribute("y", Float.toString(rect.getY() - outlineSize / 2f));
            boxElem.setAttribute("width", Float.toString(rect.getWidth() + outlineSize));
            boxElem.setAttribute("height", Float.toString(rect.getHeight() + outlineSize));
            boxElem.setAttribute("fill", target.toHexString(boxColor));
            boxElem.setAttribute("opacity", String.valueOf(boxColor.getAlpha() / 255f));
            target.getTopElement(SVGTarget.TOP_NODE_LABELS).insertBefore(boxElem, labelElem);
        }
    }

    public void renderPDF(PDFTarget target, Node node, String label, float x, float y, int fontSize, Color color, float outlineSize, Color outlineColor, boolean showBox, Color boxColor) {
        Font font = fontCache.get(fontSize);
        PdfContentByte cb = target.getContentByte();
        BaseFont bf = target.getBaseFont(font);

        //Box
        if (showBox) {
            cb.setRGBColorFill(boxColor.getRed(), boxColor.getGreen(), boxColor.getBlue());
            if (boxColor.getAlpha() < 255) {
                cb.saveState();
                float alpha = boxColor.getAlpha() / 255f;
                PdfGState gState = new PdfGState();
                gState.setFillOpacity(alpha);
                cb.setGState(gState);
            }
            float textWidth = getTextWidth(bf, fontSize, label);
            float textHeight = getTextHeight(bf, fontSize, label);

            //A height of just textHeight seems to be half the text height sometimes
            //BaseFont getAscentPoint and getDescentPoint may be not very precise
            cb.rectangle(x - textWidth / 2f - outlineSize / 2f, -y - outlineSize / 2f - textHeight, textWidth + outlineSize, textHeight * 2f + outlineSize);

            cb.fill();
            if (boxColor.getAlpha() < 255) {
                cb.restoreState();
            }
        }

        cb.setRGBColorFill(color.getRed(), color.getGreen(), color.getBlue());
        float textHeight = getTextHeight(bf, fontSize, label);
        if (outlineSize > 0) {
            cb.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_STROKE);
            cb.setRGBColorStroke(outlineColor.getRed(), outlineColor.getGreen(), outlineColor.getBlue());
            cb.setLineWidth(outlineSize);
            cb.setLineJoin(PdfContentByte.LINE_JOIN_ROUND);
            cb.setLineCap(PdfContentByte.LINE_CAP_ROUND);
            if (outlineColor.getAlpha() < 255) {
                cb.saveState();
                float alpha = outlineColor.getAlpha() / 255f;
                PdfGState gState = new PdfGState();
                gState.setStrokeOpacity(alpha);
                cb.setGState(gState);
            }
            cb.beginText();
            cb.setFontAndSize(bf, font.getSize());
            cb.showTextAligned(PdfContentByte.ALIGN_CENTER, label, x, -y - (textHeight / 2f), 0f);
            cb.endText();
            if (outlineColor.getAlpha() < 255) {
                cb.restoreState();
            }
        }
        cb.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL);
        cb.beginText();
        cb.setFontAndSize(bf, font.getSize());
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, label, x, -y - (textHeight / 2f), 0f);
        cb.endText();
    }

    private float getTextHeight(BaseFont baseFont, float fontSize, String text) {
        float ascend = baseFont.getAscentPoint(text, fontSize);
        float descend = baseFont.getDescentPoint(text, fontSize);
        return ascend + descend;
    }

    private float getTextWidth(BaseFont baseFont, float fontSize, String text) {
        return baseFont.getWidthPoint(text, fontSize);
    }

    @Override
    public PreviewProperty[] getProperties() {
        return new PreviewProperty[]{
            PreviewProperty.createProperty(this, PreviewProperty.SHOW_NODE_LABELS, Boolean.class,
            NbBundle.getMessage(org.gephi.preview.plugin.renderers.NodeLabelRenderer.class, "NodeLabelRenderer.property.display.displayName"),
            NbBundle.getMessage(org.gephi.preview.plugin.renderers.NodeLabelRenderer.class, "NodeLabelRenderer.property.display.description"),
            PreviewProperty.CATEGORY_NODE_LABELS).setValue(defaultShowLabels),
            PreviewProperty.createProperty(this, PreviewProperty.NODE_LABEL_FONT, Font.class,
            NbBundle.getMessage(org.gephi.preview.plugin.renderers.NodeLabelRenderer.class, "NodeLabelRenderer.property.font.displayName"),
            NbBundle.getMessage(org.gephi.preview.plugin.renderers.NodeLabelRenderer.class, "NodeLabelRenderer.property.font.description"),
            PreviewProperty.CATEGORY_NODE_LABELS, PreviewProperty.SHOW_NODE_LABELS).setValue(defaultFont),
            PreviewProperty.createProperty(this, PreviewProperty.NODE_LABEL_PROPORTIONAL_SIZE, Boolean.class,
            NbBundle.getMessage(org.gephi.preview.plugin.renderers.NodeLabelRenderer.class, "NodeLabelRenderer.property.proportionalSize.displayName"),
            NbBundle.getMessage(org.gephi.preview.plugin.renderers.NodeLabelRenderer.class, "NodeLabelRenderer.property.proportionalSize.description"),
            PreviewProperty.CATEGORY_NODE_LABELS, PreviewProperty.SHOW_NODE_LABELS).setValue(defaultProportinalSize),
            PreviewProperty.createProperty(this, PreviewProperty.NODE_LABEL_COLOR, DependantOriginalColor.class,
            NbBundle.getMessage(org.gephi.preview.plugin.renderers.NodeLabelRenderer.class, "NodeLabelRenderer.property.color.displayName"),
            NbBundle.getMessage(org.gephi.preview.plugin.renderers.NodeLabelRenderer.class, "NodeLabelRenderer.property.color.description"),
            PreviewProperty.CATEGORY_NODE_LABELS, PreviewProperty.SHOW_NODE_LABELS).setValue(defaultColor),
            PreviewProperty.createProperty(this, PreviewProperty.NODE_LABEL_SHORTEN, Boolean.class,
            NbBundle.getMessage(org.gephi.preview.plugin.renderers.NodeLabelRenderer.class, "NodeLabelRenderer.property.shorten.displayName"),
            NbBundle.getMessage(org.gephi.preview.plugin.renderers.NodeLabelRenderer.class, "NodeLabelRenderer.property.shorten.description"),
            PreviewProperty.CATEGORY_NODE_LABELS, PreviewProperty.SHOW_NODE_LABELS).setValue(defaultShorten),
            PreviewProperty.createProperty(this, PreviewProperty.NODE_LABEL_MAX_CHAR, Integer.class,
            NbBundle.getMessage(org.gephi.preview.plugin.renderers.NodeLabelRenderer.class, "NodeLabelRenderer.property.maxchar.displayName"),
            NbBundle.getMessage(org.gephi.preview.plugin.renderers.NodeLabelRenderer.class, "NodeLabelRenderer.property.maxchar.description"),
            PreviewProperty.CATEGORY_NODE_LABELS, PreviewProperty.SHOW_NODE_LABELS).setValue(defaultMaxChar),
            PreviewProperty.createProperty(this, PreviewProperty.NODE_LABEL_OUTLINE_SIZE, Float.class,
            NbBundle.getMessage(org.gephi.preview.plugin.renderers.NodeLabelRenderer.class, "NodeLabelRenderer.property.outlineSize.displayName"),
            NbBundle.getMessage(org.gephi.preview.plugin.renderers.NodeLabelRenderer.class, "NodeLabelRenderer.property.outlineSize.description"),
            PreviewProperty.CATEGORY_NODE_LABELS, PreviewProperty.SHOW_NODE_LABELS).setValue(defaultOutlineSize),
            PreviewProperty.createProperty(this, PreviewProperty.NODE_LABEL_OUTLINE_COLOR, DependantColor.class,
            NbBundle.getMessage(org.gephi.preview.plugin.renderers.NodeLabelRenderer.class, "NodeLabelRenderer.property.outlineColor.displayName"),
            NbBundle.getMessage(org.gephi.preview.plugin.renderers.NodeLabelRenderer.class, "NodeLabelRenderer.property.outlineColor.description"),
            PreviewProperty.CATEGORY_NODE_LABELS, PreviewProperty.SHOW_NODE_LABELS).setValue(defaultOutlineColor),
            PreviewProperty.createProperty(this, PreviewProperty.NODE_LABEL_OUTLINE_OPACITY, Float.class,
            NbBundle.getMessage(org.gephi.preview.plugin.renderers.NodeLabelRenderer.class, "NodeLabelRenderer.property.outlineOpacity.displayName"),
            NbBundle.getMessage(org.gephi.preview.plugin.renderers.NodeLabelRenderer.class, "NodeLabelRenderer.property.outlineOpacity.description"),
            PreviewProperty.CATEGORY_NODE_LABELS, PreviewProperty.SHOW_NODE_LABELS).setValue(defaultOutlineOpacity),
            PreviewProperty.createProperty(this, PreviewProperty.NODE_LABEL_SHOW_BOX, Boolean.class,
            NbBundle.getMessage(org.gephi.preview.plugin.renderers.NodeLabelRenderer.class, "NodeLabelRenderer.property.box.displayName"),
            NbBundle.getMessage(org.gephi.preview.plugin.renderers.NodeLabelRenderer.class, "NodeLabelRenderer.property.box.description"),
            PreviewProperty.CATEGORY_NODE_LABELS, PreviewProperty.SHOW_NODE_LABELS).setValue(defaultShowBox),
            PreviewProperty.createProperty(this, PreviewProperty.NODE_LABEL_BOX_COLOR, DependantColor.class,
            NbBundle.getMessage(org.gephi.preview.plugin.renderers.NodeLabelRenderer.class, "NodeLabelRenderer.property.box.color.displayName"),
            NbBundle.getMessage(org.gephi.preview.plugin.renderers.NodeLabelRenderer.class, "NodeLabelRenderer.property.box.color.description"),
            PreviewProperty.CATEGORY_NODE_LABELS, PreviewProperty.NODE_LABEL_SHOW_BOX, PreviewProperty.SHOW_NODE_LABELS).setValue(defaultBoxColor),
            PreviewProperty.createProperty(this, PreviewProperty.NODE_LABEL_BOX_OPACITY, Float.class,
            NbBundle.getMessage(org.gephi.preview.plugin.renderers.NodeLabelRenderer.class, "NodeLabelRenderer.property.box.opacity.displayName"),
            NbBundle.getMessage(org.gephi.preview.plugin.renderers.NodeLabelRenderer.class, "NodeLabelRenderer.property.box.opacity.description"),
            PreviewProperty.CATEGORY_NODE_LABELS, PreviewProperty.NODE_LABEL_SHOW_BOX, PreviewProperty.SHOW_NODE_LABELS).setValue(defaultBoxOpacity),};
    }

    @Override
    public boolean isRendererForitem(Item item, PreviewProperties properties) {
        return item instanceof LabelItem;
    }

    @Override
    public boolean needsItemBuilder(ItemBuilder itemBuilder, PreviewProperties properties) {
        return itemBuilder instanceof ItemBuilderTemplate;
    }

    public boolean needsPreviewMouseListener(PreviewMouseListener previewMouseListener) {
        return previewMouseListener instanceof MouseListenerLabel;
                }

    public String getDisplayName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
