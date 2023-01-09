package tres.client.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;

public class FontFactory {

	private static FontFactory instance;

	protected FreeTypeFontGenerator generator;

	public static FontFactory getInstance() {

		if (instance == null) {
			instance = new FontFactory();
		}

		return instance;
	}

	public FontFactory() {
		this.generator = new FreeTypeFontGenerator(Gdx.files.internal("ipaexg.ttf")); // todo: setting
	}

	public BitmapFont generate(FreeTypeFontGenerator.FreeTypeFontParameter param) {
		return this.generator.generateFont(param);
	}

	public BitmapFont simpleGenerate(int size, Color color, int borderWidth, Color borderColor, Vector2 shadowOffset) {
		FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
		param.size = size;
		param.color = color;
		param.borderWidth = borderWidth;
		param.borderColor = borderColor;
		param.shadowOffsetX = (int) shadowOffset.x;
		param.shadowOffsetY = (int) shadowOffset.y;

		return this.generate(param);
	}
}
