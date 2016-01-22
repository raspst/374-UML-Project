package uml.visitors;

import java.util.ArrayList;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.omg.CORBA.Current;

import uml.parser.ClassContainer;
import uml.types.JClass;
import uml.types.JField;
import uml.types.JMethod;

public class SequenceVisitor extends MethodContainerVisitor {
	private JClass created;
	private int index = 1;
	private int prevOp;
	public SequenceVisitor(int arg0, ClassContainer container) {
		super(arg0, container);
	}

	public SequenceVisitor(int arg0, MethodVisitor arg1, ClassContainer container) {
		super(arg0, arg1, container);
	}

	@Override
	public void visitMethodInsn(int opCode, String owner, String name, String desc, boolean itf) {
		super.visitMethodInsn(opCode, owner, name, desc, itf);
		String op = "SPECIAL";
		if (opCode == Opcodes.INVOKEVIRTUAL) {
			op = "VIRTUAL";
			JClass c = getContainer().getActiveClass();
			// System.out.println(owner +" "+ name);
			if (created == null)
				created = getContainer().getClass("java/lang/Object");
			Type[] argTypes = Type.getArgumentTypes(desc);
			ArrayList<String> params = new ArrayList<String>();
			for (Type arg : argTypes) {
				params.add(arg.getClassName());
			}
			String returnType = Type.getReturnType(desc).getClassName();
			getContainer().getActiveMethod().addParamStrings(params);
			getContainer().getActiveMethod().addVirtual(owner, name, params, returnType, index);
			// JMethod m = getContainer().getActiveClass().getMethod(name);
			// System.out.println("VIRTUAL "+ created.getName());
			// System.out.println(desc);
		} else if (opCode == Opcodes.INVOKEDYNAMIC) {
			op = "DYNAMIC";
			System.out.println("DYNAMIC" + "    " + owner + "    " + name);
		} else {
			if (name.equals("<init>")) {
				created = getContainer().getClass(owner);
			}
			//System.out.println("SPECIAL" + "    " + owner + "    " + name);
		}
		// System.out.println(op+" "+owner+" "+name);
		prevOp=opCode;
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		String op = "GETSTATIC";
		if (opcode == Opcodes.GETSTATIC) {
			created = getContainer().getClass(desc);
		}
		if (opcode == Opcodes.PUTSTATIC) {
			op = "PUTSTATIC";
			getContainer().getActiveClass().addStaticField(new JField(name, 0, getContainer().getClass(desc)));
		} else if (opcode == Opcodes.GETFIELD)
			op = "GETFIELD";
		else if (opcode == Opcodes.PUTFIELD)
			op = "PUTFIELD";
		prevOp=opcode;
		super.visitFieldInsn(opcode, owner, name, desc);
		// System.out.println(op+" "+owner+" "+name);
	}

	@Override
	public void visitTypeInsn(int opcode, String type) {
		super.visitTypeInsn(opcode, type);
		String op = "";
		if (opcode == Opcodes.NEW) {
			op = "NEW";
			created = getContainer().getClass(type);
		}
		// System.out.println(op+" "+type);
		prevOp=opcode;
	}

	@Override
	public void visitVarInsn(int opcode, int var) {
		super.visitVarInsn(opcode, var);
		ClassContainer container = getContainer();
		String op = "ILOAD";
		if (opcode == Opcodes.LLOAD)
			op = "LLOAD";
		else if (opcode == Opcodes.FLOAD)
			op = "FLOAD";
		else if (opcode == Opcodes.DLOAD)
			op = "DLOAD";
		else if (opcode == Opcodes.ALOAD) {
			op = "ALOAD";
			if (var == 0) {
				created = container.getActiveClass().getSuper();
				// System.out.println(created.getName());
				container.getActiveMethod().setStack(0, created);
				index = 0;
			} else {
				index = var;
				created = container.getActiveMethod().getStack(var);
			}
		} else if (opcode == Opcodes.ISTORE)
			op = "ISTORE";
		else if (opcode == Opcodes.LSTORE)
			op = "LSTORE";
		else if (opcode == Opcodes.FSTORE)
			op = "FSTORE";
		else if (opcode == Opcodes.DSTORE)
			op = "DSTORE";
		else if (opcode == Opcodes.ASTORE) {
			op = "ASTORE";
			index = var;
			JMethod m = container.getActiveMethod();
			m.setStack(var, created);
			if(prevOp==Opcodes.INVOKESPECIAL)m.addVirtual(created.getName(), "<init>", new ArrayList<String>(), created.getName(), index);
		} else if (opcode == Opcodes.RET)
			op = "RET";
		// System.out.println(op+" "+var);
		prevOp=opcode;
	}

	@Override
	public void visitInsn(int arg0) {
		super.visitInsn(arg0);
		// if(arg0==Opcodes.ARETURN)System.out.println("ARETURN");
	}
}
