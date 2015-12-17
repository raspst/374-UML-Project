

import java.util.Arrays;

import org.objectweb.asm.ClassVisitor;

public class ClassDeclarationVisitor extends ClassContainerVisitor {

	public ClassDeclarationVisitor(int arg0,ClassContainer container) {
		super(arg0,container);
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName,
			String[] interfaces) {
		JClass c = container.getClass(name);
		c.setAccess(access);
		JClass superClass = container.getClass(superName);
		System.out.println("Class: " + name +" extends "+superName+" implements "+Arrays.toString(interfaces));
		
		super.visit(version, access, name, signature, superName, interfaces);
	}

}
