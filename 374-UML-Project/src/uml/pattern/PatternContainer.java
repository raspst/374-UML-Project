package uml.pattern;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import uml.parser.ParserClass;
import uml.types.JClass;

public class PatternContainer {
	private HashSet<JClass> classes = new HashSet<>();
	private JClass root;
	public PatternContainer(JClass root) {
		this.root=root;
		addClass(root);
	}
	public void addClass(JClass c) {
		classes.add(c);
	}
	public void addClasses(Collection<JClass> classes) {
		this.classes.addAll(classes);
	}
	public void setRoot(JClass root) {
		addClass(root);
		this.root = root;
	}

	public JClass getRoot() {
		return root;
	}

	public Set<JClass> getClasses() {
		return classes;
	}

	public void getAnnotation(ParserClass parserClass) {
		
	}
	
	public String getName(){
		return root.getTopName();
	}

}
