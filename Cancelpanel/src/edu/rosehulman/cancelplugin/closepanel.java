package edu.rosehulman.cancelplugin;

import javax.swing.JButton;
import javax.swing.JPanel;

import edu.rosehulman.mjcommons.Message;
import edu.rosehulman.mjcommons.mjPanel;

public class closepanel extends mjPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8388723086373363373L;

	public closepanel() {
		JButton close = new JButton("Close");
		this.add(close);
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
