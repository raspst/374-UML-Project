package uml.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import uml.detector.CompositeDetector;
import uml.detector.DecoratorDetector;
import uml.detector.PatternDetector;
import uml.detector.SingletonDetector;
import uml.parser.gui.MainWindow;

public class DesignParser {
	public static void main(String[] args) throws IOException {
		Design d = parseFile("composite.txt");
		d.parse();
//		System.out.println("Method Signature formatting: ");
//		System.out.println("(<args>)<return>");
//		System.out.println("Types:");
//		System.out.println("B-byte C-char D-double F-float I-int J-long");
//		System.out.println("S-short V-void Z-boolean [-array L<class>;");
		// JMethod m =
		// d.getContainer().parseCalls("java/util/Collections","shuffle","(Ljava/util/List;)V",5);
		/*JMethod m = d.getContainer().parseCalls("DesignParser", "printStack",
				"(Ljava/util/ArrayList;)Lparser/test/Cat;", 3);*/
		PrintFactory pf = new PrintFactory(d);
		new SingletonDetector(d);
		new DecoratorDetector(d);
		ArrayList<PatternDetector> pd = new ArrayList<>();
		pd.add(new CompositeDetector(d));
		//pd.add(new AdapterDetector(d));
		new PatternIterator(pd);
		MainWindow w = new MainWindow();
		w.createAndShowGUI();
		//pf.printContainer();
		//printStack("java/util/Collections", m, 0);
		System.out.println("\n");
		//printCalls(m, 0);
		//new SingletonDetector(d).printFields();
		//NodeContainer nc = new NodeContainer();
		//java/lang/Runtime
		//java/awt/Desktop
		//java/util/Calendar
		//nc.addClass("parser/test/SingletonTest");
		//nc.parse();
//		 pf.printContainer();
		// BufferedReader b = new BufferedReader(new InputStreamReader(new
		// ByteArrayInputStream(new byte[2])));
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
}
