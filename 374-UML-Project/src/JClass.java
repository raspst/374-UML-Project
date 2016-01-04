import java.util.ArrayList;

public class JClass extends JInterface {
	private ArrayList<JField> fields;
	private ArrayList<JMethod> methods;
	private JClass superclass;
	private ArrayList<JInterface> interfaces;

	public JClass(String name) {
		super(name);
		fields = new ArrayList<JField>();
		methods = new ArrayList<JMethod>();
	}
	
	public void addField(JField f) {
		fields.add(f);
	}
	
	public void addMethod(JMethod m) {
		methods.add(m);
	}
	
	public void setDependencies(JClass superclass, ArrayList<JInterface> interfaces) {
		this.superclass = superclass;
		this.interfaces = interfaces;
	}
	
	public String getGraphViz() {
		StringBuilder s = new StringBuilder();
		s.append(this.getName() + " [\n\tlabel = \"{" + this.getName() + "|");
		for(int i = 0; i < fields.size(); i++) {
			s.append(fields.get(i).getGraphViz() + "\\l");
		}
		s.append("|");
		for(int i = 0; i < methods.size(); i++) {
			s.append(methods.get(i).getGraphViz() + "\\l");
		}
		s.append("}\"\n]");
		return s.toString();
	}
	
}
