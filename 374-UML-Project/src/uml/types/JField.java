package uml.types;

public class JField extends JType {
	private JClass type;
	private int access;
	public JField(String name, int access, JClass type) {
		super(name);
		this.setAccess(access);
		this.type = type;
		this.access=access;
	}
	
	public JClass getType(){
		return type;
	}
	
	public String getGraphViz() {
//		System.out.println("Access is: " + this.getAccess());
		return this.getAccess() + " " + this.getTopName() + " : " + this.type.getTopName();
	}
	
	public boolean isThis(){
		return access==0;
	}
	
	public boolean isParameter(){
		return access==1;
	}
}
