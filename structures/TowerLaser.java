package structures;

import enemies.TowerBall;

import java.awt.*;
import java.util.List;

public class TowerLaser extends TowerStructure {
	public static final int COST = 40;
	public final int side = 30;
	public final int laserWidth = 6;
	public final int maxLaserLength = 250;
	private final int maxCooldown = 100;

	private int laserLength;
	private int shootCooldown;
	private boolean lasering;

	public TowerLaser(int x, int y) {
		this.x = x;
		this.y = y;

		level = 1;
		lasering = false;
		shootCooldown = 0;
		laserLength = 0;
	}

	public void shoot(List<TowerBall> balls) {
		if (shootCooldown == 0) {
			lasering = true;
			shootCooldown = 1000;
		} else if (!lasering) {
			shootCooldown -= (shootCooldown == 0 ? 0 : 1);
		}

		if (lasering) {
			for (TowerBall ball : balls) {
				if (inLaser(ball)) {
					ball.hp -= getDamage();
				}
			}
		}
	}

	public void moveProjectiles() {
		if (lasering) {
			if (laserLength < maxLaserLength) {
				laserLength += 2;
			} else {
				laserLength = 0;
				lasering = false;
			}
		}
	}

	public void upgrade() {
		level++;
	}

	private boolean inLaser(TowerBall ball) {
		// up-laser in ball
		if (inRect(ball.x + ball.radius, ball.y + ball.radius, x + (side - laserWidth) / 2, y - laserLength, laserWidth, laserLength))
			return true;
		// up-laser rect ball-to-right
		if (inRect(ball.x, ball.y + ball.radius, x + (side - laserWidth) / 2, y - laserLength, laserWidth, laserLength))
			return true;
		// up-laser corner ball-to-right
		if (inCirc(x + (side + laserWidth) / 2, y - laserLength, ball.x, ball.y, ball.radius))
			return true;
		// up-laser rect ball-to-left
		if (inRect(ball.x + ball.radius * 2, ball.y + ball.radius, x + (side - laserWidth) / 2, y - laserLength, laserWidth, laserLength))
			return true;
		// up-laser corner ball-to-left
		if (inCirc(x + (side - laserWidth) / 2, y - laserLength, ball.x, ball.y, ball.radius))
			return true;

		// right-laser in ball
		if (inRect(ball.x + ball.radius, ball.y + ball.radius, x + side, y + (side - laserWidth) / 2, laserLength, laserWidth))
			return true;
		// right-laser rect ball-to-up
		if (inRect(ball.x + ball.radius, ball.y + ball.radius * 2, x + side, y + (side - laserWidth) / 2, laserLength, laserWidth))
			return true;
		// right-laser corner ball-to-up
		if (inCirc(x + side + laserLength, y + (side - laserWidth) / 2, ball.x, ball.y, ball.radius))
			return true;
		// right-laser rect ball-to-down
		if (inRect(ball.x + ball.radius, ball.y, x + side, y + (side - laserWidth) / 2, laserLength, laserWidth))
			return true;
		// right-laser corner ball-to-down
		if (inCirc(x + side + laserLength, y + (side + laserWidth) / 2, ball.x, ball.y, ball.radius))
			return true;

		// down-laser in ball
		if (inRect(ball.x + ball.radius, ball.y + ball.radius, x + (side - laserWidth) / 2, y + side, laserWidth, laserLength))
			return true;
		// down-laser rect ball-to-right
		if (inRect(ball.x, ball.y + ball.radius, x + (side - laserWidth) / 2, y + side, laserWidth, laserLength))
			return true;
		// down-laser corner ball-to-right
		if (inCirc(x + (side + laserWidth) / 2, y + side + laserLength, ball.x, ball.y, ball.radius))
			return true;
		// down-laser rect ball-to-left
		if (inRect(ball.x + ball.radius * 2, ball.y + ball.radius, x + (side - laserWidth) / 2, y + side, laserWidth, laserLength))
			return true;
		// down-laser corner ball-to-left
		if (inCirc(x + (side - laserWidth) / 2, y + side + laserLength, ball.x, ball.y, ball.radius))
			return true;

		// left-laser in ball
		if (inRect(ball.x + ball.radius, ball.y + ball.radius, x - laserLength, y + (side - laserWidth) / 2, laserLength, laserWidth))
			return true;
		// left-laser rect ball-to-up
		if (inRect(ball.x + ball.radius, ball.y + ball.radius * 2, x - laserLength, y + (side - laserWidth) / 2, laserLength, laserWidth))
			return true;
		// left-laser corner ball-to-up
		if (inCirc(x - laserLength, y + (side - laserWidth) / 2, ball.x, ball.y, ball.radius))
			return true;
		// left-laser rect ball-to-down
		if (inRect(ball.x + ball.radius, ball.y, x - laserLength, y + (side - laserWidth) / 2, laserLength, laserWidth))
			return true;
		// left-laser corner ball-to-down
		if (inCirc(x - laserLength, y + (side + laserWidth) / 2, ball.x, ball.y, ball.radius))
			return true;

		return false;
	}

	private boolean inRect(int x, int y, int rectx, int recty, int width, int height) {
		return x >= rectx && x < (rectx + width) && y >= recty && y < (recty + height);
	}

	private boolean inCirc(int cornerx, int cornery, int ballx, int bally, int ballRadius) {
		return Math.pow(ballx + ballRadius - cornerx, 2) + Math.pow(bally + ballRadius - cornery, 2) 
				<= Math.pow(ballRadius, 2);
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

	public void paint(Graphics g) {
		if (level == 1) {
			g.setColor(Color.magenta);
		} else if (level == 2) {
			g.setColor(Color.blue);
		} else if (level == 3) {
			g.setColor(Color.gray);
		}
		g.fillRect(x, y, side, side);
		g.setColor(Color.black);
		g.fillRect(x + 2, y + side/2 - 2, side - 4, 4);
		g.fillRect(x + side/2 - 2, y + 2, 4, side - 4);
	}

	public void paintProjectiles(Graphics g) {
		if (lasering) {
			if (level == 1) {
				g.setColor(Color.red);
			} else if (level == 2) {
				g.setColor(Color.yellow);
			} else if (level == 3) {
				g.setColor(Color.orange);
			}
			g.fillRect(x + (side - laserWidth) / 2, y - laserLength, laserWidth, laserLength); // north
			g.fillRect(x + side, y + (side - laserWidth) / 2, laserLength, laserWidth); // right
			g.fillRect(x + (side - laserWidth) / 2, y + side, laserWidth, laserLength); // south
			g.fillRect(x - laserLength, y + (side - laserWidth) / 2, laserLength, laserWidth); // left
		}
	}
}