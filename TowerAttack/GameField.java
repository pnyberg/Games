/**
 * The field on which the game is played
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

public class GameField extends JPanel implements ActionListener, MouseListener, MouseMotionListener {
	private final int width = 700;
	private final int height = 500;
	private final int startMoney = 100;
	private final Color[] pallet = {Color.red, Color.blue, Color.white, Color.yellow, Color.orange, Color.pink, Color.gray};

	private Timer timer;

	private final int ballWaitTime = 80;
	private int randomBallWaitTime;
	private int ballCounter;
	private TowerDoor start, goal;
	private TowerMenuBar towerMenuBar;
	private LinkedList<TowerBall> balls;
	private LinkedList<TowerRock> rocks;
	private LinkedList<TowerStructure> cannons;
	private LinkedList<Point> movementPattern;

	private boolean holding;
	private boolean placed;
	private TowerStructure holdingObject;

	// creates the values for the field
	public GameField() {
		timer = new Timer(10, this);

		start = new TowerDoor(0, 4 * 30, TowerDoor.east, Color.blue);
		goal = new TowerDoor(0, 6 * 30, TowerDoor.east, Color.red);

		towerMenuBar = new TowerMenuBar(0, 400);
		towerMenuBar.addMoney(startMoney);

		balls = new LinkedList<TowerBall>();
		rocks = new LinkedList<TowerRock>();
		cannons = new LinkedList<TowerStructure>();

		ballCounter = 0;

		addMouseListener(this);
		addMouseMotionListener(this);

		initField();
	}

	// start the game
	public void start() {
		timer.start();
	}

	// creates a new ball for per to play with
	public void createBall() {
		int palletIndex = ((int)(Math.random() * 10)) % pallet.length;
		Color ballColor = pallet[palletIndex];
		balls.add(new TowerBall(0, 4 * 30, ballColor));

		balls.getLast().addMovement((LinkedList<Point>)movementPattern.clone());

		repaint();
	}

	// configure the layout of the field
	//  obstacles etc
	public void initField() {
		int side = 30;

		for (int i = 0 ; i < 11 ; i++)
			rocks.add(new TowerRock(i * side, 0));

		rocks.add(new TowerRock(0, 1 * side));
		rocks.add(new TowerRock(4 * side, 1 * side));
		rocks.add(new TowerRock(8 * side, 1 * side));
		rocks.add(new TowerRock(9 * side, 1 * side));
		rocks.add(new TowerRock(10 * side, 1 * side));

		rocks.add(new TowerRock(0, 2 * side));
		rocks.add(new TowerRock(2 * side, 2 * side));
		rocks.add(new TowerRock(4 * side, 2 * side));
		rocks.add(new TowerRock(6 * side, 2 * side));
		rocks.add(new TowerRock(8 * side, 2 * side));
		rocks.add(new TowerRock(9 * side, 2 * side));
		rocks.add(new TowerRock(10 * side, 2 * side));

		rocks.add(new TowerRock(0, 3 * side));
		rocks.add(new TowerRock(2 * side, 3 * side));
		rocks.add(new TowerRock(4 * side, 3 * side));
		rocks.add(new TowerRock(6 * side, 3 * side));
		rocks.add(new TowerRock(8 * side, 3 * side));
		rocks.add(new TowerRock(9 * side, 3 * side));
		rocks.add(new TowerRock(10 * side, 3 * side));

		rocks.add(new TowerRock(2 * side, 4 * side));
		rocks.add(new TowerRock(6 * side, 4 * side));
		rocks.add(new TowerRock(8 * side, 4 * side));
		rocks.add(new TowerRock(9 * side, 4 * side));
		rocks.add(new TowerRock(10 * side, 4 * side));

		for (int i = 0 ; i < 7 ; i++)
			rocks.add(new TowerRock(i * side, 5 * side));
		rocks.add(new TowerRock(8 * side, 5 * side));
		rocks.add(new TowerRock(9 * side, 5 * side));
		rocks.add(new TowerRock(10 * side, 5 * side));

		rocks.add(new TowerRock(8 * side, 6 * side));
		rocks.add(new TowerRock(9 * side, 6 * side));
		rocks.add(new TowerRock(10 * side, 6 * side));

		for (int n = 7 ; n < 10 ; n++)
			for (int i = 0 ; i < 11 ; i++)
				rocks.add(new TowerRock(i * side, n * side));

		movementPattern = new LinkedList<Point>();
		movementPattern.add(new Point(1 * side, 4 * side));
		movementPattern.add(new Point(1 * side, 1 * side));
		movementPattern.add(new Point(3 * side, 1 * side));
		movementPattern.add(new Point(3 * side, 4 * side));
		movementPattern.add(new Point(5 * side, 4 * side));
		movementPattern.add(new Point(5 * side, 1 * side));
		movementPattern.add(new Point(7 * side, 1 * side));
		movementPattern.add(new Point(7 * side, 6 * side));
		movementPattern.add(new Point(0 * side, 6 * side));
	}

	public void purchase() {
		if (holdingObject instanceof TowerCannon)
			towerMenuBar.addMoney(-TowerCannon.COST);
		else if (holdingObject instanceof TowerShocker)
			towerMenuBar.addMoney(-TowerShocker.COST);
		else if (holdingObject instanceof TowerLaser)
			towerMenuBar.addMoney(-TowerLaser.COST);
	}

	public boolean inBoard(int x, int y) {
		return x >= 0 && x < width && y >= 0 && y < height;
	}

	public boolean areaIsAvailable(int x, int y) {
		return areaIsRock(x, y) && !areaIsOccupied(x, y);
	}

	public boolean areaIsRock(int x, int y) {
		for (TowerRock rock : rocks)
			if (rock.x == x && rock.y == y)
				return true;
		return false;
	}

	public boolean areaIsOccupied(int x, int y) {
		for (TowerStructure cannon : cannons)
			if (cannon.getX() == x && cannon.getY() == y)
				return true;
		return false;
	}

	public boolean canAfford() {
		int cash = towerMenuBar.getMoney();

		if (holdingObject instanceof TowerCannon)
			return cash >= TowerCannon.COST;
		else if (holdingObject instanceof TowerShocker)
			return cash >= TowerShocker.COST;
		else if (holdingObject instanceof TowerLaser)
			return cash >= TowerLaser.COST;

		return false;
	}

	public void actionPerformed(ActionEvent e) {
		for (TowerBall ball : balls)
			ball.move();

		for (TowerStructure cannon : cannons)
			cannon.shoot(balls);

		for (TowerStructure cannon : cannons)
			cannon.moveProjectiles();

		for (int i = 0 ; i < balls.size() ; i++) {
			if (goal.contains(balls.get(i)) || balls.get(i).hit) {
				if (balls.get(i).hit)
	 				towerMenuBar.cash(balls.get(i));
				balls.remove(i);
				i--;
			}
		}

		if (ballCounter % (ballWaitTime + randomBallWaitTime) == 0) {
			createBall();
			randomBallWaitTime = ((int)(Math.random() * 100)) % 40;
			ballCounter = 0;
		}
		ballCounter++;

		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// do nothing
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!timer.isRunning())
			return;

		// left mouse-button
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (holding) {
				if (inBoard(e.getX(), e.getY())) {
					int placedx = e.getX() - e.getX() % 30;
					int placedy = e.getY() - e.getY() % 30;

					if (areaIsAvailable(placedx, placedy) && canAfford()) {
						holding = false;

						purchase();
						holdingObject.setPosition(placedx, placedy);
						cannons.add(holdingObject);
					}
				}
			}

			if (towerMenuBar.iconPressed(e.getX(), e.getY())) {
				holding = true;
				holdingObject = towerMenuBar.objectPickedUp;
			}
		} else if (e.getButton() == MouseEvent.BUTTON3) { // right mouse-button
			holding = false;
			holdingObject = null;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// do nothing
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// does nothing
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// does nothing
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (holding) {
			int holdingx = e.getX() - 15;
			int holdingy = e.getY() - 15;
			holdingObject.setPosition(holdingx, holdingy);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// do nothing
	}

	// paints the field
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.green);
		g.fillRect(0, 0, width, height);

		start.paint(g);
		goal.paint(g);

		// paints all the balls on the field
		for (TowerBall ball : balls)
			ball.paint(g);

		// paints all the rocks on the field
		for (TowerRock rock : rocks)
			rock.paint(g);

		// paints all the cannons on the field
		for (TowerStructure cannon : cannons)
			cannon.paint(g);

		// paints all the projectiles on the field
		for (TowerStructure cannon : cannons)
			cannon.paintProjectiles(g);

		towerMenuBar.paint(g);

		if (holding)
			holdingObject.paint(g);
	}
}