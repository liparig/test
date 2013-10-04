package toolGUI;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;


public class MenuBarGUI extends JPanel {
	private CaricaEvent carica;
	/**
	 * Create the panel.
	 */
	public MenuBarGUI() {
		carica=new CaricaEvent();
		SalvaEvent salva=new SalvaEvent();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{55, 83, 0};
		gridBagLayout.rowHeights = new int[]{29, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JButton btnCarica = new JButton("Carica");
		btnCarica.addActionListener(carica);
		btnCarica.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_btnCarica = new GridBagConstraints();
		gbc_btnCarica.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCarica.anchor = GridBagConstraints.NORTH;
		gbc_btnCarica.insets = new Insets(0, 0, 0, 5);
		gbc_btnCarica.gridx = 0;
		gbc_btnCarica.gridy = 0;
		add(btnCarica, gbc_btnCarica);
		
		JButton btnSalva = new JButton("Salva");
		btnSalva.addActionListener(salva);
		GridBagConstraints gbc_btnSalva = new GridBagConstraints();
		gbc_btnSalva.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSalva.anchor = GridBagConstraints.NORTH;
		gbc_btnSalva.gridx = 1;
		gbc_btnSalva.gridy = 0;
		add(btnSalva, gbc_btnSalva);
		}
	public CaricaEvent getCaricaEvent(){
		return carica;
	}

}
