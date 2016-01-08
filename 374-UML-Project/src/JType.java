import java.util.HashMap;

import org.objectweb.asm.Opcodes;

public class JType {
	private String name;
	private String access = "#";
	private HashMap<Integer,String> accessTypes;
	public JType(String name) {
		this.name = name;
		accessTypes = new HashMap<Integer,String>();
		accessTypes.put(Opcodes.ACC_PRIVATE, "-");
		accessTypes.put(Opcodes.ACC_PROTECTED, "#");
		accessTypes.put(Opcodes.ACC_PUBLIC, "+");
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getTopLevelName(){
		String[] packages = name.split("/");
		//if(packages.length==1)return name.substring(1);
		return packages[packages.length-1].replace("()L", "").replace("(L", "").replace(";", "").replace(")V", "");
	}
	
	public String getAccess() {
//		System.out.println("Why the fuck aren't you # " + this.access);
		return this.access;
	}

	public void setAccess(int access){
		this.access=accessTypes.get(access);
//		System.out.println("The fuck is the access? " + this.access);
		if(this.access == null) {
			this.access = "#";
//			System.out.println("Now the fucking access should be # " + this.access);
		}
	}
}
