package own;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import framework.Game;
import framework.GameObj;
import framework.TextObject;
import framework.Vertex;

public record Pong(List<Paddle> player, List<Paddle> enemy, List<List<? extends GameObj>> goss, int width, int height,
		List<Ball> balls, List<Items> items, List<TextObject> texts, List<Wall> walls) implements Game {

	Pong(int width, int height) {
		this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), width, height, new ArrayList<>(),
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
	}

	static int playerSize = 5;
	static int enemySize = 5;

	
	
	static enum Buttons {

		w(false), s(false), f(false);

		private boolean isPressed;

		Buttons(boolean isPressed) {
			this.isPressed = isPressed;
		}

		public boolean isPressed() {
			return this.isPressed;
		}
	}

	@Override
	public void init() {
		goss.clear();

		player.clear();
		enemy.clear();
		balls.clear();
		texts.clear();
		walls.clear();
		items.clear();
		// buildWalls();
		buildPaddles();
		items.add(new Items(new Vertex(300, 250), new Vertex(0, 0), 1));
		items.add(new Items(new Vertex(300, 350), new Vertex(0, 0), 2));
		balls.add(new Ball(false, new Vertex(200, 250), new Vertex(-1, 1)));
		goss.add(items);
		goss.add(player);
		goss.add(enemy);
		goss.add(balls);
		goss.add(texts);
		goss.add(walls);
	}

	public void buildWalls() {
		for (int i = 0; i < this.width / 20; i++) {
			for (int j = 0; j < this.height / 20; j++) {
				if (i > 19 && i < 40)
					walls.add(new Wall(new Vertex(i * 20, j * 20)));
			}
		}
	}

	public void buildPaddles() {

		// PLAYER BUILDING
		player.add(new Paddle(new Vertex(20, 20), "tl.png"));
		for (int i = 0; i < playerSize; i++) {
			player.add(new Paddle("ml.png", player.get(player.size() - 1)));
		}
		player.add(new Paddle("bl.png", player.get(player.size() - 1)));

		// ENEMY BUILDING
		enemy.add(new Paddle(new Vertex(this.width - 36, 20), "tr.png"));
		for (int i = 0; i < enemySize; i++) {
			enemy.add(new Paddle("mr.png", enemy.get(enemy.size() - 1)));
		}
		enemy.add(new Paddle("br.png", enemy.get(enemy.size() - 1)));
	}

	public void showStats() {
		texts.clear();
		TextObject t1 = new TextObject(new Vertex(100, 100), "Player HP " + player.get(0).hp());
		TextObject t2 = new TextObject(new Vertex(100, 120), "Enemy HP " + enemy.get(0).hp());
		texts.add(t1);
		texts.add(t2);
	}

	public void paddleHit() {
		for (Paddle player_ : player) {
			for (Ball ball : balls) {
				if (ball.touches(player_) && !ball.lastHitPlayer()) {
					ball.setLastHitPlayer(true);
					ball.setParent(player.get(0));
					ball.velocity().x *= -1.2;
				}
			}
		}
		for (Paddle enemy_ : enemy) {
			for (Ball ball : balls) {
				if (ball.touches(enemy_) && ball.lastHitPlayer()) {
					ball.setLastHitPlayer(false);
					ball.setParent(enemy.get(0));
					ball.velocity().x *= -1.2;
				}
			}
		}
	}

	public void edgeBounce() {
		for (Ball ball : balls) {
			if (ball.pos().y <= 20) {
				ball.pos().y = 20;
				ball.velocity().y = ball.velocity().y * -1;
			}
			if (ball.pos().y >= this.height - 20 - ball.height()) {
				ball.pos().y = this.height - 20 - ball.height();
				ball.velocity().y = ball.velocity().y * -1;
			}

			if (ball.pos().x <= 0) {
				if (ball.parent() != null)
					player.get(0).takeDmg(ball.parent().dmg());
				ball.pos().x = this.width / 2;
				ball.pos().y = this.height / 2;
			}
			if (ball.pos().x >= this.width - ball.width()) {
				if (ball.parent() != null)
					enemy.get(0).takeDmg(ball.parent().dmg());
				ball.pos().x = this.width / 2;
				ball.pos().y = this.height / 2;
			}

		}
	}

	public void wallHit() {
		for (Ball ball : balls) {
			for (Wall wall : walls) {
				if (ball.touches(wall)) {
					if (ball.isRightOf(wall) && ball.isAbove(wall))
						wall.damage(20);
				}
			}
		}
	}

	public void wallKill() {

		for (int i = 0; i < walls.size(); i++) {
			if (walls.get(i).hp() <= 0)
				walls.remove(i);
		}

	}

	@Override
	public void doChecks() {
		movePlayer();
		moveEnemy();
		showStats();
		paddleHit();
		edgeBounce();
		wallHit();
		wallKill();

		for (GameObj ball : balls) {
			ball.move();
		}
	}

	public void movePlayer() {

		// MOVEMENT

		if (player.get(0).pos().y <= 20) {
			player.get(0).pos().y = 20;
			player.get(0).velocity().add(
					new Vertex(0, (Buttons.w.isPressed ? 0 : 0) + (Buttons.s.isPressed ? player.get(0).spd() : 0)));
		}

		else if (player.get(0).pos().y >= this.height - 20 - (this.player().size() * 16)) {
			player.get(0).pos().y = this.height - 20 - (this.player().size() * 16);
			player.get(0).velocity().add(
					new Vertex(0, (Buttons.w.isPressed ? -player.get(0).spd() : 0) + (Buttons.s.isPressed ? 0 : 0)));
		}

		else {
			player.get(0).velocity().add(new Vertex(0, (Buttons.w.isPressed ? -player.get(0).spd() : 0)
					+ (Buttons.s.isPressed ? player.get(0).spd() : 0)));
		}

		// DRAG & STOP

		if (Math.abs(player.get(0).velocity().y) > 0.01) {
			player.get(0).velocity().mult(0.9);
		}

		else {
			player.get(0).velocity().y = 0;
		}

		// UPDATE BODYPOS

		for (Paddle paddle : player) {
			paddle.update();
		}

	}

	public void moveEnemy() {

		// MOVEMENT

		if (enemy.get(0).pos().y <= 20) {
			enemy.get(0).pos().y = 20;
			enemy.get(0).velocity().add(new Vertex(0, (Buttons.w.isPressed ? 0 : 0) + (Buttons.s.isPressed ? 1 : 0)));
		}

		else if (enemy.get(0).pos().y >= this.height - 20 - (this.enemy().size() * 16)) {
			enemy.get(0).pos().y = this.height - 20 - (this.enemy().size() * 16);
			enemy.get(0).velocity().add(new Vertex(0, (Buttons.w.isPressed ? -1 : 0) + (Buttons.s.isPressed ? 0 : 0)));
		}

		else {
			enemy.get(0).velocity().add(new Vertex(0, (Buttons.w.isPressed ? -1 : 0) + (Buttons.s.isPressed ? 1 : 0)));
		}

		// DRAG & STOP

		if (Math.abs(enemy.get(0).velocity().y) > 0.01) {
			enemy.get(0).velocity().mult(0.9);
		}

		else {
			enemy.get(0).velocity().y = 0;
		}

		// UPDATE BODYPOS

		for (Paddle paddle : enemy) {
			paddle.update();
		}

		if (Buttons.f.isPressed) {

			Paddle temp = player.get(playerSize - 1);
			player.add(new Paddle("bl.png", player.get(player.size() - 1)));

			player.add(new Paddle("ml.png", player.get(player.size() - 1)));
			Buttons.f.isPressed = false;
		}

	}

	@Override
	public void keyPressedReaction(KeyEvent keyEvent) {
		switch (keyEvent.getKeyCode()) {
		case KeyEvent.VK_W:
			Buttons.w.isPressed = true;
			break;
		case KeyEvent.VK_S:
			Buttons.s.isPressed = true;
			break;
		case KeyEvent.VK_F:
			if (Buttons.f.isPressed) {
				Buttons.f.isPressed = false;
				break;
			} else
				Buttons.f.isPressed = true;
			break;
		}
	}

	@Override
	public void keyReleasedReaction(KeyEvent keyEvent) {
		switch (keyEvent.getKeyCode()) {
		case KeyEvent.VK_W:
			Buttons.w.isPressed = false;
			break;
		case KeyEvent.VK_S:
			Buttons.s.isPressed = false;
			break;
		}
	}

	@Override
	public boolean won() {
		return enemy.get(0).hp() == 0;
	}

	@Override
	public boolean lost() {
		return player.get(0).hp() == 0;
	}

	public static void main(String[] args) {
		Pong p = new Pong(1200, 600);
		p.play();
	}

}
