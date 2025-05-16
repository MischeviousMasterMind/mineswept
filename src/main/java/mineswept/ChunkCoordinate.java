package mineswept;

public class ChunkCoordinate {
	private int chunkX;
	private int chunkY;
	
	public ChunkCoordinate(int chunkX, int chunkY) {
		this.chunkX = chunkX;
		this.chunkY = chunkY;
	}

	public int getChunkX() {
		return chunkX;
	}
	public void setChunkX(int chunkX) {
		this.chunkX = chunkX;
	}
	public int getChunkY() {
		return chunkY;
	}
	public void setChunkY(int chunkY) {
		this.chunkY = chunkY;
	}	
} 

