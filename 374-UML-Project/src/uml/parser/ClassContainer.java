package uml.parser;
import java.util.HashMap;

import org.objectweb.asm.Type;

import uml.types.JClass;
import uml.types.JMethod;

public class ClassContainer {
	private HashMap<String, JClass> classes;
	
	private JClass activeClass;

	private JMethod activeMethod;

	public ClassContainer() {
		this.classes = new HashMap<String, JClass>();
	}
	
	public void setActiveClass(JClass c) {
		activeClass = c;
	}

	public JClass getActiveClass() {
		return activeClass;
	}

	public void setActiveMethod(JMethod m) {
		activeMethod = m;
	}

	public JMethod getActiveMethod() {
		return activeMethod;
	}
	
	public JClass getClass(String name) {
		//name = Type.getType(name).getInternalName();
		name = Type.getObjectType(name).getClassName();
		name = name.replace('.', '/');
		JClass theclass = classes.get(name);
		if (theclass == null) {
			theclass = new JClass(name);
			classes.put(name, theclass);
		}
		return theclass;
	}

}