package mineswept;

import java.math.BigInteger;

public class Chunk {

	private Tile[][] tiles;
	private int numOfTilesSweeped, numOfEmptyTiles, numOfMines;
	
	private final int width, height;
	
	private BigInteger seed;
	
	private Chunk north, south, east, west;
	
	// TODO Implement constructor
	public Chunk(int width, int height, int numOfMines) {
		
		this.width = 0;
		this.height = 0;
		
	}
	
	public Chunk(int width, int height, int numOfMines, BigInteger seed) {
		
		this(width, height, numOfMines);
		
		
	}
	
	// TODO Implement constructor
	public Chunk(Tile[][] tiles) {
		
		this.width = 0;
		this.height = 0;
		this.tiles = tiles;
		
	}
	
	public BigInteger numOfConfigurations() {
		return null;
	}
	
	// TODO Implement method
	private void generate(int width, int height, int numOfMines) {
		
		
		
	}

	public Tile getTile(int row, int col) {
		
		return tiles[row][col];
		
	}
	
	public void setTile(int row, int col, Tile newTile) {
		
		tiles[row][col] = newTile;
		
	}
	
	public BigInteger getSeed() {
		return seed;
	}

	public void addAdjacentChunk(Chunk chunk, CardinalDirection direction) {

		
	}
}
