package tres.client.ui.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class SimpleTextField extends TextField {

	public SimpleTextField(Vector2 position, float width, float height, String text, BitmapFont font, Color color) {
		super(text, new TextFieldStyle(
				font,
				color,
				null,
				null,
				null
		));
		setPosition(position.x - width / 2, position.y - height / 2);
		setSize(width, height);

		this.refreshStyle(font, color);
	}

	private void refreshStyle(BitmapFont font, Color fontColor) {
		int width = (int) getWidth();
		int height = (int) getHeight();

		Pixmap cursorPixmap = new Pixmap(1, height, Pixmap.Format.RGBA8888);
		cursorPixmap.setColor(1f, 1f, 1f, 1f);
		cursorPixmap.fill();
		NinePatchDrawable cursor = new NinePatchDrawable(new NinePatch(new Texture(cursorPixmap)));

		Pixmap selectionPixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		selectionPixmap.setColor(0f, 0.46f, 0.46f, 0.9f);
		selectionPixmap.fill();
		NinePatchDrawable selection = new NinePatchDrawable(new NinePatch(new Texture(selectionPixmap)));

		Pixmap backgroundPixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		backgroundPixmap.setColor(0, 0, 0, 1f);
		backgroundPixmap.fill();
		NinePatchDrawable background = new NinePatchDrawable(new NinePatch(new Texture(backgroundPixmap)));

		Pixmap focusedBackgroundPixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		focusedBackgroundPixmap.setColor(0, 0, 0, 1f);
		focusedBackgroundPixmap.fillRectangle(1, 1, width - 2, height - 2);
		focusedBackgroundPixmap.setColor(0, 0.5f, 0.5f, 1f);
		focusedBackgroundPixmap.drawRectangle(0, 0, width, height);
		NinePatchDrawable focusedBackground = new NinePatchDrawable(new NinePatch(new Texture(focusedBackgroundPixmap)));


		TextFieldStyle style = new TextFieldStyle(font, fontColor, cursor, selection, background);
		style.focusedBackground = focusedBackground;
		setStyle(style);

		backgroundPixmap.dispose();
		focusedBackgroundPixmap.dispose();
		cursorPixmap.dispose();
		selectionPixmap.dispose();
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}
}
