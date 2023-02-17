package framework;

public abstract class AbstractGameObj implements GameObj {
	protected Vertex pos;
	protected Vertex velocity;
	protected double width;
	protected double height;

	public Vertex pos() {
		return pos;
	}

	public Vertex velocity() {
		return velocity;
	}

	public double width() {
		return width;
	}

	public double height() {
		return height;
	}

	public AbstractGameObj(Vertex pos, Vertex vertex, double width, double height) {
		this.pos = pos;
		this.velocity = vertex;
		this.width = width;
		this.height = height;
	}
}
