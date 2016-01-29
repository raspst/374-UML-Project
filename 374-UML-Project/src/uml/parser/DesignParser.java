package uml.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;

import uml.node.NodeContainer;
import uml.types.JClass;
import uml.types.JMethod;
import uml.types.MethodInvokation;

public class DesignParser {
	public static void main(String[] args) throws IOException {
		Design d = parseFile("parser.txt");
		parseDesign(d);
		System.out.println("Method Signature formatting: ");
		System.out.println("(<args>)<return>");
		System.out.println("Types:");
		System.out.println("B-byte C-char D-double F-float I-int J-long");
		System.out.println("S-short V-void Z-boolean [-array L<class>;");
		// JMethod m =
		// d.getContainer().parseCalls("java/util/Collections","shuffle","(Ljava/util/List;)V",5);
		/*JMethod m = d.getContainer().parseCalls("DesignParser", "printStack",
				"(Ljava/util/ArrayList;)Lparser/test/Cat;", 3);*/
		PrintFactory pf = new PrintFactory(d);
		pf.printContainer();
		initialized.clear();
		//printStack("java/util/Collections", m, 0);
		System.out.println("\n");
		//printCalls(m, 0);
		//new SingletonDetector(d).printFields();
		NodeContainer nc = new NodeContainer();
		//java/lang/Runtime
		//java/awt/Desktop
		//java/util/Calendar
		//nc.addClass("parser/test/SingletonTest");
		nc.parse();
		// pf.printContainer();
		// BufferedReader b = new BufferedReader(new InputStreamReader(new
		// ByteArrayInputStream(new byte[2])));
	}

	private static void printStack(String string, JMethod m, int i) {
		System.out.println(string + ":" + string + "[a]");
		printStack(m, i);
	}

	private static HashSet<String> initialized = new HashSet<String>();

	public static void printStack(JMethod m, int depth) {
		for (MethodInvokation in : m.virtuals) {
			if (in.caller != null) {
				// for (int i = 0; i < depth; i++) {
				// System.out.print(" ");
				// }
				// System.out.println(in.caller.getName()+" "+in.owner+" "+
				// in.method + " " + in.desc + " " + in.returnType+"
				// "+in.index);
				StringBuilder s = new StringBuilder();
				if (in.method.equals("<init>") || in.method.equals("<stinit>")) {
					if (!initialized.contains(in.returnType)) {
						initialized.add(in.returnType);
						s.append("/");
						s.append(in.returnType + ":" + in.returnType);
					}
				}

				// else
				// s.append(in.caller.getName() + ":" + in.caller.getName());
				// if(in.index == 2) {
				// s.append("[a]");
				// }
				if (s.length() != 0)
					System.out.println(s.toString());
				if (in.m != null) {
					printStack(in.m, depth + 1);
				}
			}
		}
	}

	public static void printCalls(JMethod m, int depth) {
		for (MethodInvokation in : m.virtuals) {
			if (in.caller != null) {
				if (!in.owner.equals("java/io/PrintStream")) {
					StringBuilder s = new StringBuilder();
					s.append(in.caller.getName() + ":" + in.owner + ".");
					if (in.method.equals("<init>")) {
						s.append("new");
					} else if (!in.method.equals("<stinit>")) {
						s.append(in.method);
					}
					if (!in.method.equals("<stinit>")) {
						s.append(in.params.toString().replace("[", "(").replace("]", ")"));
						System.out.println(s.toString());
					}
					if (in.m != null) {
						printCalls(in.m, depth + 1);
					}
				}
			}
		}
	}

	public static void visitFiles(String pref, File dir, ArrayList<String> files) {
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {
				visitFiles(pref + f.getName() + "/", f, files);
			} else {
				files.add(pref + f.getName().substring(0, f.getName().length() - 5));
			}
		}
	}

	public static Design parseFile(String file) {
		File f = new File("in/" + file);
		Design d = null;
		if (f.exists()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(f));
				d = parseFile(br);
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return d;
	}

	public static Design parseFile(BufferedReader br) {
		Design d = new Design();
		try {
			String in;
			while ((in = br.readLine()) != null) {
				if (!in.equals("")) {
					String[] s = in.split("\\s+");
					if (s.length == 2) {
						if (s[0].equals("-w")) {
							d.whitelist(s[1]);
						}
					} else if (in.charAt(in.length() - 1) == '*') {
						String pack = in.substring(0, in.length() - 1);
						File dir = new File("src/" + pack);
						ArrayList<String> files = new ArrayList<String>();
						visitFiles(pack, dir, files);
						for (String c : files) {
							d.addClass(c);
						}
					} else
						d.addClass(in);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}

	public static void parseDesign(Design d) throws IOException {
		for (String className : d.getClassNames()) {
			// System.out.println(className);
			d.getContainer().addClass(className);
			// d.getContainer().getClass(className);
			// System.out.println(className);
		}
		d.getContainer().parse();
	}
}
