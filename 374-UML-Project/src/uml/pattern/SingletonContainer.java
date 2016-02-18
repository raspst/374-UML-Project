package uml.pattern;

import uml.types.JClass;

public class SingletonContainer extends PatternContainer{

	public SingletonContainer(JClass root) {
		super(root);
		root.addPattern("Singleton");
		root.addBorderColor("Singleton", "blue");
	}

	@Override
	public String getAnnotation() {
		return "asdf";
	}

}
