package uml.visitors.classes;

import org.objectweb.asm.ClassVisitor;

import uml.node.ClassContainer;
import uml.parser.Design;

public class ClassContainerVisitor extends ClassVisitor {
	protected Design design;

	public ClassContainerVisitor(int arg0, Design d) {
		super(arg0);
		design=d;
	}

	public ClassContainerVisitor(int arg0, ClassVisitor arg1, Design d) {
		super(arg0, arg1);
		design = d;
	}

	public ClassContainer getContainer() {
		return design.getContainer();
	}
}
