package uml.types;

import java.util.ArrayList;
import java.util.HashSet;

public class JInterface extends JType{

	private ArrayList<JMethod> methods = new ArrayList<JMethod>();
	private ArrayList<JClass> interfaces = new ArrayList<JClass>();
	protected HashSet<JClass> associates = new HashSet<JClass>();
	protected ArrayList<JClass> descendants = new ArrayList<JClass>();
	public JInterface(String name) {
		super(name);
	}
	
	public ArrayList<JMethod> getMethods() {
		return methods;
	}

	public void addMethod(JMethod method) {
		for(JField f:method.getLocalVars())
			associates.add(f.getType());
		this.methods.add(method);
	}
	
	public JMethod getMethod(String name, String desc) {
		for (JMethod m : methods) {
			if (m.getName().equals(name) && desc.equals(m.getDesc())) {
				return m;
			}
		}
		return null;
	}
	
	public void addInterface(JClass i) {
		interfaces.add(i);
		i.descendants.add((JClass)this);
	}

	public ArrayList<JClass> getInterfaces() {
		return this.interfaces;
	}
	public ArrayList<JClass> getDescendants(){
		return descendants;
	}
}
