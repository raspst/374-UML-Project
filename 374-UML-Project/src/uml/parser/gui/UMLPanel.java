package uml.parser.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class UMLPanel extends JPanel {

	public UMLPanel() {
		setBorder(BorderFactory.createLineBorder(Color.black));
		this.setSize(MainWindow.APP_DEFAULT_WIDTH, MainWindow.APP_DEFAULT_HEIGHT);
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		JPanel drawPanel = new JPanel();
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Parent");
		DefaultMutableTreeNode child1 = new DefaultMutableTreeNode("Child1");
		DefaultMutableTreeNode grandchild1 = new DefaultMutableTreeNode("Grandchild1");
		DefaultMutableTreeNode child2 = new DefaultMutableTreeNode("Child2");
		DefaultMutableTreeNode grandchild2 = new DefaultMutableTreeNode("Grandchild2");
		child1.add(grandchild1);
		child2.add(grandchild2);
		top.add(child1);
		top.add(child2);
		JTree tree = new JTree(top);
		JLabel label = new JLabel();
//		label.setText("Draw Image Here");
		
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("docs/new_diagram.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImageIcon image = new ImageIcon(img.getScaledInstance(600, 600, Image.SCALE_SMOOTH));
		label.setIcon(image);
		drawPanel.add(label);
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.2;
		c.anchor = GridBagConstraints.WEST;
		this.add(tree, c);
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.8;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.EAST;
		this.add(drawPanel, c);
	}
}
