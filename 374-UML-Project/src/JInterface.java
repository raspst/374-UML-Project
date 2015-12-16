import java.util.ArrayList;

public class JInterface extends JType {
	private JClass superclass;
	private ArrayList<JInterface> interfaces;
	private ArrayList<JField> constants;
	private ArrayList<JMethod> methods;
	
	public JInterface(String name, String access, JClass superclass, 
			ArrayList<JInterface> interfaces, ArrayList<JField> fields,
			ArrayList<JMethod> methods) {
		super(name, access);
		this.superclass= superclass;
		this.interfaces = interfaces;
		this.constants = new ArrayList<JField>();
		this.methods = new ArrayList<JMethod>();
	}
	public JClass getParent() {
		return this.superclass;
	}
	
	public ArrayList<JInterface> getInterfaces() {
		return this.interfaces;
	}
	
	public JField getField(String name) {
		for(JField f: this.constants){
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
	
	public void addMethod(JMethod method) {
		this.methods.add(method);
	}
	
	public void addField(JField field) {
		this.constants.add(field);
	}
}
