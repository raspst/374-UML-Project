package uml.visitors.methods;

import org.objectweb.asm.MethodVisitor;

import uml.parser.ClassContainer;

public class MethodContainerVisitor extends MethodVisitor {
	private ClassContainer container;

	public MethodContainerVisitor(int arg0, ClassContainer container) {
		super(arg0);
		this.container = container;
	}

	public MethodContainerVisitor(int arg0, MethodVisitor arg1, ClassContainer container) {
		super(arg0, arg1);
		this.container = container;
	}

	public ClassContainer getContainer() {
		return container;
	}
}
