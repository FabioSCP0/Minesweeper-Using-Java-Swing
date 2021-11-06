package com.github.FabioSCP0.MinesweeperUsingJavaSwing.model;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Board implements ObserverField{
	
	private final int lines;
	private final int columns;
	private final int mines;
	
	private final List<Field> fields = new ArrayList<Field>();
	private final List<Consumer<EventResult>> observers = new ArrayList<Consumer<EventResult>>();
	
	
	public int getLines() {
		return lines;
	}

	public int getColumns() {
		return columns;
	}
	
	public int getMines() {
		return mines;
	}

	public List<Field> getFields() {
		return fields;
	}

	public Board(int lines, int columns, int mines){
		this.lines = lines;
		this.columns = columns;
		this.mines = mines;
		
		generateFields();
		associateNeighbors();
		drawMines();
	}
	
	public void forEachField(Consumer<Field> function) {
		fields.forEach(function);
	}
	public void registerObserver(Consumer<EventResult> observer) {
		observers.add(observer);
	}
	
	public void open(int lines, int columns) {
		fields.parallelStream()
			.filter(f -> f.getLine() == lines && f.getColumn() == columns)
			.findFirst()
			.ifPresent(f -> f.open());
	}
	
	public void toggleMarcked(int lines, int columns) {
		fields.parallelStream()
		.filter(f -> f.getLine() == lines && f.getColumn() == columns)
		.findFirst()
		.ifPresent(f -> f.toggleMarcked());
	}
	
	public void restart() {
		fields.stream().forEach(c -> c.restart());
		drawMines();
	}
	
	public boolean achievedGoal() {
		return fields.stream().allMatch(f -> f.achievedGoal());
	}
	
	private void generateFields() {
		for (int line = 0; line < lines; line++) {
			for (int column = 0; column < columns; column++) {
				Field field = new Field(line, column);
				field.registerObserver(this);
				fields.add(field);
			}
		}
	}

	private void associateNeighbors() {
		for (Field f1 : fields) {
			for (Field f2 : fields) {
				f1.addNeighbor(f2);
			}
		}
		
	}

	private void drawMines() {
		long armedMines = 0;
		Predicate<Field> mined = m -> m.isMined();
		
 		do {
 			int random = (int)(Math.random()*fields.size());
 			fields.get(random).mined();
 			armedMines = fields.stream().filter(mined).count();
			
		}while(armedMines < mines);	
	}
	
	private void notifyObservers(boolean result) {
		observers.stream()
			.forEach(o -> o.accept(new EventResult(result)));
	}
	
	private void showMines() {
		fields.stream()
			.filter(c -> c.isMined())
			.forEach(c -> c.setOpened(true));
	}

	@Override
	public void eventHappened(Field field, EventField event) {
		if(event == EventField.EXPLOSION) {
			showMines();
			notifyObservers(false);
		}
		else if(achievedGoal()) notifyObservers(true);
		
	}
}
