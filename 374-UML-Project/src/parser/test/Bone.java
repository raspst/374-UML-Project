package parser.test;
public class Bone extends Toy {
	private String color;
	protected boolean squeaks;
	
	public Bone(String color, boolean squeaks) {
		super(color);
		this.squeaks = squeaks;
	}
	
	public void squeak() {
		int i = 5;
		if(this.squeaks) {
			System.out.println("squeak squeak");
		}
		System.out.println("asdf");
		this.color="blah";
		i=6;
	}
}
