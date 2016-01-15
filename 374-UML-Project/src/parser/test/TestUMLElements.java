package parser.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import parser.ClassContainer;
import parser.DesignParser;
import parser.JClass;
import parser.JField;
import parser.JMethod;

public class TestUMLElements {
	ClassContainer container;
	JClass c;
	JMethod m;
	JField f;
	
	@Before
	public final void setUp() {
		container = new ClassContainer();
		String[] args = {"parser.test.Animal", "parser.test.Bone", "parser.test.Dog", "parser.test.Toy"};
		try {
			DesignParser.parseDesign(args, container);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testAccess() {
		c = container.getClass("Animal");
		m = c.getMethod("makeSound");
		assert(m.getAccess().equals("+"));
		c = container.getClass("Toy");
		m = c.getMethod("getColor");
		assert(m.getAccess().equals("+"));
	}
	
	@Test
	public void testInterface() {
		c = container.getClass("Animal");
		assert(c.isInterface());
		c = container.getClass("Toy");
		assert(!c.isInterface());
		c = container.getClass("Dog");
		assert(!c.isInterface());
		c = container.getClass("Bone");
		assert(!c.isInterface());
	}

}
