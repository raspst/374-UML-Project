import java.util.ArrayList;

public class JClass extends JInterface {
	private ArrayList<JField> fields;
	private ArrayList<JMethod> methods;
	private JClass superclass;
	private ArrayList<JInterface> interfaces;

	public JClass(String name) {
		super(name);
	}
	
}
