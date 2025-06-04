package mineswept;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;

public class Window extends JPanel implements ActionListener, MouseInputListener, MouseWheelListener {

	public final static int TILE_SIZE = 100;
	public int drawTileSize = TILE_SIZE;
	public double scrollSensitivity = 1.0;
	public double scale = 1.0;

	private Game game;

	private Image hidden, tile0, tile1, tile2, tile3, tile4, tile5, tile6, tile7, tile8, mine, flag;

	private int mouseInitX, mouseInitY;
	private int currentMouseX, currentMouseY;
	private int initxScreenCoordinate, inityScreenCoordinate;
	private int xMapPositionOfMouse, yMapPositionOfMouse;

	private double zoom;

	public Window(Game game) {

		this.game = game;

		JFrame f = new JFrame("Mineswept");

		f.setSize(new Dimension(1000, 800));
		f.setBackground(Color.white);
		f.add(this);
		f.setResizable(true);

		addMouseListener(this);
		addMouseMotionListener(this);

		initializeSprites();

		Timer t = new Timer(0, this);
		t.start();

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

		zoom = 1.0;
	}
    
	@Override
	public void paint(Graphics g) {

		super.paintComponent(g);

		drawMap(g, game.getMap());

		// drawChunk(g, game.getMap().getChunk(0, 0));
		// drawChunk(g, game.getMap().getChunk(1, 0));

		getChunksOnScreen(g);
		
	
		
		
		// drawChunk(g, game.getMap().getChunk(0, 0), (int)game.getxScreenCoordinate(), (int)game.getyScreenCoordinate());

		debug(g);

	}

	public void debug(Graphics g) {

		g.setColor(Color.BLACK);

		g.drawString(String.format("Scale: %.2f", scale), (int)(getSize().getWidth() - 70), 20);

		g.setColor(Color.RED);
		g.drawString(String.format("Map Coordinate of Screen: (%d, %d)", game.getxScreenCoordinate(),
				game.getyScreenCoordinate()), 10, 20);
		g.drawString(String.format("Mouse Position on Screen: (%d, %d)", currentMouseX, currentMouseY), 10, 40);
		g.drawString(String.format("Map Coordinate of Mouse: (%d, %d)", xMapPositionOfMouse, yMapPositionOfMouse), 10, 60);

		ChunkCoordinate currentChunkCoordinate = getChunkCoordinate();

		g.drawString(String.format("Selected Chunk Coordinate: (%d, %d)", currentChunkCoordinate.getChunkX(), currentChunkCoordinate.getChunkY()), 10, 80);

		g.setColor(Color.BLUE);

		g.drawRect(currentChunkCoordinate.getChunkX() * game.getMap().getWidth() * drawTileSize - game.getxScreenCoordinate(), 
			currentChunkCoordinate.getChunkY() * game.getMap().getHeight() * drawTileSize - game.getyScreenCoordinate(), game.getMap().getWidth() * drawTileSize, game.getMap().getHeight() * drawTileSize);
	
		g.setColor(Color.RED);

		int tileWidth = 100;
		int tileHeight = 100;

		// find position of each chunk on the screen (chunk location in map * length of
		// chunk)
		int chunkX0 = (int) currentChunkCoordinate.getChunkX() * game.getMap().getWidth() * drawTileSize;
		int chunkY0 = (int) currentChunkCoordinate.getChunkY() * game.getMap().getHeight() * drawTileSize;

		// find the position of the mouse in each chunk (screen coordinate - chunk
		// coordinate)
		int deltaX = xMapPositionOfMouse - chunkX0;
		int deltaY = yMapPositionOfMouse - chunkY0;

		// find the tile position within the chunk
		int tileX = deltaX / drawTileSize;
		int tileY = deltaY / drawTileSize;

		g.drawString(String.format("Selected Tile Coordinate: (%d, %d)", tileX, tileY), 10, 100);
		g.drawString(String.format("Selected Chunk X0/Y0 Coordinate: (%d, %d)", chunkX0, chunkY0), 10, 180);
		g.drawString(String.format("Selected Delta X/Y Coordinate: (%d, %d)", deltaX, deltaY), 10, 200);

		g.drawRect((currentChunkCoordinate.getChunkX() * game.getMap().getWidth() + tileX) * drawTileSize - game.getxScreenCoordinate(), 
			(currentChunkCoordinate.getChunkY() * game.getMap().getHeight() + tileY) * drawTileSize - game.getyScreenCoordinate(), drawTileSize, drawTileSize);

		g.setColor(Color.GRAY);

		try {

			Tile selectedTile = getTile(currentChunkCoordinate);
			
			g.drawString(String.format("isFlagged: %b", selectedTile.isFlagged()), 10, 120);
			g.drawString(String.format("isRevealed: %b", selectedTile.isRevealed()), 10, 140);
			g.drawString(String.format("tileState: %d", selectedTile.getState()), 10, 160);

		} catch (NullPointerException | IndexOutOfBoundsException e) {

			g.drawString("Unable to get tile", 10, 120);

		}

		g.setColor(Color.BLACK);

	}
	
