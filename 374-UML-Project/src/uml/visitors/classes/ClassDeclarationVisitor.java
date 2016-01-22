package uml.visitors.classes;

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
		ClassContainer container = getContainer();
		JClass c = container.getClass(name);
		if (!isClass) {
			c.setInterface(true);
		}
		container.setActiveClass(c);
		c.setAccess(access);
		if (!c.getName().equals("java/lang/Object")) {
			JClass superClass = container.getClass(superName);
			c.setSuper(superClass);
		}
		for (String i : interfaces) {
			c.addInterface(container.getClass(i));
		}
		super.visit(version, access, name, signature, superName, interfaces);
	}

}
