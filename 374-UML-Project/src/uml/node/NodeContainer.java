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
					//System.out.println(method.signature);
					InsnList insns = method.instructions;
					AbstractInsnNode node = insns.getFirst();
					while(node!=null){
						node.accept(mp);
						StringWriter writer = new StringWriter();
						printer.print(new PrintWriter(writer));
						printer.getText().clear();
						//System.out.println(writer.toString());
					node = node.getNext();
					}
					ArrayList<JField> localVars = new ArrayList<JField>();
					ArrayList<JField> params = new ArrayList<JField>();
					localVars.add(new JField("this", 0, c));
					Type[] argTypes = Type.getArgumentTypes(method.desc);
					int len = argTypes.length+1;
					List<LocalVariableNode> vars = (List<LocalVariableNode>) method.localVariables;
					for(int i=1; i<len;++i){
						LocalVariableNode n = vars.get(i);
						localVars.add(new JField(n.name, 1, getClass(n.desc)));
						params.add(new JField(n.name, 1, getClass(n.desc)));
					}
					int sz = vars.size();
					for(int i = len;i<sz;++i ){
						LocalVariableNode n = vars.get(i);
						localVars.add(new JField(n.name, 2, getClass(n.desc)));
					}
					JMethod m = new JMethod(c,method.name, method.access,
					getClass(Type.getReturnType(method.desc).getClassName()), params,localVars,
					 method.desc);
					System.out.println(m.getAccess() + " " + m.getReturn().getTopName() + "    " + m.getName());
					for(JField f : m.getParams())System.out.println(f.getName()+"    "+f.getType().getTopName());
					for (LocalVariableNode var : (List<LocalVariableNode>) method.localVariables) {
						//System.out.println(var.name + "    " + var.desc);
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
