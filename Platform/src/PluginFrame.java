import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PluginFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1475586549623619649L;

	private ListPanel listWindow;
	private ExecutionPanel executionWindow;

	protected ArrayList<ConfigItem> config = new ArrayList<ConfigItem>();

	private TextArea statusTextArea;

	public PluginFrame() {
		this.setTitle("Marshall and Jeff's Plugin Program");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(500, 500));
		this.setLayout(new BorderLayout());

		// Plugin list
		listWindow = new ListPanel(this);
		this.add(listWindow, BorderLayout.WEST);

		// Execution window
		executionWindow = new ExecutionPanel(this);
		this.add(executionWindow, BorderLayout.CENTER);

		// Start stop buttons
		JPanel startStop = new JPanel();
		startStop.setBackground(new Color(0.8f, 0.3f, 0.3f));

		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				executionWindow.start();
			}
		});

		JButton stopButton = new JButton("Stop");
		stopButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				executionWindow.stop();
			}
		});

		startStop.add(startButton);
		startStop.add(stopButton);

		statusTextArea = new TextArea();
		statusTextArea.setPreferredSize(new Dimension(480, 60));
		JScrollPane scrollPane = new JScrollPane(statusTextArea);

		startStop.add(scrollPane);
		startStop.setPreferredSize(new Dimension(500, 100));

		this.add(startStop, BorderLayout.SOUTH);

		this.pack();

	}

	public void setSelectedPlugin(String jarName) {
		executionWindow.setSelectedPlugin(jarName);
	}

	public void updateDisplay() {
		this.repaint();
		this.setVisible(true);
		this.pack();
	}

	public void appendStatusMessage(String message) {
		this.statusTextArea.append(message+"\n");
	}

	public String getMessageName() {
		return "System";
	}

	public void stop(String filename) {
		executionWindow.stop(filename);
		
	}

	public void pluginStopped(String plugin) {
		listWindow.pluginStopped(plugin);
	}

	public void pluginStarted(String plugin) {
		listWindow.pluginStarted(plugin);
		
	}
}
