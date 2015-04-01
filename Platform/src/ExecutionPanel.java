import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

import javax.swing.JPanel;

import edu.rosehulman.mjcommons.Message;
import edu.rosehulman.mjcommons.mjPanel;

public class ExecutionPanel extends JPanel {
	private String selectedPlugin;
	private PluginFrame parentFrame;
	private HashMap<String, mjPanel> map = new HashMap<String, mjPanel>();
	private HashMap<String, Thread> messageMap = new HashMap<String, Thread>();

	public ExecutionPanel(PluginFrame parent) {
		this.setBackground(new Color(0.5f, 0.5f, 0.5f));
		this.setPreferredSize(new Dimension(400, 400));
		this.parentFrame = parent;
	}

	public mjPanel instantiatePlugin() {
		for (ConfigItem item : parentFrame.config) {
			if (item.jarName == this.selectedPlugin) {
				try {
					File f = new File(".//Plugins//" + item.jarName);
					URLClassLoader cl = new URLClassLoader(new URL[] {
							f.toURI().toURL(), null });
					Class<mjPanel> clazz = (Class<mjPanel>) cl
							.loadClass(item.packageName + "." + item.className);
					return clazz.newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public void start() {
		if (!map.containsKey(this.selectedPlugin)) {
			mjPanel plugin = instantiatePlugin();
			map.put(this.selectedPlugin, plugin);

			plugin.setVisible(true);
			plugin.repaint();

			startMonitor(map.get(this.selectedPlugin));
			this.add(map.get(this.selectedPlugin));
			
			parentFrame.pluginStarted(this.selectedPlugin);
		}

		this.updateDisplay();
	}

	private void startMonitor(mjPanel plugin) {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				for (;;) {
					Message recv = plugin.getMessage();
					if (recv == null) {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						continue;
					}

					if (recv.receiver == parentFrame.getMessageName()) {
						parentFrame.appendStatusMessage(recv.message);
					}

					sendMessage(recv);

				}
			}
		});

		t.start();
	}

	protected void sendMessage(Message recv) {
		for (String jars : map.keySet()) {
			if (jars == recv.receiver) {
				map.get(jars).receiveMessage(recv);
			}
		}
	}

	public void stop() {
		mjPanel removedPanel;
		removedPanel = map.remove(this.selectedPlugin);

		if (removedPanel != null) {
			this.remove(removedPanel);
			removedPanel.stop();
		}
		removedPanel = null;
		this.updateDisplay();
		
		parentFrame.pluginStopped(this.selectedPlugin);
	}

	private void updateDisplay() {
		this.setVisible(true);
		this.repaint();
		parentFrame.updateDisplay();
	}

	public void setSelectedPlugin(String jarName) {
		if (this.selectedPlugin != null) {
			mjPanel currentPanel = map.get(this.selectedPlugin);
			if (currentPanel != null) {
				this.remove(currentPanel);
			}
		}

		if (jarName != null) {
			mjPanel newPanel = map.get(jarName);
			if (newPanel != null) {
				this.add(newPanel);
			}
		}

		this.selectedPlugin = jarName;
		this.updateDisplay();
	}

	public void stop(String filename) {
		mjPanel removedpanel = map.remove(filename);
		if(removedpanel != null){
			this.remove(removedpanel);
			removedpanel.stop();
		}
		Thread t = messageMap.remove(filename);
		if(t != null){
			t.stop();
			t = null;
		}
		
	}

}
