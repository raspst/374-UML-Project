package uml.parser.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class UMLPanel extends JPanel {

	public UMLPanel() {
		setBorder(BorderFactory.createLineBorder(Color.black));
		this.setSize(MainWindow.APP_DEFAULT_WIDTH, MainWindow.APP_DEFAULT_HEIGHT);
	}
	
	public void paintComponent(Graphics g) {
		g.drawString("New Window!", 100, 100);
	}
}
