package toolGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import org.gephi.preview.api.PreviewController;
import org.gephi.preview.api.PreviewModel;
import org.gephi.preview.api.PreviewProperty;
import org.gephi.preview.api.ProcessingTarget;
import org.gephi.preview.api.RenderTarget;
import org.gephi.preview.types.DependantOriginalColor;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;

import processing.core.PApplet;

import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;

import java.awt.FlowLayout;

import javax.swing.ScrollPaneConstants;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class OverviewGUI implements Observer{
	
	private ProcessingTarget target;
	private PreviewController previewController;
	private PApplet applet;

	
	public PApplet getPApplet() {	
		previewController=Lookup.getDefault().lookup(PreviewController.class);
        previewController.refreshPreview();

		//New Processing target, get the PApplet
		target = (ProcessingTarget) previewController.getRenderTarget(RenderTarget.PROCESSING_TARGET);
		applet = target.getApplet();
		applet.init();
		try {
			
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Exceptions.printStackTrace(ex);
        }
		//Refresh the preview and reset the zoom
		previewController.render(target);
		target.refresh();
		target.resetZoom();
		target.zoomMinus();
		return applet;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		previewController=Lookup.getDefault().lookup(PreviewController.class);

		//New Processing target, get the PApplet
		target = (ProcessingTarget) previewController.getRenderTarget(RenderTarget.PROCESSING_TARGET);
		applet = target.getApplet();
		applet.init();
		try {
			
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Exceptions.printStackTrace(ex);
        }
		//Refresh the preview and reset the zoom
		previewController.render(target);
		target.refresh();
		target.resetZoom();
		target.zoomMinus();

	}
}

