package toolGUI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;

import org.gephi.graph.api.DirectedGraph;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.openide.util.Lookup;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.util.Observable;
import java.util.Observer;

public class InfoGUI extends JPanel implements Observer{
	private JLabel lblnodi;
	private JLabel lblarchi;

	/**
	 * Create the panel.
	 */
	public InfoGUI() {
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{116, 69, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 16, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblInfo = new JLabel("Informazioni");
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblInfo = new GridBagConstraints();
		gbc_lblInfo.gridwidth = 2;
		gbc_lblInfo.insets = new Insets(0, 0, 5, 5);
		gbc_lblInfo.gridx = 0;
		gbc_lblInfo.gridy = 0;
		add(lblInfo, gbc_lblInfo);
		
		JLabel lblNodi = new JLabel("Nodi:");
		GridBagConstraints gbc_lblNodi = new GridBagConstraints();
		gbc_lblNodi.insets = new Insets(0, 0, 5, 5);
		gbc_lblNodi.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNodi.gridx = 0;
		gbc_lblNodi.gridy = 1;
		add(lblNodi, gbc_lblNodi);
		
		lblnodi = new JLabel("0");
		GridBagConstraints gbc_lblnodi = new GridBagConstraints();
		gbc_lblnodi.insets = new Insets(0, 0, 5, 0);
		gbc_lblnodi.gridx = 1;
		gbc_lblnodi.gridy = 1;
		add(lblnodi, gbc_lblnodi);
		
		JLabel lblArchi = new JLabel("Archi:");
		GridBagConstraints gbc_lblArchi = new GridBagConstraints();
		gbc_lblArchi.anchor = GridBagConstraints.WEST;
		gbc_lblArchi.insets = new Insets(0, 0, 5, 5);
		gbc_lblArchi.gridx = 0;
		gbc_lblArchi.gridy = 2;
		add(lblArchi, gbc_lblArchi);
		
		lblarchi = new JLabel("0");
		GridBagConstraints gbc_lblarchi = new GridBagConstraints();
		gbc_lblarchi.insets = new Insets(0, 0, 5, 0);
		gbc_lblarchi.gridx = 1;
		gbc_lblarchi.gridy = 2;
		add(lblarchi, gbc_lblarchi);
	}

	@Override
	public void update(Observable o, Object arg) {
		GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
		//See if graph is well imported
        DirectedGraph graph = graphModel.getDirectedGraph();
        lblarchi.setText(""+ graph.getEdgeCount());
        lblnodi.setText(""+ graph.getNodeCount());
		
	}
}
