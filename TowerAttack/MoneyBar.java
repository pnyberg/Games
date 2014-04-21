import java.awt.*;

public class MoneyBar {
	public int x, y;
	private int width, height;
	public int money;

	public MoneyBar(int x, int y) {
		this.x = x;
		this.y = y;
		money = 0;
		width = 75;
		height = 30;
	}

	public void paint(Graphics g) {
		if (money >= 100)
			width = 82;

		g.setColor(Color.black);
		g.fillRect(x, y, width, height);
		g.setColor(Color.yellow);
		g.drawString("Money: " + money, x + 5, y + 17);
	}
}