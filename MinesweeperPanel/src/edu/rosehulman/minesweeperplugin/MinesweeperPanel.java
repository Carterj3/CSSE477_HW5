package edu.rosehulman.minesweeperplugin;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import edu.rosehulman.mjcommons.Message;
import edu.rosehulman.mjcommons.mjPanel;

public class MinesweeperPanel extends mjPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4805111424729395190L;
	private static final String JARNAME = "Minesweeper.jar";
	private MinesweeperModel model;
	private MinesweeperButton[][] buttons;
	private ArrayList<Message> outbox = new ArrayList<Message>();

	public MinesweeperPanel() {
		init();
	}

	public void init() {
		init(20, 20, 52);
	}

	public void init(int width, int height, int mines) {
		this.setPreferredSize(new Dimension(15 * width, 15 * height));

		if (buttons != null) {
			for (int w = 0; w < model.getWidth(); w++) {
				for (int h = 0; h < model.getHeight(); h++) {
					this.remove(buttons[w][h]);
				}
			}
		}

		model = new MinesweeperModel(width, height, mines);
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
		addStatusMessage("Clicking (" + x + "," + y + ")");
		Result result = model.click(x, y);
		if (result.getClass() == GameOver.class) {
			gameOver();
		} else if (result.getClass() == GameWon.class) {
			gameWon();
		}
		updateButtons();
	}

	private void addStatusMessage(String message) {
		Message m = new Message(JARNAME, "System", message);
		outbox.add(m);
	}

	public void toggleFlag(int x, int y) {
		addStatusMessage("Flagging (" + x + "," + y + ")");
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

	@Override
	public void stop() {

	}

	@Override
	public Message getMessage() {
		if (outbox.size() > 0) {
			return outbox.remove(0);
		}
		return null;
	}

	@Override
	public void receiveMessage(Message m) {
		outbox.add(new Message(JARNAME, "System", "Received Message ("
				+ m.message.split(":").length + ", "
				+ (m.message.split(":").length <= 1) + ") : " + m.message));
		
		String switchOn = m.message;
		
		if(switchOn.split(":").length > 1){
			switchOn = switchOn.split(":")[0]+":";
		}
		switch (switchOn) {
		case edu.rosehulman.minesweepercheatplugin.CheatConstants.ClickBomb:
			for (int w = 0; w < model.getWidth(); w++) {
				for (int h = 0; h < model.getHeight(); h++) {
					if (model.getSpot(w, h).hasMine()) {
						model.click(w, h);
						break;
					}
				}
			}
			this.updateButtons();
			break;
		case edu.rosehulman.minesweepercheatplugin.CheatConstants.ResetGame:
			init();
			break;
		case edu.rosehulman.minesweepercheatplugin.CheatConstants.ShowBlanks:
			for (int w = 0; w < model.getWidth(); w++) {
				for (int h = 0; h < model.getHeight(); h++) {
					if (model.bombsAround(w, h) == 0
							&& (!model.getSpot(w, h).getVisible())) {
						model.click(w, h);
					}
				}
			}
			this.updateButtons();
			break;
		case edu.rosehulman.minesweepercheatplugin.CheatConstants.ShowMines:
			for (int w = 0; w < model.getWidth(); w++) {
				for (int h = 0; h < model.getHeight(); h++) {
					if (model.getSpot(w, h).hasMine()) {
						model.getSpot(w, h).setIsVisible(true);
					}
				}
			}
			this.updateButtons();
			break;
		case edu.rosehulman.minesweepercheatplugin.CheatConstants.SetWidth:
			if (m.message.split(":").length <= 1) {
				return;
			}
			int newWidth = Integer.parseInt(m.message.split(":")[1]);
			outbox.add(new Message(JARNAME, "System", "New Width ("
					+newWidth + ", "
					+ m.message.split(":")[1] +")" + m.message));
			init(newWidth, model.getHeight(), model.getMines());
			break;
		case edu.rosehulman.minesweepercheatplugin.CheatConstants.SetHeight:
			if (m.message.split(":").length <= 1) {
				return;
			}
			int newHeight = Integer.parseInt(m.message.split(":")[1]);
			init(model.getWidth(), newHeight, model.getMines());
			break;
		case edu.rosehulman.minesweepercheatplugin.CheatConstants.SetMines:
			if (m.message.split(":").length <= 1) {
				return;
			}
			int newMines = Integer.parseInt(m.message.split(":")[1]);
			init(model.getWidth(), model.getHeight(), newMines);
			break;
		default:
			return;
		}
	}

}
