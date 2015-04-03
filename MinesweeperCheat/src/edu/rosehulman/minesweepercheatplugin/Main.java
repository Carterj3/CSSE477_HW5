package edu.rosehulman.minesweepercheatplugin;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.add(new CheatPanel());
		frame.setVisible(true);
		frame.pack();
	}

}
