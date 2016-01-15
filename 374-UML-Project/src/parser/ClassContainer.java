package parser;
import java.util.HashMap;

import org.objectweb.asm.Type;

public class ClassContainer {
	private HashMap<String, JClass> classes;
	
	private JClass activeClass;

	public ClassContainer() {
		this.classes = new HashMap<String, JClass>();
	}
	
	public void setActiveClass(JClass c) {
		activeClass = c;
	}

	public JClass getActiveClass() {
		return activeClass;
	}

	public JClass getClass(String name) {
		name = Type.getType(name).getInternalName();
		name = name.replace('.', '/');
		JClass theclass = classes.get(name);
		if (theclass == null) {
			theclass = new JClass(name);
			classes.put(name, theclass);
		}
		return theclass;
	}

}
