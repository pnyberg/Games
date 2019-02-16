/**
 * A shocker can hit an entire area
 */

package structures;

import enemies.TowerBall;

import java.awt.*;
import java.util.List;

public class TowerShocker extends TowerStructure {
	public static final int COST = 30;

	private final int side = 30;
	private final int shockMaxRadius = 70;

	private int shootCooldown;
	private int shockRadius;
	private int slowDown;
	private boolean areaShock;

	public TowerShocker(int x, int y) {
		this.x = x;
		this.y = y;

		level = 1;
		areaShock = false;
		shootCooldown = 0;
		shockRadius = 0;
		slowDown = 0;
	}

	public void shoot(List<TowerBall> balls) {
		if (shootCooldown == 0) {
			areaShock = true;
			shockRadius = 0;
			shootCooldown = 500;
		} else {
			shootCooldown -= (shootCooldown == 0 ? 0 : 1);
		}

		for (TowerBall ball : balls) {
			if (ballHit(ball)) {
				ball.hp -= getDamage();
			}
		}
	}

	private boolean ballHit(TowerBall ball) {
		return Math.pow((ball.x + ball.radius) - (x + side / 2), 2) + 
			   Math.pow((ball.y + ball.radius) - (y + side / 2), 2)
			   <= Math.pow(shockRadius + ball.radius, 2);
	}

	public void moveProjectiles() {
		if (areaShock) {
			if (slowDown == 0) {
				shockRadius += 3;
				slowDown = 2;
			} else {
				slowDown--;
			}
		}
		if (shockRadius > shockMaxRadius) {
			shockRadius = 0;
			areaShock = false;
		}
	}

	public void upgrade() {
		level++;
	}

	public int getDamage() {
		if (level == 1)
			return 1;
		else if (level == 2)
			return 2;
		else if (level == 3)
			return 3;
		return 0;
	}

	public void paintProjectiles(Graphics g) {
		if (level == 1) {
			g.setColor(Color.pink);
		} else if (level == 2) {
			g.setColor(Color.red);
		} else if (level == 3) {
			g.setColor(Color.blue);
		}
		g.fillOval(x + side / 2 - shockRadius, y + side / 2 - shockRadius, shockRadius * 2, shockRadius * 2);
	}

	public void paint(Graphics g) {
		if (level == 1) {
			g.setColor(Color.lightGray);
		} else if (level == 2) {
			g.setColor(Color.black);
		} else if (level == 3) {
			g.setColor(Color.white);
		}
		g.fillRect(x, y, side, side);
		g.setColor(Color.darkGray);
		g.fillOval(x, y, side, side);
	}
}