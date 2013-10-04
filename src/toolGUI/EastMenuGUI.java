package toolGUI;

import javax.swing.JPanel;

import java.awt.BorderLayout;

public class EastMenuGUI extends JPanel {
	private InfoGUI infoGUI;
	/**
	 * Create the panel.
	 */
	public EastMenuGUI() {
		setLayout(new BorderLayout(0, 0));
		
		infoGUI = new InfoGUI();
		add(infoGUI, BorderLayout.NORTH);
		
		FiltriPanel filtriPanel = new FiltriPanel();
		add(filtriPanel, BorderLayout.CENTER);

	}
	public InfoGUI getInfoGUI(){
		return infoGUI;
	}
	
}
