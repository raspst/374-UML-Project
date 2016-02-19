package uml.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class JClass extends JInterface {
	private HashSet<JField> fields;
	private JClass superclass;
	private HashMap<String, JClass> uses;
	private ArrayList<String> patterns;
	private HashMap<String, String> patternToUsesArrow;
	private HashMap<String, String> patternToAssociationsArrow;
	private HashMap<String, String> patternToColor;
//	private boolean isSingleton = false;

	public JClass(String name) {
		super(name);
		fields = new HashSet<JField>();
		uses = new HashMap<String, JClass>();
		patterns = new ArrayList<String>();
		patternToUsesArrow = new HashMap<String, String>();
		patternToAssociationsArrow = new HashMap<String, String>();
		patternToColor = new HashMap<String, String>();
	}

	public void addField(JField f) {
		String name = f.getType().getName();
		if (name != this.getName() && !name.equals("void") && !name.equals("int") && !name.equals("float")
				&& !name.equals("double") && !name.equals("boolean") && !name.equals("short") && !name.equals("byte")
				&& !name.equals("char") && !name.equals("long")) {
			associates.add(f.getType());
		}
		fields.add(f);
	}

	public void setSuper(JClass s) {
		superclass = s;
		s.descendants.add(this);
	}

	public JClass getSuper() {
		return this.superclass;
	}

	public void addUses(JClass usedClass) {
		// if (this.getName() !=
		// usedClass.getName()) {
		this.uses.put(this.getName(), usedClass);
		// }
	}

	public HashMap<String, JClass> getUses() {
		return uses;
	}

	public HashSet<JField> getFields() {
		return fields;
	}

	public JField getField(String name) {
		for (JField f : fields) {
			if (f.getTopName().equals(name)) {
				return f;
			}
		}
		return null;
	}
	
	public void addPattern(String name) {
		this.patterns.add(name);
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
	
	public void addUsesArrowAnnotation(String pattern, String annotation) {
		patternToUsesArrow.put(pattern, annotation);
	}
	
	public String getUsesArrowAnnotation(String pattern) {
		return patternToUsesArrow.get(pattern);
	}
	
	public void addAssociatesArrowAnnotation(String pattern, String annotation) {
		patternToAssociationsArrow.put(pattern, "[label=" + annotation + "]");
	}
	
	public String getAssociationsArrowAnnotation(String pattern) {
		return patternToAssociationsArrow.get(pattern);
	}
	
	public String getColor(String pattern) {
		return patternToColor.get(pattern);
	}

//	public void setSingleton(boolean value) {
//		this.isSingleton = value;
//	}
//
//	public boolean isSingleton() {
//		return this.isSingleton;
//	}
}
