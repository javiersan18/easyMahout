package easyMahout.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.RGBImageFilter;

import javax.swing.Icon;

public class GraydIcon implements Icon {
	Icon icon;

	Image image;

	public GraydIcon(Icon icon) {
		this.icon = icon;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		if (image == null) {
			Image orgImage = c.createImage(getIconWidth(), getIconHeight());
			Graphics imageG = orgImage.getGraphics();
			Color background = c.getBackground();
			imageG.setColor(background);
			imageG.fillRect(0, 0, getIconWidth(), getIconHeight());

			icon.paintIcon(c, imageG, x, y);

			ImageFilter colorfilter = new GrayFilter();
			image = c.createImage(new FilteredImageSource(orgImage.getSource(), colorfilter));

		}
		g.drawImage(image, x, y, null);
	}

	public int getIconWidth() {
		return icon.getIconWidth();
	}

	public int getIconHeight() {
		return icon.getIconHeight();
	}

	class GrayFilter extends RGBImageFilter {

		public GrayFilter() {
			// If I set ture, black is gone?!
			// canFilterIndexColorModel = true;
		}

		public int filterRGB(int x, int y, int rgb) {
			int r = (rgb & 0xff0000) >> 16;
			int g = (rgb & 0x00ff00) >> 8;
			int b = (rgb & 0x0000ff);
			int iy = (int) (r + g + b) / 3;
			iy = Math.min(255, iy);
			return ((rgb & 0xff000000) | (iy << 16) | (iy << 8) | iy);
		}
	}

}
