package uml.types;

public class MethodInvokation {
	public String owner;
	public String method;
	public JMethod m;
	public MethodInvokation(String owner, String method){
		this.owner=owner;
		this.method=method;
	}
}
