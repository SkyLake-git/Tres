package tres.sequence.ingame;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tres.network.uno.NetworkCard;
import com.tres.network.uno.container.FuncDifferenceContainerListener;
import com.tres.network.uno.container.NetworkCardContainer;
import com.tres.snooze.SleeperNotifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tres.TresApplication;
import tres.client.ui.CardTextures;
import tres.client.ui.WorldUtils;
import tres.client.ui.actor.CardActor;
import tres.client.uno.CardActors;

import java.util.Collection;

public class CardRenderer {

	protected NetworkCardContainer container;

	protected CardActors cardActors;

	protected TresApplication game;

	protected Vector2 cardScale;

	protected Logger logger;

	protected Stage stage;

	public CardRenderer(
			TresApplication game,
			Stage stage,
			NetworkCardContainer container
	) {
		this.container = container;
		this.cardActors = new CardActors();
		this.cardScale = new Vector2(0.5f, 0.5f);
		this.game = game;
		this.stage = stage;

		this.logger = LoggerFactory.getLogger(this.getClass());
		this.initListener();
	}

	public NetworkCardContainer getContainer() {
		return container;
	}

	public CardActors getCardActors() {
		return cardActors;
	}

	public TresApplication getGame() {
		return game;
	}

	public Vector2 getCardScale() {
		return cardScale;
	}

	public Stage getStage() {
		return stage;
	}

	private void initListener() {
		FuncDifferenceContainerListener<NetworkCard> listener = new FuncDifferenceContainerListener<NetworkCard>(
				(slot, card) -> {
					SleeperNotifier notifier = new SleeperNotifier();

					this.game.getRenderSleeper().addNotifier(notifier, () -> {
						CardActor actor;

						if (card.hasCardInfo()) {
							actor = new CardActor(card.getCard());
						} else {
							actor = new CardActor();
						}

						actor.setSize(actor.getWidth() * this.cardScale.x, actor.getHeight() * this.cardScale.y);
						this.stage.addActor(actor);
						synchronized (this.cardActors.getActorMap()) {
							this.cardActors.add(card.runtimeId, actor);
						}

						this.logger.info("add actor");

						this.game.getRenderSleeper().removeNotifier(notifier);
					});

					notifier.wakeup();
				},
				(slot, card) -> {
					SleeperNotifier notifier = new SleeperNotifier();

					this.game.getRenderSleeper().addNotifier(notifier, () -> {
						CardActor actor = this.cardActors.getActorMap().get(card.runtimeId);

						if (actor != null) {
							actor.remove();
							synchronized (this.cardActors.getActorMap()) {
								this.cardActors.remove(actor);
							}
						}

						this.logger.info("remove actor");
						this.game.getRenderSleeper().removeNotifier(notifier);
					});

					notifier.wakeup();
				}
		) {
		};

		listener.listen(this.container);
	}

	public float calculateCardSizedWidth() {
		int cardWidth = (int) (CardTextures.WIDTH * getCardScale().x * 0.5f);
		int crWidth = WorldUtils.WIDTH / 2;
		int crRealWidth = getCardActors().getActorMap().size() * cardWidth;
		if (crRealWidth > crWidth) {
			crRealWidth = crWidth;
		}

		return crRealWidth;
	}

	public void update(Vector2 position, Collection<CardActor> cards, int width) {
		int cardWidth = (int) (CardTextures.WIDTH * this.cardScale.x);
		int cardCount = cards.size();
		int widthWill = cardWidth * cardCount;
		float widthOver = widthWill / (float) width;

		int doCardWidth;
		if (widthOver > 1.0) {
			doCardWidth = (int) (cardWidth / widthOver);
		} else {
			doCardWidth = cardWidth;
		}

		int start = (int) (position.x - (doCardWidth * cardCount / 2));

		int i = 0;
		for (CardActor actor : cards) {
			actor.setPosition(start + (doCardWidth * i), position.y);
			i++;
		}
	}
}
