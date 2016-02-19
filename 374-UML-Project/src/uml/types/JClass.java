package uml.types;

import java.util.HashMap;
import java.util.HashSet;

public class JClass extends JInterface {
	private HashSet<JField> fields;
	private JClass superclass;
	private HashMap<String, JClass> uses;

	public JClass(String name) {
		super(name);
		fields = new HashSet<JField>();
		uses = new HashMap<String, JClass>();
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
		this.uses.put(this.getName(), usedClass);
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
}
