package tres.client.ui.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class ToggleButtonActor extends SimpleButtonActor {


	protected boolean toggle;

	public ToggleButtonActor(Vector2 position, Button button) {
		super(position, button);
	}

	@Override
	protected void refreshStyle(Color upColor, Color downColor, Color overColor) {
		int width = (int) getWidth();
		int height = (int) getHeight();
		super.refreshStyle(upColor, downColor, overColor);

		Pixmap checked = new Pixmap(width + 8, height + 8, Pixmap.Format.RGBA8888);
		checked.setColor(downColor.mul(1.5f));
		checked.fillRectangle(0, 8, width + 4, height + 4);
		checked.setColor(0.0f, 1.0f, 0.0f, 0.05f);
		checked.drawRectangle(0, 8, width + 4, height);

		this.getStyle().checked = new NinePatchDrawable(new NinePatch(new Texture(checked)));
	}

	@Override
	public void act(float delta) {
		super.act(delta);

	}
}
