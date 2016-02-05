package uml.detector;

import java.util.ArrayList;

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
	}
	
	/*
	 * Redundant but useful possibly for other detectors.
	 */
	public ArrayList<JClass> getUsers(JClass c){
		ArrayList<JClass> decendants = new ArrayList<JClass>();
		out: for(String s : design.getClassNames()){
			JClass cl = design.getClass(s);
			for(JField f:cl.getFields())if(f.getType().getName().equals(c.getName())){
				decendants.add(cl);
				continue out;
			}
			for(JMethod m:cl.getMethods()){
				for(JField f:m.getParams()){
					if(f.getType().getName().equals(c.getName())){
						decendants.add(cl);
						continue out;
					}
				}
			}
		}
		return decendants;
	}

	public boolean hasPattern(JClass c) {
		boolean passed = false;
		//System.out.println(c.getName());
		for(JClass inter : c.getInterfaces()){
			//System.out.println(inter.);
			if(c.getMethods().size()-1!=inter.getMethods().size())return false;
		}

		if(c.getInterfaces().size()==0)return false;
		JField field = null;
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
					if(in.isInvokeVirtual()&&design.getClass(in.invokeVirtualCall()[0]).getName().equals(f.getType().getName()))
						has=true;
					else if(in.isInvokeInterface()&&design.getClass(in.invokeInterfaceCall()[0]).getName().equals(f.getType().getName()))
							has = true;
				}
				if(!has){
					passed=false;
					break;
				}
				else{
					field=f;
				}
			}
		}
		if(passed){
//			System.out.println("ADAPTER: "+c.getName());
			c.addPattern("Adapter");
			c.addFillColor("Adapter", "red");
			c.addAssociatesArrowAnnotation("Adapter", "adapts");
			field.getType().addPattern("Adaptee");
			field.getType().addFillColor("Adaptee", "red");
			c.getInterfaces().get(0).addPattern("Target");
			c.getInterfaces().get(0).addFillColor("Target", "red");
//			System.out.println("ADAPTEE: "+field.getType().getName());
//			System.out.println("TARGET: "+c.getInterfaces().get(0).getName());
		}
		return passed;
	}

}
