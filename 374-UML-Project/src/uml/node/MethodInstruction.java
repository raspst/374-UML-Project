package uml.node;

import java.util.ArrayList;

import org.objectweb.asm.Type;

import uml.types.JField;

public class MethodInstruction {
	private NodeContainer container;
	private ArrayList<String> instructions;
	private ArrayList<JField> localVars;
	private String ret;
	public MethodInstruction(NodeContainer container, ArrayList<String> instructions,ArrayList<JField> localVars) {
		this.container = container;
		this.instructions = instructions;
		this.localVars = localVars;
		for (String s : instructions) {
			int val;
			if ((val = loadInstruction(s)) != -1) {
				//System.out.println("Loaded " + localVars.get(val).getName());
			} 
			else if ((val = storeInstruction(s)) != -1) {
				if(ret==null)ret="java/lang/Object";
				if(val>localVars.size())continue;
				if(val==localVars.size())localVars.add(new JField("lv"+val, 2, container.getClass(ret)));
				else if(container.getClass(ret)!=localVars.get(val).getType())localVars.add(val,new JField("lv"+val, 2, container.getClass(ret)));
				//System.out.println("Stored " + localVars.get(val).getName());
			} else if (isSpecial(s)) {
				String[] call = specialCall(s);
				//System.out.println("Special: " + call[0] + "." + call[1] + "()");
			} else if (isPutField(s)) {
				String[] call = putFieldCall(s);
				//System.out.println("Put Field: " + call[0] + " " + call[1] + " " + call[2]);
			} else if (isInvokeStatic(s)){
				String[] call = invokeStaticCall(s);
				ret = Type.getReturnType(call[2]).getClassName().replace(".", "/");
				//System.out.println("Static call: "+call[0]+" "+ret);
			}
			else
				System.out.println(s);
		}
	}
	public int loadInstruction(String in) {
		if (in.startsWith("ALOAD")){
			 int i = Integer.parseInt(in.substring(6));
			//if(localVars.get(i)==null)localVars.add(new JField(name, access, type));
				return i;
		}
		return -1;
	}
	
	public int storeInstruction(String in) {
		if (in.startsWith("ASTORE")){
			 int i = Integer.parseInt(in.substring(7));
			//if(localVars.get(i)==null)localVars.add(new JField(name, access, type));
				return i;
		}
		return -1;
	}

	public boolean isSpecial(String in) {
		return in.startsWith("INVOKESPECIAL");
	}

	/*
	 * Returns 0:owner of call, 1:method called, 2:method description
	 */
	public String[] specialCall(String in) {
		in = in.substring(14);
		String[] call = in.split("\\s+");
		String[] callOwner = call[0].split("\\.");
		String[] ret = new String[3];
		ret[0] = callOwner[0];
		ret[1] = callOwner[1];
		ret[2] = call[1];
		return ret;
	}
	
	public boolean isPutField(String in) {
		return in.startsWith("PUTFIELD");
	}
	/*
	 * Returns 0:owner of field, 1:field name, 2:field type
	 */
	public String[] putFieldCall(String in) {
		in = in.substring(9);
		String[] call = in.split(" : ");
		String[] ret = new String[3];
		ret[2] = Type.getType(call[1]).getClassName().replace(".", "/");
		in = call[0];
		call = in.split("\\.");
		ret[0] = call[0];
		ret[1] = call[1];
		return ret;
	}
	
	public boolean isInvokeStatic(String in) {
		return in.startsWith("INVOKESTATIC");
	}
	
	/*
	 * Returns 0:owner of call, 1:method called, 2:method description
	 */
	public String[] invokeStaticCall(String in) {
		in = in.substring(13);
		String[] call = in.split("\\s+");
		String[] callOwner = call[0].split("\\.");
		String[] ret = new String[3];
		ret[0] = callOwner[0];
		ret[1] = callOwner[1];
		ret[2] = call[1];
		return ret;
	}
}
