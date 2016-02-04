package uml.detector;

import uml.node.Instruction;
import uml.parser.Design;
import uml.types.JClass;
import uml.types.JField;
import uml.types.JMethod;

public class DecoratorDetector extends PatternDetector {

	public DecoratorDetector(Design d) {
		super(d);
	}

	public void applyChange(JClass c) {
		c.setSingleton(true);
		// System.out.println(c.getName());
	}

	public boolean hasPattern(JClass c) {
		for (JMethod m : c.getMethods()) {
			if (m.getName().equals("<init>")) {// && m.getAccess().equals("+")

				boolean has = false;

				out: for (JField f : c.getFields()) {
					// Makes sure the instance variable is or is parent of class
					if (f.getType().getName().equals(c.getSuper().getName())
							|| f.getType().getName().equals(c.getName())) {
						// Makes sure there is a matching parameter to instance
						// variable
						for (JField t : m.getParams()) {
							if (f.getType().getName().equals(t.getType().getName())) {
								has = true;
								break out;
							}
						}
					}
				}
				if (!has)
					return false;
				// Validates that putField was called on the instance variable
				// and was set by the parameter
				for (int i = 0; i < m.getInstructions().size(); ++i) {
					Instruction in = m.getInstructions().get(i);
					if (in.isPutField()) {
						System.out.println(in.putFieldCall()[2]);
						JClass type = container.getClass(in.putFieldCall()[2]);
						Instruction prev = m.getInstructions().get(i - 1);
						int local;
						// Makes sure the instance variable is or is parent of
						// class and previous instruction was a loadInstruction
						if ((type.getName().equals(c.getSuper().getName()) || type.getName().equals(c.getName()))
								&& (local = prev.loadInstruction()) != -1) {
							JField loc = m.getLocalVars().get(local);
							//Checks to see if the loaded variable is a parameter and same type as the field getting set
							if (loc.isParameter() && loc.getType().getName().equals(type.getName()))
								System.out.println("FUCK YEA: " + c.getName());
							return true;
						}
					}
				}
			}

		}
		return false;
	}

}
