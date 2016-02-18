package uml.pattern;

import uml.types.JClass;

public class CompositeContainer extends PatternContainer{

	public CompositeContainer(JClass root) {
		super(root);
		addClass(root.getSuper());
		root.addPattern("Composite");
		root.getSuper().addPattern("Component");
		root.getSuper().addFillColor("Component", "yellow");
		for(JClass cl: root.getSuper().getDescendants()){
			addClass(cl);
			cl.addPattern("Leaf Node");
			cl.addFillColor("Leaf Node", "yellow");
		}
	}

	@Override
	public String getAnnotation() {
		return "blah";
	}

}
