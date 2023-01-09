package tres.client.ui.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class ToggleButtonActor extends BorderlessButtonActor {

	protected boolean toggle;

	public ToggleButtonActor(Vector2 position, Button button) {
		super(position, button);
	}

	public boolean getToggle() {
		return toggle;
	}

	public void setToggle(boolean toggle) {
		this.toggle = toggle;
	}

	@Override
	public void recreateTexture(Color colorDefault, Pixmap basePixmap) {
		Pixmap cpyPixmap = new Pixmap(basePixmap.getWidth(), basePixmap.getHeight(), basePixmap.getFormat());
		super.recreateTexture(colorDefault, cpyPixmap);

		int width = basePixmap.getWidth();
		int height = basePixmap.getHeight();

		Pixmap pixmap = new Pixmap(basePixmap.getWidth(), basePixmap.getHeight(), basePixmap.getFormat());

		pixmap.drawPixmap(cpyPixmap, 0, 0);
		cpyPixmap.dispose();
		this.texture.dispose();


		if (this.toggle) {
			pixmap.setColor(0, 0.75f, 0, 1f);
		} else {
			pixmap.setColor(0.75f, 0, 0, 1f);
		}

		int gap = 8;
		pixmap.fillRectangle(width - 20 - gap, gap, 20, height - gap * 2);

		this.texture = new Texture(pixmap);

		pixmap.dispose();
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if (this.pressed) {
			this.toggle = !this.toggle;
		}
	}
}
