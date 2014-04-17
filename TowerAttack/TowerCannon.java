/**
 * A cannon is used to destory the balls
 */

import java.awt.*;
import java.util.*;

public class TowerCannon extends TowerStructure {
	public static final int COST = 10;

	private final int side = 30;
	private final int shootRadius = 100;
	private final int maxCooldown = 200;
	private int shootCooldown;

	private LinkedList<Projectile> projectiles;

	public TowerCannon(int x, int y) {
		this.x = x;
		this.y = y;

		projectiles = new LinkedList<Projectile>();

		shootCooldown = 0;
	}

	public void shoot(LinkedList<TowerBall> balls) {
		TowerBall target = null;

		for (int i = 0 ; i < balls.size() ; i++)
			if (Math.pow(balls.get(i).x - (x + side / 2), 2) + Math.pow(balls.get(i).y - (y + side / 2), 2) < Math.pow(shootRadius, 2))
				target = balls.get(i);

		if (target != null && shootCooldown == 0) {
			projectiles.add(new Projectile(x + side / 2, y + side / 2, target));
			shootCooldown = maxCooldown;
		} else
			shootCooldown -= (shootCooldown == 0 ? 0 : 1);
	}

	public void moveProjectiles() {
		for (int i = 0 ; i < projectiles.size() ; i++) {
			if (projectiles.get(i).unnecessary)
				projectiles.remove(i);
			else
				projectiles.get(i).move();
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void paintProjectiles(Graphics g) {
		for (Projectile projectile : projectiles)
			projectile.paint(g);
	}

	public void paint(Graphics g) {
		g.setColor(Color.darkGray);
		g.fillRect(x, y, side, side);
	}
}