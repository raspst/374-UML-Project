package uml.node;

import java.util.ArrayList;

import org.objectweb.asm.Type;

import uml.types.JField;

public class MethodParser {
/*	private ClassContainer container;
	private ArrayList<Instruction> instructions;
	private ArrayList<JField> localVars;*/

	public MethodParser(ClassContainer container, ArrayList<Instruction> instructions, ArrayList<JField> localVars,
			int start) {
		String ret=null;
/*		this.container = container;
		this.instructions = instructions;
		this.localVars = localVars;*/
		for (int i = start; i < instructions.size(); ++i) {
			Instruction s = instructions.get(i);
			int val;
			if ((val = s.loadInstruction()) != -1) {
				// System.out.println("Loaded " + localVars.get(val).getName());
			} else if ((val = s.storeInstruction()) != -1) {
				if (ret == null)
					ret = "java/lang/Object";
				if (val > localVars.size())
					continue;
				if (val == localVars.size())
					localVars.add(new JField("lv" + val, 2, container.getClass(ret)));
				else if (container.getClass(ret) != localVars.get(val).getType())
					localVars.add(val, new JField("lv" + val, 2, container.getClass(ret)));
				// System.out.println("Stored " + localVars.get(val).getName());
			} else if (s.isSpecial()) {
				//String[] call = s.specialCall();
				// System.out.println("Special: " + call[0] + "." + call[1] +
				// "()");
			} else if (s.isPutField()) {
				//String[] call = s.putFieldCall();
				// System.out.println("Put Field: " + call[0] + " " + call[1] +
				// " " + call[2]);
			} else if (s.isInvokeStatic()) {
				String[] call = s.invokeStaticCall();
				ret = Type.getReturnType(call[2]).getClassName().replace(".", "/");
				// System.out.println("Static call: "+call[0]+" "+ret);
			} else if (s.isgetStatic()) {
				String[] call = s.getStatic();
				ret = call[2];
				// System.out.println("Static get: "+call[0]+" "+ret);
			} else if (s.isAReturn()) {
				// if(isgetStatic(instructions.get(i-1)))System.out.println("SINGLETON!");
			}
			// else System.out.println(s);
		}
	}
}
