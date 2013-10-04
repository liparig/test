package prova;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JButton;

import org.eclipse.wb.swing.FocusTraversalOnArray;
import org.gephi.graph.api.DirectedGraph;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.preview.api.PreviewController;
import org.gephi.preview.api.PreviewModel;
import org.gephi.preview.api.PreviewProperty;
import org.gephi.preview.api.ProcessingTarget;
import org.gephi.preview.api.RenderTarget;
import org.gephi.preview.types.DependantOriginalColor;
import org.openide.util.Lookup;

import processing.core.PApplet;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Panelgui extends JPanel {

	/**
	 * 
	 */
	public Panelgui() {
		
		Import imp=new Import();
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{224, 0};
		gridBagLayout.rowHeights = new int[]{26, 37, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JButton btnImportgraph = new JButton("ImportGraph");
		btnImportgraph.addActionListener(imp);
		
		GridBagConstraints gbc_btnImportgraph = new GridBagConstraints();
		gbc_btnImportgraph.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnImportgraph.insets = new Insets(0, 0, 5, 0);
		gbc_btnImportgraph.gridx = 0;
		gbc_btnImportgraph.gridy = 0;
		add(btnImportgraph, gbc_btnImportgraph);
		

	    
	    JButton btnApplyfilter = new JButton("Degree");
	    btnApplyfilter.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
	    		DirectedGraph graph = graphModel.getDirectedGraph();
	            System.out.println("Nodes: " + graph.getNodeCount());
	            System.out.println("Edges: " + graph.getEdgeCount());
	    	}
	    });
		GridBagConstraints gbc_btnApplyfilter = new GridBagConstraints();
		gbc_btnApplyfilter.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnApplyfilter.anchor = GridBagConstraints.NORTH;
		gbc_btnApplyfilter.gridx = 0;
		gbc_btnApplyfilter.gridy = 1;
		add(btnApplyfilter, gbc_btnApplyfilter);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{btnImportgraph, btnApplyfilter}));

	}

}
