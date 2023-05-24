package tres.client.ui.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class PinSliderActor extends SliderActor {

	private final PinInterpolation interpolation;

	public PinSliderActor(Vector2 position, float width, float height, float min, float max, float stepSize, boolean vertical, BitmapFont font, String format) {
		super(position, width, height, min, max, stepSize, vertical, font, format);

		this.interpolation = new PinInterpolation(this.getMaxValue(), this.getMinValue());
		this.setVisualInterpolationInverse(this.interpolation);
	}

	public void addPin(SliderPin pin) {
		this.interpolation.pins.add(pin);
	}

	@Override
	public void setRange(float min, float max) {
		super.setRange(min, max);

		this.interpolation.maxValue = this.getMaxValue();
		this.interpolation.minValue = this.getMinValue();
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		for (SliderPin pin : this.interpolation.pins) {
			float percentage = (pin.value - this.getMinValue()) / (this.getMaxValue() - this.getMinValue());

			float x = getX() + this.getWidth() * percentage;
			x -= pin.width / 2f;

			float y = getY() + this.getHeight() + 6;

			if (!pin.hasTexture) {
				continue;
			}

			batch.draw(pin.getTexture(), x, y);
		}
	}

	public static class PinInterpolation extends Interpolation {

		public ArrayList<SliderPin> pins;

		public float maxValue;

		public float minValue;

		public PinInterpolation(float maxValue, float minValue) {
			super();

			this.maxValue = maxValue;
			this.minValue = minValue;

			this.pins = new ArrayList<>();
		}

		@Override
		public float apply(float a) {
			float nearestDist = Float.MAX_VALUE;
			float nearestValue = 0f;
			float v = this.minValue + a * this.maxValue;
			boolean found = false;
			for (SliderPin pin : this.pins) {
				float dist = Math.abs(pin.value - v);
				if (dist <= pin.valueArea) {
					nearestDist = dist;
					nearestValue = pin.value;
					found = true;
				}
			}
			if (found) {
				return (nearestValue - this.minValue) / (this.maxValue - this.minValue);
			}

			return a;
		}
	}

	public static class SliderPin {

		public float value;

		public float valueArea;

		public boolean hasTexture;

		protected int height;

		protected int width;

		protected Color color;

		protected Texture texture;

		public SliderPin(float value, float valueArea) {
			this.value = value;
			this.valueArea = valueArea;
			this.height = 12;
			this.width = 3;
			this.color = new Color(1f, 1f, 1f, 1f);
			this.markDirty();
		}


		public SliderPin(float value, float valueArea, Color color) {
			this.value = value;
			this.valueArea = valueArea;
			this.height = 12;
			this.width = 3;
			this.color = color;
			this.markDirty();
		}

		public SliderPin(float value, float valueArea, Color color, int height, int width) {
			this.value = value;
			this.valueArea = valueArea;
			this.height = height;
			this.width = width;
			this.color = color;
			this.markDirty();
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
			this.markDirty();
		}

		public Color getColor() {
			return color;
		}

		public void setColor(Color color) {
			this.color = color;
			this.markDirty();
		}

		public void markDirty() {
			this.texture = null;
			this.hasTexture = false;

			this.recreateTexture(); // todo
		}

		protected void recreateTexture() {
			Pixmap pixmap = new Pixmap(this.width, this.height, Pixmap.Format.RGBA8888);
			pixmap.setColor(this.color);
			pixmap.fill();
			this.texture = new Texture(pixmap);
			this.hasTexture = true;
			pixmap.dispose();
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
			this.markDirty();
		}

		public Texture getTexture() {
			return texture;
		}
	}
}