	public void getChunksOnScreen(Graphics g) {
		
		for(int i = (int) ((double) inityScreenCoordinate/(game.getMap().getHeight() * TILE_SIZE)); i < Math.ceil((inityScreenCoordinate+getSize().getHeight())/(game.getMap().getHeight() * TILE_SIZE)); i++) {
			for(int j = (int) ((double)initxScreenCoordinate/(game.getMap().getWidth() * TILE_SIZE)); j < Math.ceil((initxScreenCoordinate+getSize().getWidth())/(game.getMap().getWidth() * TILE_SIZE)); j++) {
				
				  ChunkCoordinate coord = new ChunkCoordinate(j, i); //replace after u figure out coords
				  
				  if(game.getMap().getChunk(j, i) == null) { //generate a new chunk
			
					  game.getMap().generateChunk(coord);
					  drawChunk(g, game.getMap().getChunk(j, i));
				  
				  }

			}
		
		}
		
	}
	
	public void generateChunksOnScreen() {
		
	}

	public void drawMap(Graphics g, Map map) {

		for (Chunk chunk : map.getAllChunks()) {
			// System.out.println(chunk); // for testing purposes
			drawChunk(g, chunk.getCoordinate());
		}

	}

	public void drawChunk(Graphics g, ChunkCoordinate coord) {

		Chunk chunk = game.getMap().getChunk(coord);

		int x = coord.getChunkX() * game.getMap().getWidth() * drawTileSize;
		int y = coord.getChunkY() * game.getMap().getHeight() * drawTileSize;

		if (chunk == null) {

			for (int row = 0; row < game.getMap().getWidth(); row++) {

				for (int col = 0; col < game.getMap().getHeight(); col++) {

					drawTile(g, null, x + col * 100, y + row * 100);

				}

			}

			return;
		}


		for (int row = 0; row < game.getMap().getWidth(); row++) {

			for (int col = 0; col < game.getMap().getHeight(); col++) {

				drawTile(g, chunk.getTile(row, col), x + col * 100, y + row * 100);

			}

		}

	}

	public ChunkCoordinate getChunkCoordinate() {
		int x = (int) Math.floor((double) xMapPositionOfMouse / (game.getMap().getWidth() * drawTileSize));
		int y = (int) Math.floor((double) yMapPositionOfMouse / (game.getMap().getHeight() * drawTileSize));
		ChunkCoordinate c = new ChunkCoordinate(x, y);
		return c;
	}

	public Tile getTile(ChunkCoordinate coordinate) {

		// find position of each chunk on the screen (chunk location in map * length of
		// chunk)
		int chunkX0 = coordinate.getChunkX() * game.getMap().getWidth() * drawTileSize;
		int chunkY0 = coordinate.getChunkY() * game.getMap().getHeight() * drawTileSize;

		// find the position of the mouse in each chunk (screen coordinate - chunk
		// coordinate)
		int deltaX = xMapPositionOfMouse - chunkX0;
		int deltaY = yMapPositionOfMouse - chunkY0;
//		int deltaX = Math.abs(xMapPositionOfMouse - chunkX0);
//		int deltaY = Math.abs(yMapPositionOfMouse - chunkY0);

		// find the tile position within the chunk
		int tileX = deltaX / drawTileSize;
		int tileY = deltaY / drawTileSize;

		return game.getMap().getChunk(coordinate).getTile(tileY, tileX);
	}

	
	public void getChunksOnScreen(Graphics g) {
		
		//for(int i = game.getyScreenCoordinate(); i < Math.ceil(game.getyScreenCoordinate()*game.getMap().getHeight()*drawTileSize); i+=game.getMap().getHeight()*drawTileSize) {
			//for(int j = game.getxScreenCoordinate(); j < Math.ceil(game.getxScreenCoordinate()*game.getMap().getWidth()*drawTileSize); j+=game.getMap().getWidth()) {
				

		ChunkCoordinate coord = new ChunkCoordinate(2, 2);
		game.getMap().generateChunk(coord);
				
				/*
				 * ChunkCoordinate coord = new ChunkCoordinate(j/game.getMap().getWidth()*drawTileSize,
				 * i/game.getMap().getHeight()*drawTileSize);
				 * 
				 * if(game.getMap().getChunk(coord.getChunkX(), coord.getChunkY()) == null) {
				 * //generate a new chunk
				 * 
				 * game.getMap().generateChunk(coord);
				 * 
				 * }
				 * 
				 * drawChunk(g, game.getMap().getChunk(coord.getChunkX(), coord.getChunkY()));
				 */
			//}
		
		//}
		
		
		
	}



