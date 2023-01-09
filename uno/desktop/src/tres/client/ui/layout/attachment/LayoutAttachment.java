package tres.client.ui.layout.attachment;

import com.badlogic.gdx.scenes.scene2d.Actor;

abstract public class LayoutAttachment {

	abstract public boolean set(Actor actor, int index);

	abstract public boolean update();
}
