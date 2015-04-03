package edu.rosehulman.minesweeperplugin;

import java.util.Random;

import javax.naming.ldap.PagedResultsControl;

public class MinesweeperModel {

	private static final Mine Mine = new Mine();
	private static final Flag Flag = new Flag();

	private int width;
	private int height;

	private int mines;

	private Spot[][] field;

	public MinesweeperModel(int width, int height, int mines) {
		initMinesweeper(width, height, mines);
	}

	private void initMinesweeper(int width, int height, int mines) {
		this.field = new Spot[width][height];
		this.width = width;
		this.height = height;
		this.mines = mines;

		for (int w = 0; w < width; w++) {
			for (int h = 0; h < height; h++) {
				field[w][h] = new Spot(this, w, h);
			}
		}

		Random rand = new Random();

		int layedMines = 0;
		for (int newMine = rand.nextInt(width * height); layedMines < mines; newMine = rand
				.nextInt(width * height)) {
			int mineY = newMine / width;
			int mineX = newMine % width;

			if (!field[mineX][mineY].hasMine()) {
				field[mineX][mineY].add(Mine);
				layedMines++;
			}
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Spot getSpot(int x, int y) {
		if (x >= width || y >= height) {
			return null;
		}
		if (x < 0 || y < 0) {
			return null;
		}
		return field[x][y];
	}

	public Result click(int x, int y) {
		field[x][y].setIsVisible(true);
		
		if(field[x][y].isFlagged()){
			return new Nothing();
		}

		if (field[x][y].hasMine()) {
			return new GameOver();
		} else {
			if (bombsAround(x, y) == 0) {
				for (int dw = -1; dw <= 1; dw++) {
					for (int dh = -1; dh <= 1; dh++) {
						if (getSpot(x + dw, y + dh) != null) {
							if (!getSpot(x + dw, y + dh).getVisible()) {
								click(x + dw, y + dh);
							}
						}
					}
				}
			}
			if (gameWon()) {
				return new GameWon();
			}
			return new Nothing();
		}
	}

	public Result toggleFlag(int x, int y) {
		field[x][y].toggleFlag();
		if (gameWon()) {
			return new GameWon();
		}
		return new Nothing();
	}

	private boolean gameWon() {
		int spotsLeft = 0;
		for (int w = 0; w < width; w++) {
			for (int h = 0; h < height; h++) {
				Spot spot = getSpot(w, h);
				if (!spot.getVisible()) {
					spotsLeft++;
				}
			}
		}
		return (spotsLeft == mines);
	}

	public int bombsAround(int x, int y) {
		int numBombs = 0;
		for (int dw = -1; dw <= 1; dw++) {
			for (int dh = -1; dh <= 1; dh++) {
				if (getSpot(x + dw, y + dh) != null) {
					numBombs += getSpot(x + dw, y + dh).hasMine() ? 1 : 0;
				}
			}
		}
		return numBombs;
	}

	public int getMines() {
		return this.mines;
	}

}
