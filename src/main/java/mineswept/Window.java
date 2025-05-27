package mineswept;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JPanel {

	
	private Game game;
	
	private Image hidden, tile0, tile1, tile2, tile3, tile4, tile5, tile6, tile7, tile8, mine, flag;

	public Window(Game game) {

		this.game = game;

		JFrame f = new JFrame("Mineswept");
		f.setSize(new Dimension(800, 1000));
		f.setBackground(Color.white);
		f.add(this);
		f.setResizable(false);
		
		initializeSprites();
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
		
	}

	@Override
	public void paint(Graphics g) {
		
		super.paintComponent(g);
		
//		ChunkCoordinate currentChunkCoordinate = new ChunkCoordinate(
//				(int) (game.getxScreenCoordinate() / game.getMap().getWidth()),
//				(int) (game.getyScreenCoordinate() / game.getMap().getHeight()));
		
		drawChunk(g, game.getMap().getChunk(0, 0), (int) getSize().getWidth()/2, (int) getSize().getHeight()/2);
		
	
		
	}

	public void drawChunk(Graphics g, Chunk chunk, int x, int y) {

		for (int row = 0; row < chunk.getWidth(); row++) {

			for (int col = 0; col < chunk.getHeight(); col++) {

				drawTile(g, chunk.getTile(row, col), x + col * 100, y + row * 100);

			}

		}

	}
	
	

	public void drawTile(Graphics g, Tile tile, int x, int y) {
		
		if(!tile.isRevealed()) {
			((Graphics2D) g).drawImage(hidden, AffineTransform.getTranslateInstance(x, y), null);
			
			if(tile.isFlagged()) {
				((Graphics2D) g).drawImage(flag, AffineTransform.getTranslateInstance(x, y), null);
			}
			
			return;
			
		} 
		
		switch(tile.getState()) {
			
			default:
				((Graphics2D) g).drawImage(tile0, AffineTransform.getTranslateInstance(x, y), null);
				break;
				
			case 1:
				((Graphics2D) g).drawImage(tile1, AffineTransform.getTranslateInstance(x, y), null);
				break;
				
			case 2:
				((Graphics2D) g).drawImage(tile2, AffineTransform.getTranslateInstance(x, y), null);
				break;
			
			case 3:
				((Graphics2D) g).drawImage(tile3, AffineTransform.getTranslateInstance(x, y), null);
				break;
				
			case 4:
				((Graphics2D) g).drawImage(tile4, AffineTransform.getTranslateInstance(x, y), null);
				break;
				
			case 5:
				((Graphics2D) g).drawImage(tile5, AffineTransform.getTranslateInstance(x, y), null);
				break;
			
			case 6:
				((Graphics2D) g).drawImage(tile6, AffineTransform.getTranslateInstance(x, y), null);
				break;
				
			case 7:
				((Graphics2D) g).drawImage(tile7, AffineTransform.getTranslateInstance(x, y), null);
				break;
				
			case 8:
				((Graphics2D) g).drawImage(tile8, AffineTransform.getTranslateInstance(x, y), null);
				break;
			
		}
	}

	private void initializeSprites() {
		hidden  = Toolkit.getDefaultToolkit().getImage("resources/tileHidden.png");
		tile0   = Toolkit.getDefaultToolkit().getImage("resources/tile-0.png");
		
		tile1   = Toolkit.getDefaultToolkit().getImage("resources/tile-1.png");
		tile2   = Toolkit.getDefaultToolkit().getImage("resources/tile-2.png");
		tile3   = Toolkit.getDefaultToolkit().getImage("resources/tile-3.png");
		tile4   = Toolkit.getDefaultToolkit().getImage("resources/tile-4.png");
		tile5   = Toolkit.getDefaultToolkit().getImage("resources/tile-5.png");
		tile6   = Toolkit.getDefaultToolkit().getImage("resources/tile-6.png");
		tile7   = Toolkit.getDefaultToolkit().getImage("resources/tile-7.png");
		tile8   = Toolkit.getDefaultToolkit().getImage("resources/tile-8.png");
		
		mine    = Toolkit.getDefaultToolkit().getImage("resources/tileMine.png");
		flag    = Toolkit.getDefaultToolkit().getImage("resources/flag.png");
	}
}
