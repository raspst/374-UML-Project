import java.util.HashMap;

public class ClassContainer {
	private HashMap<String, JClass> classes;

	private JClass activeClass;

	public ClassContainer() {
		this.classes = new HashMap<String, JClass>();
	}

	public void setActiveClass(JClass c) {
		activeClass = c;
	}

	public JClass getActiveClass() {
		return activeClass;
	}

	public JClass getClass(String name) {
		String[] packages = name.split("\\.");
		name = packages[packages.length - 1];
		packages = name.split("/");
		name = packages[packages.length - 1];
		JClass theclass = classes.get(name);
		if (theclass == null) {
			theclass = new JClass(name);
			classes.put(name, theclass);
		}
		return theclass;
	}

}
