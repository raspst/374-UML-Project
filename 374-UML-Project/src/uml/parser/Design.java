package uml.parser;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import uml.node.ClassContainer;
import uml.types.JClass;

public class Design {
	private List<String> whitelist = new LinkedList<String>();
	private HashSet<String> classes = new HashSet<String>();
	private ClassContainer container = new ClassContainer(this);
	
	public void parse(){
			for (String className : classes) {
				container.addClass(className);
			}
			container.parse();
	}
	public void addClass(String c){
		classes.add(c);
	}
	
	public void whitelist(String pack){
		whitelist.add(pack);
	}
	
	public boolean isWhitelisted(JClass c){
		for(String pack : whitelist){
			if(c.getName().startsWith(pack)) {
				return true;
			}
		}
		return false;
	}

	public Set<String> getClassNames() {
		return classes;
	}
	
	public JClass getClass(String name){
		return container.getClass(name);
	}
	
	public ClassContainer getContainer(){
		return container;
	}
}
