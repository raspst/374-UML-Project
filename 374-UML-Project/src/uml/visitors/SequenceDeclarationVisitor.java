package uml.visitors;

import org.objectweb.asm.MethodVisitor;

import uml.parser.ClassContainer;

public class SequenceDeclarationVisitor extends ClassDeclarationVisitor {
	String className;
	String methodName;
	String params;
	String returnType;
	
	public SequenceDeclarationVisitor(int arg0, ClassContainer container, String className, String methodName, String params, String returnType) {
		super(arg0, container);
		this.className = className;
		this.methodName = methodName;
		this.params = params;
		this.returnType = returnType;
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		return toDecorate;
	}

}
