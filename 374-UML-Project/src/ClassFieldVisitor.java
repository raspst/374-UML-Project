

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;

public class ClassFieldVisitor extends ClassVisitor {
	private ClassContainer container;

	public ClassFieldVisitor(int arg0, ClassVisitor arg1, ClassContainer container) {
		super(arg0, arg1);
		this.container = container;
	}
	
	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, 
			Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		String type = Type.getType(desc).getClassName();
		String[] packages = type.split("\\.");
		type = packages[packages.length-1];
//		System.out.println("    "+type+" "+name);
		JClass c = container.getActiveClass();
		JField toAdd = new JField(name, access, container.getClass(type));
		c.addField(toAdd);
		return toDecorate;
	}

}
