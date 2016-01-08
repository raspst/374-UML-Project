package problem;

import java.nio.file.Path;

public class FileAdded extends AbstractObserver {

	public FileAdded() {
		super("all", "ENTRY_CREATE");
	}

	@Override
	public void update(Path file) {
		System.out.println(file.getFileName().toString());
	}
}
