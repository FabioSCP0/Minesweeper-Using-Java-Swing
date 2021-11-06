package com.github.FabioSCP0.MinesweeperUsingJavaSwing.model;
@FunctionalInterface
public interface ObserverField {
	public void eventHappened(Field field, EventField event);
}
