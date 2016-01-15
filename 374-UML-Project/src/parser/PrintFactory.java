package parser;
import java.util.Iterator;

public class PrintFactory {
	ClassContainer container;
	String[] classes;

	public PrintFactory(ClassContainer container, String[] args) {
		this.container = container;
		classes = args;
	}

	public String printInheritance() {
		StringBuilder sb = new StringBuilder();
		for (String className : classes) {
			JClass c = container.getClass(className);
			if (container.isWhitelisted(c.getSuper())) {
				// Don't want to print Object in UML diagram
				sb.append(c.getTopName() + "->" + c.getSuper().getTopName() + '\n');
			}
		}
		return sb.toString();
	}

	public String printImplements() {
		StringBuilder sb = new StringBuilder();
		for (String className : classes) {
			JClass c = container.getClass(className);
			for (JInterface j : c.getInterfaces()) {
				if (container.isWhitelisted(j)) {
					sb.append(c.getTopName() + "->" + j.getTopName() + "\n");
				}
			}
		}
		return sb.toString();
	}

	public String printAssociates() {
		StringBuilder sb = new StringBuilder();
		for (String className : classes) {
			JClass c = container.getClass(className);
			Iterator<JClass> it = c.getAssociates().iterator();
			while (it.hasNext()) {
				JClass cl = it.next();
				if (container.isWhitelisted(cl))
					sb.append(c.getTopName() + "->" + cl.getTopName() + "\n");
			}
		}
		return sb.toString();
	}

	public String printUses() {
		StringBuilder sb = new StringBuilder();
		for (String className : classes) {
			JClass c = container.getClass(className);
			for (JClass cl : c.getUses().values()) {
				if (container.isWhitelisted(cl))
					if(!c.getAssociates().contains(cl)) {
						sb.append(c.getTopName() + "->" + cl.getTopName() + "\n");
					}
			}
		}
		return sb.toString();
	}
}
