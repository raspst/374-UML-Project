package parser.test;
public class Dog implements Animal {
	public Bone bone;
	private String breed;
	
	public Dog(String breed) {
		this.breed = breed;
	}
	
	@Override
	public void makeSound() {
		// TODO Auto-generated method stub
		System.out.println("Woof woof");
	}
	
	public String getBreed() {
		return this.breed;
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
	
	private Cat getEnemy() {
		return new Cat();
	}

	
}
