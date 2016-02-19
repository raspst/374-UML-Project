package uml.parser.gui;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class LandingPanel extends JPanel {
	protected MainWindow w;

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
				CardLayout c = (CardLayout) w.cards.getLayout();
				c.show(w.cards, "UML");
			}
		});
		this.add(analyzeButton,c);
		JLabel progressText = new JLabel("Test text");
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		this.add(progressText,c);
		JProgressBar analyzeProgressBar = new JProgressBar();
		c.gridx = 0;
		c.gridy = 2;
		this.add(analyzeProgressBar,c);
	}
	
	public void loadConfig() {
		try {
			FileInputStream in = new FileInputStream("in/config.properties");
			MainWindow.properties.load(in);
			in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(MainWindow.properties.getProperty("phases"));
		String[] command = {"dot", "-T", "png", "-o", 
				MainWindow.properties.getProperty("output-folder") + "output.png", MainWindow.properties.getProperty("input-folder") + "lab13uml.dot"};
		for(String s: command) {
			System.out.println(s);
		}
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
