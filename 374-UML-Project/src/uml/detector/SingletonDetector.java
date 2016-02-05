package uml.detector;

import uml.node.Instruction;
import uml.parser.Design;
import uml.types.JClass;
import uml.types.JField;
import uml.types.JMethod;

public class SingletonDetector extends PatternDetector{

	public SingletonDetector(Design d) {
		super(d);
	}
	
	public void applyChange(JClass c){
//		c.setSingleton(true);
		c.addPattern("Singleton");
		c.addBorderColor("Singleton", "blue");
		c.addArrowAnnotation("Singleton", " ");
//		System.out.println(c.getName());
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
				Instruction s = m.getInstructions().get(i);
				if(s.isAReturn()){
					s = m.getInstructions().get(i-1);
					if(s.isgetStatic()&&s.getStatic()[2].equals(c.getName()))
					return true;
				}
			}

		}
		return false;
	}
}
