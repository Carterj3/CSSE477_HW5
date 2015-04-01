package edu.rosehulman.minesweeperplugin;

import java.awt.GridLayout;
import javax.swing.JPanel;

public class MinesweeperPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4805111424729395190L;
	private MinesweeperModel model;
	private MinesweeperButton[][] buttons;

	public MinesweeperPanel() {
		model = new MinesweeperModel();
		this.setLayout(new GridLayout(model.getWidth(), model.getHeight()));
		buttons = new MinesweeperButton[model.getWidth()][model.getHeight()];
		for (int w = 0; w < model.getWidth(); w++) {
			for (int h = 0; h < model.getHeight(); h++) {
				buttons[w][h] = new MinesweeperButton(this, w, h);
				this.add(buttons[w][h]);
			}
		}
	}

	public Spot getSpot(int x, int y) {
		return model.getSpot(x, y);
	}

	public void click(int x, int y) {
		System.out.println("Clicking (" + x + "," + y + ")");
		Result result = model.click(x, y);
		if (result.getClass() == GameOver.class) {
			gameOver();
		} else if (result.getClass() == GameWon.class) {
			gameWon();
		}
		updateButtons();
	}

	public void toggleFlag(int x, int y) {
		System.out.println("Flagging (" + x + "," + y + ")");
		Result result = model.toggleFlag(x, y);
		if (result.getClass() == GameWon.class) {
			gameWon();
		}
		updateButtons();
	}

	private void gameWon() {
		for (int w = 0; w < model.getWidth(); w++) {
			for (int h = 0; h < model.getHeight(); h++) {
				model.getSpot(w, h).setIsVisible(true);
				buttons[w][h].gameWon();
			}
		}
	}

	private void updateButtons() {
		for (int w = 0; w < model.getWidth(); w++) {
			for (int h = 0; h < model.getHeight(); h++) {
				buttons[w][h].updateDiplay();
			}
		}
	}

	private void gameOver() {
		for (int w = 0; w < model.getWidth(); w++) {
			for (int h = 0; h < model.getHeight(); h++) {
				model.getSpot(w, h).setIsVisible(true);
				buttons[w][h].gameOver();
			}
		}
	}

}
