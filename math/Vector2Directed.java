package math;

public class Vector2Directed extends Vector2 {

	public Vector2 direction;

	public Vector2Directed(double x, double y) {
		super(x, y);
		this.direction = new Vector2(0, 0);
	}

	public void move() {
		this.x += this.direction.x;
		this.y += this.direction.y;
	}
}
