/**
 * The rocks are the obstacles in the game
 */

import java.awt.*;

public class TowerRock {
	private final Color rockColor = new Color(140, 70, 20);
	private final int side = 30;
	public int x, y;

	// sets the position of the rock
	public TowerRock(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// paint the rock
	public void paint(Graphics g) {
		g.setColor(rockColor);
		g.fillRect(x, y, side, side);
		g.setColor(Color.black);
		g.drawRect(x, y, side, side);
	}
}