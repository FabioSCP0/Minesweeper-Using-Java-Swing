package com.github.FabioSCP0.MinesweeperUsingJavaSwing.vision;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.github.FabioSCP0.MinesweeperUsingJavaSwing.model.Board;

@SuppressWarnings("serial")
public class BoardPanel extends JPanel{
	public BoardPanel(Board board) {
		
		setLayout(new GridLayout(board.getLines(),board.getColumns()));
		
		board.forEachField(c -> add(new BoardButton(c)));
		board.registerObserver(e->{
			
			SwingUtilities.invokeLater(()-> {
				if(e.isWinner()) JOptionPane.showMessageDialog(this, "Won, congrats");
				else JOptionPane.showMessageDialog(this, "Loss, sorry");
				board.restart();
			});
			
		});
	}
}
