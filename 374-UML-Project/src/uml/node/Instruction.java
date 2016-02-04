package uml.node;

import org.objectweb.asm.Type;

public class Instruction {
	private String in;
	public Instruction(String line){
		in=line;
	}
	
	public int loadInstruction() {
		if (in.startsWith("ALOAD")){
			 int i = Integer.parseInt(in.substring(6));
				return i;
		}
		return -1;
	}
	
	public int storeInstruction() {
		if (in.startsWith("ASTORE")){
			 int i = Integer.parseInt(in.substring(7));
				return i;
		}
		return -1;
	}

	public boolean isSpecial() {
		return in.startsWith("INVOKESPECIAL");
	}

	/*
	 * Returns 0:owner of call, 1:method called, 2:method description
	 */
	public String[] specialCall() {
		String in = this.in.substring(14);
		String[] call = in.split("\\s+");
		String[] callOwner = call[0].split("\\.");
		String[] ret = new String[3];
		ret[0] = callOwner[0];
		ret[1] = callOwner[1];
		ret[2] = call[1];
		return ret;
	}
	
	public boolean isPutField() {
		return in.startsWith("PUTFIELD");
	}
	/*
	 * Returns 0:owner of field, 1:field name, 2:field type
	 */
	public String[] putFieldCall() {
		String in = this.in.substring(9);
		String[] call = in.split(" : ");
		String[] ret = new String[3];
		ret[2] = Type.getType(call[1]).getClassName().replace(".", "/");
		in = call[0];
		call = in.split("\\.");
		ret[0] = call[0];
		ret[1] = call[1];
		return ret;
	}
	
	public boolean isInvokeStatic() {
		return in.startsWith("INVOKESTATIC");
	}
	
	public boolean isgetStatic() {
		return in.startsWith("GETSTATIC");
	}
	
	/*
	 * Returns 0:owner of call, 1:method called, 2:method description
	 */
	public String[] invokeStaticCall() {
		String in = this.in.substring(13);
		String[] call = in.split("\\s+");
		String[] callOwner = call[0].split("\\.");
		String[] ret = new String[3];
		ret[0] = callOwner[0];
		ret[1] = callOwner[1];
		ret[2] = call[1];
		return ret;
	}
	
	/*
	 * Returns 0:owner of call, 1:method called, 2:method description
	 */
	public String[] getStatic() {
		String in = this.in.substring(10);
		String[] call = in.split(" : ");
		String[] callOwner = call[0].split("\\.");
		String[] ret = new String[3];
		ret[0] = callOwner[0];
		ret[1] = callOwner[1];
		ret[2] = Type.getType(call[1]).getClassName().replace(".", "/");
		return ret;
	}

	public boolean isAReturn() {
		return in.equals("ARETURN");
	}
	
	@Override
	public String toString() {
		return in;
	}
}
