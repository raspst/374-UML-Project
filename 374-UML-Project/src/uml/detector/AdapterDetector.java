package uml.detector;

import java.util.ArrayList;

import uml.parser.Design;
import uml.pattern.AdapterContainer;
import uml.pattern.PatternContainer;
import uml.types.JClass;
import uml.types.JField;
import uml.types.JMethod;

public class AdapterDetector extends PatternDetector {

	JClass adaptee=null;
	
	public AdapterDetector(Design d) {
		super(d);
	}
	
	public PatternContainer applyChange(JClass c) {
		//System.out.println("ADAPTER: " + c.getName());
		//System.out.println("ADAPTEE: " + adaptee.getName());
		//System.out.println("Targets:");
		//for(JClass in : c.getInterfaces())
		//System.out.println(in.getName());
		return new AdapterContainer(c,adaptee);
	}

	/*
	 * Redundant but useful possibly for other detectors.
	 */
	public ArrayList<JClass> getUsers(JClass c) {
		ArrayList<JClass> decendants = new ArrayList<JClass>();
		out: for (String s : design.getClassNames()) {
			JClass cl = design.getClass(s);
			for (JField f : cl.getFields())
				if (f.getType().getName().equals(c.getName())) {
					decendants.add(cl);
					continue out;
				}
			for (JMethod m : cl.getMethods()) {
				for (JField f : m.getParams()) {
					if (f.getType().getName().equals(c.getName())) {
						decendants.add(cl);
						continue out;
					}
				}
			}
		}
		return decendants;
	}

	public boolean hasPattern(JClass c) {
		if (c.getInterfaces().size() == 0)
			return false;
		int count = 0;
		for (JMethod m : c.getMethods()) {
			if (!m.getName().equals("<init>") && !m.getName().equals("<clinit>")) {
				++count;
				boolean has = false;
				out: for (JClass inter : c.getInterfaces()) {
					for(JMethod method : inter.getMethods()){
						if(method.getDesc().equals(m.getDesc())){
							has = true;
							break out;
						}
					}
				}
				if(!has)return false;
			}
		}
		int compare = 0;
		for (JClass inter : c.getInterfaces())
			compare += inter.getMethods().size();
		if (compare != count)
			return false;
		for(JField f:c.getFields())adaptee=f.getType();
		if(adaptee==null){
			for(JMethod m:c.getMethods())if(m.getParams().size()>0){
				adaptee=m.getParams().get(0).getType();
				break;
			}
		}
		return adaptee!=null;
	}

}
