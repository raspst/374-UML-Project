package uml.parser.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

public class UMLPanel extends JPanel {
	protected JFrame frame;

	public UMLPanel(JFrame frame) {
		this.frame = frame;
		setBorder(BorderFactory.createLineBorder(Color.black));
		this.setSize(MainWindow.APP_DEFAULT_WIDTH, MainWindow.APP_DEFAULT_HEIGHT);
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		JPanel drawPanel = new JPanel();
		JPanel treePanel = new JPanel();
		frame.setJMenuBar(createMenuBar());
		JLabel label = new JLabel();
		label.setIcon(createImage());
		drawPanel.add(label);
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.2;
		c.anchor = GridBagConstraints.WEST;
		treePanel.add(createPatternTree());
		this.add(treePanel, c);
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.8;
		c.fill = GridBagConstraints.REMAINDER;
		c.anchor = GridBagConstraints.EAST;
		this.add(drawPanel, c);
	}
	
	public JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu helpMenu = new JMenu("Help");
		JMenuItem restartItem = new JMenuItem("Reload");
		JMenuItem aboutItem = new JMenuItem("About");
		aboutItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				displayAboutWindow();
			}
			
		});
		fileMenu.add(restartItem);
		helpMenu.add(aboutItem);
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		return menuBar;
	}
	
	public JTree createPatternTree() {
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
		return tree;
	}
	
	public ImageIcon createImage() {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("docs/new_diagram.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImageIcon image = new ImageIcon(img.getScaledInstance(600, 600, Image.SCALE_SMOOTH));
		return image;
	}
	
	public void displayAboutWindow() {
		String message = "374 Design Parser\nDeveloped by Alex Crowley and Steven Rasp\nCopyright 2016";
		JOptionPane.showMessageDialog(frame, message);
	}
}
