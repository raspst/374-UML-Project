import java.util.ArrayList;

public class JMethod extends JType {
	ArrayList<JClass> parameters;
	
	public JMethod(String name, String access, ArrayList<JClass> parameters) {
		super(name);
		this.parameters = parameters;
	}
}
