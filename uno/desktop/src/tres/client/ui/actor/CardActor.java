package tres.client.ui.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.effects.ChainVfxEffect;
import com.tres.network.uno.Card;
import tres.client.ui.CardTextures;

public class CardActor extends Entity {

	protected Texture texture;

	protected boolean isHit;

	protected boolean isSelected;

	protected float selectedTime;

	protected VfxManager vfx;

	protected ChainVfxEffect effect;

	protected Card card;

	protected Texture cardTexture;

	protected Color lastRecreateColor;

	public CardActor(Card card) {
		super();
		this.card = card;
		this.cardTexture = CardTextures.MAP.get(CardTextures.getIndex(card.symbol, card.color));
		this.recreateTexture(new Color());
		this.setBounds(0, 0, this.texture.getWidth(), this.texture.getHeight());

		this.setColor(new Color());
		this.isSelected = false;
		this.isHit = false;
		this.selectedTime = 0;
	}

	public CardActor() {
		super();
		this.card = null;
		this.cardTexture = CardTextures.BACK;
		this.recreateTexture(new Color());
		this.setBounds(0, 0, this.texture.getWidth(), this.texture.getHeight());
		this.setColor(new Color());

		this.isSelected = false;
		this.isHit = false;
		this.selectedTime = 0;
	}

	protected void recreateTexture(Color tintColor) {
		if (this.texture != null) this.texture.dispose();
		Pixmap pixmap = new Pixmap(cardTexture.getWidth(), cardTexture.getHeight(), Pixmap.Format.RGBA8888);

		this.cardTexture.getTextureData().prepare();
		Pixmap consume = this.cardTexture.getTextureData().consumePixmap();
		pixmap.drawPixmap(consume, 0, 0);


		pixmap.setColor(tintColor);
		pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());

		this.texture = new Texture(pixmap);

		pixmap.dispose();
		this.cardTexture.getTextureData().disposePixmap();
		consume.dispose();

		this.lastRecreateColor = this.getColor().cpy();
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

		if (!this.lastRecreateColor.equals(this.getColor())) {
			this.recreateTexture(this.getColor());
		}

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

	public void setHit(boolean hit) {
		isHit = hit;
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

		if (this.isSelected) {
			this.selectedTime += delta;
		} else {
			this.selectedTime = 0;
		}
	}

	@Override
	public boolean remove() {
		if (super.remove()) {
			return true;
		}

		return false;
	}

	@Override
	public Actor hit(float x, float y, boolean touchable) {
		return super.hit(x, y, touchable);
	}


	@Override
	protected void init() {

	}
}
