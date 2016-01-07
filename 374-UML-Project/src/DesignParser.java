

import java.io.IOException;
import java.util.ArrayList;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class DesignParser {
	public static void main(String[] args) throws IOException {
		ClassContainer container = new ClassContainer();
		JClass c;
		for(String className: args) {
			ClassReader reader = new ClassReader(className);
			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5,container);		
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor,container);
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor,container);
			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			c = container.getClass(className);
			System.out.println(c.getGraphViz());
		}
		for(String className: args) {
			c = container.getClass(className);
			System.out.println(c.printInheritance());
		}
		/*ArrayList<JInterface> interfaces = new ArrayList<JInterface>();
		interfaces.add(new JInterface("Interface1"));
		JClass testClass = new JClass("TestClass");
		testClass.addField(new JField("Field1", 1, new JClass("int")));
		testClass.addMethod(new JMethod("Method1", 2, new JClass("int"), new ArrayList<JClass>()));
		testClass.setDependencies(new JClass("Test Parent"), interfaces);
		System.out.println(testClass.getGraphViz());*/
	}
}
