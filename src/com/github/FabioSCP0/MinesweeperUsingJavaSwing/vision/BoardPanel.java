package com.github.FabioSCP0.MinesweeperUsingJavaSwing.vision;

import java.awt.GridLayout;

import javax.swing.JPanel;

import com.github.FabioSCP0.MinesweeperUsingJavaSwing.model.Board;

@SuppressWarnings("serial")
public class BoardPanel extends JPanel{
	public BoardPanel(Board board) {
		setLayout(new GridLayout(board.getLines(),board.getColumns()));
		board.forEachField(c -> add(new BoardButton(c)));
	}
	
	
}
