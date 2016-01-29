package parser.test;

public class ChocolateBoiler {
	private boolean empty;
	private boolean boiled;
	private static final ChocolateBoiler boiler = new ChocolateBoiler();
	
	private ChocolateBoiler() {
		empty = true;
		boiled = false;
	}
	
	public void fill() {
		if (isEmpty()) {
			empty = false;
			boiled = false;
		}
	}
	
	public void drain() {
		if (!isEmpty() && isBoiled()) {
			empty = true;
		}
	}
	
	public void boil() {
		if (!isEmpty() && !isBoiled()) {
			boiled = true;
		}
	}
	
	public boolean isEmpty() {
		return empty;
	}
	
	public boolean isBoiled() {
		return boiled;
	}
	
	public static ChocolateBoiler getInstance() {
		return boiler;
	}
}
