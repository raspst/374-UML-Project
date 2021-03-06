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
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceMethodVisitor;

import uml.parser.Design;
import uml.types.JClass;
import uml.types.JField;
import uml.types.JMethod;
import uml.visitors.classes.ClassDeclarationVisitor;
import uml.visitors.classes.ClassFieldVisitor;

public class ClassContainer {
	private HashMap<String, JClass> classes;
	private Queue<String> toParse;
	private Design design;

	public ClassContainer(Design d) {
		this.classes = new HashMap<String, JClass>();
		design = d;
		toParse = new LinkedList<String>();
	}

	public void addClass(String name) {
		// name = Type.getType(name).getClassName();
		name = name.replace('.', '/');
		JClass theclass = new JClass(name);
		classes.put(name, theclass);
		if (design.isWhitelisted(theclass) && !name.equals("V") && !name.equals("I") && !name.equals("F") && !name.equals("D")
				&& !name.equals("Z") && !name.equals("S") && !name.equals("B") && !name.equals("C") && !name.equals("J")
				&& !name.equals("void") && !name.equals("int") && !name.equals("float") && !name.equals("double")
				&& !name.equals("boolean") && !name.equals("short") && !name.equals("byte") && !name.equals("char")
				&& !name.equals("long")){
			toParse.add(name);
			design.addClass(name);
		}
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

	@SuppressWarnings("unchecked")
	public void parse() {
		try {
			while (!toParse.isEmpty()) {
				ClassReader cr;
				JClass c = getClass(toParse.remove());
				cr = new ClassReader(c.getName());
				ClassNode classNode = new ClassNode();
				ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, classNode, design);
				ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, design,c);
				cr.accept(fieldVisitor, 0);
				// System.out.println(i.getTopName());
				for (MethodNode method : (List<MethodNode>) classNode.methods) {
					//new Analyzer(new SimpleVerifier())
					//newControlFlowEdge()
					List<LocalVariableNode> vars = (List<LocalVariableNode>) method.localVariables;
					ArrayList<JField> localVars = new ArrayList<JField>();
					Type[] argTypes = Type.getArgumentTypes(method.desc);
					int paramLength = argTypes.length;
					localVars.add(new JField("this", 0, c));
					// System.out.println(method.name + " " + paramLength);
					if (vars != null) {
						for (int i = 1; i <= paramLength; ++i) {
							if (vars == null) {
								localVars.add(new JField("o" + i, 1, getClass(argTypes[i - 1].getClassName())));
							} else if (i >= vars.size()) {
								localVars.add(new JField("o" + i, 1, getClass(argTypes[i - 1].getClassName())));
							} else
								localVars.add(new JField(vars.get(i).name, 1,
										getClass(Type.getType(vars.get(i).desc).getClassName())));
							// System.out.println(localVars.get(i).getName() + "
							// " + localVars.get(i).getType().getName());
						}
						int varLength = vars.size();
						for (int i = paramLength; i < varLength; ++i)
							localVars.add(new JField(vars.get(i).name, 2,
									getClass(Type.getType(vars.get(i).desc).getClassName())));
					}
					// System.out.println(Type.getType(vars.get(1).desc).getClassName());
					int start = 0;
					int temp = 0;
					ListIterator<AbstractInsnNode> it = method.instructions.iterator();
					ArrayList<Instruction> commands = new ArrayList<Instruction>();
					HashMap<Integer, Integer> labels = new HashMap<Integer, Integer>();
					while (it.hasNext()) {
						AbstractInsnNode insn = it.next();
						String s = getInsnString(insn);
						if (s.startsWith("LINENUMBER"))
							continue;
						int val;
						if ((val = lineInstuction(s)) != -1) {
							if (start == 0 && temp != 0)
								start = temp;
							labels.put(val, start);
							start = temp;
							// System.out.println("Instruction: " +
							// lineInstuction(s) + " " + start);
						} else {
							commands.add(new Instruction(s));
							++temp;
						}
					}
					if (!commands.isEmpty()) {
						new MethodParser(this, commands, localVars, 0);
						start = temp;
					}
					JMethod m = new JMethod(c, method.name, method.access,
							getClass(Type.getReturnType(method.desc).getClassName()), localVars, commands, method.desc);
					c.addMethod(m);
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
