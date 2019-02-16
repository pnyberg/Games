/**
 * A cannon is used to destory the balls
 */

package structures;

import enemies.TowerBall;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class TowerCannon extends TowerStructure {
	public static final int COST = 10;
	private final int side = 30;
	private final int shootRadius = 100;
	private final int maxCooldown = 200;

	private int shootCooldown;
	private List<Projectile> projectiles;

	public TowerCannon(int x, int y) {
		this.x = x;
		this.y = y;

		projectiles = new LinkedList<Projectile>();

		level = 1;
		shootCooldown = 0;
	}

	public void shoot(List<TowerBall> balls) {
		TowerBall target = null;

		for (int i = 0 ; i < balls.size() ; i++)
			if (findTarget(balls.get(i))) {
				target = balls.get(i);
			}

		if (target != null && shootCooldown == 0) {
			int projectilex = x + side / 2;
			int projectiley = y + side / 2;

			projectiles.add(new Projectile(projectilex, projectiley, target, getDamage()));
			shootCooldown = maxCooldown;
		} else {
			shootCooldown -= (shootCooldown == 0 ? 0 : 1);
		}
	}

	private boolean findTarget(TowerBall ball) {
		return Math.pow(ball.x - (x + side / 2), 2) + 
			   Math.pow(ball.y - (y + side / 2), 2)
			   < Math.pow(shootRadius, 2);
	}

	public void moveProjectiles() {
		for (int i = 0 ; i < projectiles.size() ; i++) {
			if (projectiles.get(i).used) {
				projectiles.remove(i);
			} else {
				projectiles.get(i).move();
			}
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
			return 4;
		return 0;
	}

	public void paintProjectiles(Graphics g) {
		for (Projectile projectile : projectiles) {
			projectile.paint(g);
		}
	}

	public void paint(Graphics g) {
		if (level == 1) {
			g.setColor(Color.darkGray);
		} else if (level == 2) {
			g.setColor(Color.red);
		} else if (level == 3) {
			g.setColor(Color.blue);
		}
		g.fillRect(x, y, side, side);
		g.setColor(Color.black);
		g.drawRect(x + 4, y + 8, 12, 8);
		g.fillRect(x + 2, y + 5, 9, 3);
	}
}