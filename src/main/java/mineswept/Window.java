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
	
	private Image hidden, flag, tile0, tile1, tile2, tile3, tile4, tie5, tile6;

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
		
		
		
	}

	public void drawTile(Graphics g, Tile tile, int x, int y) {
		
		Image sprite = null;

		if (!tile.isRevealed()) {

			((Graphics2D) g).drawImage(hidden, AffineTransform.getTranslateInstance(x, y), null);

			if (tile.isFlagged()) {

				((Graphics2D) g).drawImage(flag, AffineTransform.getTranslateInstance(x, y), null);

			}

			return;

		}
		
		switch(tile.getState()) {

			default:
				((Graphics2D) g).drawImage(tile0, AffineTransform.getTranslateInstance(x, y), null);
				break;

			case 1:

				break;

		}
	}

	private void initializeSprites() {

		hidden= Toolkit.getDefaultToolkit().getImage("resources/tileHidden.png");

	}
}
