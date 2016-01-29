package uml.visitors.classes;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import uml.node.NodeContainer;
import uml.parser.ClassContainer;

public abstract class MethodDeclarationVisitor extends ClassContainerVisitor {
	private String methodName;
	private String desc;

	public MethodDeclarationVisitor(int arg0, ClassVisitor arg1, NodeContainer container, String methodName,
			String desc) {
		super(arg0, arg1, container);
		this.methodName = methodName;
		this.desc = desc;
	}

	public String getMethod() {
		return methodName;
	}

	public String getDesc() {
		return desc;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		return invokeVisitors(toDecorate, name, desc);
	}

	public abstract MethodVisitor invokeVisitors(MethodVisitor m, String name, String desc);

}
