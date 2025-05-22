package mineswept;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Graphics extends JPanel {

	private Game game;

	public Graphics(Game game) {

		JFrame f = new JFrame("Crossy Street");
		f.setSize(new Dimension(WIDTH, HEIGHT));
		f.setBackground(Color.white);
		f.add(this);
		f.setResizable(false);

	}

	public void paint(Graphics g) {

		ChunkCoordinate currentChunkCoordinate = new ChunkCoordinate(
				(int) (game.getxScreenCoordinate() / game.getMap().getWidth()),
				(int) (game.getyScreenCoordinate() / game.getMap().getHeight()));

		
		
	}

	public void drawTile(Graphics g) {
		
		
		
	}
}
