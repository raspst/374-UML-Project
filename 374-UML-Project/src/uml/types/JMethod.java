package uml.types;
import java.util.ArrayList;
import java.util.HashMap;

public class JMethod extends JType {
	JClass returnType;
	ArrayList<JClass> parameters;
	ArrayList<String> params;
	private String desc;
	boolean framed;
	HashMap<Integer,JClass> callStack = new HashMap<Integer,JClass>();
	public ArrayList<MethodInvokation> virtuals = new ArrayList<>();
	public JMethod(String name, int access, JClass returnType, ArrayList<JClass> parameters,String desc) {
		super(name);
		super.setAccess(access);
		this.returnType = returnType;
		this.parameters = parameters;
		this.desc=desc;
	}
	
	private String getTopLevelParameter(JClass c){
		String[] packages = c.getName().split("/");
		return packages[packages.length-1];
	}
	
	public void addVirtual(String c, String name, ArrayList<String> params, String returnType, String desc,int index){
		virtuals.add(new MethodInvokation(c, name, params, returnType,desc,index));
	}
	
	public void addParamStrings(ArrayList<String> params) {
		this.params = params;
	}
	
	public ArrayList<String> getParams() {
		return this.params;
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
	
	public String getDesc(){
		return desc;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof JMethod){
			return(((JMethod) obj).desc.equals(desc) && ((JMethod) obj).getName().equals(getName()));
		}
		return false;
	}

	public boolean stackFramed() {
		return framed;
	}
	public void setStackFramed(boolean framed){
		this.framed=framed;
	}
}
