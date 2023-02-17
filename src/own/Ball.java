package own;

import java.awt.Color;
import java.awt.Graphics;

import framework.GameObj;
import framework.Vertex;

public class Ball implements GameObj {

	private Paddle parent;
	private Vertex pos;
    private Vertex velocity;
    private double width;
    private double height;
    private boolean lastHitPlayer;

    public Ball(Paddle parent, boolean lastHitPlayer, Vertex pos, Vertex velocity) {
        this.parent = parent;
        this.lastHitPlayer = lastHitPlayer;
    	this.pos = pos;
        this.velocity = velocity;
        this.width = 20;
        this.height = 20;
    }
    
    public Ball(boolean lastHitPlayer, Vertex pos, Vertex velocity) {
    	this(null, lastHitPlayer, pos, velocity);
    }

    public void setParent(Paddle parent) {
    	this.parent = parent;
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
    
    public boolean lastHitPlayer() {
        return lastHitPlayer;
    }
    
    public void setLastHitPlayer(boolean hitPlayer) {
    	lastHitPlayer = hitPlayer;
    }

    @Override
    public void paintTo(Graphics g) {
        g.setColor(Color.PINK);
        g.fillOval((int) pos.x, (int) pos.y, (int) width, (int) height);
    }

}