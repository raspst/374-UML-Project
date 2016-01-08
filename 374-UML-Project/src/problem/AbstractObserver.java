package problem;

import java.nio.file.Path;

public class AbstractObserver implements IObserver{
	private String suffix;
	private String event;
	public AbstractObserver(String suffix, String event) {
		this.suffix=suffix;
		this.event=event;
	}
	@Override
	public void update(Path file) {
	}

	@Override
	public boolean isSuffix(String suffix) {
		return this.suffix.equals("all")||suffix.equals(this.suffix);
	}

	@Override
	public boolean isEvent(String event) {
		return this.event.equals("all")||event.equals(this.event);
	}

}
