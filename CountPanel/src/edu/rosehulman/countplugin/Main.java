package edu.rosehulman.countplugin;

import javax.swing.JFrame;


public class Main {

	public static void main(String[] args) {
		JFrame frame = new JFrame();

		frame.setTitle("Marshall and Jeff's Plugin Program");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		frame.add(new countpanel());
		
		frame.setSize(400, 400);
		frame.setVisible(true);
	}

}
