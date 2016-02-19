package uml.parser;

import java.util.Collection;
import java.util.Iterator;

import uml.types.JClass;
import uml.types.JField;

public class RenderObject {
	private Collection<ParserClass> classes;
	private String design;

	public RenderObject(Collection<ParserClass> classes) {
		this.classes = classes;
		StringBuilder sb = new StringBuilder("digraph G {\nfontname = \"Bitstream Vera Sans\"\n" + "fontsize = 8\n"
				+ "node [\nfontname = \"Bitstream Vera Sans\"fontsize = 8\nshape = \"record\"\n]"
				+ "edge [\nfontname = \"Bitstream Vera Sans\"\nfontsize = 8\n]");
		sb.append(printClasses());
		sb.append(printInheritance());
		sb.append(printImplements());
		sb.append(printUses());
		sb.append(printAssociates());
		sb.append("}");
		design = sb.toString();
	}

	public String getDesign() {
		return design;
	}

	public String printClasses() {
		StringBuilder sb = new StringBuilder();
		for (ParserClass c : classes) {
			JClass theclass = c.wrappedClass();
			String name = theclass.getTopName();
			if (!theclass.isInterface()) {
				sb.append(name + " [\n\tlabel = \"{");
				for (String st : c.getPatterns()) {
					sb.append("\t" + st + "\\l");
				}
				sb.append(name + "|");
			} else {
				sb.append(name + " [\n\tlabel = \"{interface\n"); // name +
																	// "|");
				for (String st : c.getPatterns()) {
					sb.append("\t" + st + "\\l");
				}
				sb.append(name + "|");
			}
			Iterator<JField> it = theclass.getFields().iterator();
			while (it.hasNext()) {
				sb.append(it.next().getGraphViz() + "\\l");
			}
			sb.append("|");
			for (int i = 0; i < theclass.getMethods().size(); i++) {
				if (!theclass.getMethods().get(i).getName().equals("<init>"))
					sb.append(theclass.getMethods().get(i).getGraphViz() + "\\l");
			}
			sb.append("}\"\n");
			for (String s : c.getPatterns()) {
				String color = c.getColor(s);
				if (color != null) {
					sb.append("\t" + color + "\n");
				}
			}
			sb.append("]\n");
		}
		return sb.toString();
	}

	public String printInheritance() {
		StringBuilder sb = new StringBuilder("edge [arrowhead = onormal]");
		for (ParserClass cl : classes) {
			if (cl.getSuper() != null) {
				sb.append(cl.wrappedClass().getTopName() + "->" + cl.getSuper().wrappedClass().getTopName() + "\n");
			}
		}
		return sb.toString();
	}

	public String printImplements() {
		StringBuilder sb = new StringBuilder("edge [style = dotted]");
		for (ParserClass cl : classes) {
			for (ParserClass j : cl.getInterfaces()) {
				sb.append(cl.wrappedClass().getTopName() + "->" + j.wrappedClass().getTopName() + "\n");
			}
		}
		return sb.toString();
	}

	public String printUses() {
		StringBuilder sb = new StringBuilder("edge [arrowhead = vee]");
		for (ParserClass pcl : classes) {
			for (JClass cla : pcl.wrappedClass().getUses().values()) {
				ParserClass pc = pcl.mapClass(cla);
				if (pc != null)
					if (!pcl.wrappedClass().getAssociates().contains(cla)) {
						sb.append(pcl.wrappedClass().getTopName() + "->" + cla.getTopName());// +
																								// "\n");
						for (String s : pcl.getPatterns()) {
							sb.append(pcl.getUsesArrowAnnotation(s) + " ");
						}
						sb.append("\n");
					}
			}
		}
		return sb.toString();
	}

	public String printAssociates() {
		StringBuilder sb = new StringBuilder("edge [style = solid]");
		for (ParserClass pcl : classes) {
			Iterator<JClass> it = pcl.wrappedClass().getAssociates().iterator();
			while (it.hasNext()) {
				ParserClass cl = pcl.mapClass(it.next());
				if (cl != null) {
					sb.append(pcl.wrappedClass().getTopName() + "->" + cl.wrappedClass().getTopName()); // +
																										// "\n");
					String association = pcl.getAssociationsArrowAnnotation(cl.wrappedClass().getTopName());
					if (association != null) {
						sb.append(association + " ");
					}
					sb.append("\n");
				}
			}
		}
		return sb.toString();
	}
}
