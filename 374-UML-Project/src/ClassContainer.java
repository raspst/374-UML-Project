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
}
