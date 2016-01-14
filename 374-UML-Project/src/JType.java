import java.util.HashMap;

import org.objectweb.asm.Opcodes;

public class JType {
	private String name;
	private String access = "#";
	private HashMap<Integer, String> accessTypes;

	public JType(String name) {
		this.name = name;
		accessTypes = new HashMap<Integer, String>();
		accessTypes.put(Opcodes.ACC_PRIVATE, "-");
		accessTypes.put(Opcodes.ACC_PROTECTED, "#");
		accessTypes.put(Opcodes.ACC_PUBLIC, "+");
	}

	public String getName() {
		return this.name;
	}
	
	public String getTopName(){
		return name.substring(name.lastIndexOf('/')+1);
	}

	public String getAccess() {
		return this.access;
	}

	public void setAccess(int access) {
		this.access = accessTypes.get(access);
		if (this.access == null) {
			this.access = "#";
		}
	}
}
