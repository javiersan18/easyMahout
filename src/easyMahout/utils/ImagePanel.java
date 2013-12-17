package easyMahout.utils;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private String imagePath;

	public ImagePanel(String image) {
		this.setSize(300, 400); // select the size of the panel
		this.imagePath = image;
	}

	public ImagePanel(int width, int height, String image) {
		this.setSize(width, height); // select the size of the panel
		this.imagePath = image;
	}

	@Override
	public void paintComponent(Graphics g) {
		Dimension size = getSize();
		ImageIcon image = new ImageIcon(getClass().getResource(imagePath));
		g.drawImage(image.getImage(), 0, 0, size.width, size.height, null);
		setOpaque(false);
		super.paintComponent(g);
	}

}
