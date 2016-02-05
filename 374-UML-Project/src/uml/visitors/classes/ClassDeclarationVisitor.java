package uml.visitors.classes;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import uml.node.ClassContainer;
import uml.parser.Design;
import uml.types.JClass;

public class ClassDeclarationVisitor extends ClassContainerVisitor {

	public ClassDeclarationVisitor(int arg0, Design d) {
		super(arg0, d);
	}

	public ClassDeclarationVisitor(int asm5, ClassNode classNode, Design d) {
		super(asm5, classNode, d);
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		boolean isClass = true;
		if ((access & Opcodes.ACC_INTERFACE) != 0) {
			isClass = false;
		}
		ClassContainer container = getContainer();
		JClass c = container.getClass(name);
		container.setActiveClass(c);
		if (!isClass) {
			c.setInterface(true);
		}
		c.setAccess(access);
		if (c.getName().equals("java/lang/Object"))
			c.setSuper(c);
		else {
			JClass superClass = container.getClass(superName);
			c.setSuper(superClass);
		}
		for (String i : interfaces) {
			c.addInterface(container.getClass(i));
		}
		super.visit(version, access, name, signature, superName, interfaces);
	}

}
