package org.gephi.toolkit.demos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import org.gephi.preview.api.ManagedRenderer;
import org.gephi.preview.api.PreviewController;
import org.gephi.preview.api.PreviewModel;
import org.gephi.preview.api.ProcessingTarget;
import org.gephi.preview.api.RenderTarget;
import org.gephi.preview.spi.PreviewMouseListener;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;

import processing.core.PApplet;

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

