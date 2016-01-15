package parser.test;
public class Bone extends Toy {
	private String color;
	private boolean squeaks;
	
	public Bone(String color, boolean squeaks) {
		super(color);
		this.squeaks = squeaks;
	}
	
	public void squeak() {
		if(this.squeaks) {
			System.out.println("squeak squeak");
		}
	}
}
