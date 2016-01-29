package uml.visitors.methods;

import org.objectweb.asm.MethodVisitor;

import uml.parser.ClassContainer1;

public class MethodContainerVisitor extends MethodVisitor {
	private ClassContainer1 container;

	public MethodContainerVisitor(int arg0, ClassContainer1 container) {
		super(arg0);
		this.container = container;
	}

	public MethodContainerVisitor(int arg0, MethodVisitor arg1, ClassContainer1 container) {
		super(arg0, arg1);
		this.container = container;
	}

	public ClassContainer1 getContainer() {
		return container;
	}
}
