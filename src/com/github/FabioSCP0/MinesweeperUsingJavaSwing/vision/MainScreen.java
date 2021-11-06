package com.github.FabioSCP0.MinesweeperUsingJavaSwing.vision;

import javax.swing.JFrame;

import com.github.FabioSCP0.MinesweeperUsingJavaSwing.model.Board;

@SuppressWarnings("serial")
public class MainScreen extends JFrame{
	
	public MainScreen() {
		
		Board board = new Board(16,30,50);
		add(new BoardPanel(board));
		
		setTitle("Minesweeper");
		setSize(700,440);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new MainScreen();
	}
}
