package uml.visitors;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import uml.parser.ClassContainer;
import uml.types.JClass;
import uml.types.JMethod;

public class SequenceVisitor extends MethodContainerVisitor {
	private JClass created;
	public SequenceVisitor(int arg0, ClassContainer container) {
		super(arg0,container);
	}
	public SequenceVisitor(int arg0, MethodVisitor arg1,ClassContainer container) {
		super(arg0, arg1,container);
	}

	@Override
	public void visitMethodInsn(int opCode, String owner, String name, String desc, boolean itf) {
		super.visitMethodInsn(opCode, owner, name, desc, itf);
		String op = "SPECIAL";
		if(opCode==Opcodes.INVOKEVIRTUAL){op="VIRTUAL";
			System.out.println("VIRTUAL"+ created.getName());
		}
		else if(opCode==Opcodes.INVOKEDYNAMIC)op="DYNAMIC";
		//System.out.println(op+"    "+owner+"    "+name);
	}
	
	@Override
	public void visitFieldInsn(int opcode,
            String owner,
            String name,
            String desc) {
		String op = "GETSTATIC";
		if(opcode==Opcodes.GETSTATIC){
			created=getContainer().getClass(owner);
		}
		if(opcode==Opcodes.PUTSTATIC)op="PUTSTATIC";
		else if(opcode==Opcodes.GETFIELD)op="GETFIELD";
		else if(opcode==Opcodes.PUTFIELD)op="PUTFIELD";
		super.visitFieldInsn(opcode, owner, name, desc);
		System.out.println(op+"    "+owner+"    "+name);
	}
	
	@Override
	public void visitTypeInsn(int opcode,
            String type) {
		super.visitTypeInsn(opcode, type);
		String op = "";
		if(opcode == Opcodes.NEW){
			op="NEW";
			created = getContainer().getClass(type);
		}
		System.out.println(op+"    "+type);
	}
	
	@Override
	public void visitVarInsn(int opcode, int var) {
		super.visitVarInsn(opcode, var);
		ClassContainer container = getContainer();
		String op = "ILOAD";
		if(opcode==Opcodes.LLOAD)op="LLOAD";
		else if(opcode==Opcodes.FLOAD)op="FLOAD";
		else if(opcode==Opcodes.DLOAD)op="DLOAD";
		else if(opcode==Opcodes.ALOAD){op="ALOAD";
		created = container.getActiveMethod().getStack(var);
		}
		else if(opcode==Opcodes.ISTORE)op="ISTORE";
		else if(opcode==Opcodes.LSTORE)op="LSTORE";
		else if(opcode==Opcodes.FSTORE)op="FSTORE";
		else if(opcode==Opcodes.DSTORE)op="DSTORE";
		else if(opcode==Opcodes.ASTORE){op="ASTORE";
			JMethod m = container.getActiveMethod();
			if(m.getStack(var)==null){
				m.setStack(var, created);
			}
		}
		else if(opcode==Opcodes.RET)op="RET";
		System.out.println(op+"    "+var);
	}
	
	@Override
	public void visitInsn(int arg0) {
		super.visitInsn(arg0);
		if(arg0==Opcodes.ARETURN)System.out.println("ARETURN");
	}
}
