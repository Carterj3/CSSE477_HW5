package edu.rosehulman.countplugin;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import edu.rosehulman.mjcommons.Message;
import edu.rosehulman.mjcommons.mjPanel;

public class countpanel extends mjPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5812337455326905221L;
	private JButton count;
	private Thread t;
	private ArrayList<String> messages = new ArrayList<String>();

	public countpanel() {
		count = new JButton("Count - 0");
		this.add(count);

		t = new Thread(new Runnable() {

			@Override
			public void run() {
				int counter = 0;
				for (;;) {
					count.setText("Count - " + counter);
					messages.add("Count - " + counter);
					counter++;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				}

			}
		});

		t.start();
	}

	@Override
	public void stop() {
		if (t != null) {
			t.stop();
			t = null;
		}
	}

	@Override
	public Message getMessage() {
		if (messages.size() > 0) {

			return new Message("countpanel.jar", "System", messages.remove(0));
		}
		return null;
	}

	@Override
	public void receiveMessage(Message m) {

	}
}
