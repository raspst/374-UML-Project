package parser.test;

public class SingletonTest {
	private static final SingletonTest thisInstance = new SingletonTest();
	
	private SingletonTest() {}
	
	public static SingletonTest getInstance() {
		return thisInstance;
	}
}
