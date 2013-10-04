package toolGUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import processing.core.PApplet;


import java.util.Observable;
import java.util.Observer;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class DesktopGUI extends JFrame implements Observer {

	private JPanel contentPane;
	private MenuBarGUI menuBarGUI;
	private OverviewGUI overviewGUI=null;
	private PApplet applet=null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DesktopGUI frame = new DesktopGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	EastMenuGUI eastMenuGUI;
	public DesktopGUI() {
		setTitle("ToolGUI");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 359, 389);
		contentPane = new JPanel();
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{407, 190, 0};
		gbl_contentPane.rowHeights = new int[]{30, 500, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		menuBarGUI = new MenuBarGUI();
		
		GridBagConstraints gbc_menuBarGUI = new GridBagConstraints();
		gbc_menuBarGUI.insets = new Insets(0, 0, 5, 0);
		gbc_menuBarGUI.gridwidth = 2;
		gbc_menuBarGUI.anchor = GridBagConstraints.NORTHWEST;
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
		this.pack();

		menuBarGUI.getCaricaEvent().addObserver(eastMenuGUI.getInfoGUI());
		menuBarGUI.getCaricaEvent().addObserver(this);
		
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
		}
		this.pack();
	}
}


