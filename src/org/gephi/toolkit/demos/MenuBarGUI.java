package org.gephi.toolkit.demos;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;


public class MenuBarGUI extends JPanel implements Observer {
	private CaricaEvent carica;
	private JLabel lblFile = new JLabel("");
        private MostraEvent mostra;
	/**
	 * Create the panel.
	 */
	public MenuBarGUI() {
		lblFile.setVisible(false);
		carica=new CaricaEvent();
		carica.addObserver(this);
                mostra=new MostraEvent();
		SalvaEvent salva=new SalvaEvent();
                
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{55, 83, 0, 0};
		gridBagLayout.rowHeights = new int[]{29, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		gbc_btnSalva.insets = new Insets(0, 0, 0, 5);
		gbc_btnSalva.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSalva.anchor = GridBagConstraints.NORTH;
		gbc_btnSalva.gridx = 1;
		gbc_btnSalva.gridy = 0;
		add(btnSalva, gbc_btnSalva);
                
                JButton btnMostra = new JButton("Etichette");
		btnMostra.addActionListener(mostra);
		GridBagConstraints gbc_btnMostra = new GridBagConstraints();
		gbc_btnMostra.insets = new Insets(0, 0, 0, 5);
		gbc_btnMostra.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnMostra.anchor = GridBagConstraints.NORTH;
		gbc_btnMostra.gridx = 2;
		gbc_btnMostra.gridy = 0;
		add(btnMostra, gbc_btnMostra);
                
		GridBagConstraints gbc_lblFile = new GridBagConstraints();
		gbc_lblFile.gridx = 3;
		gbc_lblFile.gridy = 0;
		add(lblFile, gbc_lblFile);
		}
	public CaricaEvent getCaricaEvent(){
		return carica;
	}
        public MostraEvent getMostraEvent(){
		return mostra;
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		lblFile.setText(" Nome File: "+arg1);
		lblFile.setVisible(true);
	}

}
