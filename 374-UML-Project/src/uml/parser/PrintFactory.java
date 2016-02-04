package uml.parser;

import java.util.Iterator;

import uml.types.JClass;

public class PrintFactory {
	private Design d;
	public PrintFactory(Design d) {
		this.d=d;
	}

	public void printContainer() {
		System.out.println("digraph G {\nfontname = \"Bitstream Vera Sans\"\n" + "fontsize = 8\n"

				+ "node [\nfontname = \"Bitstream Vera Sans\"fontsize = 8\nshape = \"record\"\n]" +

		"edge [\nfontname = \"Bitstream Vera Sans\"\nfontsize = 8\n]");
		System.out.println(printGraphViz());
		System.out.println(printInheritance());
		System.out.println("edge [style = dotted]");
		System.out.println(printImplements());
		System.out.println("edge [arrowhead = vee]");
		System.out.println(printUses());
		System.out.println("edge [style = solid]");
		System.out.println(printAssociates());
		System.out.println("}");
	}

	public String printGraphViz(){
		StringBuilder sb = new StringBuilder();
		for (String name : d.getClassNames()) {
			JClass c = d.getClass(name);
			sb.append(c.getGraphViz()+'\n');
			if(c.isSingleton()) {
				sb.append("\tcolor=blue\n");
			}
			sb.append("]\n");
		}
		return sb.toString();
	}
	
	public String printInheritance() {
		StringBuilder sb = new StringBuilder();
		for (String className : d.getClassNames()) {
			JClass c = d.getClass(className);
			if (d.isWhitelisted(c.getSuper())) {
				// Don't want to print Object in UML diagram
				sb.append(c.getTopName() + "->" + c.getSuper().getTopName() + '\n');
			}
		}
		return sb.toString();
	}

	public String printImplements() {
		StringBuilder sb = new StringBuilder();
		for (String className : d.getClassNames()) {
			JClass c = d.getClass(className);
			for (JClass j : c.getInterfaces()) {
				if (d.isWhitelisted(j)) {
					sb.append(c.getTopName() + "->" + j.getTopName() + "\n");
				}
			}
		}
		return sb.toString();
	}

	public String printAssociates() {
		StringBuilder sb = new StringBuilder();
		for (String className : d.getClassNames()) {
			JClass c = d.getClass(className);
			Iterator<JClass> it = c.getAssociates().iterator();
			while (it.hasNext()) {
				JClass cl = it.next();
				if (d.isWhitelisted(cl))
					sb.append(c.getTopName() + "->" + cl.getTopName() + "\n");
			}
		}
		return sb.toString();
	}

	public String printUses() {
		StringBuilder sb = new StringBuilder();
		for (String className : d.getClassNames()) {
			JClass c = d.getClass(className);
			for (JClass cl : c.getUses().values()) {
				if (d.isWhitelisted(cl))
					if (!c.getAssociates().contains(cl)) {
						sb.append(c.getTopName() + "->" + cl.getTopName() + "\n");
					}
			}
		}
		return sb.toString();
	}
}
