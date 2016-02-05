package uml.detector;

import uml.node.Instruction;
import uml.parser.Design;
import uml.types.JClass;
import uml.types.JField;
import uml.types.JMethod;

public class AdapterDetector extends PatternDetector {

	public AdapterDetector(Design d) {
		super(d);
	}

	public void applyChange(JClass c) {
//		c.setSingleton(true);
		System.out.println("ADAPTEE: "+c.getName());
	}

	public boolean hasPattern(JClass c) {
		boolean passed = false;
		//System.out.println(c.getName());
		for(JClass inter : c.getInterfaces()){
			//System.out.println(inter.);
			if(c.getMethods().size()-1!=inter.getMethods().size())return false;
		}
		System.out.println(c.getName());
		//if(c.getInterfaces().size()==0)return false;
		for(JField f:c.getFields()){
			for(JMethod m:c.getMethods()){
				passed=true;
				//System.out.println(m.getName());
				if(m.getName().equals("<init>"))continue;
				boolean has = false;
				//InputStreamReader
				for(Instruction in : m.getInstructions()){
					//System.out.println(in.toString());
					//if(in.isInvokeVirtual())System.out.println(in.invokeVirtualCall()[0]);
					if(in.isInvokeVirtual()&&container.getClass(in.invokeVirtualCall()[0]).getName().equals(f.getType().getName()))
						has=true;
					else if(in.isInvokeInterface()&&container.getClass(in.invokeInterfaceCall()[0]).getName().equals(f.getType().getName()))
							has = true;
				}
				if(!has){
					passed=false;
					break;
				}
			}
		}
		return passed;
	}

}
