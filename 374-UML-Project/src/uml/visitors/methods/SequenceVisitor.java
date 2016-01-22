package uml.visitors.methods;

import java.util.ArrayList;
import java.util.LinkedList;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import uml.parser.ClassContainer;
import uml.types.JClass;
import uml.types.JField;
import uml.types.JMethod;

public class SequenceVisitor extends MethodContainerVisitor {
	private JClass created;
	private int index = 1;
	private int prevOp;
	private boolean init;
	private LinkedList<Integer> arguments = new LinkedList<Integer>();

	public SequenceVisitor(int arg0, ClassContainer container) {
		super(arg0, container);
	}

	public SequenceVisitor(int arg0, MethodVisitor arg1, ClassContainer container) {
		super(arg0, arg1, container);
	}

	@Override
	public void visitMethodInsn(int opCode, String owner, String name, String desc, boolean itf) {
		super.visitMethodInsn(opCode, owner, name, desc, itf);
		if (opCode == Opcodes.INVOKEVIRTUAL) {
			if (created == null)
				created = getContainer().getClass("java/lang/Object");
			Type[] argTypes = Type.getArgumentTypes(desc);
			ArrayList<String> params = new ArrayList<String>();
			for (Type arg : argTypes) {
				params.add(arg.getClassName());
			}
			String returnType = Type.getReturnType(desc).getClassName();
			getContainer().getActiveMethod().addParamStrings(params);
			getContainer().getActiveMethod().addVirtual(owner, name, params, returnType, desc, arguments.getFirst());
			// JMethod m = getContainer().getActiveClass().getMethod(name);
			// System.out.println("VIRTUAL "+ created.getName());
			// System.out.println(desc);
		} else if (opCode == Opcodes.INVOKEDYNAMIC) {
			System.out.println("DYNAMIC" + "    " + owner + "    " + name);
		} else {
			if (name.equals("<init>")) {
				init = true;
				created = getContainer().getClass(owner);
			} else {
				init = false;
				created = getContainer().getClass(owner);
				Type[] argTypes = Type.getArgumentTypes(desc);
				ArrayList<String> params = new ArrayList<String>();
				for (Type arg : argTypes) {
					params.add(arg.getClassName());
				}
				String returnType = Type.getReturnType(desc).getClassName();
				getContainer().getActiveMethod().addVirtual(owner, name, params, returnType, desc,
						arguments.getFirst());
			}
		}
		prevOp = opCode;
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		if (opcode == Opcodes.GETSTATIC) {
			index = 0;
			created = getContainer().getClass(desc);
		}
		if (opcode == Opcodes.PUTSTATIC) {
			getContainer().getActiveClass().addStaticField(new JField(name, 0, getContainer().getClass(desc)));
		} else if (opcode == Opcodes.GETFIELD) {
		} else if (opcode == Opcodes.PUTFIELD) {
		}
		prevOp = opcode;
		super.visitFieldInsn(opcode, owner, name, desc);
		// System.out.println(op+" "+owner+" "+name);
	}

	@Override
	public void visitTypeInsn(int opcode, String type) {
		super.visitTypeInsn(opcode, type);
		if (opcode == Opcodes.NEW) {
			created = getContainer().getClass(type);
		}
		// System.out.println(op+" "+type);
		prevOp = opcode;
	}

	@Override
	public void visitVarInsn(int opcode, int var) {
		super.visitVarInsn(opcode, var);
		ClassContainer container = getContainer();
		if (opcode == Opcodes.LLOAD) {
		} else if (opcode == Opcodes.FLOAD) {
		} else if (opcode == Opcodes.DLOAD) {
		} else if (opcode == Opcodes.ALOAD) {
			if (prevOp != Opcodes.ALOAD)
				arguments.clear();
			if (var == 0) {
				created = container.getActiveClass().getSuper();
				// System.out.println(created.getName());
				container.getActiveMethod().setStack(0, created);
				index = 0;
			} else {
				index = var;
				created = container.getActiveMethod().getStack(var);
			}
			arguments.add(index);
		} else if (opcode == Opcodes.ISTORE) {
		} else if (opcode == Opcodes.LSTORE) {
		} else if (opcode == Opcodes.FSTORE) {
		} else if (opcode == Opcodes.DSTORE) {
		} else if (opcode == Opcodes.ASTORE) {
			index = var;
			JMethod m = container.getActiveMethod();
			m.setStack(var, created);
			if (prevOp == Opcodes.INVOKESPECIAL && init)
				m.addVirtual(created.getName(), "<init>", new ArrayList<String>(), created.getName(), "", index);
		} else if (opcode == Opcodes.RET)
			// System.out.println(op+" "+var);
			prevOp = opcode;
	}

	@Override
	public void visitInsn(int arg0) {
		super.visitInsn(arg0);
		// if(arg0==Opcodes.ARETURN)System.out.println("ARETURN");
	}
}
