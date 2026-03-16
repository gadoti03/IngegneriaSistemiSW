package main.java.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.conway.domain.*;


public class GridTest {
	private static final int nRows=5;
	private static final int nCols=5;
	
private IGrid grid;

	@Before
	public void setup() {
		System.out.println("GridTest | setup");	
		grid= new Grid(nRows,nCols);
	}
	@After
	public void down() {
		System.out.println("GridTest | down");
	}
	
	@Test
	public void testDims() {
		System.out.println("testDims ---------------------" );
		int nr = grid.getRows();
		int nc = grid.getCols();
		assertTrue( nr==nRows && nc==nCols );
	}
	@Test
	public void testCGridCellValue() {
		System.out.println("testCGridCellValue ---------------------" );
		grid.setCellState(0,0,true);
		assertTrue(grid.isCellAlive(0,0));
		assertFalse(grid.isCellAlive(0,1) );
	}
	@Test
	public void testGridRep() {
		System.out.println("testGridRep ---------------------" );
 		System.out.println(""+grid);
		assertTrue( grid.toString().startsWith(". . . . ."));
	}
	@Test
	public void testReset() {
		System.out.println("testReset ---------------------");
		IGrid temp = new Grid(nRows, nCols);
		grid.setCellState(0,0,true);
		assertTrue(grid.isCellAlive(0, 0));
		grid.reset();
		boolean uguali = true;
		for(int i = 0; i < nRows; i++) {
			for(int j = 0; j < nCols; j++) {
				if(grid.isCellAlive(i, j) != temp.isCellAlive(i, j))
					uguali = false;
			}
		}
		assertTrue(uguali);
	}

}
