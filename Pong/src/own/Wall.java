package own;

import java.awt.Color;
import java.awt.Graphics;

import framework.AbstractGameObj;
import framework.Vertex;

public class Wall extends AbstractGameObj {

	int hp;
	
	public Wall(Vertex pos, Vertex vertex, double width, double height) {
		super(pos, vertex, width, height);
		this.hp = 200;
	}

	public Wall(Vertex pos) {
		this(pos, new Vertex(0,0), 20, 20);
		this.hp = 200;
	}
	
	public int hp() {
		return hp;
	}
	
	public void damage(int dmg) {
		this.hp -= dmg;
	}

	@Override
	public void paintTo(Graphics g) {
		g.setColor(Color.GRAY);
		g.fill3DRect((int)pos().x, (int)pos().y, (int)width, (int)height, true);
	}

}
