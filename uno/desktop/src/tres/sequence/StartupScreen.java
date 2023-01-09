package tres.sequence;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import tres.ScreenSequence;
import tres.TresApplication;

public class StartupScreen extends ScreenSequence {

	protected OrthographicCamera camera;

	public StartupScreen(TresApplication game, Viewport viewport) {
		super(game, viewport);
	}

	@Override
	protected void init() {
		this.camera = new OrthographicCamera(getViewport().getScreenWidth(), getViewport().getScreenHeight());


		getViewport().setCamera(this.camera);
	}
}
