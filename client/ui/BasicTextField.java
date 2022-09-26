package client.ui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class BasicTextField extends TextField {

	protected boolean isColliding;

	protected StringBuilder text;

	protected int position;

	protected boolean highlight;

	public BasicTextField(Point pos, int width, int height) {
		super(ImageUtil.scaled(ImageManager.getImage(BasicButton.IMAGE_ID), width, height), pos.x, pos.y, width, height);
		setEditable(false);
		this.highlight = false;
		this.isColliding = false;
		this.text = new StringBuilder();
		this.position = 0;
	}

	public StringBuilder getTextBuilder() {
		return this.text;
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);

		FontMetrics metrics = g.getFontMetrics();
		Rectangle rect = metrics.getStringBounds(this.text.toString(), 0, this.position, g).getBounds();
		Rectangle rectAll = metrics.getStringBounds(this.text.toString(), g).getBounds();
		g.setColor(Color.orange);
		int x = (int) ((rect.width) + (this.rect.getCenterX()));
		g.drawLine(x, this.rect.y + this.rect.height - 5, x, this.rect.y + 5);

		if (this.highlight) {
			g.setColor(Color.BLUE);
			g.drawRect(this.rect.x, this.rect.y, this.rect.width - 1, this.rect.height - 1);
		}
	}

	@Override
	public void update() {
		super.update();

		this.isColliding = this.collide(Inputs.getMousePosition());

		ArrayList<Integer> keys = Inputs.getTypedKeyCodes();

		if (this.highlight) {
			for (int code : keys) {
				String before = this.getText();
				if (code == KeyEvent.VK_BACK_SPACE) {
					if (before.length() > 0) {
						int index = this.position - 1;
						if (index >= 0) {
							this.text.deleteCharAt(index);
							this.position--;
						}
					}
					return;
				}

				if (code == KeyEvent.VK_RIGHT) {
					if (before.length() < this.getCaretPosition()) {
						this.position++;
					}
					return;
				}

				if (code == KeyEvent.VK_LEFT) {
					if (this.position > 0) {
						this.position--;
					}
					return;
				}

				if (code == KeyEvent.VK_ESCAPE) {
					this.text.delete(0, this.text.length());
					this.position = 0;
					return;
				}

				String key = String.valueOf((char) code);
				this.text.insert(this.position, key);
				this.position++;
			}

		}

		if (Inputs.isButtonClicked(MouseEvent.BUTTON1)) {
			this.highlight = this.isColliding;
		}

		this.setText(this.text.toString());

	}
}
