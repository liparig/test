package prova;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.gephi.project.api.Workspace;

import processing.core.PApplet;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class Frame1 extends JFrame implements FrameUI{

	private JPanel contentPane;
	view view=null;
	/**
	 * Launch the application.
	*/
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame1 frame = new Frame1();
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
	public Frame1() {
	
	Panelgui panelgui = new Panelgui();
	getContentPane().add(panelgui, BorderLayout.EAST);
	view = new view();
	getContentPane().add(view, BorderLayout.CENTER);
	pack();

	}
}
