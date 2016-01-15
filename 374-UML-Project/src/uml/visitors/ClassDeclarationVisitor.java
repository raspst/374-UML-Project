package uml.visitors;

import org.objectweb.asm.Opcodes;

import uml.parser.ClassContainer;
import uml.types.JClass;

public class ClassDeclarationVisitor extends ClassContainerVisitor {

	public ClassDeclarationVisitor(int arg0, ClassContainer container) {
		super(arg0, container);
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		boolean isClass = true;
		if ((access & Opcodes.ACC_INTERFACE) != 0) {
			isClass = false;
		}
		JClass c = container.getClass(name);
		if (!isClass) {
			c.setInterface(true);
		}
		container.setActiveClass(c);
		c.setAccess(access);
		JClass superClass = container.getClass(superName);
		c.setSuper(superClass);
		for (String i : interfaces) {
		/*	String[] packages = i.split("/");
			i = packages[packages.length - 1];
			packages = i.split("\\.");
			i = packages[packages.length - 1];*/
			c.addInterface(container.getClass(i));
		}
		// System.out.println("Class: " + c.getName() +" extends
		// "+c.getSuper().getName()+" implements
		// "+Arrays.toString(c.getInterfaces().toArray()));
		super.visit(version, access, name, signature, superName, interfaces);
	}

}
