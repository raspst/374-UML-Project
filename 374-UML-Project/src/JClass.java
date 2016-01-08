import java.util.ArrayList;

public class JClass extends JInterface {
	private ArrayList<JField> fields;
	private JClass superclass;
	
	public JClass(String name) {
		super(name);
		fields = new ArrayList<JField>();
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
		s.append(this.getName() + " [\n\tlabel = \"{" + this.getName() + "|");
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
}
