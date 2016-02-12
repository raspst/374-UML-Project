package uml.detector;

import java.util.ArrayList;
import java.util.HashSet;

import uml.node.Instruction;
import uml.parser.Design;
import uml.types.JClass;
import uml.types.JField;
import uml.types.JMethod;

public class DecoratorDetector extends PatternDetector {
	public ArrayList<HashSet<String>> patterns = new ArrayList<HashSet<String>>();
	private static ArrayList<JClass> descendants=new ArrayList<JClass>();
	public DecoratorDetector(Design d) {
		super(d);
	}
	
	public boolean inPattern(String cl){
		for(HashSet<String> s : patterns){
			if(s.contains(cl))return true;
		}
			return false;
	}
	
	public JClass getDecoratee(JClass c){
		if(!c.getSuper().getName().equals("java/lang/Object")&&isDecorator(c.getSuper()))return getDecoratee(c.getSuper());
		if(!c.getSuper().getName().equals("java/lang/Object"))c=c.getSuper();
		return c;
	}
	
	public JClass getTopDecorator(JClass c){
		if(isDecorator(c.getSuper()))return getTopDecorator(c.getSuper());
		return c;
	}
	
	public void applyChange(JClass c) {
//		c.setSingleton(true);
		// System.out.println(c.getName());
		c.addPattern("Decorator");
//		c.addAssociatesArrowAnnotation("Decorator", "decorates");
		JClass cla = getDecoratee(c);
		cla.addPattern("Component");
		if(c.getPatterns().contains("Decorator")) {
			c.addFillColor("Decorator", "green");
			c.addAssociatesArrowAnnotation(cla.getTopName(), "decorates");
		}
		if(c.getPatterns().contains("Component") && !c.getTopName().equals(cla.getTopName())) {
			c.addFillColor("Component", "green");
		}
		for(JClass cl:descendants){
			cl.addPattern("Decorator");
			cl.addFillColor("Decorator", "green");
		}
	}
	//BufferedReader
	//ClassVisitor
	public boolean isDecorator(JClass c){
		boolean decorated=false;
		boolean passed=false;
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
					continue;
				// Validates that putField was called on the instance variable
				// and was set by the parameter
				has=false;
				for (int i = 0; i < m.getInstructions().size(); ++i) {
					Instruction in = m.getInstructions().get(i);
					if (in.isPutField()) {
						//System.out.println(in.putFieldCall()[2]);
						JClass type = design.getClass(in.putFieldCall()[2]);
						Instruction prev = m.getInstructions().get(i - 1);
						int local;
						// Makes sure the instance variable is or is parent of
						// class and previous instruction was a loadInstruction
						if ((type.getName().equals(c.getSuper().getName()) || type.getName().equals(c.getName()))
								&& (local = prev.loadInstruction()) != -1) {
							JField loc = m.getLocalVars().get(local);
							//Checks to see if the loaded variable is a parameter and same type as the field getting set
							if (loc.isParameter() && loc.getType().getName().equals(type.getName())){
								has=true;
								break;
							}
						}
					}
				}
				if(has) passed=true;
			}
			else{
				//Check to see if we have made a decorator like call in at least one of our methods.
				for (Instruction in : m.getInstructions()) {
					if(in.isInvokeVirtual()&&in.invokeVirtualCall()[2].equals(m.getDesc())){
						decorated = true;
						break;
					}
				}
			}
		}
		if(!(decorated&&passed))return false;
		boolean has = false;
		for(JClass cl:getTopDecorator(c).getDescendants()){
			if(design.getOriginalClassNames().contains(cl.getName())){
				has=true;
				break;
			}
		}
		return has;
	}
	
	public boolean hasPattern(JClass c) {
		if(isDecorator(c)&&c.equals(getTopDecorator(c))){
			System.out.println("DECORATEE: "+getDecoratee(c).getName());
			System.out.println("DECORATOR: " + getTopDecorator(c).getName());
			System.out.println("Subclasses: ");
			descendants.clear();
			for(JClass cl:getTopDecorator(c).getDescendants()){
				if(design.getOriginalClassNames().contains(cl.getName())){
					descendants.add(cl);
					System.out.println(cl.getName());
				}
			}
			//System.out.println(c.getSuper().getName());
			return true;
		}
		return false;
	}

}
