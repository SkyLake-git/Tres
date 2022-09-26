package client.ui;

import java.awt.*;
import java.awt.event.MouseEvent;

abstract public class Button extends BaseVisible {

	protected Image image;

	protected boolean clicked;

	public Button(Image image, Point pos, int width, int height) {
		this.rect = new Rectangle(pos.x, pos.y, width, height);
		this.image = image;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(this.image, this.rect.x, this.rect.y, null);
	}

	@Override
	public void update() {
		this.isColliding = this.collide(Inputs.getMousePosition());

		this.clicked = false;

		if (this.isColliding) {
			if (Inputs.isButtonClicked(MouseEvent.BUTTON1)) {
				this.clicked = true;
			}
		}
	}

	public boolean isClicked() {
		return clicked;
	}
}
