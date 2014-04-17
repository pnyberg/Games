/**
 * The ball used in the game
 */

import java.awt.*;
import java.util.*;

public class TowerBall {
	public final int radius = 13;
	public int x, y;
	public boolean hit;
	private Color ballColor;

	private boolean moving;
	private LinkedList<Point> movementPattern;

	// set position and color for ball
	public TowerBall(int x, int y, Color ballColor) {
		this.x = x;
		this.y = y;
		this.ballColor = ballColor;
		moving = false;
		hit = false;
	}

	public void addMovement(LinkedList<Point> movementPattern) {
		this.movementPattern = movementPattern;
		moving = true;
	}

	// moves the ball according to a set movement-pattern
	//  when the ball has reached a "checkpoint" it is 
	//  removed and the ball continues to the next one
	public void move() {
		if (moving) {
			Point nextDestination = movementPattern.get(0);
			if (nextDestination.x != x)
				x += (nextDestination.x > x ? 1 : -1);
			else if (nextDestination.y != y)
				y += (nextDestination.y > y ? 1 : -1);
			else
				movementPattern.remove(0);

			if (movementPattern.size() == 0)
				moving = false;
		}
	}

	public int moneyValue() {
		if (ballColor == Color.red)
			return 1;
		else
			return 2;
	}

	// paints the ball
	public void paint(Graphics g) {
/*		g.setColor(ballColor);
		g.fillOval(x + 2, y + 2, radius * 2, radius * 2);
		g.setColor(Color.black);
		g.drawOval(x + 2, y + 2, radius * 2, radius * 2);*/
		Image img = Toolkit.getDefaultToolkit().getImage("/home/per/Dropbox/Programmering/Java/Game Projects/TowerDefence/TowerAttack/Penis4.png");
		g.drawImage(img, x, y, null);
	}
}