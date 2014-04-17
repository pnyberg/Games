import java.awt.*;

public class TowerMenuBar {
	private int x, y;
	private final int width = 800;
	private final int height = 200;

	private MoneyBar moneyBar;
	private TowerMenuIcon regularCannonIcon, shockerIcon, laserIcon;
	public TowerStructure objectPickedUp;

	public TowerMenuBar(int x, int y) {
		this.x = x;
		this.y = y;

		moneyBar = new MoneyBar(x + 10, y + 30);
		regularCannonIcon = new TowerMenuIcon(x + 100, y + 20, "Regular Cannon");
		shockerIcon = new TowerMenuIcon(x + 230, y + 20, "Shocker");
		laserIcon = new TowerMenuIcon(x + 360, y + 20, "Laser");
	}

	public void addMoney(int cash) {
		moneyBar.money += cash;
	}

	public void cash(TowerBall ball) {
		addMoney(ball.moneyValue());
	}

	public int getMoney() {
		return moneyBar.money;
	}

	public boolean iconPressed(int mousex, int mousey) {
		int xCorrect = 15;
		int yCorrect = 15;

		if (regularCannonIcon.inBounds(mousex, mousey))
			objectPickedUp = new TowerCannon(mousex - xCorrect, mousey - yCorrect);
		else if (shockerIcon.inBounds(mousex, mousey))
			objectPickedUp = new TowerShocker(mousex - xCorrect, mousey - yCorrect);
		else if (laserIcon.inBounds(mousex, mousey))
			objectPickedUp = new TowerLaser(mousex - xCorrect, mousey - yCorrect);
		else
			return false;

		return true;
	}

	public void paint(Graphics g) {
		g.setColor(Color.darkGray);
		g.fillRect(x, y, width, height);

		moneyBar.paint(g);

		regularCannonIcon.paint(g);
		shockerIcon.paint(g);
		laserIcon.paint(g);
	}
}