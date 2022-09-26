package client.ui.gui;

import javax.swing.*;

public class Uno extends JFrame {

	public MainPanel panel;

	public Uno() {
		this.panel = new MainPanel(this);
		this.getContentPane().add(this.panel);
		this.setTitle("Tres Client");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setSize(1280, 700);
		this.setVisible(true);
		this.pack();

		this.panel.start();
	}

	public MainPanel getPanel() {
		return panel;
	}
}
