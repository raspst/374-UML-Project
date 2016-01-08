import java.util.ArrayList;
import java.util.HashMap;

public class ClassContainer {
	private HashMap<String, JClass> classes;
	private HashMap<String, String> superclasses;
	private HashMap<String, ArrayList<String>> interfaces;
	private HashMap<String, JInterface> interfacelist;

	private JClass activeClass;
	
	public ClassContainer() {
		this.classes = new HashMap<String, JClass>();
		this.superclasses = new HashMap<String, String>();
		this.interfaces = new HashMap<String, ArrayList<String>>();
		this.interfacelist = new HashMap<String, JInterface>();
	}
	
	public void setActiveClass(JClass c){
		activeClass=c;
	}

	public JClass getActiveClass(){
		return activeClass;
	}
	public JClass getClass(String name) 
	{
		String[] packages = name.split("\\.");
		name = packages[packages.length-1];
		packages = name.split("/");
		name = packages[packages.length-1];
		//if(packages.length==1&&name!="void"&&name!="int"&&name!="float"&&name!="double"&&name!="boolean"&&name!="short"&&name!="byte"&&name!="char"&&name!="long")name = name.substring(1);
		JClass theclass = classes.get(name);
		if (theclass == null) {
			theclass = new JClass(name);
			classes.put(name, theclass);
		}
		return theclass;
	}

	public JInterface getInterface(String name) {
		JInterface theInterface = interfacelist.get(name);
		if(theInterface == null){
			theInterface = new JInterface(name);
			interfacelist.put(name, theInterface);
		}
		return theInterface;
	}
	
	void addField(String fieldName, String className, int access, String type) {
		JClass currentClass = null;
		JClass currentType = null;

		currentClass = getClass(className);
		currentType = getClass(type);

		currentClass.addField(new JField(fieldName, access, currentType));
	}

	void addMethod(String methodName, String className, int access, JClass returnType, ArrayList<String> parameters) {
		JClass currentClass = null;
		ArrayList<JClass> new_parameters = null;
		currentClass = getClass(className);
		for (String s : parameters) {
			new_parameters.add(getClass(s));
		}
		currentClass.addMethod(new JMethod(methodName, access, returnType, new_parameters));
	}
}
