package framework;

public class Vertex {
	public double x;
	public double y;

	public Vertex(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void add(Vertex that) {
		x += that.x;
		y += that.y;
	}
	
	public void sub(Vertex that) {
		x -= that.x;
		y -= that.y;
	}

	public void moveTo(Vertex that) {
		x = that.x;
		y = that.y;
	}

	public void mult(double d) {
		this.x = x*d;
		this.y = y*d;
	}
}
