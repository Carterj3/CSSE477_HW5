package edu.rosehulman.minesweepercheatplugin;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTextField;

import edu.rosehulman.mjcommons.Message;
import edu.rosehulman.mjcommons.mjPanel;

public class CheatPanel extends mjPanel {

	private static final long serialVersionUID = 6809616649027218452L;

	private static final String JARNAME = "MinesweeperCheat.jar";

	private static final String MINESWEEPER_JARNAME = "Minesweeper.jar";

	private JButton widthButton;
	private JButton heightButton;
	private JButton mineButton;
	private JButton showMineButton;
	private JButton showBlankButton;
	private JButton resetButton;
	private JButton clickBombButton;

	private JTextField widthInput;
	private JTextField heightInput;
	private JTextField mineInput;

	private ArrayList<Message> outbox = new ArrayList<Message>();

	public CheatPanel() {
		this.setLayout(new GridLayout(5, 2));

		// Width
		widthButton = new JButton("Set Width");
		widthInput = new JTextField("20");

		widthButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setWidthMessage();
			}
		});

		this.add(widthButton);
		this.add(widthInput);
		// Height
		heightButton = new JButton("Set Height");
		heightInput = new JTextField("20");

		heightButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setHeightMessage();
			}
		});

		this.add(heightButton);
		this.add(heightInput);
		// Mines
		mineButton = new JButton("Set Mines");
		mineInput = new JTextField("10");

		mineButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setMineMessage();
			}
		});

		this.add(mineButton);
		this.add(mineInput);
		// Show mines
		showMineButton = new JButton("Show Mines");

		showMineButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showMineMessage();
			}
		});

		this.add(showMineButton);
		// Show blanks
		showBlankButton = new JButton("Show Buttons");

		showBlankButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showBlankMessage();
			}
		});

		this.add(showBlankButton);
		// Reset
		resetButton = new JButton("Reset game");

		resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sendResetMessage();
			}
		});

		this.add(resetButton);
		// Click bomb
		clickBombButton = new JButton("Click a bomb");

		clickBombButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sendClickBombMessage();
			}
		});

		this.add(clickBombButton);
	}

	protected void sendClickBombMessage() {
		Message newMessage = new Message(JARNAME, MINESWEEPER_JARNAME,
				CheatConstants.ClickBomb);
		outbox.add(newMessage);
	}

	protected void sendResetMessage() {
		Message newMessage = new Message(JARNAME, MINESWEEPER_JARNAME,
				CheatConstants.ResetGame);
		outbox.add(newMessage);
	}

	protected void showBlankMessage() {
		Message newMessage = new Message(JARNAME, MINESWEEPER_JARNAME,
				CheatConstants.ShowBlanks);
		outbox.add(newMessage);
	}

	protected void showMineMessage() {
		Message newMessage = new Message(JARNAME, MINESWEEPER_JARNAME,
				CheatConstants.ShowMines);
		outbox.add(newMessage);
	}

	protected void setMineMessage() {
		Message newMessage = new Message(JARNAME, MINESWEEPER_JARNAME,
				CheatConstants.SetMines+mineInput.getText().toString());
		outbox.add(newMessage);
	}

	protected void setWidthMessage() {
		Message newMessage = new Message(JARNAME, MINESWEEPER_JARNAME,
				CheatConstants.SetWidth+widthInput.getText().toString());
		outbox.add(newMessage);
	}

	public void setHeightMessage() {
		Message newMessage = new Message(JARNAME, MINESWEEPER_JARNAME,
				CheatConstants.SetHeight+heightInput.getText().toString());
		outbox.add(newMessage);
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

	}

}
