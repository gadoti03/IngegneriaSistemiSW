package main.java.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.conway.domain.Cell;
import main.java.conway.domain.ICell;
import main.java.conway.domain.Life;
import main.java.conway.domain.LifeInterface;


public class CellTest {
	
	private ICell c;

	@Before
	public void setup() {
		System.out.println("ConwayLifeTest | setup");
		c = new Cell();
	}

	@After
	public void down() {
		System.out.println("ConwayLifeTest | down");
	}
	
	@Test
	public void testCellAlive() {
		System.out.println("ConwayLifeTest | doing alive");
		c.setStatus(true);
		boolean r = c.isAlive();
		assertTrue(r);
	}
	
	@Test
	public void testCellDead() {
		System.out.println("ConwayLifeTest | doing alive");
		c.setStatus(false);
		boolean r = c.isAlive();
		assertFalse(r);
	}

	
	
}
