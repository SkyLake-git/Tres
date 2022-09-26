package client.ui.gui.sequence;

import java.awt.*;

public interface ISequence {

	int update(ISequence parent);

	void render(Graphics g);

	void close();
}
