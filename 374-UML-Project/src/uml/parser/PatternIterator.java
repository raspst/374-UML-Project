package uml.parser;

import java.awt.Container;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import uml.detector.PatternDetector;
import uml.pattern.PatternContainer;
import uml.types.JClass;

public class PatternIterator {
	private HashSet<PatternContainer> containers = new HashSet<>();
	private HashSet<JClass> classes = new HashSet<>();
	private HashMap<JClass, ParserClass> classMap = new HashMap<>();
	private HashMap<JClass, ParserClass> parsedDesign = new HashMap<>();
	private HashMap<String, ArrayList<PatternContainer>>  patterns = new HashMap<>();
	public PatternIterator(ArrayList<PatternDetector> detectors, Design d) {
//		for(String s:d.getClassNames()){
//			classMap.put(d.getClass(s),new ParserClass(d.getClass(s), classMap));
//		}
//		for(ParserClass c:parsedDesign.values())c.populateHierachy();
		System.out.println(new RenderObject(parsedDesign.values()).getDesign());
		for (PatternDetector detector : detectors) {
			ArrayList<PatternContainer> cont = detector.searchClasses();
			//classes.addAll(detector.getClasses());
			containers.addAll(cont);
			patterns.put(detector.getName(), cont);
		}
		annotate();
		System.out.println(getGraphViz());
		//asdf();
	}
	
	public String getGraphViz() {
		return new RenderObject(classMap.values()).getDesign();
	}
	
	public void addContainer(PatternContainer c){
		containers.add(c);
	}
	
	public void removeContainer(PatternContainer c){
		containers.remove(c);
	}
	
	public void annotate(){
		classes.clear();
		classMap.clear();
		for(PatternContainer container:containers)classes.addAll(container.getClasses());
		for(JClass c : classes)classMap.put(c, new ParserClass(c, classMap));
		for(ParserClass c:classMap.values())c.populateHierachy();
		for(PatternContainer container:containers)container.getAnnotation(classMap.get(container.getRoot()));
	}
	
	public void asdf(){
		for(String pattern:patterns.keySet()){
			System.out.println(pattern);
			for(PatternContainer pc:patterns.get(pattern)){
				System.out.println(" "+pc.getName());
			}
		}
	}
	

}
