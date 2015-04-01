import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

import javax.swing.JPanel;


public class ExecutionPanel extends JPanel {
	private String selectedPlugin;
	private PluginFrame parentFrame;
	private HashMap<String, JPanel> map = new HashMap<String, JPanel>();
	public ExecutionPanel(PluginFrame parent){
		this.setBackground(new Color(0.5f, 0.5f, 0.5f));
		this.setPreferredSize(new Dimension(400, 400));
		this.parentFrame = parent;
	}
	
	public JPanel instantiatePlugin(){
		for(ConfigItem item : parentFrame.config){
			if(item.jarName == this.selectedPlugin){
				try{
				File f = new File(".//Plugins//"+item.jarName);
				URLClassLoader cl = new URLClassLoader(new URL[] {
						f.toURI().toURL(), null });
				Class<JPanel> clazz = (Class<JPanel>) cl
						.loadClass(item.packageName + "."
								+ item.className);
				return clazz.newInstance();
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public void start() {
		if(!map.containsKey(this.selectedPlugin)){
			map.put(this.selectedPlugin, instantiatePlugin());
		}
		this.add(map.get(this.selectedPlugin));
	}

	public void stop() {
		JPanel removedPanel;
		removedPanel = map.remove(this.selectedPlugin);
		
		this.remove(removedPanel);
		removedPanel = null;
	}

	public void setSelectedPlugin(String jarName) {
		this.selectedPlugin = jarName;
	}

	
	
	
}
