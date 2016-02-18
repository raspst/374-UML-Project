package uml.pattern;

import java.util.HashSet;
import java.util.Set;

import uml.types.JClass;

public abstract class PatternContainer {
	private HashSet<JClass> classes = new HashSet<>();
	private JClass root;
	public PatternContainer(JClass root) {
		this.root=root;
		addClass(root);
	}
	public void addClass(JClass c) {
		classes.add(c);
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

	public abstract String getAnnotation();
}
