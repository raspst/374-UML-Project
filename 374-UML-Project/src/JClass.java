import java.util.ArrayList;

public class JClass {
	private String name;
	private ArrayList<JField> fields;
	private ArrayList<JMethod> methods;
	private JClass superclass;
	private ArrayList<JInterface> interfaces;
	
	public JClass(String name, ArrayList<JField> fields, 
			ArrayList<JMethod> methods, JClass superclass, 
			ArrayList<JInterface> interfaces) {
		this.name = name;
		this.fields = fields;
		this.methods = methods;
		this.superclass = superclass;
		this.interfaces = interfaces;
	}
	
	public String getName() {
		return this.name;
	}
	
	public JClass getParent() {
		return superclass;
	}
	
	public JField getField(String name) {
		for(JField f: fields) {
			if(f.getName().equals(name)) {
				return f;
			}
		}
		return null;
	}
	
	public JMethod getMethod(String name) {
		for(JMethod m: methods) {
			if(m.getName().equals(name)) {
				return m;
			}
		}
		return null;
	}
}
