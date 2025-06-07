package mineswept;

public class Tile {
	private boolean isRevealed;
	private boolean isFlagged;
	private int state;
	private int x, y;
	private Chunk chunk;
	
	public Tile(int state, int x, int y) {
		this.state = state;
		isRevealed = false;
		isFlagged = false;
		this.x = x;
		this.y = y;
	}
	public Tile(boolean isRevealed, boolean isFlagged, int state, Chunk chunk, int x, int y) {
		this.isRevealed = isRevealed;
		this.isFlagged = isFlagged;
		this.state = state;
		this.x = x;
		this.y = y;
		this.chunk = chunk;
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
	public void incrementState() {
		state++;
	}
	
	
	
	public Chunk getChunk() {
		return chunk;
	}
	public void setChunk(Chunk chunk) {
		this.chunk = chunk;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int sweep() { 
		if (!isFlagged()) {
			isRevealed = true;
			chunk.incrementNumOfTilesSweeped();			
		}
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
