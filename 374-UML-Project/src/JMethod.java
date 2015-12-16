import java.util.ArrayList;

public class JMethod {
	String name;
	ArrayList<JClass> parameters;
	
	public JMethod(String name, ArrayList<JClass> parameters) {
		this.name = name;
		this.parameters = parameters;
	}
	
	public String getName() {
		return this.name;
	}
}
