package tres.client.ui.layout;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LinearLayout extends Layout {

	protected boolean vertical;

	protected int align;

	public LinearLayout(Vector2 position, int width, int height, boolean vertical, int align) {
		super(position, width, height);

		this.vertical = vertical;
		this.align = align;
	}

	public boolean isVertical() {
		return vertical;
	}

	public void setVertical(boolean vertical) {
		this.vertical = vertical;
	}

	public int getAlign() {
		return align;
	}

	public void setAlign(int align) {
		this.align = align;
	}

	@Override
	public void set(Actor actor, int index) {
		float percentage = index / Math.max(1, this.actors.size() - 1);

		float x = this.position.x;
		float y = this.position.y;
		if (this.vertical) {
			y -= (this.height * percentage);
		} else {
			x += (this.width * percentage);
		}

		actor.setPosition(x, y, this.align);
	}
}
