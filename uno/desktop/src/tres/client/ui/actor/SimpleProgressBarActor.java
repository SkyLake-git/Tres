package tres.client.ui.actor;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class SimpleProgressBarActor extends ProgressBar {

	public SimpleProgressBarActor(float min, float max, float stepSize, boolean vertical) {
		super(min, max, stepSize, vertical, new ProgressBarStyle(
				null,
				null
		));

		this.refreshStyle();
	}

	@Override
	protected void sizeChanged() {
		super.sizeChanged();
		this.refreshStyle();
	}

	protected void refreshStyle() {
		int width = (int) getWidth();
		int height = (int) getHeight();

		Pixmap backgroundPixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		backgroundPixmap.setColor(0, 0, 0, 1f);
		backgroundPixmap.fill();
		NinePatchDrawable background = new NinePatchDrawable(new NinePatch(new Texture(backgroundPixmap)));

		Pixmap backgroundDownPixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		backgroundDownPixmap.setColor(0.0f, 1.0f, 0.0f, 0.5f);
		backgroundDownPixmap.fill();
		NinePatchDrawable backgroundDown = new NinePatchDrawable(new NinePatch(new Texture(backgroundDownPixmap)));


		ProgressBarStyle style = new ProgressBarStyle(background, null);
		style.knobBefore = backgroundDown;

		this.setStyle(style);

	}
}
