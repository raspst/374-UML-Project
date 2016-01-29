package uml.visitors.classes;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import uml.node.ClassContainer;
import uml.parser.ClassContainer1;
import uml.visitors.methods.SequenceVisitor;

public class StackFrameVisitor extends MethodDeclarationVisitor{

	public StackFrameVisitor(int arg0, ClassVisitor arg1, ClassContainer container, String methodName, String desc) {
		super(arg0, arg1, container, methodName, desc);
	}

	@Override
	public MethodVisitor invokeVisitors(MethodVisitor m, String name, String desc) {
	//	if(getMethod().equals(name) && desc.equals(getDesc()))
	//	return new SequenceVisitor(Opcodes.ASM5,m,getContainer());
		return m;
	}
}
