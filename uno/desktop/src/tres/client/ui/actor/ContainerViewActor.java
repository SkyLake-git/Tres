package tres.client.ui.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.tres.network.uno.container.NetworkCardContainer;
import tres.TresApplication;
import tres.sequence.ingame.CardRenderer;

public class ContainerViewActor extends Actor {

	protected NetworkCardContainer container;

	protected CardRenderer renderer;

	protected float cardScale;

	public ContainerViewActor(Vector2 position, NetworkCardContainer container, TresApplication game) {
		super();

		setPosition(position.x, position.y);

		this.container = container;
		this.renderer = new CardRenderer(game, this.getStage(), container);
		this.cardScale = 1.0f;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		this.renderer.update(
				new Vector2(this.getX(Align.center), this.getY(Align.center)),
				this.renderer.getCardActors().getActorMap().values(),
				(int) this.getWidth()
		);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}
}
