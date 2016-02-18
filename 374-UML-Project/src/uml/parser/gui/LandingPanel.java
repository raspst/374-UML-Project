package uml.parser.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class LandingPanel extends JPanel {

	public LandingPanel() {
		this.setSize(MainWindow.APP_DEFAULT_WIDTH, MainWindow.APP_DEFAULT_HEIGHT);
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		JButton configButton = new JButton("Load Config File");
		c.gridx = 0;
		c.gridy = 0;
		this.add(configButton,c);
		JButton analyzeButton = new JButton("Analyze");
		c.gridx = 1;
		c.gridy = 0;
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
}
