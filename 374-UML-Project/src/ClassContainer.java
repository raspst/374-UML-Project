import java.util.HashMap;

public class ClassContainer {
	private HashMap<String, JClass> classes;
	private HashMap<String, JInterface> interfacelist;

	private JClass activeClass;

	public ClassContainer() {
		this.classes = new HashMap<String, JClass>();
		this.interfacelist = new HashMap<String, JInterface>();
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
		// if(packages.length==1&&name!="void"&&name!="int"&&name!="float"&&name!="double"&&name!="boolean"&&name!="short"&&name!="byte"&&name!="char"&&name!="long")name
		// = name.substring(1);
		JClass theclass = classes.get(name);
		if (theclass == null) {
			theclass = new JClass(name);
			classes.put(name, theclass);
		}
		return theclass;
	}

}
