package parser.test;

import uml.parser.Design;

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
	Design d2;
	Design d3;
	Design d4;
	Design d5;
//	PrintFactory pf;
//	PrintFactory pf2;
//	PrintFactory pf3;
//	PrintFactory pf4;
//	PrintFactory pf5;
//	@Before
//	public void setup() {
//		d = DesignParser.parseFile("testcases2.txt");
//		d.parse();
//		pf = new PrintFactory(d);
//		new SingletonDetector(d);
//		new DecoratorDetector(d);
//		new AdapterDetector(d);
//		new CompositeDetector(d);
//		d2 = DesignParser.parseFile("testcases3.txt");
//		d2.parse();
//		pf2 = new PrintFactory(d2);
//		new SingletonDetector(d2);
//		new DecoratorDetector(d2);
//		new AdapterDetector(d2);
//		new CompositeDetector(d2);
//		d3 = DesignParser.parseFile("parser.txt");
//		d3.parse();
//		pf3 = new PrintFactory(d3);
//		new SingletonDetector(d3);
//		new DecoratorDetector(d3);
//		new AdapterDetector(d3);
//		new CompositeDetector(d3);
//		d4 = DesignParser.parseFile("adaptee.txt");
//		d4.parse();
//		pf4 = new PrintFactory(d4);
//		new SingletonDetector(d4);
//		new DecoratorDetector(d4);
//		new AdapterDetector(d4);
//		new CompositeDetector(d4);
//		d5 = DesignParser.parseFile("composite.txt");
//		d5.parse();
//		pf5 = new PrintFactory(d5);
//		new SingletonDetector(d5);
//		new DecoratorDetector(d5);
//		new AdapterDetector(d5);
//		new CompositeDetector(d5);
////		pf.printContainer();
////		pf2.printContainer();
////		pf3.printContainer();
////		pf4.printContainer();
//	}
	
