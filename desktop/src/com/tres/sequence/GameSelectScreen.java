package com.tres.sequence;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tres.DesktopLauncher;
import com.tres.ScreenSequence;
import com.tres.TresApplication;
import com.tres.client.ui.WorldUtils;
import com.tres.client.ui.actor.BorderlessButtonActor;
import com.tres.client.ui.layout.NarrowLayout;
import com.tres.client.ui.layout.attachment.LimitedViewAttachment;
import com.tres.network.packet.protocol.types.AvailableGameInfo;
import com.tres.promise.Promise;
import com.tres.snooze.SleeperNotifier;

import java.util.ArrayList;

public class GameSelectScreen extends ScreenSequence {
	protected OrthographicCamera camera;

	protected NarrowLayout listLayout;

	protected LimitedViewAttachment listAttachment;

	protected ArrayList<BorderlessButtonActor> list;

	protected BorderlessButtonActor refreshButton;

	protected ArrayList<AvailableGameInfo> addQueue;


	public GameSelectScreen(TresApplication game, Viewport viewport) {
		super(game, viewport);

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

		this.refreshButton = new BorderlessButtonActor(
				new Vector2(310, 50),
				new BorderlessButtonActor.Button(
						"Refresh",
						80,
						50,
						new Color(0f, 0f, 0f, 1f),
						0f
				)
		);

		this.list = new ArrayList<>();

		this.stage.addActor(this.refreshButton);

		this.getViewport().setCamera(this.camera);

		this.addQueue = new ArrayList<>();
	}

	public void addGame(AvailableGameInfo gameInfo) {
		BorderlessButtonActor button = new BorderlessButtonActor(
				new Vector2(0, 0),
				new BorderlessButtonActor.Button(
						"Game: " + gameInfo.getGameId() + "\nPlayer: " + gameInfo.getPlayers(),
						400,
						120,
						new Color(0.1f, 0.1f, 0.1f, 1f),
						0f
				)
		);

		this.stage.addActor(button);
		this.list.add(button);
		this.listLayout.add(button);
	}

	public void clearList() {
		for (Actor actor : this.list) {
			actor.remove();
		}

		this.list.clear();
		this.listLayout.getActors().clear();
	}

	synchronized private void refresh(Promise<ArrayList<AvailableGameInfo>> promise) {
		this.clearList();

		ArrayList<AvailableGameInfo> result = promise.getResult();

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
			Promise<ArrayList<AvailableGameInfo>> promise = DesktopLauncher.client.getSession().getActions().getAvailableGames();
			promise.onSuccess(() -> {
				DesktopLauncher.client.getLogger().info("Received!!");

				SleeperNotifier notifier = new SleeperNotifier();
				this.game.getRenderSleeper().addNotifier(notifier, () -> {
					DesktopLauncher.client.getLogger().info("notify");
					this.refresh(promise);

					this.game.getRenderSleeper().removeNotifier(notifier);
				});

				notifier.wakeup();
			});
		}
	}
}
