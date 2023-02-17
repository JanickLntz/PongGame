package own;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import framework.GameObj;
import framework.Vertex;

public record Items(Vertex pos, Vertex velocity, double width, double height, String fileName, Image image, int type) implements GameObj{

	public Items {
		
		switch (type){
		case 1: {
			System.out.println("test");
			var iIcon = new ImageIcon(getClass().getClassLoader().getResource("dmg.png"));
			width = iIcon.getIconWidth();
			height = iIcon.getIconHeight();
			image = iIcon.getImage();
			break;
		}
		case 2: {
			System.out.println("test2");
			var iIcon = new ImageIcon(getClass().getClassLoader().getResource("hp.png"));
			width = iIcon.getIconWidth();
			height = iIcon.getIconHeight();
			image = iIcon.getImage();
			break;
		}
		case 3: {
			var iIcon = new ImageIcon(getClass().getClassLoader().getResource("spd.png"));
			width = iIcon.getIconWidth();
			height = iIcon.getIconHeight();
			image = iIcon.getImage();
			break;
		}
		case 4: {
			var iIcon = new ImageIcon(getClass().getClassLoader().getResource("size.png"));
			width = iIcon.getIconWidth();
			height = iIcon.getIconHeight();
			image = iIcon.getImage();
			break;
		}
		case 5: {
			var iIcon = new ImageIcon(getClass().getClassLoader().getResource("ball.png"));
			width = iIcon.getIconWidth();
			height = iIcon.getIconHeight();
			image = iIcon.getImage();
			break;
		}
		default:
			var iIcon = new ImageIcon(getClass().getClassLoader().getResource(fileName));
			width = iIcon.getIconWidth();
			height = iIcon.getIconHeight();
			image = iIcon.getImage();
			break;
		}
	}
	
	public Items(Vertex pos, Vertex velocity, int type) {
		this(pos, velocity, 0, 0, "", null,type);
	}
	
	
	
	@Override
	public void paintTo(Graphics g) {
		g.drawImage(image, (int) pos.x, (int) pos.y, null);
	}

}
