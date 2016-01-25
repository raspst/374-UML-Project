package uml.parser;

import uml.types.JClass;
import uml.types.JField;
import uml.types.JMethod;

public class SingletonDetector {
	private Design d;

	public SingletonDetector(Design d) {
		this.d = d;
	}

	public void printFields() {
		for (String cl : d.getClassNames()) {
			JClass c = d.getClass(cl);

			for(JMethod m : c.getMethods()){
				for (JField f : c.getFields()) {
					if(m.getReturn().equals(f.getType().getName()))
						System.out.println(f.getType().getName());
					//System.out.println(m.getReturn());
					//System.out.println(f.getType().getName());
				}
			}
		}
	}
}
