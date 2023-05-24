package tres;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import tres.sequence.ConnectScreen;

public class TresMainScreen extends ScreenSequence {

	protected OrthographicCamera camera;

	protected BitmapFont font;

	protected SpriteBatch batch;


	public TresMainScreen(TresApplication game, Viewport viewport) {
		super(game);
	}


	@Override
	protected void init() {
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, getViewport().getScreenWidth(), getViewport().getScreenHeight());

		this.stage = new Stage(this.getViewport());
		this.font = new BitmapFont();
		this.font.setColor(0, 0.9f, 0, 0.8f);
		this.batch = new SpriteBatch();

		this.getViewport().setCamera(this.camera);
	}

	@Override
	public void render(float delta) {
		this.batch.begin();

		ScreenUtils.clear(0.25f, 0.25f, 0.25f, 1f);
		this.stage.act(delta);
		this.stage.draw();

		this.batch.end();

		this.camera.update();

		if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
			this.game.setScreen(new ConnectScreen(this.game, this.stage.getViewport()));
			this.dispose();
		}

	}


	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		super.dispose();
		this.batch.dispose();
	}
}
