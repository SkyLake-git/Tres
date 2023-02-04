package tres.client.ui.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.effects.BloomEffect;
import com.crashinvaders.vfx.effects.ChainVfxEffect;
import com.tres.network.uno.Card;
import tres.client.ui.CardTextures;

public class CardActor extends Actor {

	protected Texture texture;

	protected boolean isHit;

	protected boolean isSelected;

	protected float selectedTime;

	protected VfxManager vfx;

	protected ChainVfxEffect effect;

	protected Card card;

	public CardActor(Card card) {
		this.card = card;
		this.texture = CardTextures.MAP.get(CardTextures.getIndex(card.symbol, card.color));
		this.setBounds(0, 0, this.texture.getWidth(), this.texture.getHeight());
		this.vfx = new VfxManager(Pixmap.Format.RGBA8888);

		this.effect = new BloomEffect();
		this.vfx.addEffect(effect);

		this.isSelected = false;
		this.isHit = false;
		this.selectedTime = 0;
	}

	public Card getCard() {
		return card;
	}

	public Texture getTexture() {
		return texture;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		float x = getX();
		float y = getY();

		if (this.isSelected) {
			y += 50 * Math.min(1, this.selectedTime / 0.225f);

			// this.vfx.cleanUpBuffers();
			// this.vfx.beginInputCapture();
		}

		batch.draw(this.texture, x, y, getWidth(), getHeight());
		super.draw(batch, parentAlpha);

		if (this.isSelected) {
			// this.vfx.endInputCapture();
			// this.vfx.applyEffects();
			// this.vfx.renderToScreen();
		}
	}

	public boolean isHit() {
		return isHit;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		Vector2 mousePosLocal = this.screenToLocalCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
		this.isHit = this.hit(mousePosLocal.x, mousePosLocal.y, true) != null;

		if (this.isSelected) {
			this.selectedTime += delta;
		} else {
			this.selectedTime = 0;
		}
	}

	@Override
	public boolean remove() {
		if (super.remove()) {
			this.vfx.dispose();
			this.effect.dispose();
			return true;
		}

		return false;
	}

	@Override
	public Actor hit(float x, float y, boolean touchable) {
		return super.hit(x, y, touchable);
	}
}
