package uml.visitors.classes;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;

import uml.node.ClassContainer;
import uml.types.JClass;
import uml.types.JField;

public class ClassFieldVisitor extends ClassContainerVisitor {

	public ClassFieldVisitor(int arg0, ClassVisitor arg1, ClassContainer container) {
		super(arg0, arg1, container);
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		String type = Type.getType(desc).getClassName();
		ClassContainer container = getContainer();
		JClass c = container.getActiveClass();
		JField toAdd = new JField(name, access, container.getClass(type));
		c.addField(toAdd);
		return toDecorate;
	}

}
