package main.java.conway.domain;

public interface ICell {

    /* Sets the state of the cell */
    void setStatus(boolean alive);

    /* Returns the current state of the cell */
    boolean isAlive();

    /* Returns the current state of the cell */
    void switchCellState();
}