package uml.detector;

import java.util.ArrayList;
import java.util.HashSet;

import uml.node.Instruction;
import uml.parser.Design;
import uml.types.JClass;
import uml.types.JField;
import uml.types.JMethod;

public class CompositeDetector extends PatternDetector {
	public ArrayList<HashSet<String>> patterns = new ArrayList<HashSet<String>>();
	public CompositeDetector(Design d) {
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
	
	public void applyChange(JClass c) {
		c.addPattern("Composite");
		c.getSuper().addPattern("Component");
		c.addFillColor("Composite", "yellow");
		c.getSuper().addFillColor("Component", "yellow");
		for(JClass cl: c.getSuper().getDescendants()) {
			cl.addPattern("Leaf Node");
			cl.addFillColor("Leaf Node", "yellow");
		}
//		c.setSingleton(true);
		// System.out.println(c.getName());
//		if(c.getPatterns().contains("Decorator")) {
//			c.addFillColor("Decorator", "green");
//			c.addAssociatesArrowAnnotation("Decorator", "decorates");
//		}
//		if(c.getPatterns().contains("Component")) {
//			c.addFillColor("Component", "green");
//		}
	}
	//BufferedReader
	//ClassVisitor	
	public boolean hasPattern(JClass c) {
		boolean add=false,remove=false;
		for(JMethod m : c.getMethods()){
			if(m.getName().equals("add")){
				ArrayList<JField> parameters = m.getParams();
				if(parameters.size()==1&&parameters.get(0).getType()==c.getSuper())add=true;
			}
			if(m.getName().equals("remove")){
				ArrayList<JField> parameters = m.getParams();
				if(parameters.size()==1&&parameters.get(0).getType()==c.getSuper())remove=true;
			}
		}
		if(add&&remove){
			System.out.println("COMPONENT: "+c.getSuper().getName());
			System.out.println("COMPOSITE: "+c.getName());
			System.out.println("Leaf Nodes: ");
			for(JClass cl : c.getSuper().getDescendants()){
				if(cl!=c)System.out.println(cl.getName());
			}
		}
		/*if(isDecorator(c)){
			System.out.println("DECORATEE: "+getDecoratee(c.getSuper()).getName());
			System.out.println("DECORATOR: " + getTopDecorator(c).getName());
			System.out.println("Subclasses: ");
			for(JClass cl:getDecendants(getTopDecorator(c)))System.out.println(cl.getName());
			//System.out.println(c.getSuper().getName());
			return true;
		}*/
		return false;
	}
}
