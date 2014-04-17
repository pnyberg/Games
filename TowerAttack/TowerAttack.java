import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TowerAttack extends JFrame implements ActionListener {
	private GameField gameField;
	private JPanel buttonPanel;
	private JButton startButton;

	// creates the frame for the game
	public TowerAttack() {
		gameField = new GameField();

		buttonPanel = new JPanel();
		startButton = new JButton("Start game");

		startButton.addActionListener(this);

		buttonPanel.add(startButton);

		add(gameField);
		add(buttonPanel, BorderLayout.SOUTH);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800,600);
		setVisible(true);
	}

	// checks if any button has been pushed
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startButton) {
			gameField.requestFocus();
			gameField.start();
		}
	}

	public static void main(String[] args) {
		new TowerAttack();
	}
}