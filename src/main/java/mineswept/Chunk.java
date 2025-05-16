package mineswept;

import java.math.BigInteger;

public class Chunk {

	private Tile[][] tiles;
	private int numOfTilesSweeped, numOfEmptyTiles, numOfMines;
	
	private final int width, height;
	
	private BigInteger seed;
	
	// TODO Implement constructor
	public Chunk(int width, int height, int numOfMines) {
		
		this.width = 0;
		this.height = 0;
		
	}
	
	// TODO Implement constructor
	public Chunk(Tile[][] tiles) {
		
		this.width = 0;
		this.height = 0;
		this.tiles = tiles;
		
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
}
