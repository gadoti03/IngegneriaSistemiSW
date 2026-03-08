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
	public ICell getCell(int row, int column) {
		return this.grid[row][column];
	}

	@Override
	public void setCellState(int row, int column, boolean alive) {
		this.grid[row][column].setStatus(alive);
	}

	@Override
	public boolean isCellAlive(int row, int column) {
		return this.grid[row][column].isAlive();
	}
	
	@Override
	public int countNeighbors(int row, int colums) {
		int count = 0;
		
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                
                if (i == 0 && j == 0) continue;

                int neighborRow = row + i;
                int neighborCol = colums + j;

                if (neighborRow >= 0 && neighborRow < rows && neighborCol >= 0 && neighborCol < cols) {
                    
                    if (isCellAlive(neighborRow, neighborCol)) {
                        count++;
                    }
                }
            }
        }
        return count;
	}
	
	@Override
	  public void reset() {
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					grid[i][j].setStatus(false);
				}
			}
	  }
}
