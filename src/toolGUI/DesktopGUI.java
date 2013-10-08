package toolGUI;

import javax.swing.JFrame;
import javax.swing.JPanel;

import processing.core.PApplet;


import java.util.Observable;
import java.util.Observer;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class DesktopGUI extends JFrame implements Observer {

	private JPanel contentPane;
	private MenuBarGUI menuBarGUI;
	private OverviewGUI overviewGUI=null;
	private PApplet applet=null;

	/**
	 * Create the frame.
	 */
	EastMenuGUI eastMenuGUI;
	public DesktopGUI() {
		setTitle("ToolGUI");
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				if(applet!=null){
					applet.mousePressed();
				}
			}
		});
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();


		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{800, 190, 0};
		gbl_contentPane.rowHeights = new int[]{30, 600, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		menuBarGUI = new MenuBarGUI();
		
		GridBagConstraints gbc_menuBarGUI = new GridBagConstraints();
		gbc_menuBarGUI.insets = new Insets(0, 0, 5, 0);
		gbc_menuBarGUI.gridwidth = 2;
		gbc_menuBarGUI.anchor = GridBagConstraints.NORTH;
		gbc_menuBarGUI.fill = GridBagConstraints.HORIZONTAL;
		gbc_menuBarGUI.gridx = 0;
		gbc_menuBarGUI.gridy = 0;
		contentPane.add(menuBarGUI, gbc_menuBarGUI);
		
		eastMenuGUI = new EastMenuGUI();
		GridBagConstraints gbc_eastMenuGUI = new GridBagConstraints();
		gbc_eastMenuGUI.anchor = GridBagConstraints.NORTHEAST;
		gbc_eastMenuGUI.gridx = 1;
		gbc_eastMenuGUI.gridy = 1;
		contentPane.add(eastMenuGUI, gbc_eastMenuGUI);

		menuBarGUI.getCaricaEvent().addObserver(eastMenuGUI.getInfoGUI());
		menuBarGUI.getCaricaEvent().addObserver(eastMenuGUI.getFiltriPanel());

		menuBarGUI.getCaricaEvent().addObserver(this);
		eastMenuGUI.getFiltriPanel().getApplyEvent().addObserver(this);
		this.pack();
		
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		if(overviewGUI==null){
		overviewGUI=new OverviewGUI();
		applet=overviewGUI.getPApplet();
		GridBagConstraints gbc_applet = new GridBagConstraints();
		gbc_applet.anchor = GridBagConstraints.NORTHWEST;
		gbc_applet.fill = GridBagConstraints.BOTH;
		gbc_applet.gridx = 0;
		gbc_applet.gridy = 1;
		contentPane.add(applet,gbc_applet);
		menuBarGUI.getCaricaEvent().addObserver(overviewGUI);
		eastMenuGUI.getFiltriPanel().getApplyEvent().addObserver(overviewGUI);
		}
		applet.mousePressed();

		this.pack();
	}
}


