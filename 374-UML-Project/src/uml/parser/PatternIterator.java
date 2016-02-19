package uml.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import uml.detector.PatternDetector;
import uml.pattern.PatternContainer;
import uml.types.JClass;

public class PatternIterator {
	private ArrayList<PatternContainer> containers = new ArrayList<>();
	private HashSet<JClass> classes = new HashSet<>();
	private HashMap<JClass, ParserClass> classMap = new HashMap<>();

	public PatternIterator(ArrayList<PatternDetector> detectors) {
		for (PatternDetector detector : detectors) {
			ArrayList<PatternContainer> cont = detector.searchClasses();
			classes.addAll(detector.getClasses());
			containers.addAll(cont);
		}
		for(JClass c : classes)classMap.put(c, new ParserClass(c, classMap));
		for(ParserClass c:classMap.values())c.populateHierachy();
		for(PatternContainer container:containers)container.getAnnotation(classMap.get(container.getRoot()));
		System.out.println("digraph G {\nfontname = \"Bitstream Vera Sans\"\n" + "fontsize = 8\n"

				+ "node [\nfontname = \"Bitstream Vera Sans\"fontsize = 8\nshape = \"record\"\n]" +

		"edge [\nfontname = \"Bitstream Vera Sans\"\nfontsize = 8\n]");
		for(ParserClass c:classMap.values())c.printClass();
		System.out.println("edge [arrowhead = onormal]");
		System.out.println(printInheritance());
		System.out.println("edge [style = dotted]");
		System.out.println(printImplements());
		System.out.println("edge [arrowhead = vee]");
		System.out.println(printUses());
		System.out.println("edge [style = solid]");
		System.out.println(printAssociates());
		System.out.println("}");
	}
	
	public String printInheritance(){
		StringBuilder sb = new StringBuilder();
		for (ParserClass cl:classMap.values()) {
			if (cl.getSuper()!=null) {
				sb.append(cl.wrappedClass().getTopName() + "->" + cl.getSuper().wrappedClass().getTopName() + "\n");
			}
		}
		return sb.toString();
	}
	
	public String printImplements() {
		StringBuilder sb = new StringBuilder();
		for (ParserClass cl:classMap.values()) {
			for (ParserClass j : cl.getInterfaces()) {
					sb.append(cl.wrappedClass().getTopName() + "->" + j.wrappedClass().getTopName() + "\n");
			}
		}
		return sb.toString();
	}
	
	public String printAssociates() {
		StringBuilder sb = new StringBuilder();
		for (ParserClass pcl:classMap.values()) {
			Iterator<JClass> it = pcl.wrappedClass().getAssociates().iterator();
			while (it.hasNext()) {
				ParserClass cl = pcl.mapClass(it.next());
				if (cl!=null) {
					sb.append(pcl.wrappedClass().getTopName() + "->" + cl.wrappedClass().getTopName()); // + "\n");
					String association = pcl.getAssociationsArrowAnnotation(cl.wrappedClass().getTopName());
					if(association != null) {
						sb.append(association + " ");
					}
					sb.append("\n");
				}
			}
		}
		return sb.toString();
	}

	public String printUses() {
		StringBuilder sb = new StringBuilder();
		for (ParserClass pcl:classMap.values()) {
			for (JClass cla : pcl.wrappedClass().getUses().values()) {
				ParserClass pc=pcl.mapClass(cla);
				if (pc!=null)
					if (!pcl.wrappedClass().getAssociates().contains(cla)) {
						sb.append(pcl.wrappedClass().getTopName() + "->" + cla.getTopName());// + "\n");
						for(String s: pcl.getPatterns()) {
							sb.append(pcl.getUsesArrowAnnotation(s) + " ");
						}
						sb.append("\n");
					}
			}
		}
		return sb.toString();
	}
}
