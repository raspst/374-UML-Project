package uml.parser.gui;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;

public class MainWindow {
	
	public static int APP_DEFAULT_WIDTH = 1600;
	public static int APP_DEFAULT_HEIGHT = 900;
	
	protected LandingPanel landingPanel;
	protected JFrame mainFrame;
	protected JPanel cards;
	protected static Properties properties = new Properties();
	
	public void createAndShowGUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		mainFrame = new JFrame("374 Design Parser");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(APP_DEFAULT_WIDTH, APP_DEFAULT_HEIGHT);
		cards = new JPanel(new CardLayout());
		landingPanel = new LandingPanel(this);
		cards.add("LANDING", landingPanel);
//		umlPanel = new UMLPanel(mainFrame);
//		cards.add("UML", umlPanel);
		mainFrame.add(cards);
		mainFrame.setVisible(true);
	}
}
