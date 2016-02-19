package uml.parser;

import java.util.ArrayList;
import java.util.HashMap;

import uml.types.JClass;

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
