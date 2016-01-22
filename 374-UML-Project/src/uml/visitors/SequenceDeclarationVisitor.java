package uml.visitors;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import uml.parser.ClassContainer;

public class SequenceDeclarationVisitor extends ClassMethodVisitor{
	String className;
	String methodName;
	String params;
	String returnType;
	
	public SequenceDeclarationVisitor(int arg0, ClassVisitor arg1, ClassContainer container, String className, String methodName, String params, String returnType) {
		super(arg0, arg1,container);
		this.className = className;
		this.methodName = methodName;
		this.params = params;
		this.returnType = returnType;
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		if(methodName.equals(name))
		return new SequenceVisitor(Opcodes.ASM5,toDecorate,container);
		return toDecorate;
	}

}
