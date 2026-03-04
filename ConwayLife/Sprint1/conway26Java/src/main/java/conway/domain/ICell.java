package main.java.conway.domain;

public interface ICell {

	void setStatus(boolean alive);
	boolean isAlive();
	void switchCellState();
	
}
