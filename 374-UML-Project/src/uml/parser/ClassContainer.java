package uml.parser;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import uml.types.JClass;
import uml.types.JMethod;
import uml.types.MethodInvokation;
import uml.visitors.classes.ClassDeclarationVisitor;
import uml.visitors.classes.ClassFieldVisitor;
import uml.visitors.classes.ClassMethodVisitor;
import uml.visitors.classes.StackFrameVisitor;

public class ClassContainer {
	private HashMap<String, JClass> classes;
	private Queue<String> toParse;
	private JClass activeClass;
	private JMethod activeMethod;

	public ClassContainer() {
		this.classes = new HashMap<String, JClass>();
		toParse = new LinkedList<String>();
	}

	public void setActiveClass(JClass c) {
		activeClass = c;
	}

	public JClass getActiveClass() {
		return activeClass;
	}

	public void setActiveMethod(JMethod m) {
		activeMethod = m;
	}

	public JMethod getActiveMethod() {
		return activeMethod;
	}

	public JClass getClass(String name) {
		// name = Type.getType(name).getInternalName();
		/*
		 * if(name==null) System.out.println(name);
		 */
		name = Type.getObjectType(name).getClassName();
		name = name.replace('.', '/');
		name = name.replace("[]", "");
		JClass theclass = classes.get(name);
		if (theclass == null) {
			theclass = addClass(name);
		}
		return theclass;
	}

	public JClass addClass(String name) {
		name = Type.getObjectType(name).getClassName();
		name = name.replace('.', '/');
		JClass theclass = new JClass(name);
		classes.put(name, theclass);
		if (!name.equals("void") && !name.equals("int") && !name.equals("float") && !name.equals("double")
				&& !name.equals("boolean") && !name.equals("short") && !name.equals("byte") && !name.equals("char")
				&& !name.equals("long"))
			toParse.add(name);
		return theclass;
	}

	public void parse() {
		while (!toParse.isEmpty()) {
			String c = toParse.remove();
			try {
				ClassReader reader = new ClassReader(c);
				ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, this);
				ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, this);
				ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, this);
				reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			} catch (Exception e) {
				System.out.println(c);
				e.printStackTrace();
			}
		}
	}

	public JMethod parseCalls(String c, String method, String desc, int depth) {
		if (depth == 0)
			return null;
		try {
			JMethod m = getClass(c).getMethod(method, desc);
			if (m != null && m.stackFramed())
				return m;
			ClassReader reader = new ClassReader(c);
			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, this);
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, declVisitor, this);
			ClassVisitor sequenceDeclarationVisitor = new StackFrameVisitor(Opcodes.ASM5, methodVisitor, this,
					method, desc);
			reader.accept(sequenceDeclarationVisitor, ClassReader.EXPAND_FRAMES);
			getClass(c).getMethod(method, desc).setStackFramed(true);
			for (MethodInvokation in : getClass(c).getMethod(method, desc).virtuals) {
				in.caller = getClass(c);
				in.m = parseCalls(in.owner, in.method, in.desc, depth - 1);
			}
		} catch (Exception e) {

		}
		return getClass(c).getMethod(method, desc);
	}
}
