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
		if(hasPattern(c))return getDecoratee(c.getSuper());
		c.addPattern("Component");
		return c;
	}
	
	public JClass getTopDecorator(JClass c){
		if(hasPattern(c.getSuper()))return getDecoratee(c.getSuper());
		return c;
	}
	
	public ArrayList<JClass> getDecendants(JClass c){
		ArrayList<JClass> decendants = new ArrayList<JClass>();
		for(String s : design.getClassNames()){
			JClass cl = design.getClass(s);
			if(cl.getSuper()==c) {
				decendants.add(cl);
				cl.addPattern("Decorator");
				cl.addFillColor("Decorator", "green");
			}
		}
		return decendants;
	}
	
	public void applyChange(JClass c) {
//		c.setSingleton(true);
		// System.out.println(c.getName());
		if(c.getPatterns().contains("Decorator")) {
			c.addFillColor("Decorator", "green");
			c.addAssociatesArrowAnnotation("Decorator", "decorates");
		}
		if(c.getPatterns().contains("Component")) {
			c.addFillColor("Component", "green");
		}
	}
	//BufferedReader
	//ClassVisitor
	public boolean isDecorator(JClass c){
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
						//System.out.println(in.putFieldCall()[2]);
						JClass type = container.getClass(in.putFieldCall()[2]);
						Instruction prev = m.getInstructions().get(i - 1);
						int local;
						// Makes sure the instance variable is or is parent of
						// class and previous instruction was a loadInstruction
						if ((type.getName().equals(c.getSuper().getName()) || type.getName().equals(c.getName()))
								&& (local = prev.loadInstruction()) != -1) {
							JField loc = m.getLocalVars().get(local);
							//Checks to see if the loaded variable is a parameter and same type as the field getting set
							if (loc.isParameter() && loc.getType().getName().equals(type.getName())){
								c.addPattern("Decorator");
								c.addAssociatesArrowAnnotation("Decorator", "decorates");
								return true;
							}
						}
					}
				}
			}

		}
		return false;
	}
	
	public boolean hasPattern(JClass c) {
		if(isDecorator(c)){
			System.out.println("DECORATEE: "+getDecoratee(c.getSuper()).getName());
			System.out.println("DECORATOR: " + getTopDecorator(c).getName());
			System.out.println("Subclasses: ");
			for(JClass cl:getDecendants(getTopDecorator(c)))System.out.println(cl.getName());
			//System.out.println(c.getSuper().getName());
			return true;
		}
		return false;
	}

}
