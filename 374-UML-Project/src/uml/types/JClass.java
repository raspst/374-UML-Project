package uml.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class JClass extends JType {
	private HashSet<JField> fields;
	private JClass superclass;
	private boolean isInterface;
	private HashMap<String, JClass> uses;
	private HashSet<JClass> associates;
	private HashSet<JField> statics;
	private ArrayList<JClass> interfaces;
	private ArrayList<JMethod> methods;
	private ArrayList<String> patterns;
	private HashMap<String, String> patternToUsesArrow;
	private HashMap<String, String> patternToAssociationsArrow;
	private HashMap<String, String> patternToColor;
	private ArrayList<JClass> descendants;
//	private boolean isSingleton = false;

	public JClass(String name) {
		super(name);
		fields = new HashSet<JField>();
		uses = new HashMap<String, JClass>();
		associates = new HashSet<JClass>();
		statics = new HashSet<JField>();
		this.methods = new ArrayList<JMethod>();
		interfaces = new ArrayList<JClass>();
		patterns = new ArrayList<String>();
		patternToUsesArrow = new HashMap<String, String>();
		patternToAssociationsArrow = new HashMap<String, String>();
		patternToColor = new HashMap<String, String>();
		descendants = new ArrayList<JClass>();
	}

	public void addInterface(JClass i) {
		interfaces.add(i);
		i.descendants.add(this);
	}

	public ArrayList<JClass> getInterfaces() {
		return this.interfaces;
	}

	public JMethod getMethod(String name, String desc) {
		for (JMethod m : methods) {
			if (m.getName().equals(name) && desc.equals(m.getDesc())) {
				return m;
			}
		}
		return null;
	}

	public ArrayList<JMethod> getMethods() {
		return methods;
	}

	public void addMethod(JMethod method) {
		this.methods.add(method);
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

	public void addStaticField(JField f) {
		String name = f.getType().getName();
		if (name != this.getName() && name != "void" && name != "int" && name != "float" && name != "double"
				&& name != "boolean" && name != "short" && name != "byte" && name != "char" && name != "long") {
			associates.add(f.getType());
		}
		statics.add(f);
	}

	public void setInterface(boolean isInterface) {
		this.isInterface = isInterface;
	}

	public boolean isInterface() {
		return isInterface;
	}

	public void setSuper(JClass s) {
		superclass = s;
		s.descendants.add(this);
	}
	
	public ArrayList<JClass> getDescendants(){
		return descendants;
	}

	public HashSet<JClass> getAssociates() {
		return associates;
	}

	public JClass getSuper() {
		return this.superclass;
	}

	public String getGraphViz() {
		StringBuilder s = new StringBuilder();
		String name = this.getTopName();
		if (!this.isInterface) {
			s.append(name + " [\n\tlabel = \"{");
//			if (this.isSingleton) {
//				s.append("\tSingleton\\l");
//			}
			for(String st: patterns) {
				s.append("\t" + st + "\\l");
			}
			s.append(name + "|");
		} else {
			s.append(name + " [\n\tlabel = \"{interface\n"); //name + "|");
			for(String st: patterns) {
				s.append("\t" + st + "\\l");
			}
			s.append(name + "|");
		}
		Iterator<JField> it = fields.iterator();
		while (it.hasNext()) {
			s.append(it.next().getGraphViz() + "\\l");
		}
		s.append("|");
		for (int i = 0; i < getMethods().size(); i++) {
			if (!getMethods().get(i).getName().equals("<init>"))
				s.append(getMethods().get(i).getGraphViz() + "\\l");
		}
		s.append("}\"\n");
		return s.toString();
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

	public JField getStaticField(String name) {
		for (JField f : statics) {
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
