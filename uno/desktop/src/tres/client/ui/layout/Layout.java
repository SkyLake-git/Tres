package tres.client.ui.layout;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import tres.client.ui.layout.attachment.LayoutAttachment;

import java.util.ArrayList;
import java.util.Collection;

abstract public class Layout {

	protected Vector2 position;
	protected int width;
	protected int height;

	protected ArrayList<Actor> actors;

	protected ArrayList<LayoutAttachment> attachments;

	public Layout(Vector2 position, int width, int height) {
		this.position = position;
		this.width = width;
		this.height = height;

		this.attachments = new ArrayList<>();
		this.actors = new ArrayList<>();
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public ArrayList<Actor> getActors() {
		return actors;
	}

	public ArrayList<LayoutAttachment> getAttachments() {
		return attachments;
	}

	public void addAttachment(LayoutAttachment attachment) {
		this.attachments.add(attachment);
	}

	abstract public void set(Actor actor, int index);

	public void add(Actor actor) {
		this.actors.add(actor);
		this.update();
	}

	public void addAll(Collection<? extends Actor> actors) {
		this.actors.addAll(actors);
		this.update();
	}

	public void update() {
		int index = 0;


		boolean result = true;
		for (LayoutAttachment attachment : this.attachments) {
			boolean currentResult = attachment.update();

			if (!currentResult) {
				result = false;
				break;
			}
		}

		if (!result) {
			return;
		}


		for (Actor e : this.actors) {
			boolean setResult = true;
			for (LayoutAttachment attachment : this.attachments) {
				boolean currentResult = attachment.set(e, index);

				if (!currentResult) {
					setResult = false;
					break;
				}
			}

			if (!setResult) {
				continue;
			}
			this.set(e, index);

			index++;
		}
	}


}
