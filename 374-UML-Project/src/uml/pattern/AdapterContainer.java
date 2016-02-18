package uml.pattern;

import uml.types.JClass;

public class AdapterContainer extends PatternContainer{
	private JClass adaptee;
	public AdapterContainer(JClass root,JClass adaptee) {
		super(root);
		this.adaptee=adaptee;
		addClass(adaptee);
		root.addPattern("Adapter");
		root.addFillColor("Adapter", "red");
		root.addAssociatesArrowAnnotation(adaptee.getTopName(), "adapts");
		adaptee.addPattern("Adaptee");
		adaptee.addFillColor("Adaptee", "red");
		for(JClass in : root.getInterfaces()){
			addClass(in);
			in.addPattern("Target");
			in.addFillColor("Target", "red");
		}
	}
	@Override
	public String getAnnotation() {
		for(JClass in : getRoot().getInterfaces()){
			
		}
		return "";
	}

}
