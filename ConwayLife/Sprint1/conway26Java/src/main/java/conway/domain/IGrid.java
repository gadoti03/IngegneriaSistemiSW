package main.java.conway.domain;

public interface IGrid {

    /* Returns the number of rows of the grid */
    public int getRows();

    /* Returns the number of columns of the grid */
    public int getCols();

    /* Returns the cell located at the given position */
    public ICell getCell(int row, int column);
    
    /* Returns the current state of the cell */
    public boolean isCellAlive(int row, int column);

    /* Sets the state of the cell at the given position */
    public void setCellState(int row, int column, boolean alive);
    
    /* Set the grid ad the initial state */
    public void reset();
}