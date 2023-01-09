package tres.client.ui.layout.attachment;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class LimitedViewAttachment extends LayoutAttachment {

	protected int offset = 0;

	protected int size;

	public LimitedViewAttachment(int size) {
		this.size = size;
	}

	public void scroll(int value) {
		this.offset += value;

		if (this.offset < 0) {
			this.offset = 0;
		}
	}

	public void scroll() {
		this.scroll(1);
	}

	@Override
	public boolean set(Actor actor, int index) {
		if (index < this.offset) {
			return false;
		}

		return index <= this.offset + this.size;
	}

	@Override
	public boolean update() {
		return true;
	}
}
