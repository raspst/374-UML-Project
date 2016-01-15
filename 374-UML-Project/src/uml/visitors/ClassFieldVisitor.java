package uml.visitors;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;

import uml.parser.ClassContainer;
import uml.types.JClass;
import uml.types.JField;

public class ClassFieldVisitor extends ClassVisitor {
	private ClassContainer container;

	public ClassFieldVisitor(int arg0, ClassVisitor arg1, ClassContainer container) {
		super(arg0, arg1);
		this.container = container;
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		String type = Type.getType(desc).getClassName();
		JClass c = container.getActiveClass();
		JField toAdd = new JField(name, access, container.getClass(type));
		c.addField(toAdd);
		return toDecorate;
	}

}
