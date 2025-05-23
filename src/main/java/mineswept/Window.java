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

	public final static int WIDTH = 800;
	public final static int HEIGHT = 1000;
	
	private Game game;
	
	private Image hidden, tile0, tile1, tile2, tile3, tile4, tile5, tile6, tile7, tile8, mine, flag;

	public Window(Game game) {

		this.game = game;

		JFrame f = new JFrame("Mineswept");
		f.setSize(new Dimension(WIDTH, HEIGHT));
		f.setBackground(Color.white);
		f.add(this);
		f.setResizable(false);
		
		initializeSprites();
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	public void paint(Graphics g) {
		
		super.paintComponent(g);
		
//		ChunkCoordinate currentChunkCoordinate = new ChunkCoordinate(
//				(int) (game.getxScreenCoordinate() / game.getMap().getWidth()),
//				(int) (game.getyScreenCoordinate() / game.getMap().getHeight()));

		AffineTransform tx = AffineTransform.getTranslateInstance(0, 0);
		
		
		((Graphics2D) g).drawImage(hidden, tx, null);
	}

	public void drawTile(Window g, Tile tile, int x, int y) {
		
		
		
	}

	private void initializeSprites() {
		hidden= Toolkit.getDefaultToolkit().getImage("resources/tileHidden.png");
		tile0= Toolkit.getDefaultToolkit().getImage("resources/tile-0.png");
		
		tile1= Toolkit.getDefaultToolkit().getImage("resources/tile-1.png");
		tile2= Toolkit.getDefaultToolkit().getImage("resources/tile-2.png");
		tile3= Toolkit.getDefaultToolkit().getImage("resources/tile-3.png");
		tile4= Toolkit.getDefaultToolkit().getImage("resources/tile-4.png");
		tile5= Toolkit.getDefaultToolkit().getImage("resources/tile-5.png");
		tile6= Toolkit.getDefaultToolkit().getImage("resources/tile-6.png");
		tile7= Toolkit.getDefaultToolkit().getImage("resources/tile-7.png");
		tile8= Toolkit.getDefaultToolkit().getImage("resources/tile-8.png");
		
		mine= Toolkit.getDefaultToolkit().getImage("resources/tileMine.png");
		flag= Toolkit.getDefaultToolkit().getImage("resources/flag.png");
	}
}
