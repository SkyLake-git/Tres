package client.ui;

import client.ui.gui.DrawUtil;
import math.Vector2;

import javax.swing.*;
import java.awt.*;

abstract public class TextField extends JTextField implements Visible {

	protected Rectangle rect;

	protected Image image;

	public TextField(Image image, int x, int y, int width, int height) {
		super(null, "", 5);
		this.image = image;
		this.rect = new Rectangle(x, y, width, height);
		setBounds(this.rect);
	}

	@Override
	public void draw(Graphics g) {
		Color prevColor = g.getColor();
		Font prevFont = g.getFont();
		g.drawImage(this.image, this.rect.x, this.rect.y, null);
		g.setColor(Color.black);
		g.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));
		DrawUtil.drawStringCentered(g, (int) this.rect.getCenterX(), (int) this.rect.getCenterY(), this.getText());
		g.setColor(prevColor);
		g.setFont(prevFont);
	}

	@Override
	public void paintImmediately(int x, int y, int w, int h) {
	}

	@Override
	public void update() {

	}

	@Override
	public boolean collide(Vector2 pos) {
		return rect.x <= pos.x && pos.x <= rect.x + rect.width
				&& rect.y <= pos.y && pos.y <= rect.y + rect.height;
	}

}
