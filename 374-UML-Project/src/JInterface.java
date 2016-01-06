import java.util.ArrayList;

public class JInterface extends JType {
	private ArrayList<JInterface> interfaces;
	private ArrayList<JField> constants;
	private ArrayList<JMethod> methods;
	
	public JInterface(String name) {
		super(name);
		this.constants = new ArrayList<JField>();
		this.methods = new ArrayList<JMethod>();
		interfaces = new ArrayList<JInterface>();
	}
	
	public void addInterface(JInterface i){
		interfaces.add(i);
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
	
	public ArrayList<JMethod> getMethods(){
		return methods;
	}
	
	public ArrayList<JField> getFields() {
		return this.constants;
	}
	
	public void addMethod(JMethod method) {
		this.methods.add(method);
	}
	
	public void addField(JField field) {
		this.constants.add(field);
	}
	
	public void setDependencies(JClass superclass, ArrayList<JInterface> interfaces){
		this.interfaces = interfaces;
	}
	
	
}
