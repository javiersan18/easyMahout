package easyMahout.utils;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class IconHelpPanel extends JPanel {

	public IconHelpPanel() {
		this.setSize(300, 400); // select the size of the panel
	}
	
	public IconHelpPanel(int width, int height) {
		this.setSize(width, height); // select the size of the panel
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Dimension size = getSize();
		ImageIcon image = new ImageIcon(getClass().getResource("/easyMahout/GUI/images/help.jpg"));
		g.drawImage(image.getImage(), 0, 0, size.width, size.height, null);
		setOpaque(false);
		super.paintComponent(g);
	}

}
