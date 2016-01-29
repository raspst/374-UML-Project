package uml.types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class JClass extends JInterface {
	private HashSet<JField> fields;
	private JClass superclass;
	private boolean isInterface;
	private HashMap<String, JClass> uses;
	private HashSet<JClass> associates;
	private HashSet<JField> statics;
	private boolean isSingleton = false;

	public JClass(String name) {
		super(name);
		fields = new HashSet<JField>();
		uses = new HashMap<String, JClass>();
		associates = new HashSet<JClass>();
		statics = new HashSet<JField>();
	}

	public void addField(JField f) {
		String name = f.getType()
				.getName();
		if (name != this.getName() && !name.equals("void") && !name.equals("int") && !name.equals("float") && !name.equals("double")
				&& !name.equals("boolean") && !name.equals("short") && !name.equals("byte") && !name.equals("char") && !name.equals("long")) {
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
	}

	public HashSet<JClass> getAssociates() {
		return associates;
	}

	public JClass getSuper() {
		return this.superclass;
	}

	public void setDependencies(JClass superclass, ArrayList<JInterface> interfaces) {
		this.superclass = superclass;
	}

	public String getGraphViz() {
		StringBuilder s = new StringBuilder();
		String name = this.getTopName();
		if (!this.isInterface) {
			s.append(name + " [\n\tlabel = \"{");
			if(this.isSingleton) {
				s.append("\tSingleton\\l");
			}
			s.append(name + "|");
		} else {
			s.append(name + " [\n\tlabel = \"{interface\n" + name + "|");
		}
		Iterator<JField> it = fields.iterator();
		while(it.hasNext()) {
			s.append(it.next().getGraphViz() + "\\l");
		}
		s.append("|");
		for (int i = 0; i < getMethods().size(); i++) {
			if(!getMethods().get(i).getName().equals("<init>"))
			s.append(getMethods().get(i).getGraphViz() + "\\l");
		}
		s.append("}\"\n");
		return s.toString();
	}

	public void addUses(JClass usedClass) {
		if (this.getName() != 
				usedClass.getName()) {
			this.uses.put(this.getName(), usedClass);
		}
	}

	public HashMap<String, JClass> getUses() {
		return uses;
	}
	
	public HashSet<JField> getFields() {
		return fields;
	}
	
	public JField getField(String name) {
		for(JField f: fields) {
			if(f.getTopName().equals(name)) {
				return f;
			}
		}
		return null;
	}
	
	public JField getStaticField(String name) {
		for(JField f: statics) {
			if(f.getTopName().equals(name)) {
				return f;
			}
		}
		return null;
	}
	
	public void setSingleton(boolean value) {
		this.isSingleton = value;
	}
	
	public boolean isSingleton() {
		return this.isSingleton;
	}
}
