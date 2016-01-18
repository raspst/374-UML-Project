package uml.parser;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import uml.visitors.ClassDeclarationVisitor;
import uml.visitors.ClassFieldVisitor;
import uml.visitors.ClassMethodVisitor;

public class DesignParser {
	public static void main(String[] args) throws IOException {
		Design d = parseFile("parser.txt");
		parseDesign(d);
		PrintFactory pf = new PrintFactory(d);
		//pf.printContainer();
		//BufferedReader b = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(new byte[2])));
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
	
	public static Design parseFile(String file){
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
			ClassReader reader = new ClassReader(className);
			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, d.getContainer());
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, d.getContainer());
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, d.getContainer());
			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			// System.out.println(className);
		}
	}
}
