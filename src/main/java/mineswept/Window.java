package mineswept;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Window extends JPanel {

	public final static int WIDTH = 800;
	public final static int HEIGHT = 1000;
	
	private Game game;
	
	private Image hidden, tile0, tile1, tile2, tile3, tile4, tie5, tile6;

	public Window(Game game) {

		this.game = game;

		JFrame f = new JFrame("Mineswept");
		f.setSize(new Dimension(WIDTH, HEIGHT));
		f.setBackground(Color.white);
		f.add(this);
		f.setResizable(false);
		
		hidden = Toolkit.getDefaultToolkit().getImage("resources/tileHidden.png");
		
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
}
