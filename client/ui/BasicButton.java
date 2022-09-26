package client.ui;

import client.ui.gui.DrawUtil;

import java.awt.*;

public class BasicButton extends Button {

	public static final int IMAGE_ID;

	protected boolean highlight;

	protected String text;

	protected Color color;

	static {
		IMAGE_ID = ImageManager.readImage("./client/resources/button.png");
	}

	public BasicButton(Point pos, int width, int height, Color color, String text) {
		super(ImageUtil.scaled(ImageManager.getImage(IMAGE_ID), width, height), pos, width, height);
		this.color = color;

		this.text = text;

		this.highlight = false;
	}

	@Override
	public void draw(Graphics g) {
		Rectangle rect = (Rectangle) this.rect.clone();
		if (this.isColliding) {
			this.rect.y += 4;
		}

		super.draw(g);
		Color prevColor = g.getColor();
		g.setColor(this.color);
		DrawUtil.drawStringCentered(g, (int) this.rect.getCenterX(), (int) this.rect.getCenterY(), this.text);

		if (this.highlight || this.isClicked() || this.isColliding) {
			g.setColor(new Color(0, 200, 12, 50));
			g.fillRect(this.rect.x, this.rect.y, rect.width, rect.height);
		}

		g.setColor(prevColor);
		this.rect.y = rect.y;


	}

	@Override
	public void update() {
		super.update();
	}
}
