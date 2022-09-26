package client.ui;

import network.uno.Card;

import java.awt.*;

public class CardImage extends BaseVisible {

	protected Card.Symbol symbol;
	protected Card.Color color;

	protected Image image;

	public CardImage(Card.Symbol symbol, Card.Color color) {
		this.symbol = symbol;
		this.color = color;
		this.image = CardImageIds.getImage(symbol, color);
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, rect.x, rect.y, rect.width, rect.height, null);
	}

	@Override
	public void update() {

	}
}
