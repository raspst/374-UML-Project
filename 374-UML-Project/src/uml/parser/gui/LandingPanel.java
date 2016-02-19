package uml.parser.gui;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import uml.detector.AdapterDetector;
import uml.detector.CompositeDetector;
import uml.detector.DecoratorDetector;
import uml.detector.PatternDetector;
import uml.detector.SingletonDetector;
import uml.parser.Design;
import uml.parser.DesignParser;
import uml.parser.PatternIterator;

public class LandingPanel extends JPanel {
	protected MainWindow w;
	private JLabel progressText;
	private JProgressBar progressBar;
	private Design d;
	private PatternIterator patternIterator;

	public LandingPanel(final MainWindow w) {
		this.w = w;
		this.setSize(MainWindow.APP_DEFAULT_WIDTH, MainWindow.APP_DEFAULT_HEIGHT);
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		JButton configButton = new JButton("Load Config File");
		configButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				loadConfig();
			}
		});
		c.gridx = 0;
		c.gridy = 0;
		this.add(configButton,c);
		JButton analyzeButton = new JButton("Analyze");
		c.gridx = 1;
		c.gridy = 0;
		analyzeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				runAnalyze();
			}
		});
		this.add(analyzeButton,c);
		progressText = new JLabel("Process not started");
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		this.add(progressText,c);
		progressBar = new JProgressBar();
		c.gridx = 0;
		c.gridy = 2;
		this.add(progressBar,c);
	}
	
	public void loadConfig() {
		try {
			String name = "in/config.properties";
			if(System.getProperty("os.name").startsWith("Windows"))name ="in/winconfig.properties";
			FileInputStream in = new FileInputStream(name);
			MainWindow.properties.load(in);
			in.close();
			progressText.setText("Config loaded");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(MainWindow.properties.getProperty("phases"));
	}
	
	public void runAnalyze() {
		String phases = MainWindow.properties.getProperty("phases");
		if(phases == null) {
			JOptionPane.showMessageDialog(this, "Load configuration file first. If you have already loaded file, an error occurred, and you should try again");
			return;
		}
		String[] sepPhases = phases.split(",");
		progressBar.setMaximum(sepPhases.length);
		int i = 1;
		for(String s: sepPhases) {
			progressText.setText("Beginning Phase " + i + " : " + s);
			if(i == 1) {
				executeLoad();
			}
			else if(i == 2) {
				executeDetect();
			}
			else if(i == 3) {
				executeGenerate();
			}
			else {
				return;
			}
			i++;
			progressBar.setValue(i);
		}
	}
	public void executeLoad() {
		d = DesignParser.parseFile(MainWindow.properties.getProperty("parse-file"));
		d.parse();
	}
	
	public void executeDetect() {
//		new SingletonDetector(d);
//		new DecoratorDetector(d);
		ArrayList<PatternDetector> pd = new ArrayList<>();
		pd.add(new CompositeDetector(d));
		pd.add(new AdapterDetector(d));
		pd.add(new SingletonDetector(d));
		pd.add(new DecoratorDetector(d));
		patternIterator = new PatternIterator(pd, d);
	}
	
	public void executeGenerate() {
		File out = new File("in/dotFile.dot");
		WatchService watcher = null;
		try {
			watcher = FileSystems.getDefault().newWatchService();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			FileOutputStream os = new FileOutputStream(out);
			os.write(patternIterator.getGraphViz().getBytes());
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
		UMLPanel umlPanel = new UMLPanel(w.mainFrame, patternIterator);
//		w.mainFrame.add(umlPanel);
		w.cards.add("UML",umlPanel);
		CardLayout c = (CardLayout) w.cards.getLayout();
		c.show(w.cards, "UML");
	}
}
