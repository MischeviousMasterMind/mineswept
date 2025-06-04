package mineswept;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;

public class Game {

	private int xScreenCoordinate;
	private int yScreenCoordinate;
	private int timeElapsed;
	private int numOfFlags;
	private int numOfRevealedTiles;
	private int numOfRevealedChunks;
	private boolean isGameOver;
	// The window handle
	private static long window;
	private Map map;

	public Game(Map map) {

		this.map = map;

	}

	public void clickTile(ChunkCoordinate tileLocation) {

	}

	public boolean flagTile() {
		return false;
	}

	public int getFlags() {
		return -1;
	}

	public static void main(String[] args) {

		Map map = new Map(32, 16, 16);
		map.generateChunk(new ChunkCoordinate(1, 0));
		map.generateChunk(new ChunkCoordinate(0, 1));
		map.generateChunk(new ChunkCoordinate(-1, 0));
		map.generateChunk(new ChunkCoordinate(0, -1));




		// map.updateChunkBorder(new ChunkCoordinate(0, 0));
		// map.updateChunkBorder(new ChunkCoordinate(1, 0));
		Game game = new Game(map);

		// try {
			
		// 	game.read(new File("test-input"));
		// 	game.write(new File("test-output"));

		// } catch (FileNotFoundException e) {

		// 	e.printStackTrace();
		// 	System.out.println("File not found :(");
		// 	System.exit(-1);

		// }

		Window window = new Window(game);
	}

	public void read(File file) throws FileNotFoundException {
		Scanner s = new Scanner(file);

		// instance variables for Game class at the top of the file

		xScreenCoordinate = s.nextInt();
		s.nextLine();
		yScreenCoordinate = s.nextInt();
		s.nextLine();
		timeElapsed = s.nextInt();
		s.nextLine();
		numOfFlags = s.nextInt();
		s.nextLine();
		numOfRevealedTiles = s.nextInt();
		s.nextLine();
		numOfRevealedChunks = s.nextInt();
		s.nextLine();

		int numOfMines = s.nextInt();
		s.nextLine();
		int width = s.nextInt();
		s.nextLine();
		int height = s.nextInt();
		s.nextLine();

		HashMap<ChunkCoordinate, Chunk> chunks = new HashMap<>();
		
		Chunk chunk = null;

		while (s.hasNextLine()) {

			s.nextLine();

			int chunkX = s.nextInt();
			s.nextLine();
			int chunkY = s.nextInt();
			s.nextLine();


			int numOfTilesSweeped = s.nextInt();
			s.nextLine();
			int numOfEmptyTiles = s.nextInt();
			s.nextLine();

			Tile[][] tileArr = new Tile[height][width];
			int colIndex = 0;
			int rowIndex = 0;

			String tileData = s.nextLine();
			for (int i = 0; i <= tileData.length() - 3; i += 3) {
				int revealed = Integer.parseInt(tileData.substring(i, i + 1));
				int flagged = Integer.parseInt(tileData.substring(i + 1, i + 2));
				int state = Integer.parseInt(tileData.substring(i + 2, i + 3));

				boolean isRevealed = false, isFlagged = false;
				if (revealed == 1) {
					isRevealed = true;
				}
				if (flagged == 1) {
					isFlagged = true;
				}
				if (colIndex >= width) {
					colIndex = 0;
					rowIndex++;
				}

				tileArr[rowIndex][colIndex] = new Tile(isRevealed, isFlagged, state, chunk, rowIndex, colIndex);
				colIndex++;
			}
			
			ChunkCoordinate coordinate = new ChunkCoordinate(chunkX, chunkY);
			// create chunk based on tileArr
			chunk = new Chunk(tileArr, coordinate);
			chunk.setNumOfTilesSweeped(numOfTilesSweeped);
			chunk.setNumOfEmptyTiles(numOfEmptyTiles);

			chunks.put(coordinate, chunk);
		}

		map = new Map(numOfMines, width, height, chunks);

		s.close();

	}

	public void write(File file) throws FileNotFoundException {

		PrintStream writer = new PrintStream(file);

		writer.println(xScreenCoordinate);
		writer.println(yScreenCoordinate);
		writer.println(timeElapsed);
		writer.println(numOfFlags);
		writer.println(numOfRevealedTiles);
		writer.println(numOfRevealedChunks);
		
		writer.println();
		
		writer.println(map.getNumMines());
		writer.println(map.getWidth());
		writer.println(map.getHeight());

		for (Chunk chunk : map.getAllChunks()) {
			
			writer.println();

			writer.println(chunk.getCoordinate().getChunkX());
			writer.println(chunk.getCoordinate().getChunkY());
			writer.println(chunk.getNumOfTilesSweeped());
			writer.println(chunk.getNumOfEmptyTiles());

			for (Tile[] tiles : chunk.getTiles()) {
				for (Tile tile : tiles) {

					if (tile.isRevealed()) {
						writer.print("1");
					} else {
						writer.print("0");
					}

					if (tile.isFlagged()) {
						writer.print("1");
					} else {
						writer.print("0");
					}

					writer.print(tile.getState());

				}

			}

			writer.println();

		}

		writer.close();

	}

	public int getxScreenCoordinate() {
		return xScreenCoordinate;
	}

	public int getyScreenCoordinate() {
		return yScreenCoordinate;
	}

	public int getTimeElapsed() {
		return timeElapsed;
	}

	public int getNumOfFlags() {
		return numOfFlags;
	}

	public int getNumOfRevealedTiles() {
		return numOfRevealedTiles;
	}

	public int getNumOfRevealedChunks() {
		return numOfRevealedChunks;
	}

	public boolean isGameOver() {
		return isGameOver;
	}

	public Map getMap() {
		return map;
	}

	public void setScreenCoordinate(int x, int y) {

		xScreenCoordinate = x;
		yScreenCoordinate = y;

	}
}
