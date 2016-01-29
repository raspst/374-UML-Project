package uml.parser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import uml.node.ClassContainer;
import uml.types.JClass;
import uml.types.JInterface;

public class Design {
	private List<String> whitelist = new LinkedList<String>();
	private ArrayList<String> classes = new ArrayList<String>();
	private ClassContainer container = new ClassContainer(whitelist);
	
	public void addClass(String c){
		classes.add(c);
	}
	
	public void whitelist(String pack){
		whitelist.add(pack);
	}
	
	public boolean isWhitelisted(JInterface c){
		for(String pack : whitelist){
			if(c.getName().startsWith(pack)) {
				return true;
			}
		}
		return false;
	}

	public List<String> getClassNames() {
		return classes;
	}
	
	public JClass getClass(String name){
		return container.getClass(name);
	}
	
	public ClassContainer getContainer(){
		return container;
	}
}
