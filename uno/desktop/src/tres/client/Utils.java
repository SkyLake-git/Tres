package tres.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Utils {

	public static Vector2 getMousePosition(Viewport viewport) {
		return viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
	}
}
