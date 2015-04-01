package edu.rosehulman.minesweeperplugin;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class MinesweeperButton extends JButton implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2604067970866341397L;

	private MinesweeperPanel parent;
	private int x;
	private int y;

	public MinesweeperButton(MinesweeperPanel minesweeperPanel, int x, int y) {
		this.parent = minesweeperPanel;
		this.x = x;
		this.y = y;
		
		this.setMargin(new Insets(0, 0, 0, 0));
		
		updateDiplay();
		this.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch (e.getButton()) {
		case MouseEvent.BUTTON1: // Left
			parent.click(x, y);
			break;
		case MouseEvent.BUTTON3: // Right
			parent.toggleFlag(x, y);
			break;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void updateDiplay() {
		Spot spot = parent.getSpot(x, y);
		switch (spot.getDisplay()) {
		case BOMB:
			this.setText("B");
			this.setBackground(Color.red);
			break;
		case FLAG:
			this.setText("F");
			this.setBackground(Color.blue);
			break;
		case ZERO:
			this.setText("");
			this.setEnabled(false);
			break;
		case ONE:
			this.setText("1");
			break;
		case TWO:
			this.setText("2");
			break;
		case THREE:
			this.setText("3");
			break;
		case FOUR:
			this.setText("4");
			break;
		case FIVE:
			this.setText("5");
			break;
		case SIX:
			this.setText("6");
			break;
		case SEVEN:
			this.setText("7");
			break;
		case EIGHT:
			this.setText("8");
			break;
		default:
			this.setText("");
			break;
		}
		this.setVisible(true);
	}

	public void gameOver() {
		this.setEnabled(false);
	}

	public void gameWon() {
		this.setEnabled(false);
	}

}
