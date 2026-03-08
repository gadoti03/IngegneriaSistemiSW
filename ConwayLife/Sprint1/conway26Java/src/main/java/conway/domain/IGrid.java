package main.java.conway.domain;

public interface IGrid {

    /* Returns the number of rows of the grid */
    int getRows();

    /* Returns the number of columns of the grid */
    int getCols();

    /* Returns the cell located at the given position */
    ICell getCell(int row, int column);
    
    /* Returns the current state of the cell */
    boolean isCellAlive(int row, int column);

    /* Sets the state of the cell at the given position */
    void setCellState(int row, int column, boolean alive);
    
    /* Return the number of adjacent live cells */
    int countNeighbors(int row, int colums);
    
    /* Set the grid ad the initial state */
    void reset();
}