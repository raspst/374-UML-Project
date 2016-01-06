

import java.util.Arrays;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class ClassDeclarationVisitor extends ClassContainerVisitor {

	public ClassDeclarationVisitor(int arg0,ClassContainer container) {
		super(arg0,container);
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName,
			String[] interfaces) {
		boolean isClass=true;
        if((access&Opcodes.ACC_INTERFACE)!=0){
            isClass=false;
        }
        if(!isClass){
		JInterface inter = container.getInterface(name);
        }
        else{
    	JClass c = container.getClass(name);
    	c.setAccess(access);
		JClass superClass = container.getClass(superName);
		c.setSuper(superClass);
		for(String i:interfaces){
		c.addInterface(container.getInterface(i));
		}
		System.out.println("Class: " + c.getName() +" extends "+c.getSuper().getName()+" implements "+Arrays.toString(c.getInterfaces().toArray()));
        }
		super.visit(version, access, name, signature, superName, interfaces);
	}

}
