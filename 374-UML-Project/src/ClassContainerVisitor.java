import org.objectweb.asm.ClassVisitor;

public class ClassContainerVisitor extends ClassVisitor {
	protected ClassContainer container;
	public ClassContainerVisitor(int arg0, ClassContainer container) {
		super(arg0);
		this.container=container;
	}
	public ClassContainerVisitor(int arg0, ClassVisitor arg1,ClassContainer container) {
		super(arg0, arg1);
		this.container=container;
	}
}
