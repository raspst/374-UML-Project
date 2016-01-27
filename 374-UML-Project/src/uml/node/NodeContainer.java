package uml.node;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.ParameterNode;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceMethodVisitor;

import uml.types.JClass;
import uml.types.JMethod;

public class NodeContainer {
	private HashMap<String, JClass> classes;
	private Queue<String> toParse;
	private JClass activeClass;
	private JMethod activeMethod;

	public NodeContainer() {
		this.classes = new HashMap<String, JClass>();
		toParse = new LinkedList<String>();
	}

	public void addClass(String name) {
		name = Type.getObjectType(name).getClassName();
		name = name.replace('.', '/');
		JClass theclass = new JClass(name);
		classes.put(name, theclass);
		if (!name.equals("void") && !name.equals("int") && !name.equals("float") && !name.equals("double")
				&& !name.equals("boolean") && !name.equals("short") && !name.equals("byte") && !name.equals("char")
				&& !name.equals("long"))
			toParse.add(name);
	}

	public JClass getClass(String c) {
		JClass theClass = classes.get(c);
		if (theClass == null) {
			theClass = new JClass(c);
			classes.put(c, theClass);
		}
		return theClass;
	}

	public void parse() {
		try {
			while (!toParse.isEmpty()) {
				ClassReader cr;
				cr = new ClassReader(toParse.remove());
				ClassNode classNode = new ClassNode();
				cr.accept(classNode, 0);
				JClass c = getClass(classNode.name);
				c.setSuper(getClass(classNode.superName));
				System.out.println(c.getName());
				System.out.println(c.getSuper().getName());
				for (MethodNode method : (List<MethodNode>) classNode.methods) {
					System.out.println(method.signature);
					InsnList insns = method.instructions;
					AbstractInsnNode node = insns.getFirst();
					while(node!=null){
						node.accept(mp);
						StringWriter writer = new StringWriter();
						printer.print(new PrintWriter(writer));
						printer.getText().clear();
						System.out.println(writer.toString());
					node = node.getNext();
					}
					if (method.parameters != null) {
						for (ParameterNode p : (List<ParameterNode>) method.parameters) {
							System.out.println("param" + p.name);
						}
					}
					// JMethod m = new JMethod(method.name, method.access,
					// Type.getReturnType(method.desc), method.parameters,
					// method.desc);
					System.out.println(method.access + " " + Type.getReturnType(method.desc) + "    " + method.name);
					for (LocalVariableNode var : (List<LocalVariableNode>) method.localVariables) {

						System.out.println(var.name + "    " + var.desc);
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static Printer printer = new Textifier();
	private static TraceMethodVisitor mp = new TraceMethodVisitor(printer);
}
