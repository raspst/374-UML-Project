package uml.detector;

import java.util.ArrayList;

import uml.parser.Design;
import uml.pattern.PatternContainer;
import uml.types.JClass;

public abstract class PatternDetector {
	protected Design design;

	public PatternDetector(Design d) {
		design = d;
	}
	
	public ArrayList<PatternContainer> searchClasses(){
		ArrayList<PatternContainer> containers = new ArrayList<>();
		for (String cl : design.getClassNames()) {
			JClass c = design.getClass(cl);
			if (hasPattern(c))
				containers.add(applyChange(c));
		}
		return containers;
	}

	public abstract boolean hasPattern(JClass c);

	public abstract PatternContainer applyChange(JClass c);
}
