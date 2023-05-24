package tres.sequence.ingame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tres.network.uno.NetworkCard;
import com.tres.network.uno.container.FuncDifferenceContainerListener;
import com.tres.utils.EventLinks;
import com.tres.utils.Utils;
import tres.ScreenSequence;
import tres.TresApplication;
import tres.client.event.game.PlayerJoinedToGameEvent;
import tres.client.event.game.PlayerLeftGameEvent;
import tres.client.ui.CardTextures;
import tres.client.ui.FontFactory;
import tres.client.ui.TextureUtils;
import tres.client.ui.WorldUtils;
import tres.client.ui.actor.CardActor;
import tres.client.ui.actor.CardStackActor;
import tres.client.uno.*;
import tres.sequence.GameSelectScreen;

import java.util.*;

public class InGamePlayScreen extends ScreenSequence {

	protected OrthographicCamera camera;

	protected HashMap<Short, CardRenderer> cardRenderers;

	protected BitmapFont font;

	protected boolean connectedToGame;

	protected float connectedTime;

	protected EventLinks eventLinks;

	protected int gameId;

	protected float cardFocusTime;

	protected CardStackActor cardStack;

	protected ClientGame uno;

	protected ClientPlayer player;

	protected InGamePlayerData inGameData;

	public InGamePlayScreen(TresApplication game, Viewport viewport, int gameId) {
		super(game);
		this.gameId = gameId;
		this.stage.setViewport(viewport);
	}

	@Override
	protected void init() {
		this.connectedToGame = false;
		ClientPlayer player = this.game.getClient().getSession().getPlayer();
		this.font = FontFactory.getInstance().simpleGenerate(75, new Color(0.8f, 0.8f, 0.8f, 1f), 1, new Color(0.1f, 0.1f, 0.1f, 1f), new Vector2(1, 1));
		this.camera = new OrthographicCamera(getViewport().getScreenWidth(), getViewport().getScreenHeight());
		this.cardRenderers = new HashMap<>();

		assert player != null;
		this.initPlayerCardRenderer(player.getId());

		this.cardStack = new CardStackActor();
		this.cardStack.setPosition(0, 0);

		this.stage.setDebugUnderMouse(true);

		getViewport().setCamera(this.camera);

		this.camera.position.set(0f, 0f, 500f);
		// this.camera.lookAt(0, 0, 0);
		this.camera.near = 1f;
		this.camera.far = 3500f;
		// this.camera.update();

		if (this.game.getClient().getSession().getPlayer() == null) {
			throw new RuntimeException("Player null");
		}

		this.eventLinks = new EventLinks(this.game.getClient().getEventEmitter());

		this.game.getClient().getSession().getActions().requestJoinGame(this.gameId);

		this.eventLinks.on(this.game.getClient().getEventEmitter().on(PlayerJoinedToGameEvent.class, (channel, event) -> {
			short playerId = event.getPlayerRuntimeId();

			if (this.game.getClient().getSession().getPlayer().getId() == playerId) {
				this.connectedToGame = true;

				this.uno = this.game.getClient().getSession().getPlayer().getInGameData().getGame();

				this.initCardStackListener();
			}

			this.initPlayerCardRenderer(playerId);
		}));

		this.eventLinks.on(this.game.getClient().getEventEmitter().on(PlayerLeftGameEvent.class, ((channel, event) -> {
			short playerId = event.getPlayerRuntimeId();

			if (this.game.getClient().getSession().getPlayer().getId() == playerId) {
				Utils.synchronizeSleeperOnce(this.game.getRenderSleeper(), (notifier -> {
					this.game.setScreen(new GameSelectScreen(this.game, this.getViewport()));
				}), true);
			}
		})));


		this.stage.addActor(this.cardStack);

		this.player = player;
		this.inGameData = player.getInGameData();
	}

	public CardRenderer getMineCardRenderer() {
		return this.cardRenderers.get(this.player.getId());
	}

	private void initPlayerCardRenderer(short playerRuntimeId) {
		ViewPlayer player = this.game.getClient().getSession().getViewPlayers().get(playerRuntimeId);

		if (player == null) {
			return;
		}

		if (this.cardRenderers.containsKey(playerRuntimeId)) {
			return;
		}

		CardRenderer renderer = new CardRenderer(this.game, this.stage, player.getCards());

		this.cardRenderers.put(playerRuntimeId, renderer);
	}

	private void initCardStackListener() {
		FuncDifferenceContainerListener<NetworkCard> listener = new FuncDifferenceContainerListener<NetworkCard>(
				(slot, card) -> {
					Utils.synchronizeSleeperOnce(this.game.getRenderSleeper(), (notifier) -> {
						CardActor actor;

						if (card.hasCardInfo()) {
							actor = new CardActor(card.getCard());
						} else {
							actor = new CardActor();
						}

						this.cardStack.addToTop(card.runtimeId, actor);

						this.updateCardTint();
					}, true);
				},
				(slot, card) -> {
					Utils.synchronizeSleeperOnce(this.game.getRenderSleeper(), (notifier) -> {
						this.cardStack.getCards().remove(card.runtimeId);

						this.updateCardTint();
					}, true);
				}
		) {
		};

		listener.listen(this.uno.getStack());
	}

