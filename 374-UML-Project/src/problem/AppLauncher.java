package problem;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.File;
import java.io.IOException;

/*
 * Copyright (c) 2008, 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * Modified By: 
 * 		Chandan R. Rupakheti
 */
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class has been modified from the original WatchDir program found at: 
 * https://docs.oracle.com/javase/tutorial/essential/io/examples/WatchDir.java.
 * 
 * This program listens to three kind of events in a supplied directory:
 * <ol>
 * 	<li>ENTRY_CREATE - When a file/folder gets created.</li>
 * 	<li>ENTRY_DELETE - When a file/folder gets deleted.</li>
 * 	<li>ENTRY_MODIFY - When a file/folder get modified.</li>
 * </ol>
 * 
 * Based on the event it launches the relevant application through the
 * {@link #handleDirectoryEvent(String, Path)} method.
 * 
 * 
 * @author Chandan R. Rupakheti (chandan.rupakheti@rose-hulman.edu)
 */
public class AppLauncher extends Thread {

	private final WatchService watcher;
	private final Path dir;
	private boolean stop;
	private DirectoryHandler handler;
	private List<Process> processes;
	/**
	 * Creates a WatchService and registers the given directory
	 */
	AppLauncher(Path dir) throws IOException {
		handler=new DirectoryHandler(this);
		this.stop = true;
		this.dir = dir;
		this.processes = Collections.synchronizedList(new ArrayList<Process>());
		this.watcher = FileSystems.getDefault().newWatchService();
		dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
	}

	/**
	 * Process all events for keys queued to the watcher
	 */
	public void run() {
		this.stop = false;
		while(!stop) {
			// Wait for key to be signalled
			WatchKey key;
			try {
				key = watcher.take();
			} catch (InterruptedException x) {
				return;
			}

			// Context for directory entry event is the file name of entry
			List<WatchEvent<?>> events = key.pollEvents();
			if(!events.isEmpty()) {
				@SuppressWarnings("unchecked")
				WatchEvent<Path> event = (WatchEvent<Path>)events.get(0);
				Path name = event.context();
				Path child = dir.resolve(name);
				handler.entryChange(event.kind().name(), child);
				// Call the Observer method
			}

			// Reset key and remove from set if directory no longer accessible
			if (!key.reset()) {
				break;
			}
		}

		// We gracefully stopped the service now, let's delete the temp file
		this.clearEverything();
	}

	public void addObserver(IObserver o){
		handler.registerObserver(o);
	}
	
	
	public void launchProcess(String command, String arg){
		try {
			System.out.format("Launching %s ...%n", command);
			ProcessBuilder processBuilder = new ProcessBuilder(command, arg);
			// Start and add the process to the processes list
			Process process = processBuilder.start();
			processes.add(process);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method is for internal use to delete the temporary file created by
	 * the {@link #clearEverything()} method. As well as to kill all of the newly
	 * created process.
	 */
	protected void clearEverything() {
		File file = new File(dir.toFile() + "/.temp");
		file.delete();
		
		for(Process p: this.processes) {
			if(p!=null)
			p.destroy();
		}
	}

	/**
	 * This method gracefully stops the WatchDir service.
	 * @throws IOException
	 */
	public void stopGracefully() throws IOException {
		this.stop = true;
		File file = new File(dir.toFile() + "/.temp");

		// Let's force the while loop in the run method to compe out of the blocking watcher.take() call here
		// You can also create a directory by calling file.mkdir()
		file.createNewFile();
	}
	
	/**
	 * Returns true if the launcher is running, otherwise false.
	 */
	public boolean isRunning() {
		return !stop;
	}
	
	/**
	 * Returns the number of applications launched so far by the launcher.
	 */
	public int getApplicationsCount() {
		return this.processes.size();
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		// Register directory to the launcher
		Path dir = Paths.get("./input_output");
		AppLauncher launcher = new AppLauncher(dir);
		//F2
		launcher.addObserver(new FileAdded());
		//F2
		launcher.addObserver(new ModifiedTxt());
		launcher.addObserver(new Launcher("txt", "notepad", launcher));
		launcher.addObserver(new Launcher("html", "explorer", launcher));
		launcher.addObserver(new Launcher("htm", "explorer", launcher));
		//F1
		launcher.addObserver(new Launcher("java", "javac", launcher));
		launcher.addObserver(new JavaLauncher(launcher));
		launcher.start();
		
		
		System.out.format("Launcher started watching %s ...%nPress the return key to stop ...", dir);

		// Wait for an input
		System.in.read();

		launcher.stopGracefully();
		launcher.join();

		System.out.println("Directory watching stopped ...");
	}

}