package uml.detector;

import uml.parser.Design;
import uml.types.JClass;

public abstract class PatternDetector {
	public PatternDetector(Design d) {
		for (String cl : d.getClassNames()) {
			JClass c = d.getClass(cl);
			if(hasPattern(c))applyChange(c);
		}
	}
	public abstract boolean hasPattern(JClass c);
	public abstract void applyChange(JClass c);
}
