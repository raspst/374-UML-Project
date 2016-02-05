package uml.visitors.classes;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;

import uml.parser.Design;
import uml.types.JClass;
import uml.types.JField;

public class ClassFieldVisitor extends ClassContainerVisitor {
	private JClass c;
	public ClassFieldVisitor(int arg0, ClassVisitor arg1, Design d,JClass c) {
		super(arg0, arg1, d);
		this.c=c;
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		String type = Type.getType(desc).getClassName();
		JField toAdd = new JField(name, access, design.getClass(type));
		c.addField(toAdd);
		return toDecorate;
	}

}
