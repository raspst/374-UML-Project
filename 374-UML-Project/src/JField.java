
public class JField extends JType {
	private JClass type;
	
	public JField(String name, int access, JClass type) {
		super(name);
		this.setAccess(access);
		this.type = type;
	}
}
