import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JPanel;

import static java.nio.file.StandardWatchEventKinds.*;

public class ListPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6709953027407085389L;
	private ArrayList<Path> plugins;
	private static String pluginConfigPath = ".//Plugins//Plugins.config";
	private HashMap<String, JButton> JButtonMap = new HashMap<String, JButton>();
	private PluginFrame parentFrame;

	ListPanel(PluginFrame parent) {

		this.parentFrame = parent;

		this.setBackground(new Color(0.3f, 0.3f, 0.8f));
		this.setPreferredSize(new Dimension(100, 400));
		this.setLayout(new FlowLayout());

		parentFrame.config = new ArrayList<ConfigItem>();

		loadConfig();
		scanForPlugins();
		try {
			WatchService watcher = FileSystems.getDefault().newWatchService();
			Path dir = Paths.get(".//Plugins");
			WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE);
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					for (;;) {

						// wait for key to be signaled
						WatchKey key;
						try {
							key = watcher.take();
						} catch (InterruptedException x) {
							return;
						}

						for (WatchEvent<?> event : key.pollEvents()) {
							WatchEvent.Kind<?> kind = event.kind();
							WatchEvent<Path> ev = (WatchEvent<Path>) event;
							Path filename = ev.context();
							System.out.println(filename);
							if (kind == ENTRY_DELETE) {
								parentFrame.stop(filename.toString());

								JButton removedButton = JButtonMap
										.remove(filename.toString());
								if (removedButton != null) {
									ListPanel.this.remove(removedButton);
								}

								updateDisplay();
							}

							// This key is registered only
							// for ENTRY_CREATE events,
							// but an OVERFLOW event can
							// occur regardless if events
							// are lost or discarded.
							if (kind == OVERFLOW) {
								continue;
							}

							if (kind == ENTRY_CREATE) {
								scanForPlugins();
								updateDisplay();
							}

						}

						// Reset the key -- this step is critical if you want to
						// receive further watch events. If the key is no longer
						// valid,
						// the directory is inaccessible so exit the loop.
						boolean valid = key.reset();
						if (!valid) {
							break;
						}
					}

				}
			});
			t.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void updateDisplay() {
		this.repaint();
		this.setVisible(true);
		parentFrame.updateDisplay();

	}

	private void loadConfig() {
		File configFile = new File(pluginConfigPath);
		try {
			BufferedReader br = new BufferedReader(new FileReader(configFile));
			for (String line; (line = br.readLine()) != null;) {
				String[] splits = line.split(",");
				parentFrame.config.add(new ConfigItem(splits[0], splits[1],
						splits[2], splits[3]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void scanForPlugins() {
		ArrayList<Path> pluginFolderContents = contents(".//Plugins");
		this.plugins = new ArrayList<Path>();
		for (Path file : pluginFolderContents) {
			String filename = file.getFileName().toString();
			if (filename.endsWith(".jar")) {
				for (ConfigItem item : parentFrame.config) {
					if (item.jarName.equals(filename)) {
						try {

							if (!JButtonMap.containsKey(filename)) {
								JButton temp = new JButton(filename);
								temp.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										parentFrame
												.setSelectedPlugin(item.jarName);
										parentFrame
												.appendStatusMessage("Selecting "
														+ item.jarName
														+ " | "
														+ filename);
									}
								});
								temp.setBackground(Color.RED);
								this.add(temp);
								plugins.add(file);
								JButtonMap.put(filename, temp);
							}

						} catch (Exception e) {
							parentFrame.appendStatusMessage("ConfigItem : "
									+ item.toString());
							e.printStackTrace();
						}
					}
				}

			}
		}
	}

	public ArrayList<Path> contents(String directory) {
		// http://www.adam-bien.com/roller/abien/entry/listing_directory_contents_with_jdk
		ArrayList<Path> files = new ArrayList<Path>();
		try (DirectoryStream<Path> directoryStream = Files
				.newDirectoryStream(Paths.get(directory))) {
			for (Path path : directoryStream) {
				files.add(path);

			}
		} catch (IOException ex) {
		}

		return files;
	}

	public void pluginStopped(String plugin) {
		JButton pluginButton = JButtonMap.get(plugin);
		if (pluginButton != null) {
			pluginButton.setBackground(Color.RED);
		}
	}

	public void pluginStarted(String plugin) {
		JButton pluginButton = JButtonMap.get(plugin);
		if (pluginButton != null) {
			pluginButton.setBackground(Color.GREEN);
		}
	}

}
