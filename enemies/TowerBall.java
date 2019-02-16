/**
 * The ball used in the game
 */

package enemies;

import java.awt.*;
import java.util.*;

public class TowerBall {
	public final int radius = 13;
	public int x, y;
	public int hp;
	private int damage;
	private int moneyValue;
	private Color ballColor;

	private boolean moving;
	private boolean goaled;
	private LinkedList<Point> movementPattern;

	// set position and color for ball
	public TowerBall(int x, int y, int level) {
		this.x = x;
		this.y = y;
		moving = goaled = false;
		setAttributes(level);
	}

	public void setAttributes(int level) {
		if (level == 1) {
			hp = 1;
			damage = 1;
			moneyValue = 1;
			ballColor = Color.red;
		} else if (level == 2) {
			hp = 2;
			damage = 2;
			moneyValue = 2;
			ballColor = Color.blue;
		} else if (level == 3) {
			hp = 3;
			damage = 3;
			moneyValue = 4;
			ballColor = Color.orange;
		} else if (level == 4) {
			hp = 5;
			damage = 5;
			moneyValue = 7;
			ballColor = Color.white;
		}
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

	public void reachedGoal() {
		goaled = true;
	}

	public int moneyValue() {
		return moneyValue;
	}

	public int getDamage() {
		return damage;
	}

	public boolean goaled() {
		return goaled;
	}

	// paints the ball
	public void paint(Graphics g) {
		g.setColor(ballColor);
		g.fillOval(x + 2, y + 2, radius * 2, radius * 2);
		g.setColor(Color.black);
		g.drawOval(x + 2, y + 2, radius * 2, radius * 2);
//		Image img = Toolkit.getDefaultToolkit().getImage("./Penis4.png");
//		g.drawImage(img, x, y, null);
	}
}