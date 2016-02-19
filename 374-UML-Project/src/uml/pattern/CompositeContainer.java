package uml.pattern;

import uml.parser.ParserClass;
import uml.types.JClass;

public class CompositeContainer extends PatternContainer {

	public CompositeContainer(JClass root) {
		super(root);
		addClass(root.getSuper());
		addClasses(root.getSuper().getDescendants());
		for (JClass cl : root.getSuper().getDescendants()) {
			addClass(cl);
		}
	}

	@Override
	public void getAnnotation(ParserClass parserClass) {
		parserClass.addPattern("Composite");
		ParserClass parent = parserClass.getSuper();
		parent.addPattern("Component");
		parent.addFillColor("Component", "yellow");
		for (ParserClass pcl : parent.getDescendants()) {
			pcl.addPattern("Leaf Node");
			pcl.addFillColor("Leaf Node", "yellow");
		}
	}
}
