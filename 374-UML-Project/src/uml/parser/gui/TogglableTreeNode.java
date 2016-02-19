package uml.parser.gui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.tree.DefaultMutableTreeNode;

import uml.parser.PatternIterator;
import uml.pattern.PatternContainer;

public class TogglableTreeNode extends DefaultMutableTreeNode{
	private boolean visible = true;
	private PatternIterator pi;
	private UMLPanel panel;
	private PatternContainer pc;
	public TogglableTreeNode(String name, PatternIterator pi, UMLPanel panel, PatternContainer pc) {
		super(name);
		this.pi = pi;
		this.panel = panel;
		this.pc = pc;
	}
	
	public void updateImage() {
		visible = !visible;
		if(visible) {
			pi.addContainer(pc);
		}
		else {
			pi.removeContainer(pc);
		}
		pi.annotate();
		File out = new File("in/dotFile.dot");
		try {
			FileOutputStream os = new FileOutputStream(out);
			os.write(pi.getGraphViz().getBytes());
			os.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		String[] command = {MainWindow.properties.getProperty("dot-path"), "-T", "png", "-o", 
				MainWindow.properties.getProperty("output-folder") + "output.png", MainWindow.properties.getProperty("input-folder") + "dotFile.dot"};
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		panel.label.setIcon(panel.createImage());
	}
}
