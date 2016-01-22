package uml.visitors.classes;

import java.util.ArrayList;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import uml.parser.ClassContainer;
import uml.types.JClass;
import uml.types.JMethod;

public class ClassMethodVisitor extends ClassContainerVisitor {

	public ClassMethodVisitor(int arg0, ClassVisitor arg1, ClassContainer container) {
		super(arg0, arg1, container);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		ClassContainer container = getContainer();
		Type[] argTypes = Type.getArgumentTypes(desc);
		String[] classNames = new String[argTypes.length];
		for (int i = 0; i < argTypes.length; i++) {
			classNames[i] = argTypes[i].getClassName();
		}

		JClass c = container.getActiveClass();
		JClass returnClass = container.getClass(Type.getReturnType(desc).getClassName());
		c.addUses(returnClass);
		ArrayList<JClass> parameters = new ArrayList<JClass>();
		for (String s : classNames) {
			JClass par = container.getClass(s);
			parameters.add(par);
			c.addUses(par);
		}
		JMethod toAdd = new JMethod(name, access, container.getClass(Type.getReturnType(desc).getClassName()),
				parameters, desc);
		if (c.getMethod(toAdd.getName(), desc) == null) {
			c.addMethod(toAdd);
			container.setActiveMethod(toAdd);
		} else
			container.setActiveMethod(c.getMethod(toAdd.getName(), desc));
		return toDecorate;
	}

}
