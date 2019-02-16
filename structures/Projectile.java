package structures;

import enemies.TowerBall;

import java.awt.*;

public class Projectile {
	private final int side = 3;
	public int x, y;
	public boolean used;
	private int damage;
	private TowerBall target;

	public Projectile(int x, int y, TowerBall target, int damage) {
		this.x = x;
		this.y = y;
		this.target = target;
		this.damage = damage;
		used = false;
	}

	public void move() {
		int xStep = 0;
		int yStep = 0;
		int targetMidX = target.x + target.radius;
		int targetMidY = target.y + target.radius;

		if (Math.abs(targetMidX - x) == Math.abs(targetMidY - y)) {
			xStep = 2;
			yStep = 2;
		} else if (Math.abs(targetMidX - x) > Math.abs(targetMidY - y)) {
			xStep = 3;
			yStep = 1;
		} else if (Math.abs(targetMidX - x) < Math.abs(targetMidY - y)) {
			xStep = 1;
			yStep = 3;
		}

		int xMovement = getMovement(targetMidX, x, xStep);
		int yMovement = getMovement(targetMidY, y, yStep);

		x += (targetMidX > x ? xMovement : -xMovement);
		y += (targetMidY > y ? yMovement : -yMovement);

		if (targetMidX == x && targetMidY == y) {
			target.hp -= damage;
			used = true;
		}

		if (target.hp <= 0 || target.goaled())
			used = true;
	}

	public int getMovement(int targetPos, int pos, int maxStepSize) {
		return Math.abs(targetPos - pos) > maxStepSize ? maxStepSize : Math.abs(targetPos - pos);
	}

	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(x, y, side, side);
	}
}