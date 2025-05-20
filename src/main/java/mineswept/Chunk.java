package mineswept;

import java.math.BigInteger;
import java.util.Random;

public class Chunk {

	private Tile[][] tiles;
	private int numOfTilesSweeped, numOfEmptyTiles, numOfMines;
	
	private static Random random = new Random();

	private final int width, height;
	
	private BigInteger seed;
	
	private Chunk north, south, east, west;
	
	// TODO Implement constructor
	public Chunk(int width, int height, int numOfMines) {
		
		this.width = 0;
		this.height = 0;
		
		seed = new BigInteger(numOfConfigurations().bitLength(), 0, random).mod(numOfConfigurations());

		generate();
	}
	
	public Chunk(int width, int height, int numOfMines, BigInteger seed) {
		
		this.width = 0;
		this.height = 0;
		this.seed = seed;

		generate();
	}
	
	// TODO Implement constructor
	public Chunk(Tile[][] tiles) {
		
		this.width = 0;
		this.height = 0;
		this.tiles = tiles;
		
	}
	
	// TODO Implement method
	private void generate() {
		
		BigInteger temp = seed;
		
		int count = 0;
		
		for(int i = 0; i < width; i++)
		{
			for(int ii = 0; ii < height; ii++)
			{
				System.out.print("Comparing " + temp + " to: " + nCr(width * height - i * height - ii - 1, numOfMines - 1 - count));
				
				if(temp.compareTo(nCr(width * height - i * height - ii - 1, numOfMines - 1 - count)) >= 0 && !temp.equals(BigInteger.ZERO))
				{
					System.out.println(" SUBTRACTING!");
					temp = temp.subtract(nCr(width * height - i * height - ii - 1, numOfMines - 1 - count));
				}
				else
				{
					System.out.println(" adding a mine!");
					tiles[i][ii] = new Tile(false, false, 9);
					count++;
					
					if(count == numOfMines) {
						return;
					}
				}	
			}
		}
		
	}

	public final BigInteger numOfConfigurations() {

		return nCr(width * height, numOfMines);

	}

	private BigInteger nCr(int n, int r) {

		return factorial(n).divide(factorial(n - r)).divide(factorial(r));

	}

	private BigInteger factorial(int n) {

		BigInteger number = BigInteger.valueOf(n);
		
		if(n == 0) return BigInteger.ONE;
		
		for (int i = 2; i < n; i++)
		{
			number = number.multiply(BigInteger.valueOf(i));
		}

		return number;
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

	public void setAdjacentChunk(Chunk adjacentChunk, CardinalDirection direction) {
		switch(direction) {
			case NORTH:
				north = adjacentChunk;
				break;
			case SOUTH:
				south = adjacentChunk;
				break;	
			case WEST:
				west = adjacentChunk;
				break;
			case EAST:
				east = adjacentChunk;
				break;		
		}		
	}
}
