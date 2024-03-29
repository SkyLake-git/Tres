package tres;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import tres.client.ui.WorldUtils;

abstract public class ScreenSequence extends ScreenAdapter {

	protected Stage stage;

	protected TresApplication game;

	public ScreenSequence(TresApplication game) {
		this.stage = new Stage(new FitViewport(WorldUtils.WIDTH, WorldUtils.HEIGHT));
		this.game = game;


		this.init();
	}

	public void focus() {
		Gdx.input.setInputProcessor(this.stage);
	}

	abstract protected void init();

	public Stage getStage() {
		return stage;
	}

	public Viewport getViewport() {
		return stage.getViewport();
	}

	@Override
	public void resize(int width, int height) {
		getViewport().update(width, height, false);
	}

	@Override
	public void dispose() {
		this.stage.dispose();
	}
}
