package client.ui;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtil {

	public static BufferedImage scaled(Image target, int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		image.createGraphics().drawImage(target.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING), 0, 0, width, height, null);
		return image;
	}

	public static BufferedImage expand(Image target, double x, double y) {
		int width = (int) (target.getWidth(null) * x);
		int height = (int) (target.getHeight(null) * y);
		return scaled(target, width, height);
	}

}
