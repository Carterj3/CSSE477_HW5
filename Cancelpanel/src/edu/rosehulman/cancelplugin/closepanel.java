package edu.rosehulman.cancelplugin;

import javax.swing.JButton;
import javax.swing.JPanel;

public class closepanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8388723086373363373L;

	public closepanel() {
		JButton close = new JButton("Close");
		this.add(close);
	}
}
