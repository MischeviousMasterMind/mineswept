package mineswept;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class Chunk {

	private Tile[][] tiles;
	private int numOfTilesSweeped, numOfEmptyTiles, numOfMines;

	private static Random random = new Random();

	private final int width, height;

	private BigInteger seed;

	private Chunk north, south, east, west;

	private ChunkCoordinate coordinate;

	private boolean updated;

	// TODO Implement constructor
	public Chunk(int width, int height, int numOfMines, ChunkCoordinate coordinate) {

		this.width = width;
		this.height = height;
		this.numOfMines = numOfMines;
		this.coordinate = coordinate;

		seed = new BigInteger(numOfConfigurations().bitLength(), 0, random).mod(numOfConfigurations());

		generate();
	}

	public Chunk(int width, int height, int numOfMines, ChunkCoordinate coordinate, BigInteger seed) {

		this.width = width;
		this.height = height;
		this.numOfMines = numOfMines;
		this.coordinate = coordinate;
		this.seed = seed;

		generate();
	}

	// TODO Implement constructor
	public Chunk(Tile[][] tiles, ChunkCoordinate coordinate) {

		this.width = tiles[0].length;
		this.height = tiles.length;
		this.coordinate = coordinate;
		this.tiles = tiles;

	}

	public ArrayList<Tile> getNeighboringTiles(int row, int col) {

		ArrayList<Tile> neighboringTiles = new ArrayList<>();

		for (int i = -1; i <=  1; i++) {

			for (int ii = -1; ii <= 1; ii++) {

				if ((i == 0 && ii == 0) || (row + i < 0) || (row + i >= width) || (col + ii < 0) || (col + ii >= height)) {

					continue;

				}

				if (tiles[row + i][col + ii] != null) {

					neighboringTiles.add(tiles[row + i][col + ii]);

				}

			}

		}

		return neighboringTiles;
	}

	public int getNumOfTilesSweeped() {
		return numOfTilesSweeped;
	}

	public void setNumOfTilesSweeped(int numOfTilesSweeped) {
		this.numOfTilesSweeped = numOfTilesSweeped;
	}

	public void incrementNumOfTilesSweeped() {
		numOfTilesSweeped++;
	}

	public int getNumOfEmptyTiles() {
		return numOfEmptyTiles;
	}

	public void setNumOfEmptyTiles(int numOfEmptyTiles) {
		this.numOfEmptyTiles = numOfEmptyTiles;
	}

	public int getNumOfMines() {
		return numOfMines;
	}

	public void setNumOfMines(int numOfMines) {
		this.numOfMines = numOfMines;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	// TODO Implement method
	private void generate() {

		tiles = new Tile[width][height];

		BigInteger temp = seed;

		int count = 0;

		for (int i = 0; i < width; i++) {

			for (int ii = 0; ii < height; ii++) {

				tiles[i][ii] = new Tile(false, false, 0, this, i, ii);

			}

		}

		for (int i = 0; i < width; i++) {
			for (int ii = 0; ii < height; ii++) {

				if (count == numOfMines || (temp.compareTo(nCr(width * height - i * height - ii - 1, numOfMines - 1 - count)) >= 0
						&& !temp.equals(BigInteger.ZERO))) {

					temp = temp.subtract(nCr(width * height - i * height - ii - 1, numOfMines - 1 - count));	

				} else {

					tiles[i][ii].setState(9);

					for (Tile neighboringTile : getNeighboringTiles(i, ii)) {

						if (neighboringTile.getState() < 9) {
							neighboringTile.incrementState();
						}

					}

					count++;
					
				}
			}
		}

	}

	public final BigInteger numOfConfigurations() {

		return nCr(this.width * this.height, this.numOfMines);

	}

	public static BigInteger nCr(int n, int r) {

		return (factorial(n).divide(factorial(n - r))).divide(factorial(r));

	}

	private static BigInteger factorial(int n) {

		if (n <= 0) {
			return BigInteger.ONE;
		}

		BigInteger number = BigInteger.ONE;

		for (int i = n; i > 1; i--) {
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

	public Tile[][] getTiles() {
		return tiles;
	}

	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}

	public ChunkCoordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(ChunkCoordinate coordinate) {
		this.coordinate = coordinate;
	}

	public BigInteger getSeed() {
		return seed;
	}

	public void setAdjacentChunk(Chunk adjacentChunk, CardinalDirection direction) {
		switch (direction) {
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

	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}
}
