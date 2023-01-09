package tres;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import tres.client.event.packet.DataPacketReceiveEvent;
import tres.client.event.packet.DataPacketSendEvent;
import tres.client.ui.WorldUtils;
import tres.client.ui.actor.ChatViewerActor;
import com.tres.network.packet.DataPacket;
import tres.sequence.SettingsScreen;
import com.tres.snooze.Sleeper;

public class TresApplication extends Game {

	protected boolean isDisposed;
	protected Viewport viewport;
	protected SpriteBatch batch;
	protected BitmapFont font;
	protected ShapeRenderer shapeRenderer;

	protected Viewport debugViewport;

	protected TresApplicationSettings settings;

	protected Sleeper renderSleeper;

	protected ChatViewerActor packetLog;

	public TresApplication(Lwjgl3ApplicationConfiguration config) {
		this.settings = new TresApplicationSettings();
	}

	public TresApplicationSettings getSettings() {
		return settings;
	}

	@Override
	public void create() {
		this.viewport = new FitViewport(WorldUtils.WIDTH, WorldUtils.HEIGHT);
		this.debugViewport = new ScreenViewport();
		this.setScreen(new TresMainScreen(this, viewport));

		this.batch = new SpriteBatch();
		this.font = new BitmapFont();
		this.shapeRenderer = new ShapeRenderer();
		this.renderSleeper = new Sleeper();
		this.packetLog = new ChatViewerActor(
				new Vector2(5, this.debugViewport.getScreenHeight() + 50),
				new ChatViewerActor.ChatView(
						1f,
						new Color(0f, 0.8f, 0f, 1f),
						25,
						30,
						new Color(0f, 0f, 0.0f, 0f)
				)
		);

		DesktopLauncher.client.getEventEmitter().on(DataPacketReceiveEvent.class, (channel, event) -> {
			DataPacket packet = event.getPacket();
			synchronized (this.packetLog) {
				this.packetLog.addLog(new ChatViewerActor.ChatLog(
						"Recv: " + packet.getName(),
						14
				));
			}
		});

		DesktopLauncher.client.getEventEmitter().on(DataPacketSendEvent.class, (channel, event) -> {
			DataPacket packet = event.getPacket();
			synchronized (this.packetLog) {
				this.packetLog.addLog(new ChatViewerActor.ChatLog(
						"Send: " + packet.getName(),
						14
				));
			}
		});
	}

	public Sleeper getRenderSleeper() {
		return renderSleeper;
	}

	@Override
	public void setScreen(Screen screen) {
		super.setScreen(screen);

		if (screen instanceof ScreenSequence) {
			((ScreenSequence) screen).focus();
		}
	}

	@Override
	public void render() {
		this.viewport.apply();
		super.render();

		this.renderSleeper.processNotifications();

		this.debugViewport.apply();
		this.batch.begin();
		this.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		this.batch.setColor(0, 1f, 0.2f, 0.5f);

		float delta = Gdx.graphics.getDeltaTime();

		CharSequence str = "FPS: " + Gdx.graphics.getFramesPerSecond();
		this.font.draw(this.batch, str, 0, this.debugViewport.getScreenHeight() - 5);
		this.font.draw(this.batch, "F: " + String.format("%.0f", 1 / delta), 100, this.debugViewport.getScreenHeight() - 5);

		this.packetLog.act(delta);
		this.packetLog.draw(this.batch, 1f);

		this.batch.end();
		this.shapeRenderer.end();

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			if (!(this.getScreen() instanceof SettingsScreen)) {
				this.setScreen(new SettingsScreen(this, this.viewport, this.getScreen()));
			}
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.F11) && Gdx.graphics.isFullscreen()) {
			Gdx.graphics.setWindowedMode(1600, 900);
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.F11) && !Gdx.graphics.isFullscreen()) {
			Gdx.graphics.setWindowedMode(this.settings.getMonitorWidth(), this.settings.getMonitorHeight());

		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.F9)) {
			Gdx.graphics.setWindowedMode(300, 168);
		}
	}

	@Override
	public void resize(int width, int height) {
		boolean applied = false;

		if (width != this.viewport.getScreenWidth() && width >= 10) {
			height = (int) WorldUtils.getHeightFromWidth(width);

			applied = true;
		}

		if (height != this.viewport.getScreenHeight() && height >= 10 && !applied) {
			width = (int) WorldUtils.getWidthFromHeight(height);

			applied = true;
		}

		if (width >= this.settings.getMonitorWidth()) {
			Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		} else {
			if (applied) {
				Gdx.graphics.setWindowedMode(width, height);
			}

		}

		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		super.resize(width, height);

		this.viewport.update(width, height, false);
		this.debugViewport.update(width, height, false);


		this.batch.dispose();
		this.batch = new SpriteBatch();

		this.shapeRenderer.dispose();
		this.shapeRenderer = new ShapeRenderer();


	}

	@Override
	public void dispose() {
		super.dispose();

		this.isDisposed = true;
	}

	public boolean isDisposed() {
		return isDisposed;
	}
}
