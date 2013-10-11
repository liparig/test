package org.gephi.toolkit.demos;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import org.gephi.graph.api.DirectedGraph;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.openide.util.Lookup;
import java.util.Observable;
import java.util.Observer;
import javax.swing.border.TitledBorder;

public class InfoGUI extends JPanel implements Observer{
	private JLabel lblnodi;
	private JLabel lblarchi;
	private JLabel lblOr;

	/**
	 * Create the panel.
	 */
	public InfoGUI() {
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{116, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Informazioni Grafo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{116, 81, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
	
		
		JLabel lblNodi = new JLabel("Nodi:");
		GridBagConstraints gbc_lblNodi = new GridBagConstraints();
		gbc_lblNodi.anchor = GridBagConstraints.NORTH;
		gbc_lblNodi.insets = new Insets(0, 0, 5, 5);
		gbc_lblNodi.gridx = 0;
		gbc_lblNodi.gridy = 0;
		panel.add(lblNodi, gbc_lblNodi);
		
		lblnodi = new JLabel("0");
		GridBagConstraints gbc_lblnodi = new GridBagConstraints();
		gbc_lblnodi.insets = new Insets(0, 0, 5, 0);
		gbc_lblnodi.gridx = 1;
		gbc_lblnodi.gridy = 0;
		panel.add(lblnodi, gbc_lblnodi);
		
		JLabel lblArchi = new JLabel("Archi:");
		GridBagConstraints gbc_lblArchi = new GridBagConstraints();
		gbc_lblArchi.insets = new Insets(0, 0, 5, 5);
		gbc_lblArchi.gridx = 0;
		gbc_lblArchi.gridy = 1;
		panel.add(lblArchi, gbc_lblArchi);
		
		lblarchi = new JLabel("0");
		GridBagConstraints gbc_lblarchi = new GridBagConstraints();
		gbc_lblarchi.insets = new Insets(0, 0, 5, 0);
		gbc_lblarchi.gridx = 1;
		gbc_lblarchi.gridy = 1;
		panel.add(lblarchi, gbc_lblarchi);
		
		JLabel lblOrientamento = new JLabel("Tipo:");
		GridBagConstraints gbc_lblOrientamento = new GridBagConstraints();
		gbc_lblOrientamento.insets = new Insets(0, 0, 0, 5);
		gbc_lblOrientamento.gridx = 0;
		gbc_lblOrientamento.gridy = 2;
		panel.add(lblOrientamento, gbc_lblOrientamento);
		
		lblOr = new JLabel("");
		GridBagConstraints gbc_lblOr = new GridBagConstraints();
		gbc_lblOr.gridx = 1;
		gbc_lblOr.gridy = 2;
		panel.add(lblOr, gbc_lblOr);
	}

	@Override
	public void update(Observable o, Object arg) {
		GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
		//See if graph is well imported
        DirectedGraph graph = graphModel.getDirectedGraph();
        lblarchi.setText(""+ graph.getEdgeCount());
        lblnodi.setText(""+ graph.getNodeCount());
        if(graphModel.isDirected()){
        	lblOr.setText("Orientato");
        }
        if(graphModel.isUndirected()){
        	lblOr.setText("Non Orientato");
        }
        if(graphModel.isMixed()){
        	lblOr.setText("Misto");
        }
        if(graphModel.isHierarchical()){
        	lblOr.setText("Gerarchico");
        }
		
	}
}
