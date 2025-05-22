package mineswept;

import java.io.PrintStream;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.HashMap; // import the HashMap class

public class Game {

	private double xScreenCoordinate;
	private double yScreenCoordinate;
	private int timeElapsed;
	private int numOfFlags;
	private int numOfRevealedTiles;
	private int numOfRevealedChunks;
	private boolean isGameOver;
	// The window handle
	private static long window;
	private Map map;

	public Game() {

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
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
//		GLFWErrorCallback.createPrint(System.err).set();
//		
//		/* Initialize the library */
//		if (!glfwInit()) {
//			throw new IllegalStateException("Unable to initialize GLFW");
//		}
//		
//		// Configure GLFW
//		glfwDefaultWindowHints(); // optional, the current window hints are already the default
////		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
//		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
//		
//	    /* Create a windowed mode window and its OpenGL context */
//	    window = glfwCreateWindow(640, 480, "Hello World", NULL, NULL);
//		if (window == NULL) {
//			throw new RuntimeException("Failed to create the GLFW window");
//		}
//		
//		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
//		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
//			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
//				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
//		});

		Game game = new Game();

		try {
			game.read(new File("test-input"));
			game.write(new File("test-output"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void read(File file) throws FileNotFoundException {
		Scanner s = new Scanner(file);

		// instance variables for Game class at the top of the file
		xScreenCoordinate = s.nextDouble();
		s.nextLine();
		yScreenCoordinate = s.nextDouble();
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

		HashMap<ChunkCoordinate, Chunk> chunks = new HashMap<ChunkCoordinate, Chunk>();
		
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

				tileArr[rowIndex][colIndex] = new Tile(isRevealed, isFlagged, state);
				colIndex++;
			}
			
			ChunkCoordinate coordinate = new ChunkCoordinate(chunkX, chunkY);
			// create chunk based on tileArr
			Chunk chunk = new Chunk(tileArr, coordinate);
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

}
