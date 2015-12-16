
public class JField extends JType {
	private JClass type;
	
	public JField(String name, String access, JClass type) {
		super(name,access);
		this.type = type;
	}
}
