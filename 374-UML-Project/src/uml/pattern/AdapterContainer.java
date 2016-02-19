package uml.pattern;

import uml.parser.ParserClass;
import uml.types.JClass;

public class AdapterContainer extends PatternContainer {
	private JClass adaptee;

	public AdapterContainer(JClass root, JClass adaptee) {
		super(root);
		this.adaptee = adaptee;
		addClass(adaptee);
		addClasses(root.getInterfaces());
	}

	@Override
	public void getAnnotation(ParserClass parserClass) {
		parserClass.addPattern("Adapter");
		parserClass.addFillColor("Adapter", "red");
		parserClass.addAssociatesArrowAnnotation(adaptee, "adapts");
		ParserClass adaptee = parserClass.mapClass(this.adaptee);
		adaptee.addPattern("Adaptee");
		adaptee.addFillColor("Adaptee", "red");
		for (ParserClass pin : parserClass.getInterfaces()) {
			pin.addPattern("Target");
			pin.addFillColor("Target", "red");
		}
	}

}
