package own;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import framework.GameObj;
import framework.Vertex;

public class Paddle implements GameObj {

    private Paddle parent;
    private Vertex pos;
    private Vertex velocity;
    private double width;
    private double height;
    private double hp;
    private double dmg;
    private double spd;
    private String fileName;
    private Image image;

    public Paddle(Paddle parent, Vertex pos, Vertex velocity, double width, double height, double hp, double dmg, double spd, String fileName, Image image) {
        this.parent = parent;
        this.pos = pos;
        this.velocity = velocity;
        this.width = width;
        this.height = height;
        this.hp = hp;
        this.dmg = dmg;
        this.spd = spd;
        this.fileName = fileName;
        this.image = image;

        var iIcon = new ImageIcon(getClass().getClassLoader().getResource(fileName));
        this.width = iIcon.getIconWidth();
        this.height = iIcon.getIconHeight();
        this.image = iIcon.getImage();
    }

    public Paddle(Vertex pos, String fileName) {
        this(null, pos, new Vertex(0, 0), 0, 0, 100, 20, 2, fileName, null);
    }

    public Paddle(String fileName, Paddle parent) {
        this(parent, new Vertex(parent.pos.x, parent.pos.y + parent.height), new Vertex(0, 0), 0, 0, parent.hp, parent.dmg, parent.spd, fileName, null);
    }

    public void update() {
        if (parent != null) {
            this.pos.y = parent.pos.y + height;
        }
    }

    public Paddle parent() {
        return parent;
    }

    @Override
    public Vertex pos() {
        return pos;
    }

    @Override
    public Vertex velocity() {
        return velocity;
    }

    @Override
    public double width() {
        return width;
    }

    @Override
    public double height() {
        return height;
    }

    public double hp() {
        return hp;
    }
    
    public void takeDmg(double dmg) {
    	hp -= dmg;
    }

    public double dmg() {
        return dmg;
    }

    public double spd() {
        return spd;
    }

    public String fileName() {
        return fileName;
    }

    public Image image() {
        return image;
    }

    @Override
    public void paintTo(Graphics g) {
        g.drawImage(image, (int) pos.x, (int) pos.y, null);
    }


}
