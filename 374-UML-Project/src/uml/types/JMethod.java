package uml.types;
import java.util.ArrayList;
import java.util.HashMap;

public class JMethod extends JType {
	JClass returnType;
	ArrayList<JClass> parameters;
	HashMap<Integer,JClass> callStack = new HashMap<Integer,JClass>();
	public ArrayList<MethodInvokation> virtuals = new ArrayList<>();
	public JMethod(String name, int access, JClass returnType, ArrayList<JClass> parameters) {
		super(name);
		super.setAccess(access);
		this.returnType = returnType;
		this.parameters = parameters;
	}
	
	private String getTopLevelParameter(JClass c){
		String[] packages = c.getName().split("/");
		return packages[packages.length-1];
	}
	
	public void addVirtual(String c, String name, ArrayList<String> params, String returnType, int index){
		virtuals.add(new MethodInvokation(c, name, params, returnType,index));
	}
	
	public void printVirtuals(){
		//for(String s:virtuals)System.out.println(s);
	}
	
	public String getGraphViz() {
		StringBuilder s = new StringBuilder();
		s.append(this.getAccess() + " " + this.getTopName() + "(");
		for(int i = 0; i < parameters.size(); i++) {
			s.append(getTopLevelParameter(parameters.get(i))+ ",");
		}
		s.append(") : " + getTopLevelParameter(returnType));
		return s.toString();
	}
	
	public void setStack(int index, JClass type){
		callStack.put(index, type);
	}
	
	public JClass getStack(int index){
		return callStack.get(index);
	}
}
