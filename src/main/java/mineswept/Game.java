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

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
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
		GLFWErrorCallback.createPrint(System.err).set();
		
		/* Initialize the library */
		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		
		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
//		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
		
	    /* Create a windowed mode window and its OpenGL context */
	    window = glfwCreateWindow(640, 480, "Hello World", NULL, NULL);
		if (window == NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}
		
		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});
	}
	
	public void read(File file) throws FileNotFoundException {
		 Scanner s = new Scanner(file);
		 
		// instance variables for Game class at the top of the file
		 xScreenCoordinate = s.nextDouble();
		 yScreenCoordinate = s.nextDouble();
		 timeElapsed = s.nextInt();
		 numOfFlags = s.nextInt();
		 numOfRevealedTiles = s.nextInt();
		 numOfRevealedChunks = s.nextInt();
		 isGameOver = s.nextBoolean();
		 
		 // read all of the chunks
		 ChunkCoordinate coordinate = new ChunkCoordinate(s.nextInt(), s.nextInt());
		 
		 int numOfTilesSweeped = s.nextInt();
		 int numOfEmptyTiles = s.nextInt();
		 int numOfMines = s.nextInt();
		 int width = s.nextInt();
		 int height = s.nextInt();
		 
		 while (s.hasNextLine()) {		  			 
			 Tile[][] tileArr = new Tile[height][width];
			 
			 String tileData = s.nextLine();
			 for (int i=0; i< tileData.length()-3; i+=3) {
				int revealed = Integer.parseInt(tileData.substring(i,i+1)); 
				int flagged = Integer.parseInt(tileData.substring(i+1,i+2));
				int state = Integer.parseInt(tileData.substring(i+2, i+3));
				
				boolean isRevealed = false, isFlagged = false;					
				if (revealed==1) {
					isRevealed = true;
				}
				if (flagged == 1) {
					isFlagged = true;
				}
				
				Tile t = new Tile (isRevealed, isFlagged, state);
			 }				 
		 
		 
		 // create chunk based on tileArr
		 Chunk chunk = new Chunk(tileArr);
		 chunk.setNumOfTilesSweeped(numOfTilesSweeped);
		 chunk.setNumOfEmptyTiles(numOfEmptyTiles);
		 
		 HashMap<ChunkCoordinate, Chunk> chunks = new HashMap<ChunkCoordinate, Chunk>();
		 chunks.put(coordinate, chunk);
		 
		 map = new Map(numOfMines, width, height, chunks);	
		 }
		 s.close();
	}

	public void write(File file) throws FileNotFoundException{
		
		PrintStream writer = new PrintStream(file);
		
		writer.println(xScreenCoordinate);
		writer.println(yScreenCoordinate);
		writer.println(timeElapsed);
		writer.println(numOfFlags);
		writer.println(numOfRevealedTiles);
		writer.println(numOfRevealedChunks);
		
		for(Chunk chunk : map.getAllChunks()) {
			
			writer.println(chunk.getCoordinate().getChunkX());
			writer.println(chunk.getCoordinate().getChunkY());
			writer.println(chunk.getNumOfTilesSweeped());
			writer.println(chunk.getNumOfEmptyTiles());
			writer.println(chunk.getNumOfMines());
			writer.println(chunk.getWidth());
			writer.println(chunk.getHeight());
			
			for(Tile[] tiles : chunk.getTiles()) {
				for(Tile tile : tiles) {
					
					if(tile.isRevealed()) {
						writer.print("1");
					} else {
						writer.print("0");
					}
					
					if(tile.isFlagged()) {
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
