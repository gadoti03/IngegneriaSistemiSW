package main.java.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.conway.domain.*;


public class GridTest {
	
	private IGrid g;
	public static final int rowsSize = 5;
	public static final int colsSize = 5;

	@Before
	public void setup() {
		System.out.println("GriTest | setup");
		g = new Grid(rowsSize,colsSize);
	}

	@After
	public void down() {
		System.out.println("GridTest | down");
	}
	
	@Test
	public void testSetGetCellCorretto() {
		System.out.println("GridTest | testSetGetCellCorretto");
		g.setStatus(0, 0, true);
		assertTrue(g.isAlive(0, 0));
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetCellErrato1() {
		System.out.println("GridTest | testGetCellErrato1");
	    g.isAlive(-1, -1);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetCellErrato2() {
		System.out.println("GridTest | testGetCellErrato2");
	    g.isAlive(rowsSize, colsSize);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testSetCellErrato1() {
		System.out.println("GridTest | testSetCellErrato1");
	    g.setStatus(-1, -1, true);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testSetCellErrato2() {
		System.out.println("GridTest | testSetCellErrato2");
	    g.setStatus(rowsSize, colsSize, true);
	}
	
	@Test
	public void testGetRows() {
		assertTrue(g.getRows()==rowsSize);
	}
	
	@Test
	public void testGetCols() {
		assertTrue(g.getCols()==colsSize);
	}
	
	

	
	
}
