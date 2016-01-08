package problem;

import java.nio.file.Path;

public class JavaLauncher extends Launcher implements IObserver {

	public JavaLauncher(AppLauncher launcher) {
		super("class", "java", launcher);
	}
	
	@Override
	public void update(Path file) {
		//Java did not like the relative main path and I do not know how to resolve this
		String arg = file.toString();
		StringBuilder sb = new StringBuilder("-cp ");
		sb.append(arg.substring(1,arg.lastIndexOf("\\")));
		sb.append(" ");
		sb.append(arg.substring(arg.lastIndexOf("\\")+1,arg.lastIndexOf(".")));
		arg=sb.toString();
		// Run the application
		System.out.println(arg);
		launcher.launchProcess(command,arg);
	}

}
