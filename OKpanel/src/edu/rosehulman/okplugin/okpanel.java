package edu.rosehulman.okplugin;

import javax.swing.JButton;
import javax.swing.JPanel;

import edu.rosehulman.mjcommons.Message;
import edu.rosehulman.mjcommons.mjPanel;

public class okpanel extends mjPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9207998522182390430L;

	public okpanel() {
		JButton ok = new JButton("OK");
		this.add(ok);
	}

	@Override
	public void stop() {

	}

	@Override
	public Message getMessage() {
		return null;
	}

	@Override
	public void receiveMessage(Message m) {
	}

}