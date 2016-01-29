package uml.node;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;
import java.util.regex.Pattern;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.AnalyzerException;
import org.objectweb.asm.tree.analysis.Frame;
import org.objectweb.asm.tree.analysis.SourceInterpreter;
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
		// name = Type.getType(name).getClassName();
		name = name.replace('.', '/');
		JClass theclass = new JClass(name);
		classes.put(name, theclass);
		if (!name.equals("V") && !name.equals("I") && !name.equals("F") && !name.equals("D") && !name.equals("Z")
				&& !name.equals("S") && !name.equals("B") && !name.equals("C") && !name.equals("J")
				&& !name.equals("void") && !name.equals("int") && !name.equals("float") && !name.equals("double")
				&& !name.equals("boolean") && !name.equals("short") && !name.equals("byte") && !name.equals("char")
				&& !name.equals("long"))
			toParse.add(name);
	}

	public JClass getClass(String name) {
		// getObjectType
		// name = Type.getType(name).getClassName();
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
				cr = new ClassReader(toParse.remove());
				ClassNode classNode = new ClassNode();
				cr.accept(classNode, 0);
				JClass c = getClass(classNode.name);
				// must be java/lang/Object
				if (classNode.superName == null)
					c.setSuper(c);
				else
					c.setSuper(getClass(classNode.superName));
				// if(classNode.interfaces!=null)
				/*
				 * for (String n : (List<String>) classNode.interfaces) { JClass
				 * cl =
				 * getClass(Type.getObjectType(classNode.name).getClassName());
				 * cl.setInterface(true); c.addInterface(cl); }
				 */
				System.out.println(c.getName());
				System.out.println(c.getSuper().getName());
				// for (JInterface i : c.getInterfaces())
				// System.out.println(i.getTopName());
				if (c.getTopName().equals("Runtime"))
					for (MethodNode method : (List<MethodNode>) classNode.methods) {
						List<LocalVariableNode> vars = (List<LocalVariableNode>) method.localVariables;
						ArrayList<JField> localVars = new ArrayList<JField>();
						Type[] argTypes = Type.getArgumentTypes(method.desc);
						int paramLength = argTypes.length;
						localVars.add(new JField("this", 0, c));
						System.out.println(method.name + "    " + paramLength);
						for (int i = 1; i <= paramLength; ++i) {
							if (vars == null) {
								localVars.add(new JField("o" + i, 1, getClass(argTypes[i - 1].getClassName())));
							} else if (i >= vars.size()) {
								localVars.add(new JField("o" + i, 1, getClass(argTypes[i - 1].getClassName())));
							} else
								localVars.add(new JField(vars.get(i).name, 1,
										getClass(Type.getType(vars.get(i).desc).getClassName())));
							//System.out.println(localVars.get(i).getName() + "    " + localVars.get(i).getType().getName());
						}
						int varLength = vars.size();
						for(int i = paramLength; i<varLength;++i)
							localVars.add(new JField(vars.get(i).name, 2, getClass(Type.getType(vars.get(i).desc).getClassName())));
						// System.out.println(Type.getType(vars.get(1).desc).getClassName());
						Analyzer a = new Analyzer(new SourceInterpreter());

						try {
							Frame[] frames = a.analyze(c.getName(), method);
							for (Frame f : frames) {
								if (f != null) {
									// if(locals>=0&&locals<=paramLength)System.out.println(localVars.get(locals).getName());
								}
								// if(locals>0)System.out.println(f.getLocal(locals-1).);
								// System.out.println(f.toString());
							}
						} catch (AnalyzerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						ListIterator<AbstractInsnNode> it = method.instructions.iterator();
						ArrayList<String>commands = new ArrayList<String>();
						while (it.hasNext()) {
							AbstractInsnNode insn = it.next();
							String s = getInsnString(insn);
							if(s.startsWith("LINENUMBER"))continue;
							int val;
							if ((val = lineInstuction(s)) != -1){
								if(!commands.isEmpty()){
								ArrayList<String> arr =new ArrayList<String>();
										arr.addAll(commands);
								new MethodInstruction(this,arr,localVars);
								commands.clear();
								}
								System.out.println("Instruction: " + lineInstuction(s));
							}
							else{
								commands.add(s);
							}
						}
						if(!commands.isEmpty()){
							ArrayList<String> arr =new ArrayList<String>();
									arr.addAll(commands);
							new MethodInstruction(this,arr,localVars);
							commands.clear();
							}
						// method.parameters
						int i = 0;
						// System.out.println(len);
						// if(len>0)System.out.println(argTypes[0]);
						// System.out.println(vars.size());
						// System.out.println(method.desc);
						// if(vars!=null&&len<=vars.size())
						// JMethod m = new JMethod(c, method.name,
						// method.access,
						// getClass(Type.getReturnType(method.desc).getClassName()),
						// params, localVars, method.desc);
						// System.out.println(m.getAccess() + " " +
						// m.getReturn().getTopName() + " " + m.getName());
						// for (JField f : m.getLocalVars())
						// System.out.println(f.getName() + " " +
						// f.getType().getTopName());
					}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int lineInstuction(String in) {
		String pattern = "^(L)([0-9]+)$";
		if (Pattern.matches(pattern, in))
			return Integer.parseInt(in.substring(1));
		return -1;
	}

	public String getInsnString(AbstractInsnNode node) {
		node.accept(mp);
		StringWriter writer = new StringWriter();
		printer.print(new PrintWriter(writer));
		printer.getText().clear();
		return writer.toString().trim();
	}

	private static Printer printer = new Textifier();
	private static TraceMethodVisitor mp = new TraceMethodVisitor(printer);
}
