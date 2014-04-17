import java.awt.*;

public class Projectile {
	private final int side = 3;
	public int x, y;
	public boolean unnecessary;
	private TowerBall target;

	public Projectile(int x, int y, TowerBall target) {
		this.x = x;
		this.y = y;
		this.target = target;
		unnecessary = false;
	}

	public void move() {
		int xMovement = 0;
		int yMovement = 0;
		int targetMidX = target.x + target.radius;
		int targetMidY = target.y + target.radius;

		if (Math.abs(targetMidX - x) == Math.abs(targetMidY - y)) {
			xMovement = Math.abs(targetMidX - x) > 2 ? 2 : Math.abs(targetMidX - x);
			yMovement = Math.abs(targetMidY - y) > 2 ? 2 : Math.abs(targetMidY - y);
		} else if (Math.abs(targetMidX - x) > Math.abs(targetMidY - y)) {
			xMovement = Math.abs(targetMidX - x) > 3 ? 3 : Math.abs(targetMidX - x);
			yMovement = Math.abs(targetMidY - y) > 1 ? 1 : 0;
		} else if (Math.abs(targetMidX - x) < Math.abs(targetMidY - y)) {
			xMovement = Math.abs(targetMidX - x) > 1 ? 1 : 0;
			yMovement = Math.abs(targetMidY - y) > 3 ? 3 : Math.abs(targetMidY - y);
		}

		x += (targetMidX > x ? xMovement : -xMovement);
		y += (targetMidY > y ? yMovement : -yMovement);

		if (targetMidX == x && targetMidY == y)
			target.hit = true;
		if (target.hit)
			unnecessary = true;
	}

	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(x, y, side, side);
	}
}