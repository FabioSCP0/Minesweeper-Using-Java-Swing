package com.github.FabioSCP0.MinesweeperUsingJavaSwing.model;

import java.util.ArrayList;
import java.util.List;

public class Field {
	
	//position x
	private final int line;
	//position y
	private final int column;
	
	//Has the field been opened yet?
	private boolean opened = false;
	//Does the field contain a mine?
	private boolean mined = false;
	//Has the field been marked?
	private boolean marcked = false;
	
	//Self-referential relationship one-to-many
	private List<Field> neighborhood = new ArrayList<Field>();
	private List<ObserverField> observers = new ArrayList<ObserverField>();
	
	//Class constructor
	Field(int line, int column){
		this.line = line;
		this.column = column;
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}
	
	void setOpened(boolean opened) {
		this.opened = opened;
		if(opened) notifyObservers(EventField.OPENED);
	}
	
	public void registerObserver(ObserverField observer) {
		observers.add(observer);
	}
	
	public boolean isOpen() {
		return opened;
	}
	
	public boolean isClosed() {
		return !isOpen();
	}
	
	public boolean isMined() {
		return mined;
	}
	
	public boolean isMarcked() {
		return marcked;
	}
	
	void mined() {
		mined = true;
	}
	
	//if the field has not yet been opened, it can be marked as a possible mine location marked is true
	public void toggleMarcked() {
		if(!opened) {
			marcked = !marcked;
			if(marcked) notifyObservers(EventField.MARKED);
			else notifyObservers(EventField.UNMARKED);
		}
	}
	
	void restart() {
		opened = false;
		mined = false;
		marcked = false;
		notifyObservers(EventField.RESTART);
	}
	
	//Open README to understand that logical
	boolean addNeighbor(Field neighbor) {
		boolean diferentLine = this.line != neighbor.line;
		boolean diferentColumn = this.column != neighbor.column;
		boolean diagonal = diferentLine && diferentColumn;
		
		int deltaLine = Math.abs(this.line - neighbor.line);
		int deltaColumn = Math.abs(this.column - neighbor.column);
		int delta = deltaLine + deltaColumn;
		
		if(delta==1 && !diagonal) {
			neighborhood.add(neighbor);
			return true;
		}else if(delta==2 && diagonal) {
			neighborhood.add(neighbor);
			return true;
		}else return false;
	}
	
	//We don't expect to find any minefields
	public boolean safetedNeighborhood() {
		return neighborhood
				.stream()
				.noneMatch(n -> n.mined);
	}
	
	//We hope to find a number of mines near a neighborhood
	public int mineInNeighborhood() {
		return (int) neighborhood
				.stream()
				.filter(n -> n.mined)
				.count();
	}
	
	//function to open one field, or many empyt fields via recursive call
	public boolean open() {
		if(!opened && !marcked) {
			opened = true;
			//if the chosen field is mined, throws an explosion exception
			setOpened(true);
			if(mined) {
				notifyObservers(EventField.EXPLOSION);
				return true;
			}
			if(safetedNeighborhood()) neighborhood.forEach(n -> n.open());
			//return true if all the fields in the neighborhood is opened with sucess
			return true;
		//if the field is open or marked, it is not possible to open it and returns false
		}else return false;
	}
	
	//Logic to define if we can open all fields without mines and mark all mine fields
	boolean achievedGoal() {
		boolean unraveled = !mined && opened;
		boolean protecteds = mined && marcked;
		return unraveled || protecteds;
	}
	
	private void notifyObservers(EventField event) {
		observers.stream()
			.forEach(o -> o.eventHappened(this, event));
	}
}
