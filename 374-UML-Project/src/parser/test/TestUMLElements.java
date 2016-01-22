package parser.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import uml.parser.ClassContainer;
import uml.parser.Design;
import uml.parser.DesignParser;
import uml.parser.PrintFactory;
import uml.types.JClass;
import uml.types.JField;
import uml.types.JMethod;

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

}
