

import java.util.ArrayList;
import java.util.Arrays;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class ClassMethodVisitor extends ClassVisitor {
	private ClassContainer container;

	public ClassMethodVisitor(int arg0, ClassVisitor arg1, ClassContainer container) {
		super(arg0, arg1);
		this.container = container;
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, 
			String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		Type[] argTypes = Type.getArgumentTypes(desc);
		String[] classNames = new String[argTypes.length];
		String symbol = "";
		for(int i=0; i < argTypes.length; i++) {
			classNames[i] = argTypes[i].getClassName();
		}
		
		if(((access & Opcodes.ACC_PUBLIC) != 0)) {
			symbol="+";
		}
		else if(((access & Opcodes.ACC_PRIVATE) != 0)) {
			symbol="-";
		}
		else if(((access & Opcodes.ACC_PROTECTED) != 0)) {
			symbol="#";
		}
//		System.out.println("desc"+name);
//		System.out.println("    method "+ symbol+name + " " + Arrays.toString(classNames) + Type.getReturnType(desc).getClassName());
		JClass c = container.getActiveClass();
		ArrayList<JClass> parameters = new ArrayList<JClass>();
		for(String s: classNames) {
			parameters.add(container.getClass(s));
		}
		JMethod toAdd = new JMethod(name, access, container.getClass(Type.getReturnType(desc).getClassName()), parameters);
		c.addMethod(toAdd);
		return toDecorate;
	}

}
