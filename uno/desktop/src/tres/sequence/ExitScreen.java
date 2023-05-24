package tres.sequence;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import tres.ScreenSequence;
import tres.TresApplication;
import tres.client.ui.TextureUtils;

public class ExitScreen extends ScreenSequence {
	protected OrthographicCamera camera;

	protected ShapeRenderer renderer;

	protected float percentage;

	protected BitmapFont font;

	public ExitScreen(TresApplication game, Viewport viewport) {
		super(game);
	}

	@Override
	protected void init() {
		this.camera = new OrthographicCamera(getViewport().getScreenWidth(), getViewport().getScreenHeight());

		this.renderer = new ShapeRenderer();

		this.font = new BitmapFont();
		this.percentage = 0.0f;

		getViewport().setCamera(this.camera);
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		this.renderer.setAutoShapeType(true);
		this.renderer.begin();
		this.renderer.setColor(1f, 1f, 1f, 1f);

		ScreenUtils.clear(0, 0, 0, 1f);

		int width = (int) (getViewport().getScreenWidth() * this.percentage);
		int height = (int) (getViewport().getScreenHeight() * this.percentage);

		this.renderer.rect(-(width / 2f), (height / 2f), width / 2f, height / 2f);

		TextureUtils.drawCenterFont(stage.getBatch(), this.font, "Closing", 0, 0);

		this.renderer.end();
	}
}
