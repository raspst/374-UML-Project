package uml.visitors.classes;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.tree.ClassNode;

import uml.node.NodeContainer;
import uml.parser.ClassContainer;

public class ClassContainerVisitor extends ClassVisitor {
	private NodeContainer container;

	public ClassContainerVisitor(int arg0, NodeContainer container) {
		super(arg0);
		this.container = container;
	}

	public ClassContainerVisitor(int arg0, ClassVisitor arg1, NodeContainer container) {
		super(arg0, arg1);
		this.container = container;
	}

	public NodeContainer getContainer() {
		return container;
	}
}
