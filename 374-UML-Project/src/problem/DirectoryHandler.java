package problem;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DirectoryHandler implements ISubject {
	private ArrayList observers;
	private String eventName;
	private Path file;

	public DirectoryHandler(AppLauncher appLauncher) {
		observers = new ArrayList();
	}

	public void registerObserver(IObserver o) {
		observers.add(o);
	}

	public void removeObserver(IObserver o) {
		int i = observers.indexOf(o);
		if (i >= 0) {
			observers.remove(i);
		}
	}

	@Override
	public void notifyObservers() {
		boolean handled = false;
		for (int i = 0; i < observers.size(); i++) {
			IObserver observer = (IObserver) observers.get(i);
			// Make sure that the suffix and event types apply to the observer
			if (observer.isEvent(eventName) && observer.isSuffix(getSuffix(file.toString()))) {
				observer.update(file);
				handled = true;
			}
		}
		if (eventName.equals("ENTRY_CREATE")&&!handled)
			System.err.format("No support available for: %s...%n", file);
	}

	/**
	 * This method gets called when ever the directory being monitored changes.
	 * 
	 * @param eventName
	 *            One of the following three strings:
	 *            <ol>
	 *            <li>ENTRY_CREATE - When a file/folder gets created.</li>
	 *            <li>ENTRY_DELETE - When a file/folder gets deleted.</li>
	 *            <li>ENTRY_MODIFY - When a file/folder get modified.</li>
	 *            </ol>
	 * @param file
	 *            The file that generate the event
	 * @throws IOException
	 */
	public void entryChange(String eventName, Path file) {
		this.eventName = eventName;
		this.file = file;
		notifyObservers();
	}

	static String getSuffix(String file) {
		try {
			return file.substring(file.lastIndexOf(".") + 1);
		} catch (Exception e) {
			return "";
		}
	}

}
