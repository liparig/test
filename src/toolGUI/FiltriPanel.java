package toolGUI;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JSlider;
import java.awt.GridBagConstraints;
import javax.swing.JCheckBox;
import java.awt.Insets;
import javax.swing.JButton;

public class FiltriPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public FiltriPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 36, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JCheckBox ckDegree = new JCheckBox("Degree Range");
		GridBagConstraints gbc_ckDegree = new GridBagConstraints();
		gbc_ckDegree.anchor = GridBagConstraints.WEST;
		gbc_ckDegree.insets = new Insets(0, 0, 5, 0);
		gbc_ckDegree.gridx = 0;
		gbc_ckDegree.gridy = 0;
		add(ckDegree, gbc_ckDegree);
		
		JSlider slider = new JSlider();
		GridBagConstraints gbc_slider = new GridBagConstraints();
		gbc_slider.insets = new Insets(0, 0, 5, 0);
		gbc_slider.gridx = 0;
		gbc_slider.gridy = 1;
		add(slider, gbc_slider);
		
		JCheckBox ckInDegree = new JCheckBox("In Degree Range");
		GridBagConstraints gbc_ckInDegree = new GridBagConstraints();
		gbc_ckInDegree.anchor = GridBagConstraints.WEST;
		gbc_ckInDegree.insets = new Insets(0, 0, 5, 0);
		gbc_ckInDegree.gridx = 0;
		gbc_ckInDegree.gridy = 2;
		add(ckInDegree, gbc_ckInDegree);
		
		JSlider slider_1 = new JSlider();
		GridBagConstraints gbc_slider_1 = new GridBagConstraints();
		gbc_slider_1.insets = new Insets(0, 0, 5, 0);
		gbc_slider_1.gridx = 0;
		gbc_slider_1.gridy = 3;
		add(slider_1, gbc_slider_1);
		
		JCheckBox ckEdge = new JCheckBox("Edge Weight Range");
		GridBagConstraints gbc_ckEdge = new GridBagConstraints();
		gbc_ckEdge.anchor = GridBagConstraints.WEST;
		gbc_ckEdge.insets = new Insets(0, 0, 5, 0);
		gbc_ckEdge.gridx = 0;
		gbc_ckEdge.gridy = 4;
		add(ckEdge, gbc_ckEdge);
		
		JSlider slider_2 = new JSlider();
		GridBagConstraints gbc_slider_2 = new GridBagConstraints();
		gbc_slider_2.insets = new Insets(0, 0, 5, 0);
		gbc_slider_2.gridx = 0;
		gbc_slider_2.gridy = 5;
		add(slider_2, gbc_slider_2);
		
		JButton btnApplica = new JButton("Applica >");
		GridBagConstraints gbc_btnApplica = new GridBagConstraints();
		gbc_btnApplica.gridx = 0;
		gbc_btnApplica.gridy = 6;
		add(btnApplica, gbc_btnApplica);

	}

}
