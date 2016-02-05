package uml.types;

import java.util.ArrayList;

import uml.node.Instruction;

public class JMethod extends JType {
	private JClass owner;
	private ArrayList<JField> parameters;
	private JClass returnType;
	private String desc;
	private ArrayList<JField> localVars = new ArrayList<JField>();
	private ArrayList<Instruction> instructions;

	public JMethod(JClass owner, String name, int access, JClass returnType, ArrayList<JField> locals,
			ArrayList<Instruction> instructions, String desc) {
		super(name);
		super.setAccess(access);
		this.owner = owner;
		this.returnType = returnType;
		this.instructions = instructions;
		localVars = locals;
		this.parameters = new ArrayList<JField>();
		for (JField f : locals) {
			if (f.isParameter())
				parameters.add(f);
		}
		this.desc = desc;
		localVars = locals;
	}

	public JClass getOwner() {
		return owner;
	}

	public ArrayList<JField> getParams() {
		return parameters;
	}


	public String getGraphViz() {
		StringBuilder s = new StringBuilder();
		s.append(this.getAccess() + " " + this.getTopName() + "(");
		for (int i = 0; i < parameters.size(); i++) {
			// TODO: may break
			s.append(parameters.get(i).getType().getTopName() + ",");
		}
		s.append(") : " + returnType.getTopName());
		return s.toString();
	}

	public JField getVar(int index) {
		return parameters.get(index);
	}

	public String getDesc() {
		return desc;
	}

	public JClass getReturn() {
		return returnType;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof JMethod) {
			return (((JMethod) obj).desc.equals(desc) && ((JMethod) obj).getName().equals(getName()));
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
