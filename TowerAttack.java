/**
 * BUGG: Sätt ut tre "shockers" med level (nerifrån) 3, 2 och 1. Fjärde går ej att sätta ut
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TowerAttack extends JFrame implements ActionListener, MouseListener, MouseMotionListener {
	private GameField gameField;
	private JPanel buttonPanel;
	private JButton startButton;

	private Timer timer;

	// creates the frame for the game
	public TowerAttack() {
		timer = new Timer(10, this);

		gameField = new GameField(timer);

		buttonPanel = new JPanel();
		startButton = new JButton("Start game");

		addMouseListener(this);
		addMouseMotionListener(this);

		startButton.addActionListener(this);

		buttonPanel.add(startButton);

		add(gameField);
		add(buttonPanel, BorderLayout.SOUTH);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(700,600);
		setVisible(true);
	}

	// checks if any button has been pushed
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startButton) {
			timer.start();
 		} else {
			gameField.do_round();
 		}
	}

	@Override
	public void mousePressed(MouseEvent e) { /* do nothing */ }

	@Override
	public void mouseClicked(MouseEvent e) {
		gameField.mouseClicked(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) { /* do nothing */ }

	@Override
	public void mouseEntered(MouseEvent e) { /* do nothing */ }

	@Override
	public void mouseExited(MouseEvent e) { /* do nothing */ }

	// if your holding an object, update it's position
	@Override
	public void mouseMoved(MouseEvent e) {
		gameField.mouseMoved(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) { /* do nothing */ }

	public static void main(String[] args) {
		new TowerAttack();
	}
}