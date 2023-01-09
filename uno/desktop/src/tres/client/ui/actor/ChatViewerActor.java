package tres.client.ui.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;

public class ChatViewerActor extends Actor {

	public static class ChatView {
		public float fontScale;
		public Color fontColor;
		public int maxColumn;
		public int lines;

		public Color backgroundColor;

		public ChatView(float fontScale, Color fontColor, int maxColumn, int lines, Color backgroundColor) {
			this.fontScale = fontScale;
			this.fontColor = fontColor;
			this.maxColumn = maxColumn;
			this.lines = lines;
			this.backgroundColor = backgroundColor;
		}
	}

	public static class ChatLog {
		public CharSequence content;
		public float duration;
		public float remain;

		public ChatLog(CharSequence content, int duration) {
			this.content = content;
			this.duration = duration;
			this.remain = duration;
		}
	}

	protected BitmapFont font;

	protected ChatView chatView;

	protected ArrayList<ChatLog> logs;

	protected Texture texture;


	public ChatViewerActor(Vector2 position, ChatView chatView) {
		this.font = new BitmapFont();
		this.font.getData().setScale(chatView.fontScale);
		this.font.setColor(chatView.fontColor);

		this.chatView = chatView;

		this.logs = new ArrayList<>();

		setPosition(position.x, position.y);

		setSize((float) (chatView.maxColumn * 5), this.font.getLineHeight() * (chatView.lines) * 1.15f + 15);
		this.recreateTexture((int) getWidth(), (int) getHeight(), chatView.backgroundColor);
	}

	public void recreateTexture(int width, int height, Color color) {
		Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fill();

		this.texture = new Texture(pixmap);

		pixmap.dispose();
	}

	public void addLog(ChatLog log) {
		this.logs.add(log);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		batch.draw(this.texture, getX() - 15, getY() - 6);


		Color baseColor = this.font.getColor();
		int count = 0;
		for (ChatLog log : this.logs) {
			count++;
			CharSequence str = log.content.subSequence(0, Math.min(this.chatView.maxColumn, log.content.length()));
			if (log.content.length() > this.chatView.maxColumn) {
				str = str + "...";
			}
			float alpha = Math.min(1.0f, log.remain / 0.25f);
			float mx = -(70 * (1.0f - alpha));
			this.font.setColor(baseColor.r, baseColor.g, baseColor.b, alpha);

			this.font.draw(batch, str, getX() + mx, getY() + count * this.font.getLineHeight());

			if (count > this.chatView.lines) {
				break;
			}
		}
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		for (ChatLog log : (ArrayList<ChatLog>) this.logs.clone()) {
			log.remain -= delta;

			if (log.remain < 0) {
				this.logs.remove(log);
			}
		}

	}
}
