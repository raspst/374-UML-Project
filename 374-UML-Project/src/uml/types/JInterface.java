package uml.types;
import java.util.ArrayList;

public class JInterface extends JType {
	private ArrayList<JInterface> interfaces;
	private ArrayList<JMethod> methods;

	public JInterface(String name) {
		super(name);
		this.methods = new ArrayList<JMethod>();
		interfaces = new ArrayList<JInterface>();
	}

	public void addInterface(JInterface i) {
		interfaces.add(i);
	}

	public ArrayList<JInterface> getInterfaces() {
		return this.interfaces;
	}

	public JMethod getMethod(String name, String desc) {
		for (JMethod m : methods) {
			if (m.getName().equals(name) && desc.equals(m.getDesc())) {
				return m;
			}
		}
		return null;
	}

	public ArrayList<JMethod> getMethods() {
		return methods;
	}

	public void addMethod(JMethod method) {
		this.methods.add(method);
	}

}
