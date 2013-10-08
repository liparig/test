package toolGUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;

import org.gephi.io.importer.api.Container;
import org.gephi.io.importer.api.EdgeDefault;
import org.gephi.io.importer.api.ImportController;
import org.gephi.io.processor.plugin.DefaultProcessor;
import org.gephi.preview.api.PreviewController;
import org.gephi.preview.api.PreviewModel;
import org.gephi.preview.api.PreviewProperty;
import org.gephi.preview.types.DependantOriginalColor;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.openide.util.Lookup;


public class CaricaEvent extends Observable implements ActionListener {

	public void actionPerformed(ActionEvent arg0) {
		String path;
		ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
		pc.closeCurrentProject();
		pc.newProject();
		Workspace workspace=pc.getCurrentWorkspace();
        ImportController importController = Lookup.getDefault().lookup(ImportController.class);
        Container container;
        try {
            File file = new File(getClass().getResource("/org/gephi/toolkit/demos/resources/gediminas_graph.gv").toURI());
            path=file.getName();
            container = importController.importFile(file);
            container.getLoader().setEdgeDefault(EdgeDefault.DIRECTED);   //Force DIRECTED
            container.setAllowAutoNode(false);  //Don't create missing nodes
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
    	
        PreviewController previewController=Lookup.getDefault().lookup(PreviewController.class);
        PreviewModel previewModel=previewController.getModel();
        previewModel.getProperties().putValue(PreviewProperty.SHOW_NODE_LABELS, Boolean.FALSE);
        previewModel.getProperties().putValue(PreviewProperty.NODE_LABEL_COLOR, new DependantOriginalColor(Color.WHITE));
        previewModel.getProperties().putValue(PreviewProperty.EDGE_CURVED, Boolean.FALSE);
        previewModel.getProperties().putValue(PreviewProperty.EDGE_OPACITY, 50);
        previewModel.getProperties().putValue(PreviewProperty.EDGE_RADIUS, 10f);
        previewModel.getProperties().putValue(PreviewProperty.BACKGROUND_COLOR, Color.BLACK);
        previewController.refreshPreview();
      //Append imported data to GraphAPI
        importController.process(container, new DefaultProcessor(), workspace);
        this.setChanged();
        this.notifyObservers(path);
	}

}
