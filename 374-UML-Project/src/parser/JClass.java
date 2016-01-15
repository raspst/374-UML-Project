package parser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.objectweb.asm.Type;

public class JClass extends JInterface {
	private ArrayList<JField> fields;
	private JClass superclass;
	private boolean isInterface;
	private HashMap<String, JClass> uses;
	private HashSet<JClass> associates;

	public JClass(String name) {
		super(name);
		fields = new ArrayList<JField>();
		uses = new HashMap<String, JClass>();
		associates = new HashSet<JClass>();
	}

	public void addField(JField f) {
		String name = f.getType().getName();
		if (name != this.getName() && name != "void" && name != "int" && name != "float" && name != "double"
				&& name != "boolean" && name != "short" && name != "byte" && name != "char" && name != "long") {
			associates.add(f.getType());
		}
		fields.add(f);
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
		String name = this.getName();
		if (!this.isInterface) {
			s.append(name + " [\n\tlabel = \"{" + name + "|");
		} else {
			s.append(name + " [\n\tlabel = \"{interface\n" + name + "|");
		}
		for (int i = 0; i < fields.size(); i++) {
			s.append(fields.get(i).getGraphViz() + "\\l");
		}
		s.append("|");
		for (int i = 0; i < getMethods().size(); i++) {
			s.append(getMethods().get(i).getGraphViz() + "\\l");
		}
		s.append("}\"\n]");
		return s.toString();
	}

	public void addUses(JClass usedClass) {
		if (this.getName() != usedClass.getName()) {
			this.uses.put(this.getName(), usedClass);
		}
	}

	public HashMap<String, JClass> getUses() {
		return uses;
	}
}
