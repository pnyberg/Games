/**
 * The field on which the game is played
 */

import enemies.TowerBall;
import structures.TowerCannon;
import structures.TowerLaser;
import structures.TowerShocker;
import structures.TowerStructure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

public class GameField extends JPanel {
	private final int width = 700;
	private final int height = 500;
	private final int menuHeight = 400;
	private final int side = 30;
	private final int startMoney = 100;
	private final int ballWaitTime = 80;

	private int randomBallWaitTime;
	private int ballCounter, ballCountdown;
	private TowerDoor start, goal;
	private TowerMenuBar towerMenuBar;
	private LinkedList<TowerBall> balls;
	private LinkedList<TowerRock> rocks;
	private LinkedList<TowerStructure> cannons;
	private LinkedList<Point> movementPattern;

	private boolean placed;
	private TowerStructure holdingObject;

	private Timer timer;

	// creates the values for the field
	public GameField(Timer timer) {
		this.timer = timer;
		start = new TowerDoor(0, 4 * 30, TowerDoor.east, Color.blue);
		goal = new TowerDoor(0, 6 * 30, TowerDoor.east, Color.red);

		towerMenuBar = new TowerMenuBar(0, menuHeight);
		towerMenuBar.addMoney(startMoney);

		balls = new LinkedList<TowerBall>();
		rocks = new LinkedList<TowerRock>();
		cannons = new LinkedList<TowerStructure>();

		ballCounter = 0;
		ballCountdown = 0;

		initField();
	}

	// configure the layout of the field
	//  obstacles etc
	public void initField() {
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

	// creates a new ball for per to play with
	public void createBall() {
		int levelRandomizer = (int)(Math.random() * 20);
		int level;

		if ((levelRandomizer - ballCounter) > -10)
			level = 1;
		else if ((levelRandomizer - ballCounter) > -30)
			level = 2;
		else if ((levelRandomizer - ballCounter) > -55)
			level = 3;
		else
			level = 4;

		balls.add(new TowerBall(0, 4 * 30, level));
		balls.getLast().addMovement((LinkedList<Point>)movementPattern.clone());
		ballCounter++;
	}

	// removes balls if they have reached goal or is hit
	public void checkRemoving() {
		for (int i = 0 ; i < balls.size() ; i++)
			if (goal.contains(balls.get(i))) {
				towerMenuBar.takeDamage(balls.get(i));
				balls.get(i).reachedGoal();
				balls.remove(i--);
			}
			else if (balls.get(i).hp <= 0)
	 			towerMenuBar.cash(balls.remove(i--));

/*
	 	if (towerMenuBar.getHealth() == 0)
	 		stop(); // TODO: give a message + "clean up" (holding + ev wave)
*/
	}

	// check if a new ball should be put in play
	public void checkCreateNewball() {
		if (ballCountdown == 0) {
			createBall();
			randomBallWaitTime = ((int)(Math.random() * 100)) % 40;
			ballCountdown = ballWaitTime + randomBallWaitTime;
		}

		ballCountdown--;
	}

	// removes the cost of the tower from the money-total
	public void purchaseCurrentHoldingObject() {
		if (holdingObject instanceof TowerCannon)
			towerMenuBar.addMoney(-TowerCannon.COST);
		else if (holdingObject instanceof TowerShocker)
			towerMenuBar.addMoney(-TowerShocker.COST);
		else if (holdingObject instanceof TowerLaser)
			towerMenuBar.addMoney(-TowerLaser.COST);
	}

	// if the given coordinate is on the board
	public boolean inBoard(int x, int y) {
		return x >= 0 && x < width && y >= 0 && y < menuHeight;
	}

	// if the given coordinate is on the menubar
	public boolean inMenuBar(int x, int y) {
		return x >= 0 && x < width && y >= menuHeight && y < height;
	}

	// checks if the given position is a rock or has 
	//  another cannon there already
	public boolean areaIsAvailable(int x, int y) {
		return areaIsRock(x, y) && !areaIsOccupied(x, y);
	}

	// if the given positions is a rock
	public boolean areaIsRock(int x, int y) {
		for (TowerRock rock : rocks)
			if (rock.x == x && rock.y == y)
				return true;
		return false;
	}

	// if the given position already has a cannon there
	public boolean areaIsOccupied(int x, int y) {
		for (TowerStructure cannon : cannons)
			if (cannon.getX() == x && cannon.getY() == y)
				return true;
		return false;
	}

	// if the amount of money is enough to buy the 
	//  cannon that is held
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

	// when timer ticks, update game
	//  move, shoot and ball-handling
	public void do_round() {
		for (TowerBall ball : balls)
			ball.move();

		for (TowerStructure cannon : cannons)
			cannon.shoot(balls);

		for (TowerStructure cannon : cannons)
			cannon.moveProjectiles();

		checkRemoving();
		checkCreateNewball();

		repaint();
	}

	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		// left mouse-button
		if (e.getButton() == MouseEvent.BUTTON1) {
			int placedx = x - (x % 30);
			int placedy = y - (y % 30);

			if (holdingObject != null && inBoard(x, y)) {
				// place the cannon
				if (areaIsAvailable(placedx, placedy) && canAfford()) {
					purchaseCurrentHoldingObject();
					holdingObject.setPosition(placedx, placedy);
					cannons.add(holdingObject);
					holdingObject = null;
				}
			} else if (areaIsOccupied(placedx, placedy)) {
				// sets the bar to "upgrade"
				for (TowerStructure cannon : cannons)
					if (cannon.getX() == placedx && cannon.getY() == placedy) {
						towerMenuBar.setState(cannon);
						break;
					}
			} else if (inBoard(x, y))
				// sets the bar to "shopping"
				towerMenuBar.setState(TowerMenuBar.CANNONS);

			// pick up the cannon from icon
			if (towerMenuBar.iconPressed(x, y))
				holdingObject = towerMenuBar.objectPickedUp();

		} else if (e.getButton() == MouseEvent.BUTTON3) // right mouse-button
			holdingObject = null;
	}

	public void mouseMoved(MouseEvent e) {
		if (holdingObject != null) {
			int holdingx = e.getX() - 15;
			int holdingy = e.getY() - 15;
			holdingObject.setPosition(holdingx, holdingy);
		}
	}

	// paints the field
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// paints the background
		g.setColor(Color.green);
		g.fillRect(0, 0, width, height);

		// paints the "doors"
		start.paint(g);
		goal.paint(g);

		for (TowerBall ball : balls)
			ball.paint(g);

		for (TowerRock rock : rocks)
			rock.paint(g);

		for (TowerStructure cannon : cannons)
			cannon.paint(g);

		for (TowerStructure cannon : cannons)
			cannon.paintProjectiles(g);

		// paints the menubar with all its components
		towerMenuBar.paint(g);

		// paints the object held (if there is one)
		if (holdingObject != null)
			holdingObject.paint(g);
	}
}