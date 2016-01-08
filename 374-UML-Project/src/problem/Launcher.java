package problem;

import java.nio.file.Path;

public class Launcher extends AbstractObserver {
	
	protected AppLauncher launcher;
	String command;

	public Launcher(String suffix, String command, AppLauncher launcher) {
		super(suffix, "ENTRY_CREATE");
		this.command = command;
		this.launcher = launcher;
	}

	@Override
	public void update(Path file) {
		String arg = file.toString();
		// Run the application
		launcher.launchProcess(command, arg);
	}

}
