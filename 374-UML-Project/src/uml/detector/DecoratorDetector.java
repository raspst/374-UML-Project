package uml.detector;

import uml.node.Instruction;
import uml.parser.Design;
import uml.types.JClass;
import uml.types.JField;
import uml.types.JMethod;

public class DecoratorDetector extends PatternDetector{

	public DecoratorDetector(Design d) {
		super(d);
	}
	
	public void applyChange(JClass c){
		c.setSingleton(true);
//		System.out.println(c.getName());
	}

	public boolean hasPattern(JClass c) {
		for (JMethod m : c.getMethods()) {
			if (m.getName().equals("<init>") ){//&& m.getAccess().equals("+")

				boolean has = false;
				out: for(JField t : m.getParams()){
				for (JField f : c.getFields())
					if ((f.getType().getName().equals(c.getSuper().getName()) || f.getType().getName().equals(c.getName())) && f.getType().getName().equals(t.getType().getName())) {
						has = true;
						break out;
					}
				}
				if (!has)
					return false;
				for (int i = 0; i < m.getInstructions().size(); ++i) {
					Instruction in = m.getInstructions().get(i);
					if(in.isPutField()){
						System.out.println(in.putFieldCall()[2]);
						JClass type = container.getClass(in.putFieldCall()[2]);
						Instruction prev = m.getInstructions().get(i-1);
						int local;
						if((type.getName().equals(c.getSuper().getName()) || type.getName().equals(c.getName()))&&(local=prev.loadInstruction())!=-1){
							JField loc = m.getLocalVars().get(local);
							if(loc.isParameter()&&loc.getType().getName().equals(type.getName()))
							System.out.println("FUCK YEA: "+c.getName());
							return true;
						}
					}
				}
			}

		}
		return false;
	}
	
}
