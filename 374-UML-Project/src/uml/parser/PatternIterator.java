package uml.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
		System.out.println(new RenderObject(classMap.values()).getDesign());
	}
	

}
