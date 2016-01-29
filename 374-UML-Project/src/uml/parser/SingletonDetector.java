package uml.parser;

import uml.types.JClass;
import uml.types.JField;
import uml.types.JMethod;
import static uml.node.MethodInstruction.*;

public class SingletonDetector extends PatternDetector{

	public SingletonDetector(Design d) {
		super(d);
	}
	
	public void applyChange(JClass c){
		System.out.println(c.getName());
	}

	public boolean hasPattern(JClass c) {
		boolean has = false;
		for (JField f : c.getFields())
			if (f.getType().getName().equals(c.getName())) {
				has = true;
				break;
			}
		if (!has)
			return false;
		for (JMethod m : c.getMethods()) {
			if (m.getName().equals("<init>") && !m.getAccess().equals("-"))
				continue;
			if (!m.getReturn().getName().equals(c.getName()))
				continue;
			for (int i = 0; i < m.getInstructions().size(); ++i) {
				String s = m.getInstructions().get(i);
				if(s.equals("ARETURN")){
					s = m.getInstructions().get(i-1);
					if(isgetStatic(s)&&getStatic(s)[2].equals(c.getName()))
					return true;
				}
			}

		}
		return false;
	}
}
