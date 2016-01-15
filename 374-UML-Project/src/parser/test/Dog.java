package parser.test;
public class Dog implements Animal {
	private Bone bone;
	private String species;
	
	public Dog(String species) {
		this.species = species;
	}
	
	@Override
	public void makeSound() {
		// TODO Auto-generated method stub
		System.out.println("Woof woof");
	}
	
	public String getSpecies() {
		return this.species;
	}
	
	public void giveBone(Bone bone) {
		this.bone = bone;
	}
	
	protected void eatBone() {
		if(this.bone != null) {
			System.out.println("Woof yum yum");
			this.bone = null;
		}
	}

	
}
