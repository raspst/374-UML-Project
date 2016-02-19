package uml.pattern;

import uml.parser.ParserClass;
import uml.types.JClass;

public class DecoratorContainer extends PatternContainer{
	private JClass decoratee;
	public DecoratorContainer(JClass root,JClass decoratee) {
		super(root);	
		this.decoratee=decoratee;
		addClass(decoratee);
		for(JClass cl:root.getDescendants())addClass(cl);
	}
	@Override
	public void getAnnotation(ParserClass parserClass) {
		parserClass.addPattern("Decorator");
		parserClass.addFillColor("Decorator", "green");
		parserClass.addAssociatesArrowAnnotation(decoratee, "decorates");
		ParserClass decoratee = parserClass.mapClass(this.decoratee);
		decoratee.addPattern("Component");
		if(!parserClass.wrappedClass().getTopName().equals(this.decoratee.getTopName())) {
			decoratee.addFillColor("Component", "green");
		}
		for(ParserClass cl:parserClass.getDescendants()){
			cl.addPattern("Decorator");
			cl.addFillColor("Decorator", "green");
		}
	}

}
