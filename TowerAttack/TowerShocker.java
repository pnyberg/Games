/**
 * A shocker can hit an entire area
 */

import java.awt.*;
import java.util.*;

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

		shootCooldown = 0;
		shockRadius = 0;
		slowDown = 0;
		areaShock = false;
	}

	public void shoot(LinkedList<TowerBall> balls) {
		TowerBall target = null;

		if (shootCooldown == 0) {
			areaShock = true;
			shockRadius = 0;
			shootCooldown = 500;
		} else
			shootCooldown -= (shootCooldown == 0 ? 0 : 1);

		for (TowerBall ball : balls) {
			if (Math.pow((ball.x + ball.radius) - (x + side / 2), 2) + Math.pow((ball.y + ball.radius) - (y + side / 2), 2) <= Math.pow(shockRadius + ball.radius, 2))
				ball.hit = true;
		}
	}

	public void moveProjectiles() {
		if (areaShock)
			if (slowDown == 0) {
				shockRadius += 3;
				slowDown = 2;
			} else {
				slowDown--;
			}

		if (shockRadius > shockMaxRadius) {
			shockRadius = 0;
			areaShock = false;
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void paintProjectiles(Graphics g) {
		// has no projectiles to paint
	}

	public void paint(Graphics g) {
		g.setColor(Color.pink);
		g.fillOval(x + side / 2 - shockRadius, y + side / 2 - shockRadius, shockRadius * 2, shockRadius * 2);

		g.setColor(Color.lightGray);
		g.fillRect(x, y, side, side);
		g.setColor(Color.darkGray);
		g.fillOval(x, y, side, side);
	}
}