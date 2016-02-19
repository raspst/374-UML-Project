package uml.detector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import uml.parser.Design;
import uml.pattern.PatternContainer;
import uml.types.JClass;

public abstract class PatternDetector {
	protected Design design;
	private HashSet<JClass> classes=new HashSet<>();
	public PatternDetector(Design d) {
		design = d;
	}
	
	public ArrayList<PatternContainer> searchClasses(){
		ArrayList<PatternContainer> containers = new ArrayList<>();
		for (String cl : design.getClassNames()) {
			JClass c = design.getClass(cl);
			if (hasPattern(c)){
				PatternContainer container = applyChange(c);
				containers.add(container);
				classes.addAll(container.getClasses());
			}
		}
		return containers;
	}

	public abstract boolean hasPattern(JClass c);

	public abstract PatternContainer applyChange(JClass c);
	
	public Set<JClass> getClasses() {
		return classes;
	}
}
