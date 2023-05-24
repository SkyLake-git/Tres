package tres.sequence;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import tres.DesktopLauncher;
import tres.ScreenSequence;
import tres.TresApplication;
import tres.client.ui.actor.SimpleButtonActor;
import tres.client.ui.actor.SimpleTextField;
import tres.client.ui.actor.TextActor;
import tres.client.ui.layout.Layout;
import tres.client.ui.layout.NarrowLayout;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ConnectScreen extends ScreenSequence {

	protected OrthographicCamera camera;


	protected SimpleButtonActor connectButton;


	protected Layout ipLayout;

	protected SimpleTextField addressField;

	protected SimpleTextField portField;

	public ConnectScreen(TresApplication game, Viewport viewport) {
		super(game);
	}

	@Override
	protected void init() {
		this.camera = new OrthographicCamera(getViewport().getScreenWidth(), getViewport().getScreenHeight());
		//this.stage.setDebugUnderMouse(true);
		this.connectButton = new SimpleButtonActor(
				new Vector2(0, -120),
				new SimpleButtonActor.Button(
						"Connect",
						400,
						60,
						null,
						1
				)
		);

		this.ipLayout = new NarrowLayout(new Vector2(-90, 0), 200, 30, Align.left, 15, false);

		this.addressField = new SimpleTextField(
				new Vector2(0, 0),
				180,
				45,
				"",
				new BitmapFont(),
				new Color(1f, 1f, 1f, 1f)
		);

		this.portField = new SimpleTextField(
				new Vector2(0, 0),
				69,
				45,
				"",
				new BitmapFont(),
				new Color(1f, 1f, 1f, 1f)
		);

		this.addressField.setText("localhost");
		this.portField.setText("34560");

		this.ipLayout.add(this.addressField);
		this.ipLayout.add(this.portField);

		this.stage.addActor(this.connectButton);
		this.stage.addActor(this.addressField);
		this.stage.addActor(this.portField);

		this.getViewport().setCamera(this.camera);

	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0.25f, 0.25f, 0.25f, 1f);

		this.stage.act(delta);
		this.stage.draw();

		if (this.connectButton.isPressed()) {

			this.connectButton.setDisabled(true);
			this.addressField.setDisabled(true);
			this.portField.setDisabled(true);

			try {
				DesktopLauncher.client.start(new InetSocketAddress(this.addressField.getText(), Integer.parseInt(this.portField.getText())));

				this.game.setScreen(new GameSelectScreen(this.game, getViewport()));


			} catch (IOException e) {
				e.printStackTrace();

				TextActor actor = TextActor.annotation(this.connectButton, "Failed to connect", new BitmapFont());
				actor.setDuration(3);
				this.stage.addActor(actor);

				this.connectButton.setDisabled(false);
				this.addressField.setDisabled(false);
				this.portField.setDisabled(false);
			}
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
	}
}
