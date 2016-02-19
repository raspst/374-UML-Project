package uml.parser.gui;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;

public class MainWindow {
	
	public static int APP_DEFAULT_WIDTH = 1024;
	public static int APP_DEFAULT_HEIGHT = 768;
	
	protected LandingPanel landingPanel;
	protected UMLPanel umlPanel;
	protected JPanel cards;
	
	public void createAndShowGUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		JFrame mainFrame = new JFrame("374 Design Parser");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(APP_DEFAULT_WIDTH, APP_DEFAULT_HEIGHT);
		cards = new JPanel(new CardLayout());
		landingPanel = new LandingPanel(this);
//		landingPanel.setFocusable(true);
		cards.add("LANDING", landingPanel);
		umlPanel = new UMLPanel();
//		umlPanel.setFocusable(true);
		cards.add("UML", umlPanel);
		mainFrame.add(cards);
//		mainFrame.pack();
		mainFrame.setVisible(true);
	}
}
