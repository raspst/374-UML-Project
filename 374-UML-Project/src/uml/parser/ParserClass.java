package uml.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import uml.types.JClass;
import uml.types.JField;

public class ParserClass {
	private HashMap<JClass, ParserClass> classMap;
	private JClass theclass;
	private ArrayList<String> patterns = new ArrayList<>();
	private HashMap<String, String> patternToAssociationsArrow = new HashMap<>();
	private HashMap<String, String> patternToColor = new HashMap<>();
	private ArrayList<ParserClass> descendants = new ArrayList<>();
	private ArrayList<ParserClass> interfaces = new ArrayList<>();
	private HashMap<String, String> patternToUsesArrow = new HashMap<>();
	public ParserClass(JClass c, HashMap<JClass, ParserClass> map) {
		classMap = map;
		theclass = c;
	}

	public void populateHierachy() {
		for (JClass cl : theclass.getDescendants()) {
			ParserClass pcl = mapClass(cl);
			if (pcl != null)
				descendants.add(pcl);
		}
		for (JClass cl : theclass.getInterfaces()) {
			ParserClass pcl = mapClass(cl);
			if (pcl != null)
				interfaces.add(pcl);
		}
	}

	public ParserClass getSuper() {
		return classMap.get(theclass.getSuper());
	}

	public JClass wrappedClass() {
		return theclass;
	}

	public ParserClass mapClass(JClass c) {
		return classMap.get(c);
	}

	public void addPattern(String name) {
		patterns.add(name);
	}

	public ArrayList<String> getPatterns() {
		return patterns;
	}

	public void printClass() {
		StringBuilder sb = new StringBuilder();
		sb.append(theclass.getGraphViz());
		String name = theclass.getTopName();
		if (!theclass.isInterface()) {
			sb.append(name + " [\n\tlabel = \"{");
			for (String st : patterns) {
				sb.append("\t" + st + "\\l");
			}
			sb.append(name + "|");
		} else {
			sb.append(name + " [\n\tlabel = \"{interface\n"); // name + "|");
			for (String st : patterns) {
				sb.append("\t" + st + "\\l");
			}
			sb.append(name + "|");
		}
		Iterator<JField> it = theclass.getFields().iterator();
		while (it.hasNext()) {
			sb.append(it.next().getGraphViz() + "\\l");
		}
		sb.append("|");
		for (int i = 0; i < theclass.getMethods().size(); i++) {
			if (!theclass.getMethods().get(i).getName().equals("<init>"))
				sb.append(theclass.getMethods().get(i).getGraphViz() + "\\l");
		}
		sb.append("}\"\n");
		for (String s : patterns) {
			String color = getColor(s);
			if (color != null) {
				sb.append("\t" + color + "\n");
			}
		}
		sb.append("]\n");
		System.out.println(sb.toString());
	}

	public void addBorderColor(String pattern, String color) {
		patternToColor.put(pattern, "color=" + color);
	}

	public void addFillColor(String pattern, String color) {
		patternToColor.put(pattern, "style=filled\n\tfillcolor=" + color);
	}

	public String getColor(String pattern) {
		return patternToColor.get(pattern);
	}

	public void addAssociatesArrowAnnotation(JClass c, String annotation) {
		patternToAssociationsArrow.put(c.getTopName(), "[label=" + annotation + "]");
	}

	public ArrayList<ParserClass> getDescendants() {
		return descendants;
	}

	public ArrayList<ParserClass> getInterfaces() {
		return interfaces;
	}

	public String getAssociationsArrowAnnotation(String pattern) {
		return patternToAssociationsArrow.get(pattern);
	}

	public void addUsesArrowAnnotation(String pattern, String annotation) {
		patternToUsesArrow.put(pattern, annotation);
	}

	public String getUsesArrowAnnotation(String pattern) {
		return patternToUsesArrow.get(pattern);
	}
}
