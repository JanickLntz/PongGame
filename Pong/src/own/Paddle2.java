package own;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import framework.GameObj;
import framework.Vertex;

public record Paddle2(Paddle2 parent, Vertex pos, Vertex velocity, double width, double height, double hp, double dmg,
		double spd, String fileName, Image image) implements GameObj {

	/// COMPACT CONSTRUCTOR ///
	public Paddle2 {
		var iIcon = new ImageIcon(getClass().getClassLoader().getResource(fileName));
		width = iIcon.getIconWidth();
		height = iIcon.getIconHeight();
		image = iIcon.getImage();
	}
	
	/// 1ST PADDLE W/O PARENT ///
	public Paddle2(Vertex pos, String fileName) {
		this(null, pos, new Vertex(0, 0), 0, 0, 100, 20, 2, fileName, null);
	}
	
	/// NEXT PADDLE W PARENT ///
	public Paddle2(String fileName, Paddle2 parent) {
		this(parent, new Vertex(parent.pos.x, parent.pos.y + parent.height), new Vertex(0, 0), 0, 0, parent.hp,
				parent.dmg, parent.spd, fileName, null);
	}

	public void update() {
		if (parent != null) {
			this.pos.y = parent.pos.y + height;
		}
	}

	@Override
	public void paintTo(Graphics g) {
		g.drawImage(image, (int) pos.x, (int) pos.y, null);
	}

}
