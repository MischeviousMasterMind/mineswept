package mineswept;

public class Tile {
	private boolean isRevealed;
	private boolean isFlagged;
	private int state;
	
	public Tile(int state) {
		this.state = state;
		isRevealed = false;
		isFlagged = false;
	}
	public Tile(boolean isRevealed, boolean isFlagged, int state) {
		this.isRevealed = isRevealed;
		this.isFlagged = isFlagged;
		this.state = state;
	}
	
	public boolean isRevealed() {
		return isRevealed;
	}
	public void setRevealed(boolean isRevealed) {
		this.isRevealed = isRevealed;
	}
	public boolean isFlagged() {
		return isFlagged;
	}
	public void setFlagged(boolean isFlagged) {
		this.isFlagged = isFlagged;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	public int sweep() {
		isRevealed = true;
		return state;
	}	
	public boolean flag() {
		if (!isRevealed) {
			isFlagged = !isFlagged;
			return !isFlagged;
		}
		return isFlagged;
	}
}
