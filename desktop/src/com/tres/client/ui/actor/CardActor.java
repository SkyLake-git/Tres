package com.tres.client.ui.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tres.client.ui.CardTextures;
import com.tres.network.uno.Card;

public class CardActor extends Actor {

	protected Texture texture;

	public CardActor(Card card) {
		this.texture = CardTextures.MAP.get(CardTextures.getIndex(card.symbol, card.color));
		this.setBounds(0, 0, this.texture.getWidth(), this.texture.getHeight());
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

	@Override
	public Actor hit(float x, float y, boolean touchable) {
		return super.hit(x, y, touchable);
	}
}
