import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.objectweb.asm.Type;

public class ClassContainer {
	private HashMap<String, JClass> classes;

	private List<String> whitelist = new LinkedList<String>();
	private JClass activeClass;

	public ClassContainer() {
		this.classes = new HashMap<String, JClass>();
	}

	public void whitelist(String pack){
		whitelist.add(pack);
	}
	
	public boolean isWhitelisted(JInterface c){
		for(String pack : whitelist){
			if(c.getName().startsWith(pack))return true;
		}
		return false;
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
		//String[] packages = name.split("/");
		//System.out.println(packages.toString());
		//name = packages[packages.length - 1];
		//packages = name.split("/");
		//name = packages[packages.length - 1];
		JClass theclass = classes.get(name);
		if (theclass == null) {
			theclass = new JClass(name);
			classes.put(name, theclass);
		}
		return theclass;
	}

}