//	@Test 
//	public void checkDecorator() {
//		JClass inputStreamReader = d2.getClass("java/io/InputStreamReader");
//		JClass outputStreamWriter = d2.getClass("java/io/OutputStreamWriter");
//		assert(inputStreamReader.getPatterns().isEmpty());
//		assert(outputStreamWriter.getPatterns().isEmpty());
//		assert(inputStreamReader.getColor("Decorator") == null);
//		assert(inputStreamReader.getColor("Component") == null);
//		assert(outputStreamWriter.getColor("Decorator") == null);
//		assert(outputStreamWriter.getColor("Component") == null);
//		assert(inputStreamReader.getAssociationsArrowAnnotation("Decorator") == null);
//		assert(inputStreamReader.getAssociationsArrowAnnotation("Component") == null);
//		assert(outputStreamWriter.getAssociationsArrowAnnotation("Decorator") == null);
//		assert(outputStreamWriter.getAssociationsArrowAnnotation("Component") == null);
//		
//		JClass filterInputStream = d3.getClass("java/io/FilterInputStream");
//		JClass filterOutputStream = d3.getClass("java/io/FilterOutputStream");
//		JClass decryptionInputStream = d3.getClass("editor/solution/problem/DecryptionInputStream");
//		JClass encryptionOutputStream = d3.getClass("editor/solution/problem/EncryptionOutputStream");
//		JClass inputStream = d3.getClass("java/io/InputStream");
//		JClass outputStream = d3.getClass("java/io/OutputStream");
//		JClass iDecryption = d3.getClass("editor/solution/problem/IDecryption");
//		JClass iEncryption = d3.getClass("editor/solution/problem/IEncryption");
//		JClass subCipher = d3.getClass("editor/solution/problem/SubstitutionCipher");
//		JClass editApp = d3.getClass("editor/solution/problem/TextEditorApp");
//		
//		assert(filterInputStream.getPatterns().contains("Decorator"));
//		assert(filterOutputStream.getPatterns().contains("Decorator"));
//		assert(decryptionInputStream.getPatterns().contains("Decorator"));
//		assert(encryptionOutputStream.getPatterns().contains("Decorator"));
//		assert(inputStream.getPatterns().contains("Component"));
//		assert(outputStream.getPatterns().contains("Component"));
//		assert(iDecryption.getPatterns().isEmpty());
//		assert(iEncryption.getPatterns().isEmpty());
//		assert(subCipher.getPatterns().isEmpty());
//		assert(editApp.getPatterns().isEmpty());
//		
//		assert(filterInputStream.getColor("Decorator").equals("green"));
//		assert(filterInputStream.getColor("Component") == null);
//		assert(filterOutputStream.getColor("Decorator").equals("green"));
//		assert(filterOutputStream.getColor("Component") == null);
//		assert(decryptionInputStream.getColor("Decorator").equals("green"));
//		assert(decryptionInputStream.getColor("Component") == null);
//		assert(encryptionOutputStream.getColor("Decorator").equals("green"));
//		assert(encryptionOutputStream.getColor("Component") == null);
//		assert(inputStream.getColor("Decorator") == null);
//		assert(inputStream.getColor("Component").equals("green"));
//		assert(outputStream.getColor("Decorator") == null);
//		assert(outputStream.getColor("Component").equals("green"));
//		
////		assert(filterInputStream.getAssociationsArrowAnnotation("Decorator").equals("[label=decorates]"));
////		assert(filterOutputStream.getAssociationsArrowAnnotation("Decorator").equals("[label=decorates"));
//		for(JClass cl: filterInputStream.getAssociates()) {
//			String s = filterInputStream.getAssociationsArrowAnnotation(cl.getTopName());
//			if(s != null) {
//				assert(s.equals("[label=decorates]"));
//			}
//		}
//		for(JClass cl: filterOutputStream.getAssociates()) {
//			String s = filterOutputStream.getAssociationsArrowAnnotation(cl.getTopName());
//			if(s != null) {
//				assert(s.equals("[label=decorates]"));
//			}
//		}
//		assert(decryptionInputStream.getAssociationsArrowAnnotation("Decorator") == null);
//		assert(encryptionOutputStream.getAssociationsArrowAnnotation("Decorator") == null);
//		assert(inputStream.getAssociationsArrowAnnotation("Component") == null);
//		assert(outputStream.getAssociationsArrowAnnotation("Component") == null);
//		
//	}
//	
//	@Test
//	public void checkAdapter() {
//		JClass mouse = d.getClass("java/awt/event/MouseAdapter");
//		assert(mouse.getInterfaces().size() == 3);
//		for(JClass cla: mouse.getInterfaces()) {
//			assert(cla.getPatterns().contains("Target"));
//		}
//		assert(mouse.getFields().isEmpty());
//		assert(mouse.getPatterns().contains("Adapter"));
//		assert(mouse.getColor("Adapter").equals("red"));
//		for(JClass cl: mouse.getAssociates()) {
//			String s = mouse.getAssociationsArrowAnnotation(cl.getTopName());
//			if(s != null) {
//				assert(cl.getPatterns().contains("Adaptee"));
//				assert(s.equals("[label=adapts]"));
//			}
//		}
//		
//		JClass app = d4.getClass("transformer/client/App");
//		JClass adapter = d4.getClass("transformer/client/ArrayListAdapter");
//		JClass transformer = d4.getClass("transformer/lib/LinearTransformer");
//		JClass enumer = d4.getClass("java/util/Enumeration");
//		JClass iter = d4.getClass("java/util/Iterator");
//		
//		assert(app.getPatterns().isEmpty());
//		assert(adapter.getPatterns().contains("Adapter"));
//		assert(transformer.getPatterns().isEmpty());
//		assert(enumer.getPatterns().contains("Target"));
//		assert(iter.getPatterns().contains("Adaptee"));
//		
//		assert(adapter.getColor("Adapter").equals("red"));
//		assert(enumer.getColor("Target").equals("red"));
//		assert(iter.getColor("Adaptee").equals("red"));
//		
//		assert(adapter.getAssociationsArrowAnnotation("Adapter").equals("[label=adapts]"));
//		assert(enumer.getAssociationsArrowAnnotation("Target") == null);
//		assert(iter.getAssociationsArrowAnnotation("Adaptee") == null);
//	}
//	
//	@Test
//	public void checkComposite() {
//		JClass composite = d5.getClass("java/awt/Composite");
//		JClass component = d5.getClass("java/awt/Component");
//		JClass button = d5.getClass("java/awt/Button");
//		JClass check = d5.getClass("java/awt/Checkbox");
//		
//		assert(composite.getPatterns().contains("Composite"));
//		assert(component.getPatterns().contains("Component"));
//		assert(button.getPatterns().contains("Leaf Node"));
//		assert(check.getPatterns().contains("Leaf Node"));
//		
//		assert(composite.getColor("Composite").equals("yellow"));
//		assert(component.getColor("Component").equals("yellow"));
//		assert(button.getColor("Leaf Node").equals("yellow"));
//		assert(check.getColor("Leaf Node").equals("yellow"));
//	}
//	@Test
//	public void checkSingleton() {
//		/*JClass bone = d.getClass("parser/test/Bone");
//		assertFalse(bone.isSingleton());
//		JClass cat = d.getClass("parser/test/Cat");
//		assertFalse(cat.isSingleton());
//		JClass boiler = d.getClass("parser/test/ChocolateBoiler");
//		assertTrue(boiler.isSingleton());
//		JClass dog = d.getClass("parser/test/Dog");
//		assertFalse(dog.isSingleton());
//		JClass singletonTest = d.getClass("parser/test/SingletonTest");
//		assertTrue(singletonTest.isSingleton());
//		JClass toy = d.getClass("parser/test/Toy");
//		assertFalse(toy.isSingleton());
//		
//		JClass calendar = d.getClass("java/util/Calendar");
//		assertFalse(calendar.isSingleton());
//		JClass runtime = d.getClass("java/lang/Runtime");
//		assertTrue(runtime.isSingleton());
//		JClass desktop = d.getClass("java/awt/Desktop");
//		assertFalse(desktop.isSingleton());
//		JClass filter = d.getClass("java/io/FilterInputStream");
//		assertFalse(filter.isSingleton());*/
//		
//	}
}
