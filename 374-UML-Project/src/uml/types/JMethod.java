package uml.types;
import java.util.ArrayList;

import uml.node.Instruction;

public class JMethod extends JType {
	JClass owner;
	ArrayList<JField> parameters;
	JClass returnType;
	private String desc;
	ArrayList<JField> localVars = new ArrayList<JField>();
	private ArrayList<Instruction>instructions;
	public JMethod(JClass owner, String name, int access, JClass returnType,ArrayList<JField> locals,ArrayList<Instruction> instructions,String desc) {
		super(name);
		super.setAccess(access);
		this.owner=owner;
		this.returnType = returnType;
		this.instructions = instructions;
		localVars=locals;
		this.parameters = new ArrayList<JField>();
		for(JField f:locals){
			if(f.isParameter())parameters.add(f);
		}
		this.desc=desc;
		localVars=locals;
	}
	
	private String getTopLevelParameter(JClass c){
		String[] packages = c.getName().split("/");
		return packages[packages.length-1];
	}
	
	public ArrayList<JField> getParams() {
		return parameters;
	}
	
	public void printVirtuals(){
		//for(String s:virtuals)System.out.println(s);
	}
	
	public String getGraphViz() {
		StringBuilder s = new StringBuilder();
		s.append(this.getAccess() + " " + this.getTopName() + "(");
		for(int i = 0; i < parameters.size(); i++) {
			//TODO: may break
			s.append(getTopLevelParameter(parameters.get(i).getType())+ ",");
		}
		s.append(") : " + getTopLevelParameter(returnType));
		return s.toString();
	}
	
	public JField getVar(int index){
		return parameters.get(index);
	}
	
	public String getDesc(){
		return desc;
	}
	
	public JClass getReturn(){
		return returnType;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof JMethod){
			return(((JMethod) obj).desc.equals(desc) && ((JMethod) obj).getName().equals(getName()));
		}
		return false;
	}

	public ArrayList<JField> getLocalVars() {
		return localVars;
	}

	public ArrayList<Instruction> getInstructions() {
		return instructions;
	}
}