	public void drawTile(Graphics g, Tile tile, int x, int y) {
		
		AffineTransform tileTransform = AffineTransform.getTranslateInstance(x - game.getxScreenCoordinate(),
				y - game.getyScreenCoordinate());

		tileTransform.scale(scale, scale);

		if (tile == null) {

			((Graphics2D) g).drawImage(hidden, tileTransform, null);
			return;

		}

		if (!tile.isRevealed()) {
			((Graphics2D) g).drawImage(hidden, tileTransform, null);
			
			if (tile.isFlagged()) {
				((Graphics2D) g).drawImage(flag, tileTransform, null);
			}
			return;
		}


		switch (tile.getState()) {

			default:
				((Graphics2D) g).drawImage(tile0, tileTransform, null);
				break;

			case 1:
				((Graphics2D) g).drawImage(tile1, tileTransform, null);
				break;

			case 2:
				((Graphics2D) g).drawImage(tile2, tileTransform, null);
				break;

			case 3:
				((Graphics2D) g).drawImage(tile3, tileTransform, null);
				break;

			case 4:
				((Graphics2D) g).drawImage(tile4, tileTransform, null);
				break;

			case 5:
				((Graphics2D) g).drawImage(tile5, tileTransform, null);
				break;

			case 6:
				((Graphics2D) g).drawImage(tile6, tileTransform, null);
				break;

			case 7:
				((Graphics2D) g).drawImage(tile7, tileTransform, null);
				break;

			case 8:
				((Graphics2D) g).drawImage(tile8, tileTransform, null);
				break;
			
			case 9:
				((Graphics2D) g).drawImage(mine, tileTransform, null);
				break;
		}
	}

	private void initializeSprites() {
		hidden = Toolkit.getDefaultToolkit().getImage("resources/tileHidden.png");
		tile0 = Toolkit.getDefaultToolkit().getImage("resources/tile-0.png");

		tile1 = Toolkit.getDefaultToolkit().getImage("resources/tile-1.png");
		tile2 = Toolkit.getDefaultToolkit().getImage("resources/tile-2.png");
		tile3 = Toolkit.getDefaultToolkit().getImage("resources/tile-3.png");
		tile4 = Toolkit.getDefaultToolkit().getImage("resources/tile-4.png");
		tile5 = Toolkit.getDefaultToolkit().getImage("resources/tile-5.png");
		tile6 = Toolkit.getDefaultToolkit().getImage("resources/tile-6.png");
		tile7 = Toolkit.getDefaultToolkit().getImage("resources/tile-7.png");
		tile8 = Toolkit.getDefaultToolkit().getImage("resources/tile-8.png");

		mine = Toolkit.getDefaultToolkit().getImage("resources/tileMine.png");
		flag = Toolkit.getDefaultToolkit().getImage("resources/flag2.png");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		ChunkCoordinate coordinate = getChunkCoordinate();
		Chunk chunk = game.getMap().getChunk(coordinate);
		Tile tile = getTile(coordinate);

		if (e.getButton() == MouseEvent.BUTTON1) { // left click
			tile.sweep();
			helperSweep(game.getMap(), tile);			
		} else if (e.getButton() == MouseEvent.BUTTON3) { // right click
			tile.flag();
		} else if (e.getButton() == MouseEvent.BUTTON2) { // scroll wheel
			// clear a bunch of space
		}
	}
	
	public void helperSweep(Map map, Tile tile) {
		if (!tile.isRevealed()) {
	        tile.sweep();
	    }

	    if (tile.getState() == 0) {
	        for (Tile neighboringTile : tile.getChunk().getNeighboringTiles(tile.getX(), tile.getY())) {
	            if (!neighboringTile.isRevealed()) {
	                helperSweep(map, neighboringTile);
	            }
	        }
	    }
	}



	@Override
	public void mousePressed(MouseEvent e) {

		System.out.println("Mouse is pressed");

		mouseInitX = e.getX();
		mouseInitY = e.getY();

		initxScreenCoordinate = game.getxScreenCoordinate();
		inityScreenCoordinate = game.getyScreenCoordinate();
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

		//if(mouseInitX+TILE_SIZE > currentMouseX) {
			//currentMouseX = mouseInitX;
			//game.set
		//}
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {

		currentMouseX = e.getX();
		currentMouseY = e.getY();

		game.setScreenCoordinate(initxScreenCoordinate + (mouseInitX - e.getX()),
				inityScreenCoordinate + (mouseInitY - e.getY()));

		System.out.println("Mouse is being dragged");

		xMapPositionOfMouse = currentMouseX + (int) game.getxScreenCoordinate();
		yMapPositionOfMouse = currentMouseY + (int) game.getyScreenCoordinate();

	}

	@Override
	public void mouseMoved(MouseEvent e) {

		currentMouseX = e.getX();
		currentMouseY = e.getY();

		xMapPositionOfMouse = e.getX() + game.getxScreenCoordinate();
		yMapPositionOfMouse = e.getY() + game.getyScreenCoordinate();

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
		System.out.println("Mouse Scrolled!");

		scale = 1.0 + e.getPreciseWheelRotation();
		drawTileSize = (int)(TILE_SIZE * scale * scrollSensitivity);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
	}

	
}
