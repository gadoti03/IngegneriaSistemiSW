package main.java.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.conway.domain.IGrid;
import main.java.conway.domain.Life;
import main.java.conway.domain.LifeInterface;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
 

public class LifeTest {
private LifeInterface lifeModel;

	@Before
	public void setup() {
		System.out.println("GridTest | setup");	
		lifeModel = new Life(5, 5);
	}

	@After
	public void down() {
		System.out.println("GridTest | down");
	}
	
	@Test
	public void testSetCellAlive() {
	    
	    lifeModel.setCellState(2, 3, true);
	
	    assertTrue(lifeModel.isCellAlive(2, 3));
	}
	
	@Test
	public void testSetCellDead() {
	
	    lifeModel.setCellState(1, 1, false);
	
	    assertFalse(lifeModel.isCellAlive(1, 1));
	}
	
	@Test
	public void testLonelyCellDies() {
	
	    lifeModel.setCellState(1, 1, true);
	
	    lifeModel.nextGeneration();
	
	    assertFalse(lifeModel.isCellAlive(1, 1));
	}
	
	@Test
	public void testBlockStillLife() {
	
		lifeModel.setCellState(1,1,true);
		lifeModel.setCellState(1,2,true);
		lifeModel.setCellState(2,1,true);
		lifeModel.setCellState(2,2,true);
	
		lifeModel.nextGeneration();
	
	    assertTrue(lifeModel.isCellAlive(1,1));
	    assertTrue(lifeModel.isCellAlive(1,2));
	    assertTrue(lifeModel.isCellAlive(2,1));
	    assertTrue(lifeModel.isCellAlive(2,2));
	}
	
	@Test
	public void testReset() {
	
		lifeModel.setCellState(2,2,true);
	
		lifeModel.resetGrids();
	
	    assertFalse(lifeModel.isCellAlive(2,2));
	}
	
	@Test
	public void testGetGrid() {
	
	    IGrid grid = lifeModel.getGrid();
	
	    assertNotNull(grid);
	}

}