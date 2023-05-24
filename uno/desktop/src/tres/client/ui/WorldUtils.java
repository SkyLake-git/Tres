package tres.client.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class WorldUtils {

	public static int WIDTH = 1920;

	public static int HEIGHT = 1080;

	public static float RATIO_W = 16f;

	public static float RATIO_H = 9f;

	public static float getHeightFromWidth(float width) {
		return width * (RATIO_H / RATIO_W);
	}

	public static void drawAsScreenViewport(Batch batch, Viewport beforeViewport, Runnable callback) {
		batch.end();
		ScreenViewport viewport = (new ScreenViewport());
		viewport.setCamera(beforeViewport.getCamera());
		viewport.update((int) beforeViewport.getCamera().viewportWidth, (int) beforeViewport.getCamera().viewportHeight, false);

		batch.begin();
		callback.run();
		beforeViewport.apply();

	}

	public static float getWidthFromHeight(float height) {
		return height * (RATIO_W / RATIO_H);
	}

	public static Vector2 percentage(float wp, float hp) {
		return new Vector2(WIDTH * wp, HEIGHT * hp);
	}

	public static Vector2 calcScale(Viewport viewport) {
		return new Vector2(viewport.getScreenWidth() / viewport.getWorldWidth(), viewport.getScreenHeight() / viewport.getWorldHeight());
	}

	public static Vector2 calcReverseScale(Viewport viewport) {
		return new Vector2(viewport.getWorldWidth() / viewport.getScreenWidth(), viewport.getWorldHeight() / viewport.getScreenHeight());
	}
}
