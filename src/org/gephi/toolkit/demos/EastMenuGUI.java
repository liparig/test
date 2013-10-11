package org.gephi.toolkit.demos;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import javax.swing.border.EmptyBorder;

public class EastMenuGUI extends JPanel {
	private InfoGUI infoGUI=null;
	FiltriPanel filtriPanel=null;
	/**
	 * Create the panel.
	 */
	public EastMenuGUI() {
		setLayout(new BorderLayout(0, 0));
		
		infoGUI = new InfoGUI();
		infoGUI.setBorder(new EmptyBorder(0, 0, 10, 0));
		add(infoGUI, BorderLayout.NORTH);
		
		filtriPanel = new FiltriPanel();
		filtriPanel.setBorder(new EmptyBorder(0, 0, 15, 0));
		add(filtriPanel, BorderLayout.CENTER);

	}
	public InfoGUI getInfoGUI(){
		return infoGUI;
	}
	public FiltriPanel getFiltriPanel(){
		return filtriPanel;
	}
	
}
