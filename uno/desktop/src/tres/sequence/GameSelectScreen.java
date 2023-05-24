package tres.sequence;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tres.network.packet.protocol.types.AvailableGameInfo;
import com.tres.promise.Promise;
import com.tres.snooze.SleeperNotifier;
import tres.DesktopLauncher;
import tres.ScreenSequence;
import tres.TresApplication;
import tres.client.ui.WorldUtils;
import tres.client.ui.actor.SimpleButtonActor;
import tres.client.ui.layout.NarrowLayout;
import tres.client.ui.layout.attachment.LimitedViewAttachment;
import tres.sequence.ingame.InGamePlayScreen;

import java.util.Collection;
import java.util.HashMap;

public class GameSelectScreen extends ScreenSequence {
	protected OrthographicCamera camera;

	protected NarrowLayout listLayout;

	protected LimitedViewAttachment listAttachment;

	protected HashMap<AvailableGameInfo, SimpleButtonActor> list;

	protected SimpleButtonActor refreshButton;

	public GameSelectScreen(TresApplication game, Viewport viewport) {
		super(game);

	}

	@Override
	protected void init() {
		this.camera = new OrthographicCamera(getViewport().getScreenWidth(), getViewport().getScreenHeight());

		this.listAttachment = new LimitedViewAttachment(5);

		this.listLayout = new NarrowLayout(
				WorldUtils.percentage(0, -0.5f).add(0, 60),
				300,
				120 * 5,
				Align.center,
				50,
				true
		);

		this.refreshButton = new SimpleButtonActor(
				new Vector2(310, 50),
				new SimpleButtonActor.Button(
						"Refresh",
						80,
						50,
						null,
						0f
				)
		);

		this.list = new HashMap<>();

		this.stage.addActor(this.refreshButton);

		this.getViewport().setCamera(this.camera);
	}

	public void addGame(AvailableGameInfo gameInfo) {
		SimpleButtonActor button = new SimpleButtonActor(
				new Vector2(0, 0),
				new SimpleButtonActor.Button(
						"Game: " + gameInfo.getGameId() + "\nPlayer: " + gameInfo.getPlayers(),
						400,
						120,
						new Color(0.1f, 0.1f, 0.1f, 1f),
						0f
				)
		);

		this.stage.addActor(button);
		this.list.put(gameInfo, button);
		this.listLayout.add(button);
	}

	public void clearList() {
		for (Actor actor : this.list.values()) {
			actor.remove();
		}

		this.list.clear();
		this.listLayout.getActors().clear();
	}

	synchronized private void refresh(Promise<Collection<AvailableGameInfo>> promise) {
		this.clearList();

		Collection<AvailableGameInfo> result = promise.getResult();

		for (AvailableGameInfo gameInfo : result) {
			this.addGame(gameInfo);
		}
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		ScreenUtils.clear(0.25f, 0.25f, 0.25f, 1f);

		this.stage.act(delta);
		this.stage.draw();


		if (this.refreshButton.isPressed()) {
			Promise<Collection<AvailableGameInfo>> promise = DesktopLauncher.client.getSession().getActions().getAvailableGames();
			promise.onSuccess(() -> {

				SleeperNotifier notifier = new SleeperNotifier();
				this.game.getRenderSleeper().addNotifier(notifier, () -> {
					this.refresh(promise);

					this.game.getRenderSleeper().removeNotifier(notifier);
				});

				notifier.wakeup();
			});
		}

		for (AvailableGameInfo info : this.list.keySet()) {
			SimpleButtonActor button = this.list.get(info);
			if (button.isPressed()) {
				this.game.setScreen(new InGamePlayScreen(this.game, this.getViewport(), info.getGameId()));
				break;
			}
		}
	}
}
