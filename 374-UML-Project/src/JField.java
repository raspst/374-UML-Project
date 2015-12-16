
public class JField {
	private String name;
	private JClass type;
	
	public JField(String name, JClass type) {
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return this.name;
	}
}
