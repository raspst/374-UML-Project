import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class TestUMLElements {
	ClassContainer container;
	JClass c;
	
	@Before
	public final void setUp() {
		container = new ClassContainer();
		DesignParser.parseDesign("", container);
	}
	@Test
	public void test() {

	}

}
