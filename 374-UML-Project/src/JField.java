
public class JField extends JType {
	private JClass type;
	
	public JField(String name, int access, JClass type) {
		super(name);
		this.setAccess(access);
		this.type = type;
	}
	
	public JClass getType(){
		return type;
	}
	
	public String getGraphViz() {
//		System.out.println("Access is: " + this.getAccess());
		return this.getAccess() + " " + this.getName() + " : " + this.type.getName();
	}
}
