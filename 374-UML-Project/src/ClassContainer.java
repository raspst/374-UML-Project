import java.util.ArrayList;
import java.util.HashMap;

public class ClassContainer {
	private HashMap<String, JClass> classes;
	private HashMap<String, String> superclasses;
	private HashMap<String, ArrayList<String>> interfaces;

	public ClassContainer() {
		this.classes = new HashMap<String, JClass>();
		this.superclasses = new HashMap<String, String>();
		this.interfaces = new HashMap<String, ArrayList<String>>();
	}

	public JClass getClass(String name) {
		JClass theclass = classes.get(name);
		if (theclass == null) {
			theclass = new JClass(name);
			classes.put(name, theclass);
		}
		return theclass;
	}

	void addField(String fieldName, String className, int access, String type) {
		JClass currentClass = null;
		JClass currentType = null;

		currentClass = getClass(className);
		currentType = getClass(type);

		currentClass.addField(new JField(fieldName, access, currentType));
	}

	void addMethod(String methodName, String className, String access, ArrayList<String> parameters) {
		JClass currentClass = null;
		ArrayList<JClass> new_parameters = null;
		currentClass = getClass(className);
		for (String s : parameters) {
			new_parameters.add(getClass(s));
		}
		currentClass.addMethod(new JMethod(methodName, access, new_parameters));
	}
}
