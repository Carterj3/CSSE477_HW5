public class ConfigItem {
	public String jarName;
	public String className;
	public String packageName;
	public String methodName;

	public ConfigItem(String jarName, String packageName, String className,
			String methodName) {
		
		this.jarName = jarName;
		this.className = className;
		this.packageName = packageName;
		this.methodName = methodName;
	}

	@Override
	public String toString() {
		return jarName + " " + packageName + " " + className + " " + methodName;
	}
}
