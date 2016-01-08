import java.util.ArrayList;
import java.util.HashMap;

public class JClass extends JInterface {
	private ArrayList<JField> fields;
	private JClass superclass;
	public boolean isInterface;
	private HashMap<String, JClass> uses;
	public JClass(String name) {
		super(name);
		fields = new ArrayList<JField>();
		uses = new HashMap<String, JClass>();
	}
	
	public void addField(JField f) {
		fields.add(f);
	}
	
	public void setSuper(JClass s) {
		superclass=s;
	}
	
	public JClass getSuper() {
		return this.superclass;
	}
	
	public void setDependencies(JClass superclass, ArrayList<JInterface> interfaces) {
		this.superclass = superclass;
	}
	
	public String getGraphViz() {
		StringBuilder s = new StringBuilder();
		if(!this.isInterface) {
			s.append(this.getName() + " [\n\tlabel = \"{" + this.getName() + "|");
		}
		else {
			s.append(this.getName() + " [\n\tlabel = \"{interface\n" + this.getName() + "|");
		}
		for(int i = 0; i < fields.size(); i++) {
			s.append(fields.get(i).getGraphViz() + "\\l");
		}
		s.append("|");
		for(int i = 0; i < getMethods().size(); i++) {
			s.append(getMethods().get(i).getGraphViz() + "\\l");
		}
		s.append("}\"\n]");
		return s.toString();
	}
	
	public String printInheritance() {
		// Don't want to print Object in UML diagram
		if(this.superclass == null) {
			return "";
		}
		if(!this.superclass.getName().equals("Object")) {
			return this.getName() + "->" + this.superclass.getName();
		}
		else {
			return "";
		}
	}
	
	public String printImplements() {
		StringBuilder s = new StringBuilder();
		for(JInterface j: this.getInterfaces()) {
			s.append(this.getName() + "->" + j.getName() + "\n");
		}
		return s.toString();
	}
	
	public void addUses(JClass usedClass) {
		this.uses.put(this.getName(), usedClass);
	}
	
	public String getUses() {
		StringBuilder s = new StringBuilder();
		for(JClass c: this.uses.values()) {
			s.append(this.getName() + "->" + c.getName() + "\n");
		}
		return s.toString();
	}
}
