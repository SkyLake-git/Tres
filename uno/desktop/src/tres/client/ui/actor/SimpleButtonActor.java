package tres.client.ui.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import org.jetbrains.annotations.Nullable;
import tres.DesktopLauncher;

public class SimpleButtonActor extends TextButton {

	public static final Color DEFAULT_COLOR = new Color(0.2f, 0.35f, 0.3f, 1f);

	protected Button button;

	protected boolean hit;

	protected float hitTimes;

	protected float pressedTime;

	protected boolean lastOver;

	protected boolean lastClick;

	public SimpleButtonActor(Vector2 position, Button button) {
		super(String.valueOf(button.text), new TextButtonStyle(null, null, null, new BitmapFont()));
		if (button.backgroundColor == null) {
			button.backgroundColor = DEFAULT_COLOR.cpy();
		}
		setX(position.x - button.width / 2);
		setY(position.y - button.height / 2);

		this.lastOver = false;
		this.lastClick = false;

		this.button = button;

		this.hitTimes = 0.0f;

		setWidth(button.width);
		setHeight(button.height);

		this.refreshStyle(button.backgroundColor, button.backgroundColor.add(-0.1f, -0.1f, -0.1f, 1f), button.backgroundColor);
	}

	protected void refreshStyle(Color upColor, Color downColor, Color overColor) {

		int width = (int) getWidth();
		int height = (int) getHeight();

		Pixmap up = new Pixmap(width + 8, height + 8, Pixmap.Format.RGBA8888);
		up.setColor(0.1f, 0.1f, 0.1f, 1f);
		up.fillRectangle(0, 8, width + 4, height + 4);
		up.setColor(upColor);
		up.fillRectangle(7, 0, width + 7, height);

		Pixmap down = new Pixmap(width + 8, height + 8, Pixmap.Format.RGBA8888);
		down.setColor(downColor);
		down.fillRectangle(0, 8, width + 4, height + 4);

		TextButtonStyle style = new TextButtonStyle(
				new NinePatchDrawable(new NinePatch(new Texture(up))),
				new NinePatchDrawable(new NinePatch(new Texture(down))),
				null,
				new BitmapFont()
		);

		Pixmap disabled = new Pixmap(width + 8, height + 8, Pixmap.Format.RGBA8888);
		disabled.setColor(downColor.cpy().mul(0.5f));
		disabled.fillRectangle(0, 8, width + 4, height + 4);
		disabled.setColor(0.0f, 1.0f, 0.0f, 0.05f);
		disabled.drawRectangle(0, 8, width + 4, height);

		Pixmap over = new Pixmap(up.getWidth(), up.getHeight(), Pixmap.Format.RGBA8888);
		over.drawPixmap(up, 0, 0);
		over.setColor(0f, 0.5f, 0.5f, 1f);
		style.over = new NinePatchDrawable(new NinePatch(new Texture(over)));
		style.disabled = new NinePatchDrawable(new NinePatch(new Texture(disabled)));

		up.dispose();
		down.dispose();
		over.dispose();
		disabled.dispose();
		this.setStyle(style);
		style.unpressedOffsetX = 7;
		style.unpressedOffsetY = 7;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if (this.getStyle().checked == null) {
			this.setChecked(false);
		}

		if (!this.lastOver && this.getClickListener().isOver()) {
			DesktopLauncher.applicationThread.getApplication().getSounds().playButtonOver(0.7f, 3f);
		}

		if (!this.lastClick && this.getClickListener().isVisualPressed()) {
			DesktopLauncher.applicationThread.getApplication().getSounds().playButtonClick(0.7f, 2f);
		}

		this.lastOver = this.getClickListener().isOver();
		this.lastClick = this.getClickListener().isVisualPressed();

	}

	public static class Button {
		public CharSequence text;

		public float width;

		public float height;

		public @Nullable Color backgroundColor;

		public float confirmSeconds;

		public Button(CharSequence text, float width, float height, @Nullable Color backgroundColor, float confirmSeconds) {
			this.text = text;
			this.width = width;
			this.height = height;
			this.backgroundColor = backgroundColor;
			this.confirmSeconds = confirmSeconds;
		}
	}

}
