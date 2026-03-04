package main.java.conway.domain;

public class Grid implements IGrid{

	private int rows;
	private int cols;
	private ICell[][] grid;
	
	public Grid(int rows, int cols) {
		this.rows=rows;
		this.cols=cols;
		this.grid=new ICell[rows][cols];
		for (int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				this.grid[i][j]=new Cell();
			}
		}
	}

	@Override
	public int getRows() {
		return this.rows;
	}

	@Override
	public int getCols() {
		return this.cols;
	}

	@Override
	public boolean isAlive(int row, int col) {
		return this.grid[row][col].isAlive();
	}

	@Override
	public void setStatus(int row, int col, boolean status) {
		this.grid[row][col].setStatus(status);
		
	}
	
	@Override
    public int countNeighborsLive(int row, int col) {
		int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;

                int neighborRow = row + i;
                int neighborCol = col + j;
                
                if (neighborRow >= 0 && neighborRow < rows && neighborCol >= 0 && neighborCol < cols) {
                    
                    if (getCell(neighborRow,neighborCol).isAlive()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

	@Override
	public ICell getCell(int row, int col) {
		return this.grid[row][col];
	}
}
