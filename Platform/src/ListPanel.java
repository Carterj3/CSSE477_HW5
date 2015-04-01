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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ListPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6709953027407085389L;
	private ArrayList<Path> plugins;
	private static String pluginConfigPath = ".//Plugins//Plugins.config";

	private PluginFrame parentFrame;

	ListPanel(PluginFrame parent) {
		
		this.parentFrame = parent;

		this.setBackground(new Color(0.3f, 0.3f, 0.8f));
		this.setPreferredSize(new Dimension(100, 400));
		this.setLayout(new FlowLayout());
		
		parentFrame.config = new ArrayList<ConfigItem>();

		loadConfig();
		scanForPlugins();
	}

	private void loadConfig() {
		File configFile = new File(pluginConfigPath);
		try {
			BufferedReader br = new BufferedReader(new FileReader(configFile));
			for (String line; (line = br.readLine()) != null;) {
				String[] splits = line.split(",");
				parentFrame.config.add(new ConfigItem(splits[0], splits[1], splits[2],
						splits[3]));
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
							
							JButton temp = new JButton(filename);
							temp.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e) {
									parentFrame.setSelectedPlugin(item.jarName);
									System.out.println("Selecting " + item.jarName +" | "+filename);
								}
							});
							this.add(temp);
							plugins.add(file);

						} catch (Exception e) {
							System.out.println("ConfigItem : "
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

}
