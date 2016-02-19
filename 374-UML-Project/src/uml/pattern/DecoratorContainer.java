package uml.pattern;

import uml.parser.ParserClass;
import uml.types.JClass;

public class DecoratorContainer extends PatternContainer{
	private JClass decoratee;
	public DecoratorContainer(JClass root,JClass decoratee) {
		super(root);
		root.addPattern("Decorator");
		root.addFillColor("Decorator", "green");
		root.addAssociatesArrowAnnotation(decoratee.getTopName(), "decorates");
	
		this.decoratee=decoratee;
		addClass(decoratee);
		decoratee.addPattern("Component");
		
		if(!root.getTopName().equals(decoratee.getTopName())) {
			decoratee.addFillColor("Component", "green");
		}
		
		for(JClass cl:root.getDescendants()){
			addClass(cl);
			cl.addPattern("Decorator");
			cl.addFillColor("Decorator", "green");
		}
	}
	@Override
	public void getAnnotation(ParserClass parserClass) {

	}

}
