import java.util.ArrayList;

public class JClass extends JInterface {
	private ArrayList<JField> fields;
	private ArrayList<JMethod> methods;
	private JClass superclass;
	private ArrayList<JInterface> interfaces;
	
	public JClass(String name, String access, JClass superclass, ArrayList<JInterface> interfaces, ArrayList<JField> fields,
			ArrayList<JMethod> methods) {
		super(name, access, superclass, interfaces, fields, methods);
	}	
}
