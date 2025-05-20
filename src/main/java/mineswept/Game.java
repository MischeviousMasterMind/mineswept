package mineswept;

import java.time.Duration;
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

public class Game {
	
	private Map map; 
	private double xScreenCoordinate; 
	private double yScreenCoordinate; 
	private Duration timeElapsed;
	private int numOfFlags;
	private int numOfRevealedTiles;
	private int numOfRevealedChunks;
	private boolean isGameOver;
	// The window handle
	private static long window;
	
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
		
	    /* Create a windowed mode window and its OpenGL context */
	    window = glfwCreateWindow(640, 480, "Hello World", NULL, NULL);
		if (window == NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}
	}
}
