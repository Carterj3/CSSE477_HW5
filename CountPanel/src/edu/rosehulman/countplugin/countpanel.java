package edu.rosehulman.countplugin;

import javax.swing.JButton;
import javax.swing.JPanel;

public class countpanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5812337455326905221L;
	private JButton count;

	public countpanel() {
		count = new JButton("Count - 0");
		this.add(count);

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				int counter = 0;
				for (;;) {
					count.setText("Count - " + counter);
					System.out.println("Count - " + counter);
					counter++;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				}

			}
		});

		t.start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
	}
}
