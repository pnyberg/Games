import java.awt.*;
import java.util.*;

public abstract class TowerStructure {
	public static final int COST = 0;

	public int x, y;
	
	public TowerStructure() {}

	public TowerStructure(int x, int y) {}

	public abstract void shoot(LinkedList<TowerBall> balls);

	public abstract void moveProjectiles();

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public abstract int getX();

	public abstract int getY();

	public abstract void paint(Graphics g);

	public abstract void paintProjectiles(Graphics g);
}