	protected void updateCardTint(Collection<CardActor> all) {
		if (this.cardStack.getTopStack() == null) {
			return;
		}

		for (CardActor actor : all) {
			if (this.uno.getRule().getCardRule().canPlay(actor.getCard(), this.cardStack.getTopStack().getActor().getCard())) {
				actor.setColor(0.0f, 0f, 0f, 0f);
			} else {
				actor.setColor(0f, 0f, 0f, 0.5f);
			}
		}
	}

	protected void updateCardTint() {
		this.updateCardTint(this.getMineCardRenderer().getCardActors().getActorMap().values());
	}

	protected void renderLoad(float delta) {
		if (this.connectedToGame) {
			TextureUtils.drawCenterFont(this.stage.getBatch(), this.font, "参加に成功しました！ 準備中です...", 0, 0);
		} else {
			TextureUtils.drawCenterFont(this.stage.getBatch(), this.font, "ゲームに参加中...", 0, 0);
		}
	}

	protected void cameraRotateUpdate(float delta) {
		if (Gdx.input.isButtonPressed(Input.Buttons.MIDDLE)) {
			this.camera.translate(Gdx.input.getDeltaX() * this.game.getSettings().getSensitivity(), 0);
		}
	}

	@Override
	public void focus() {
		super.focus();

		getViewport().setCamera(this.camera);
		getViewport().apply();
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0f, 0f, 0f, 1f);

		Vector2 mousePos = this.stage.screenToStageCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

		this.connectedTime += delta;

		this.cameraRotateUpdate(delta);

		if (!this.connectedToGame || this.connectedTime < 3f) {
			this.stage.getBatch().setProjectionMatrix(this.camera.combined);
			this.stage.getBatch().begin();
			this.renderLoad(delta);
			this.stage.getBatch().end();
			return;
		}

		Vector2 basePos = new Vector2(0, 0);

		basePos.y = -600f + (Math.min(1f, this.cardFocusTime * 7f) * 200f);


		if (mousePos.y < basePos.y + CardTextures.HEIGHT * this.getMineCardRenderer().getCardScale().y + 25f) {
			this.cardFocusTime += delta;
		} else {
			this.cardFocusTime = 0f;
		}

		List<CardActor> hitActors = new ArrayList<>();

		Collection<CardActor> cardActorCollection = this.getMineCardRenderer().getCardActors().getActorMap().values();

		for (CardActor actor : cardActorCollection) {
			Vector2 localPos = actor.screenToLocalCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
			actor.setHit(actor.hit(localPos.x, localPos.y, true) != null);
			actor.setSelected(false);
			if (actor.isHit()) {
				hitActors.add(actor);
			}
		}

		for (Map.Entry<Short, CardRenderer> entry : this.cardRenderers.entrySet()) {
			CardRenderer cardRenderer = entry.getValue();
			Vector2 position;
			if (entry.getKey() == this.player.getId()) {
				position = new Vector2(0, basePos.y);
			} else {
				position = new Vector2(0, 0);
				this.game.getClient().getLogger().info(String.format("%d, %d", entry.getKey(), this.player.getId()));
			}


			int cardWidth = (int) (CardTextures.WIDTH * cardRenderer.getCardScale().x * 0.5f);
			int crWidth = WorldUtils.WIDTH / 2;
			int crRealWidth = cardRenderer.getCardActors().getActorMap().size() * cardWidth;
			if (crRealWidth > crWidth) {
				crRealWidth = crWidth;
			}

			cardRenderer.update(position, cardActorCollection, crWidth);
		}


		CardActor target = null;
		float x = -WorldUtils.WIDTH;

		for (CardActor actor : hitActors) {
			if (actor.getX() > x) {
				x = actor.getX();
				target = actor;
			}
		}

		if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
			PlayerTurnData turnData = this.inGameData.getTurnData();
			if (turnData != null) {
				turnData.drawCard();
			}
		}

		if (Gdx.input.isButtonJustPressed(Input.Buttons.MIDDLE)) {
			PlayerTurnData turnData = this.inGameData.getTurnData();
			if (turnData != null) {
				turnData.skipTurn();
			}
		}

		if (target != null) {
			target.setSelected(true);

			if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
				if (this.cardStack.getTopStack() == null || this.uno.getRule().getCardRule().canPlay(target.getCard(), this.cardStack.getTopStack().getActor().getCard())) {
					Integer runtimeId = this.getMineCardRenderer().getCardActors().getIdMap().get(target.getCard());
					if (runtimeId != null) {
						PlayerTurnData turnData = this.inGameData.getTurnData();
						if (turnData != null) {
							turnData.playCard(Collections.singletonList(runtimeId));
						}
					}
				}
			}
			for (CardActor actor : cardActorCollection) {
				if (actor == target) {
					continue;
				}

				float dist = Math.abs(target.getX() - actor.getX());
				float perc = 1.0f - (dist / (this.getMineCardRenderer().calculateCardSizedWidth()));

				if (target.getX() > actor.getX()) {
					actor.setX(actor.getX() - 50 * perc);
				} else {
					actor.setX(actor.getX() + 50 * perc);
				}
			}
		}

		this.stage.act(delta);
		this.stage.draw();

	}

	@Override
	public void dispose() {
		super.dispose();

		this.eventLinks.offAll();
	}
}
