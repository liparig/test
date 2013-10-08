package toolGUI;

import java.awt.Graphics;
import org.gephi.graph.api.Node;
import org.gephi.preview.api.Item;
import org.gephi.preview.api.PDFTarget;
import org.gephi.preview.api.PreviewModel;
import org.gephi.preview.api.PreviewProperties;
import org.gephi.preview.api.PreviewProperty;
import org.gephi.preview.api.ProcessingTarget;
import org.gephi.preview.api.RenderTarget;
import org.gephi.preview.api.SVGTarget;
import org.gephi.preview.spi.ItemBuilder;
import org.gephi.preview.spi.Renderer;
import processing.core.PGraphics;

public class RendererTemplate implements Renderer {

    @Override
    public String getDisplayName() {
        return "Some name";
    }

    @Override
    public void preProcess(PreviewModel previewModel) {
    }

    @Override
    public void render(Item item, RenderTarget target, PreviewProperties properties) {
        //Retrieve clicked node for the label:
        LabelItem label = (LabelItem) item;
        Node node = label.node;

        //Finally draw your graphics for the node label in each target (or just processing):
        if (target instanceof ProcessingTarget) {
            PGraphics g = ((ProcessingTarget) target).getGraphics();
            //Or basic java2d graphics : Graphics g = ((ProcessingTarget) target).getApplet().getGraphics();
        } else if (target instanceof PDFTarget) {
        } else if (target instanceof SVGTarget) {
        }
    }

    @Override
    public PreviewProperty[] getProperties() {
        return new PreviewProperty[0];
    }

    @Override
    public boolean isRendererForitem(Item item, PreviewProperties properties) {
        return item instanceof LabelItem;
    }

    @Override
    public boolean needsItemBuilder(ItemBuilder itemBuilder, PreviewProperties properties) {
        return itemBuilder instanceof ItemBuilderTemplate;
    }
}