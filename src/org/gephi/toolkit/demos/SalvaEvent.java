package org.gephi.toolkit.demos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.gephi.io.exporter.api.ExportController;
import org.gephi.io.exporter.preview.PDFExporter;
import org.gephi.preview.api.PreviewController;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.openide.util.Lookup;

import com.itextpdf.text.PageSize;

public class SalvaEvent implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		//Init a project - and therefore a workspace
		ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
		Workspace workspace = pc.getCurrentWorkspace();
		//Simple PDF export
		PreviewController preview=Lookup.getDefault().lookup(PreviewController.class);
		if(preview.getModel()!=null){
			ExportController ec = Lookup.getDefault().lookup(ExportController.class);
			try {
				ec.exportFile(new File("/Users/Lipari/Documents/GitHub/test/src/org/gephi/toolkit/demos/resources/simple.pdf"));
			} catch (IOException ex) {
				ex.printStackTrace();
			  return;
			}
			//PDF Exporter config and export to Byte array
			PDFExporter pdfExporter = (PDFExporter) ec.getExporter("pdf");
			pdfExporter.setPageSize(PageSize.A0);
			pdfExporter.setWorkspace(workspace);
		}
		else{
			JFrame frame=new JFrame();
			JOptionPane.showMessageDialog(frame,"Nessun Grafo da salvare.");
		}
			
	}

}
