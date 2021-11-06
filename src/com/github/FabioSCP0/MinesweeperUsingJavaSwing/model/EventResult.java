package com.github.FabioSCP0.MinesweeperUsingJavaSwing.model;

public class EventResult {
	
	private final boolean winner;
	
	public EventResult(boolean won) {
		this.winner = won;
	}

	public boolean isWinner() {
		return winner;
	}
	
}
