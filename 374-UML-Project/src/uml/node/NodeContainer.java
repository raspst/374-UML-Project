package uml.node;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
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
import uml.types.JField;
import uml.types.JInterface;
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
		if(Type.getObjectType(name)!=null)
		name = Type.getObjectType(name).getClassName();
		name = name.replace('.', '/');
		JClass theclass = new JClass(name);
		classes.put(name, theclass);
		if (!name.equals("V") && !name.equals("I") && !name.equals("F") && !name.equals("D")
				&& !name.equals("Z") && !name.equals("S") && !name.equals("B") && !name.equals("C")
				&& !name.equals("J")&&!name.equals("void") && !name.equals("int") && !name.equals("float") && !name.equals("double")
				&& !name.equals("boolean") && !name.equals("short") && !name.equals("byte") && !name.equals("char")
				&& !name.equals("long"))
			toParse.add(name);
	}

	public JClass getClass(String name) {
		if(Type.getObjectType(name)!=null)
			name = Type.getObjectType(name).getClassName();
		name = name.replace('.', '/');
		name = name.replace("[]", "");
		JClass theClass = classes.get(name);
		if (theClass == null) {
			addClass(name);
			return getClass(name);
		}
		return theClass;
	}

	public void parse() {
		try {
			while (!toParse.isEmpty()) {
				ClassReader cr;
				System.out.println(toParse.peek());
				cr = new ClassReader(toParse.remove());
				ClassNode classNode = new ClassNode();
				cr.accept(classNode, 0);
				JClass c = getClass(Type.getObjectType(classNode.name).getClassName());
				//must be java/lang/Object
				if (classNode.superName == null)
					c.setSuper(c);
				else
					c.setSuper(getClass(Type.getObjectType(classNode.superName).getClassName()));
				// if(classNode.interfaces!=null)
				for (String n : (List<String>) classNode.interfaces) {
					JClass cl = getClass(Type.getObjectType(classNode.name).getClassName());
					cl.setInterface(true);
					c.addInterface(cl);
				}
				System.out.println(c.getName());
				System.out.println(c.getSuper().getName());
//				for (JInterface i : c.getInterfaces())
//					System.out.println(i.getTopName());
				for (MethodNode method : (List<MethodNode>) classNode.methods) {
					// System.out.println(method.signature);
					InsnList insns = method.instructions;
					AbstractInsnNode node = insns.getFirst();
					while (node != null) {
						node.accept(mp);
						StringWriter writer = new StringWriter();
						printer.print(new PrintWriter(writer));
						printer.getText().clear();
						// System.out.println(writer.toString());
						node = node.getNext();
					}
					ArrayList<JField> localVars = new ArrayList<JField>();
					ArrayList<JField> params = new ArrayList<JField>();
					Type[] argTypes = Type.getArgumentTypes(method.desc);
					int len = argTypes.length;
					List<LocalVariableNode> vars = (List<LocalVariableNode>) method.localVariables;
					int i = 0;
					if(vars!=null&&vars.size()>0 && vars.get(0).desc.equals("this")){
						localVars.add(new JField("this", 0, c));
						++i;
					}
					//System.out.println(len);
					//if(len>0)System.out.println(argTypes[0]);
					//System.out.println(vars.size());
					//System.out.println(method.desc);
					if(vars!=null&&len<=vars.size())
					for (; i < len; ++i) {
						LocalVariableNode n = vars.get(i);
						localVars.add(new JField(n.name, 1, getClass(Type.getType(n.desc).getClassName())));
						params.add(new JField(n.name, 1, getClass(Type.getType(n.desc).getClassName())));
					}
					int sz = 0;
					if(vars!=null)sz=vars.size();
					//if(len==0)len+=1;
					for (i = len; i < sz; ++i) {
						LocalVariableNode n = vars.get(i);
						localVars.add(new JField(n.name, 2, getClass(Type.getType(n.desc).getClassName())));
					}
					JMethod m = new JMethod(c, method.name, method.access,
							getClass(Type.getReturnType(method.desc).getClassName()), params, localVars, method.desc);
					System.out.println(m.getAccess() + " " + m.getReturn().getTopName() + "    " + m.getName());
//					for (JField f : m.getLocalVars())
//						System.out.println(f.getName() + "    " + f.getType().getTopName());
//					for (LocalVariableNode var : (List<LocalVariableNode>) method.localVariables) {
//						// System.out.println(var.name + " " + var.desc);
//					}
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
