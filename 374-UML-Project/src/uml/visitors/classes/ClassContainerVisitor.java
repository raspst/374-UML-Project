package uml.visitors.classes;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.tree.ClassNode;

import uml.node.ClassContainer;
import uml.parser.ClassContainer1;

public class ClassContainerVisitor extends ClassVisitor {
	private ClassContainer container;

	public ClassContainerVisitor(int arg0, ClassContainer container) {
		super(arg0);
		this.container = container;
	}

	public ClassContainerVisitor(int arg0, ClassVisitor arg1, ClassContainer container) {
		super(arg0, arg1);
		this.container = container;
	}

	public ClassContainer getContainer() {
		return container;
	}
}
