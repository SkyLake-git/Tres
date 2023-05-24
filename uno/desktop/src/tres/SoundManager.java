package tres;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

	protected Sound buttonOver;

	protected Sound buttonClick;

	public SoundManager() {
		this.buttonOver = Gdx.audio.newSound(Gdx.files.internal("sound/button45.mp3"));
		this.buttonClick = Gdx.audio.newSound(Gdx.files.internal("sound/button63.mp3"));
	}

	public void playButtonOver(float volume, float pitch) {
		this.buttonOver.play(volume, pitch, 0f);
	}

	public void playButtonClick(float volume, float pitch) {
		this.buttonClick.play(volume, pitch, 0f);
	}
}
