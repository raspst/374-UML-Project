package uml.types;

import java.util.ArrayList;

public class MethodInvokation {
	public String owner;
	public String method;
	public ArrayList<String> params;
	public String returnType;
	public JMethod m;
	public int index;
	public String desc;

	public MethodInvokation(String owner, String method, ArrayList<String> params, String returnType, String desc,
			int index) {
		this.owner = owner;
		this.method = method;
		this.params = params;
		this.returnType = returnType;
		this.index = index;
		this.desc = desc;
	}
}
