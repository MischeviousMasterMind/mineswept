package mineswept;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Map {
	
	private final HashMap<ChunkCoordinate, Chunk> chunks;
	private final int numMines;
	private final int width;
	private final int height;
	
	
	public Map(int numMines, int width, int height) {
		
		this.chunks = new HashMap<>();
		this.numMines = numMines;
		this.width = width;
		this.height = height;
 		
		//put a new chunk into the hashmap, and set the coords to (0,0)
		ChunkCoordinate newChunkCoord = new ChunkCoordinate(0, 0);
		Chunk newChunk = new Chunk(width, height, numMines, newChunkCoord);
		
		chunks.put(newChunkCoord, newChunk);
	}
	
	public Map(int numMines, int width, int length, HashMap<ChunkCoordinate, Chunk> chunks) {
		
		this.chunks = chunks;
		this.numMines = numMines;
		this.width = width;
		this.height = length;

	}


	public void generateChunk(ChunkCoordinate coord) {
		
		chunks.put(coord, new Chunk(width, height, numMines, coord));

	}
  
	public ChunkCoordinate[] generateChunks(ChunkCoordinate coord) {
		
		ChunkCoordinate[] generated = new ChunkCoordinate[8];
		
		//check/add the north chunk 
		if(!chunks.containsKey(new ChunkCoordinate(coord.getChunkX(), coord.getChunkY()-1))) {
			 
			//add to the list 
			generated[0] = new ChunkCoordinate(coord.getChunkX(), coord.getChunkY()-1);
			
			//create the new chunk
			getChunk(generated[0]);
			
			//store the new chunk into the hashmap 
			chunks.put(generated[0], getChunk(generated[0]));
			
			//set the adjacent chunk 
			chunks.get(coord).setAdjacentChunk(getChunk(generated[0]), CardinalDirection.NORTH); 
			getChunk(generated[0]).setAdjacentChunk(chunks.get(coord), CardinalDirection.SOUTH);
			
		}
		
		//check/add the south chunk 
		if(!chunks.containsKey(new ChunkCoordinate(coord.getChunkX(), coord.getChunkY()+1))) {
					
			//add to the list 
			generated[1] = new ChunkCoordinate(coord.getChunkX(), coord.getChunkY()+1);
					
			//create the new chunk
			generateChunk(generated[1]);
					
			//store the new chunk into the hashmap 
			chunks.put(generated[1], getChunk(generated[1]));
					
			//set the adjacent chunk 
			chunks.get(coord).setAdjacentChunk(getChunk(generated[1]), CardinalDirection.SOUTH); 
			getChunk(generated[1]).setAdjacentChunk(chunks.get(coord), CardinalDirection.NORTH);
					
		}
	
		//check/add the east chunk 
		if(!chunks.containsKey(new ChunkCoordinate(coord.getChunkX()+1, coord.getChunkY()))) {
					
			//add to the list 
			generated[2] = new ChunkCoordinate(coord.getChunkX()+1, coord.getChunkY());
					
			//create the new chunk
			generateChunk(generated[2]);
					
			//store the new chunk into the hashmap 
			chunks.put(generated[2], getChunk(generated[2]));
					
			//set the adjacent chunk 
			chunks.get(coord).setAdjacentChunk(getChunk(generated[2]), CardinalDirection.EAST); 
			getChunk(generated[2]).setAdjacentChunk(chunks.get(coord), CardinalDirection.WEST);
					
		}
		
		//check/add the west chunk 
		if(!chunks.containsKey(new ChunkCoordinate(coord.getChunkX()-1, coord.getChunkY()))) {
					
			//add to the list 
			generated[3] = new ChunkCoordinate(coord.getChunkX()-1, coord.getChunkY());
					
			//create the new chunk
			generateChunk(generated[3]);
					
			//store the new chunk into the hashmap 
			chunks.put(generated[3], getChunk(generated[3]));
					
			//set the adjacent chunk 
			chunks.get(coord).setAdjacentChunk(getChunk(generated[3]), CardinalDirection.WEST); 
			getChunk(generated[3]).setAdjacentChunk(chunks.get(coord), CardinalDirection.EAST);
					
		} 
		
		//northeast chunk 
//		if(!chunks.containsKey(new ChunkCoordinate(coord.getChunkX()-1, coord.getChunkY()+1))) {
//			
//			//add to the list 
//			generated[4] = new ChunkCoordinate(coord.getChunkX()-1, coord.getChunkY()+1);
//					
//			//create the new chunk
//			generateChunk(generated[4]);
//					
//			//store the new chunk into the hashmap 
//			chunks.put(generated[4], getChunk(generated[4]));
//					
//			//set the adjacent chunk 
//			chunks.get(coord).setAdjacentChunk(getChunk(generated[4]), CardinalDirection.WEST); 
//			getChunk(generated[4]).setAdjacentChunk(chunks.get(coord), CardinalDirection.EAST);
//			getChunk(generated[4]).setAdjacentChunk(chunks.get(coord), CardinalDirection.EAST);
//					
//		} 
		
		return generated; 
		
	}

	public ArrayList<Chunk> getNeighboringChunks(ChunkCoordinate coord) {

		ArrayList<Chunk> neighboringChunks = new ArrayList<>();

		for (int i = -1; i <=  1; i++) {

			for (int ii = -1; ii <= 1; ii++) {

				int x = coord.getChunkX();
				int y = coord.getChunkY();

				if ((i == x && ii == y) || (x + i < 0) || (x + i >= width) || (y + ii < 0) || (y + ii >= height)) {

					continue;

				}

				Chunk neighborChunk = chunks.get(new ChunkCoordinate(x + i, y + ii));

				if (neighborChunk != null) {

					neighboringChunks.add(neighborChunk);

				}

			}

		}

		return neighboringChunks;
	}

	// TODO: Fix this method
	public void updateChunkBorder(ChunkCoordinate coord) {

		

	}
	
	public Chunk getChunk(int chunkX, int chunkY) {
		
		return chunks.get(new ChunkCoordinate(chunkX, chunkY));
		
	}
	
	public Chunk getChunk(ChunkCoordinate coordinate) {
		
		return chunks.get(coordinate);
		
	}
	
	public Collection<Chunk> getAllChunks() {
		
		return chunks.values();
		
	}

	public int getNumMines() {
		return numMines;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
}
