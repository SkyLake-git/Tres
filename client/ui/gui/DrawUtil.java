package client.ui.gui;

import java.awt.*;

public class DrawUtil {

	public static void drawOvalCentered(Graphics g, int x, int y, int width, int height) {
		g.drawOval(-(width / 2) + x, -(height / 2) + y, width, height);
	}

	public static void fillOvalCentered(Graphics g, int x, int y, int width, int height) {
		g.fillOval(-(width / 2) + x, -(height / 2) + y, width, height);
	}

	public static void drawStringCentered(Graphics g, int x, int y, String str) {
		FontMetrics metrics = g.getFontMetrics();
		Rectangle textRect = metrics.getStringBounds(str, g).getBounds();
		int nx = x - textRect.width / 2;
		int ny = y - textRect.height / 2 + metrics.getMaxAscent();

		g.drawString(str, nx, ny);
	}
}
