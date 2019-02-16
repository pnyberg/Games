package structures;

import enemies.TowerBall;

import java.awt.Graphics;
import java.util.List;

public abstract class TowerStructure {
	public static final int COST = 0;
	protected int x;
	protected int y;
	protected int level;
	
	public TowerStructure() {}

	public TowerStructure(int x, int y) {}

	public abstract void shoot(List<TowerBall> balls);

	public abstract void moveProjectiles();

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public abstract void upgrade();

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getLevel() {
		return level;
	}

	public abstract void paint(Graphics g);

	public abstract void paintProjectiles(Graphics g);
}