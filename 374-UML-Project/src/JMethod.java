import java.util.ArrayList;

public class JMethod extends JType {
	JClass returnType;
	ArrayList<JClass> parameters;
	
	public JMethod(String name, int access, JClass returnType, ArrayList<JClass> parameters) {
		super(name);
		super.setAccess(access);
		this.returnType = returnType;
		this.parameters = parameters;
	}
	
	public String getGraphViz() {
		StringBuilder s = new StringBuilder();
		s.append(this.getAccess() + " " + this.getName() + "(");
		for(int i = 0; i < parameters.size(); i++) {
			s.append(parameters.get(i).getName() + ",");
		}
		s.append(") : " + this.returnType.getName());
		return s.toString();
	}
}
