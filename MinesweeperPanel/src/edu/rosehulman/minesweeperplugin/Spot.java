package edu.rosehulman.minesweeperplugin;

public class Spot {

	public enum DISPLAY {
		UNKNOWN, FLAG, BOMB,
		ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT
	}
	
	private boolean hasMine = false;
	private boolean isFlagged = false;
	private boolean isVisible = false;
	
	private MinesweeperModel parent;
	
	private int x;
	private int y;

	public Spot(MinesweeperModel parent, int x, int y) {
		this.parent = parent;
		this.x = x;
		this.y = y;
	}

	public void add(Mine mine) {
		this.hasMine = true;
	}

	public boolean hasMine() {
		return this.hasMine;
	}

	public DISPLAY getDisplay() {
		if(isFlagged){
			return DISPLAY.FLAG;
		}
		if(!isVisible){
			return DISPLAY.UNKNOWN;
		}
		if(isVisible && hasMine){
			return DISPLAY.BOMB;
		}
		switch (parent.bombsAround(x,y)) {
		case 0:
			return DISPLAY.ZERO;
		case 1:
			return DISPLAY.ONE;
		case 2:
			return DISPLAY.TWO;
		case 3:
			return DISPLAY.THREE;
		case 4:
			return DISPLAY.FOUR;
		case 5:
			return DISPLAY.FIVE;
		case 6:
			return DISPLAY.SIX;
		case 7:
			return DISPLAY.SEVEN;
		case 8:
			return DISPLAY.EIGHT;
		default:
			return DISPLAY.UNKNOWN;
		}
	}

	public void setIsVisible(boolean b) {
		this.isVisible = b;
		
	}

	public void toggleFlag() {
		this.isFlagged = !this.isFlagged;
	}

	public boolean getVisible() {
		return this.isVisible;
	}

	public boolean isFlagged() {
		return this.isFlagged;
	}

}
