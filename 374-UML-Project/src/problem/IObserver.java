package problem;

import java.nio.file.Path;

public interface IObserver {
	public void update(Path file);
	public boolean isSuffix(String suffix);
	public boolean isEvent(String event);
}
