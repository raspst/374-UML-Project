package parser.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import uml.detector.SingletonDetector;
import uml.parser.Design;
import uml.parser.DesignParser;
import uml.parser.PrintFactory;
import uml.types.JClass;

public class TestUMLElements {
//	ClassContainer container;
//	JClass c;
//	JMethod m;
//	JField f;
//	
//	@Before
//	public final void setUp() {
//		container = new ClassContainer();
//		Design d = DesignParser.parseFile("parser.txt");
//		String[] args = {"parser.test.Animal", "parser.test.Bone", "parser.test.Dog", "parser.test.Toy"};
//		try {
//			DesignParser.parseDesign(d);
//			PrintFactory pf = new PrintFactory(d);
//			pf.printContainer();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	@Test
//	public void testAccess() {
//		// Animal access test
//		c = container.getClass("Animal");
//		m = c.getMethod("makeSound");
//		assert(m.getAccess().equals("+"));
//		
//		// Toy access tests
//		c = container.getClass("Toy");
//		m = c.getMethod("getColor");
//		f = c.getField("color");
//		assert(f.getAccess().equals("-"));
//		assert(m.getAccess().equals("+"));
//		
//		// Dog access tests
//		c = container.getClass("parser/test/Dog"); // this shouldn't need to happen!!
//		m = c.getMethod("makeSound");
//		assert(m.getAccess().equals("+"));
//		m = c.getMethod("getBreed");
//		assert(m.getAccess().equals("+"));
//		m = c.getMethod("giveBone");
//		assert(m.getAccess().equals("+"));
//		m = c.getMethod("eatBone");
//		assert(m.getAccess().equals("#"));
//		m = c.getMethod("getEnemy");
//		assert(m.getAccess().equals("-"));
//		f = c.getField("bone");
//		assert(f.getAccess().equals("+"));
//		f = c.getField("breed");
//		assert(f.getAccess().equals("-"));
//		
//		// Bone access tests
//		c = container.getClass("parser/test/Bone"); // this also shouldn't need to happen!!
//		m = c.getMethod("squeak");
//		assert(m.getAccess().equals("+"));
//		f = c.getField("color");
//		assert(f.getAccess().equals("-"));
//		f = c.getField("squeaks");
//		assert(f.getAccess().equals("#"));
//		
//		// Cat access tests
//		c = container.getClass("parser/test/Cat");
//		m = c.getMethod("makeSound");
//		assert(m.getAccess().equals("+"));
//	}
//	
//	@Test
//	public void testInterface() {
//		c = container.getClass("Animal");
//		assert(c.isInterface());
//		c = container.getClass("Toy");
//		assert(!c.isInterface());
//		c = container.getClass("parser/test/Dog");
//		assert(!c.isInterface());
//		c = container.getClass("parser/test/Bone");
//		assert(!c.isInterface());
//		c = container.getClass("parser/test/Cat");
//		assert(!c.isInterface());
//	}
//	
//	@Test
//	public void testImplements() {
//		c = container.getClass("parser/test/Dog");
//		assert(c.getInterfaces().contains(container.getClass("Animal")));
//		c = container.getClass("parser/test/Cat");
//		assert(c.getInterfaces().contains(container.getClass("Animal")));
//	}
//	
//	@Test
//	public void testInheritance() {
//		c = container.getClass("parser/test/Bone");
//		assert(c.getSuper().equals(container.getClass("Toy")));
//	}
//	
//	@Test
//	public void testUses() {
//		c = container.getClass("parser/test/Dog");
//		assert(c.getUses().containsValue(container.getClass("parser/test/Bone")));
//		assert(c.getUses().containsValue(container.getClass("parser/test/Cat")));
//	}
//	
//	@Test
//	public void testAssociates() {
//		c = container.getClass("parser/test/Dog");
//		assert(c.getAssociates().contains(container.getClass("parser/test/Bone")));
//	}
	Design d;
	PrintFactory pf;
	@Before
	public void setup() {
		d = DesignParser.parseFile("testcases.txt");
			d.parse();
		pf = new PrintFactory(d);
		new SingletonDetector(d);
	}
	@Test
	public void checkSingleton() {
		/*JClass bone = d.getClass("parser/test/Bone");
		assertFalse(bone.isSingleton());
		JClass cat = d.getClass("parser/test/Cat");
		assertFalse(cat.isSingleton());
		JClass boiler = d.getClass("parser/test/ChocolateBoiler");
		assertTrue(boiler.isSingleton());
		JClass dog = d.getClass("parser/test/Dog");
		assertFalse(dog.isSingleton());
		JClass singletonTest = d.getClass("parser/test/SingletonTest");
		assertTrue(singletonTest.isSingleton());
		JClass toy = d.getClass("parser/test/Toy");
		assertFalse(toy.isSingleton());
		
		JClass calendar = d.getClass("java/util/Calendar");
		assertFalse(calendar.isSingleton());
		JClass runtime = d.getClass("java/lang/Runtime");
		assertTrue(runtime.isSingleton());
		JClass desktop = d.getClass("java/awt/Desktop");
		assertFalse(desktop.isSingleton());
		JClass filter = d.getClass("java/io/FilterInputStream");
		assertFalse(filter.isSingleton());*/
		
	}
}
