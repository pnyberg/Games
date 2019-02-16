import java.awt.*;

public class TowerMenuIcon {
	private int x, y;
	private boolean enabled;
	private String[] textPart;

	public TowerMenuIcon(int x, int y, String text) {
		this.x = x;
		this.y = y;
		enabled = true;

		textPart = text.split(" ");
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean enabled() {
		return enabled;
	}

	public boolean inBounds(int mousex, int mousey) {
		return ((x + 10) <= mousex && mousex < (x + 70) && (y + 10) <= mousey &&  mousey < (y + 70));
	}

	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(x, y, 80, 10);
		g.fillRect(x, y + 10, 10, 60);
		g.fillRect(x + 70, y + 10, 10, 60);
		g.fillRect(x, y + 70, 80, 10);

		for (int i = 0 ; i < textPart.length ; i++)
			g.drawString(textPart[i], x + 17, y + 40 + 13 * i - 3 * textPart.length);

		if (!enabled) {
			g.drawLine(x, y, x + 80, y + 80);
			g.drawLine(x + 80, y, x, y + 80);
		}
	}
}