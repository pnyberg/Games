import java.awt.*;

public class TowerDoor {
	public static final int north = 0;
	public static final int east = 1;
	public static final int south = 2;
	public static final int west = 3;

	private final int side = 30;

	private int x, y;
	private int direction;
	private Color doorColor;

	public TowerDoor(int x, int y, int direction, Color doorColor) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.doorColor = doorColor;
	}

	public boolean contains(TowerBall ball) {
		return ball.x == x && ball.y == y; 
	}

	public void paint(Graphics g) {
		g.setColor(doorColor);

		if (direction == north)
			g.fillRect(x, y + (2 * side) / 3, side, side / 3);
		else if (direction == east)
			g.fillRect(x, y, side / 3, side);
		else if (direction == south)
			g.fillRect(x, y, side, side / 3);
		else if (direction == west)
			g.fillRect(x + (2 * side) / 3, y, side / 3, side);
	}
}