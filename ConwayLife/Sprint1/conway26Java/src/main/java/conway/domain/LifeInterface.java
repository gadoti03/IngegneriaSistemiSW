package main.java.conway.domain;

public interface LifeInterface {

    /* Computes the next generation of the grid */
    void nextGeneration();

    /* Returns true if the cell at the given row and column is alive */
    boolean isCellAlive(int row, int col);

    /* Sets the state of the cell at the given row and column */
    void setCellState(int row, int col, boolean alive);

    /* Returns the cell at the given row and column */
    ICell getCell(int x, int y);

    /* Returns the grid */
    IGrid getGrid();

    /* Resets the grid */
    void resetGrids();
}