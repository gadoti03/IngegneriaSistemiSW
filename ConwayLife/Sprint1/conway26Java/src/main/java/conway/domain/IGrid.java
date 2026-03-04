package main.java.conway.domain;

public interface IGrid {
	public int getRows();
	public int getCols();
	public boolean isAlive(int row, int col);
	public void setStatus(int row, int col, boolean status);
	public int countNeighborsLive(int row, int col);
	public ICell getCell(int row, int col);
}
