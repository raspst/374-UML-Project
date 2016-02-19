package uml.pattern;

import uml.parser.ParserClass;
import uml.types.JClass;

public class SingletonContainer extends PatternContainer{

	public SingletonContainer(JClass root) {
		super(root);
	}

	@Override
	public void getAnnotation(ParserClass parserClass) {
		parserClass.addPattern("Singleton");
		parserClass.addBorderColor("Singleton", "blue");
	}

}
