package parser.test;

public class Cat implements Animal {
	Dog d = new Dog("minpin");
	@Override
	public void makeSound() {
		// TODO Auto-generated method stub
		//Object o = new Object();
		//o.notify();
		System.out.println("Meow");
	}
	
	public Dog doggyDog(){
		return d;
	}
}
