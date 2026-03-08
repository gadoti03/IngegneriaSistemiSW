package main.java.conway.domain;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Life implements LifeInterface{
	
	public static LifeInterface CreateLife(int nr, int nc) {
	   return new Life(nr,nc);  
	   // nr and nc can be read from a configuration file
	}
	
	// Rows and Columns
	private final int rows;
    private final int cols;
    
    
    // References that point to the grids containing the current and next state
    private IGrid currentGrid;
    private IGrid nextGrid;
    
    /*
    public static LifeInterface CreateGameRules() {
    	return new Life(5, 5); 
	   // Dimensioni di default, possono essere 
	   //lette da un file di configurazione o passate come parametri
    }
    */

    public Life(int rows, int cols) {
    	this.rows = rows;
    	this.cols = cols;
        this.currentGrid = new Grid(rows, cols);
        this.nextGrid = new Grid(rows, cols);
    }

    @Override
    public void nextGeneration() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int neighbors = this.currentGrid.countNeighbors(row,col); 
            	// Apply the rules reading from currentGrid, writign in nextGrid
                applyLifeRules(row, col, neighbors);
            }
        }
        swapGrids();
    }
    
    protected void applyLifeRules(int row, int col, int neighbors) {
        boolean nextStatus;
        boolean isAlive = this.currentGrid.isCellAlive(row, col);
        //apply Life rules
        if (isAlive) {
        	nextStatus =  (neighbors == 2 || neighbors == 3);                
        } else {
        	nextStatus = neighbors == 3;           
        }
        this.nextGrid.setCellState(row, col, nextStatus);
    }

	@Override
	public boolean isCellAlive(int row, int col) {
		return this.currentGrid.isCellAlive(row, col);
	}

	@Override
	public void setCellState(int row, int col, boolean alive) {
		this.currentGrid.setCellState(row, col, alive);
	}

	@Override
	public ICell getCell(int row, int col) {
		return this.currentGrid.getCell(row, col);
	}

	@Override
	public IGrid getGrid() {
		return this.currentGrid;
	}
	
	protected void swapGrids() {
        IGrid temp = currentGrid;
        currentGrid = nextGrid;
        nextGrid = temp;
	}

	@Override
	public void resetGrids() {
		currentGrid.reset();
		nextGrid.reset();
	}	
}
