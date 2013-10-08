package toolGUI;

import javax.swing.JPanel;

import java.awt.GridBagLayout;


import java.awt.GridBagConstraints;

import javax.swing.JCheckBox;

import java.awt.Insets;

import javax.swing.JButton;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import org.gephi.graph.api.DirectedGraph;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.gephi.graph.api.NodeIterable;
import org.openide.util.Lookup;

import javax.swing.border.TitledBorder;
import javax.swing.SwingConstants;
import java.awt.Dimension;

public class FiltriPanel extends JPanel implements Observer {
	private JPanel panel;
	private JLabel lblvalArchi;
	private JLabel lblvalNodi;
	private ApplyEvent apply;
	private FilterRangeSlider rangeSliderDegree;
	private FilterRangeSlider rangeSliderInDegree;
	private FilterDecimalSlider rangeSliderEdge;
	/**
	 * Create the panel.
	 */
	public FiltriPanel() {
		
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{20, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Filtri", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0};
		gbl_panel_1.rowHeights = new int[]{20, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JCheckBox ckDegree = new JCheckBox("Degree Range");
		ckDegree.setMinimumSize(new Dimension(107, 23));
		ckDegree.setMaximumSize(new Dimension(107, 23));
		ckDegree.setPreferredSize(new Dimension(107, 23));
		ckDegree.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_ckDegree = new GridBagConstraints();
		gbc_ckDegree.anchor = GridBagConstraints.WEST;
		gbc_ckDegree.gridx = 0;
		gbc_ckDegree.gridy = 0;
		panel_1.add(ckDegree, gbc_ckDegree);
		
		rangeSliderDegree = new FilterRangeSlider(0,250);
		rangeSliderDegree.setVisible(false);
		GridBagConstraints gbc_rangeSliderDegree = new GridBagConstraints();
		gbc_rangeSliderDegree.fill = GridBagConstraints.BOTH;
		gbc_rangeSliderDegree.gridx = 0;
		gbc_rangeSliderDegree.gridy = 1;
		panel_1.add(rangeSliderDegree, gbc_rangeSliderDegree);
		
		JCheckBox ckInDegree = new JCheckBox("In Degree Range");
		GridBagConstraints gbc_ckInDegree = new GridBagConstraints();
		gbc_ckInDegree.anchor = GridBagConstraints.WEST;
		gbc_ckInDegree.gridx = 0;
		gbc_ckInDegree.gridy = 2;
		panel_1.add(ckInDegree, gbc_ckInDegree);
		
		rangeSliderInDegree = new FilterRangeSlider(0,250);
		rangeSliderInDegree.setVisible(false);
		GridBagConstraints gbc_rangeSliderInDegree = new GridBagConstraints();
		gbc_rangeSliderInDegree.fill = GridBagConstraints.BOTH;
		gbc_rangeSliderInDegree.gridx = 0;
		gbc_rangeSliderInDegree.gridy = 3;
		panel_1.add(rangeSliderInDegree, gbc_rangeSliderInDegree);

		JCheckBox ckEdge = new JCheckBox("Edge Weight Range");
		GridBagConstraints gbc_ckEdge = new GridBagConstraints();
		gbc_ckEdge.anchor = GridBagConstraints.WEST;
		gbc_ckEdge.gridx = 0;
		gbc_ckEdge.gridy = 4;
		panel_1.add(ckEdge, gbc_ckEdge);
		
		rangeSliderEdge = new FilterDecimalSlider(0,1000);
		rangeSliderEdge.setVisible(false);
		GridBagConstraints gbc_rangeSliderEdge = new GridBagConstraints();
		gbc_rangeSliderEdge.fill = GridBagConstraints.BOTH;
		gbc_rangeSliderEdge.insets = new Insets(0, 0, 5, 0);
		gbc_rangeSliderEdge.gridx = 0;
		gbc_rangeSliderEdge.gridy = 5;
		panel_1.add(rangeSliderEdge, gbc_rangeSliderEdge);

		apply=new ApplyEvent(ckDegree,rangeSliderDegree.getRangeSlider(),ckInDegree,rangeSliderInDegree.getRangeSlider(),ckEdge,rangeSliderEdge.getRangeSlider());
		
		JButton btnApplica = new JButton("Applica >");
		GridBagConstraints gbc_btnApplica = new GridBagConstraints();
		gbc_btnApplica.anchor = GridBagConstraints.EAST;
		gbc_btnApplica.insets = new Insets(0, 0, 5, 0);
		gbc_btnApplica.gridx = 0;
		gbc_btnApplica.gridy = 6;
		panel_1.add(btnApplica, gbc_btnApplica);
		btnApplica.addActionListener(apply);
		

		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Info Grafo Filtrato", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 7;
		panel_1.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 26, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		panel.setVisible(false);
		JLabel lblNodi = new JLabel("Nodi:");
		GridBagConstraints gbc_lblNodi = new GridBagConstraints();
		gbc_lblNodi.insets = new Insets(0, 0, 5, 5);
		gbc_lblNodi.gridx = 0;
		gbc_lblNodi.gridy = 1;
		panel.add(lblNodi, gbc_lblNodi);
		
		lblvalNodi = new JLabel("0");
		GridBagConstraints gbc_lblvalNodi = new GridBagConstraints();
		gbc_lblvalNodi.insets = new Insets(0, 0, 5, 0);
		gbc_lblvalNodi.gridx = 1;
		gbc_lblvalNodi.gridy = 1;
		panel.add(lblvalNodi, gbc_lblvalNodi);
		
		JLabel lblArchi = new JLabel("Archi:");
		GridBagConstraints gbc_lblArchi = new GridBagConstraints();
		gbc_lblArchi.insets = new Insets(0, 0, 0, 5);
		gbc_lblArchi.gridx = 0;
		gbc_lblArchi.gridy = 2;
		panel.add(lblArchi, gbc_lblArchi);
		
		lblvalArchi = new JLabel("0");
		GridBagConstraints gbc_lblvalArchi = new GridBagConstraints();
		gbc_lblvalArchi.gridx = 1;
		gbc_lblvalArchi.gridy = 2;
		panel.add(lblvalArchi, gbc_lblvalArchi);
		panel.setVisible(false);
		apply.addObserver(this);
		
		
	}
	public ApplyEvent getApplyEvent(){
		return apply;
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg0.getClass()==ApplyEvent.class){
		GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
		DirectedGraph graph = graphModel.getDirectedGraphVisible();
		lblvalNodi.setText("" + graph.getNodeCount());
		lblvalArchi.setText("" + graph.getEdgeCount());
		panel.setVisible(true);
		}
		if(arg0.getClass()==CaricaEvent.class){
			rangeSliderDegree.setUpperValue(getMaxDegree());
			rangeSliderInDegree.setUpperValue(getMaxInDegree());
			rangeSliderDegree.setVisible(true);
			rangeSliderInDegree.setVisible(true);
			rangeSliderEdge.setVisible(true);

			}
	}
	private int getMaxDegree(){
		GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
		NodeIterable nodeIterable;
		Iterator <Node>ite;
		nodeIterable=graphModel.getDirectedGraph().getNodes();
		int max=0;
		int tmp=0;
		ite=nodeIterable.iterator();
		while(ite.hasNext()){
			tmp=graphModel.getDirectedGraph().getDegree(ite.next());
			if(max<tmp)
				max=tmp;
			}
		return max;
	
	}
	private int getMaxInDegree(){
		GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
		NodeIterable nodeIterable;
		nodeIterable=graphModel.getDirectedGraph().getNodes();
		Iterator <Node>ite;
		int max=0;
		int tmp=0;
		ite=nodeIterable.iterator();
		while(ite.hasNext()){
			tmp=graphModel.getDirectedGraph().getInDegree(ite.next());
			if(max<tmp)
				max=tmp;
			}
		return max;
	}

}
