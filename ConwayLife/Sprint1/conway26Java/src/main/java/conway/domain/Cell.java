package main.java.conway.domain;

public class Cell implements ICell {

	private boolean status = false;
	
	@Override
	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public boolean isAlive() {
		return this.status;
	}

	@Override
	public void switchCellState() {
		this.status = !this.status;
	}

	
}
