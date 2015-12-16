import java.util.ArrayList;
import java.util.HashMap;

public class ClassContainer {
	private ArrayList<JClass> classes;
	private HashMap<String, String> superclasses;
	private HashMap<String, ArrayList<String>> interfaces;
	
	public ClassContainer() {
		this.classes = new ArrayList<JClass>();
		this.superclasses = new HashMap<String, String>();
		this.interfaces = new HashMap<String, ArrayList<String>>();
	}
	
	public void addClass(JClass a_class) {
		this.classes.add(a_class);
	}
	
	public JClass getClass(String name) {
		for(JClass c: this.classes) {
			if(c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}
	
	void addField(String fieldName, String className, String access, String type) {
		JClass currentClass = null;
		JClass currentType = null;
		for(JClass c: this.classes) {
			if(c.getName().equals(className)) {
				currentClass = c;
				break;
			}
		}
		for(JClass j: this.classes) {
			if(j.getName().equals(type)) {
				currentType = j;
			}
		}
		currentClass.addField(new JField(fieldName, access, currentType));
	}
	
	void addMethod(String methodName, String className, String access, ArrayList<String> parameters) {
		JClass currentClass = null;
		ArrayList<JClass> new_parameters = null;
		for(JClass c: this.classes) {
			if(c.getName().equals(className)) {
				currentClass = c;
				break;
			}
		}
		for(JClass j: this.classes) {
			for(String s: parameters) {
				if(j.getName().equals(s)) {
					new_parameters.add(j);
				}
			}
		}
		currentClass.addMethod(new JMethod(methodName, access, new_parameters));
	}
}
