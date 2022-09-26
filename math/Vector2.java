package math;

import java.awt.*;

public class Vector2 implements Cloneable {

	public double x;
	public double y;

	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector2 abs() {
		return new Vector2(Math.abs(this.x), Math.abs(this.y));
	}

	public Vector2 floor() {
		return new Vector2(Math.floor(this.x), Math.floor(this.y));
	}

	public Vector2 ceil() {
		return new Vector2(Math.ceil(this.x), Math.ceil(this.y));
	}

	public Vector2 add(double x, double y) {
		return new Vector2(this.x + x, this.y + y);
	}

	public Vector2 subtract(double x, double y) {
		return new Vector2(this.x - x, this.y - y);
	}

	public Vector2 addVector(Vector2 v) {
		return new Vector2(this.x + v.x, this.y + v.y);
	}

	public Vector2 subtractVector(Vector2 v) {
		return new Vector2(this.x - v.x, this.y - v.y);
	}

	public Vector2 multiply(double m) {
		return new Vector2(this.x * m, this.y * m);
	}

	public Vector2 divide(double d) {
		return new Vector2(this.x / d, this.y / d);
	}

	public double lengthSquared() {
		return Math.pow(this.x, 2) + Math.pow(this.y, 2);
	}

	public double length() {
		return Math.sqrt(this.lengthSquared());
	}

	public double distanceSquared(Vector2 v) {
		return Math.pow(this.x - v.x, 2) + Math.pow(this.y - v.y, 2);
	}

	public double distance(Vector2 v) {
		return Math.sqrt(this.distanceSquared(v));
	}

	public Vector2 normalize() {
		double len = this.lengthSquared();
		if (len > 0) {
			return this.divide(Math.sqrt(len));
		}

		return new Vector2(0, 0);
	}

	public double dot(Vector2 v) {
		return this.x * v.x + this.y * v.y;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Vector2(this.x, this.y);
	}

	public Point toPoint() {
		return new Point((int) this.x, (int) this.y);
	}
}
