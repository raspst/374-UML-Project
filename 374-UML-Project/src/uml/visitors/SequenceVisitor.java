package uml.visitors;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class SequenceVisitor extends MethodVisitor {
	
	public SequenceVisitor(int arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	public SequenceVisitor(int arg0, MethodVisitor arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void visitMethodInsn(int opCode, String owner, String name, String desc, boolean itf) {
		super.visitMethodInsn(opCode, owner, name, desc, itf);
		if(opCode==Opcodes.INVOKEVIRTUAL)
		System.out.println(owner+"    "+name);
	}
}
