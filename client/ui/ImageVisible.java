package client.ui;

import java.awt.*;

public class ImageVisible extends BaseVisible {

	protected Image image;

	ImageVisible(Image image) {
		super();
		this.image = image;
	}
	

	@Override
	public void draw(Graphics g) {
		g.drawImage(this.image, this.rect.x, this.rect.y, this.rect.width, this.rect.height, null);
	}

	@Override
	public void update() {

	}
}
