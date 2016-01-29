package parser.test;

public class SingletonTest {
	private static SingletonTest thisInstance;
	
	private SingletonTest() {}
	
	public static SingletonTest getInstance() {
		if(thisInstance == null) {
			thisInstance = new SingletonTest();
		}
		return thisInstance;
	}
}
