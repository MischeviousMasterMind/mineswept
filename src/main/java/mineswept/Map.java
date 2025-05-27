package mineswept;

import java.util.Collection;
import java.util.HashMap;

public class Map {
	
	private HashMap<ChunkCoordinate, Chunk> chunks;
	private int numMines;
	private int width;
	private int height;
	
	
	public Map(int numMines, int width, int length) {
		
		this.chunks = new HashMap<>();
		this.numMines = numMines;
		this.width = width;
		this.height = length;
 		
		//put a new chunk into the hashmap, and set the coords to (0,0)
		ChunkCoordinate newChunkCoord = new ChunkCoordinate(0, 0);
		Chunk newChunk = new Chunk(width, length, numMines, newChunkCoord);
		
		chunks.put(newChunkCoord, newChunk);

		
	}
	
	public Map(int numMines, int width, int length, HashMap<ChunkCoordinate, Chunk> chunks) {
		
		this.chunks = chunks;
		this.numMines = numMines;
		this.width = width;
		this.height = length;

	}
	
	public ChunkCoordinate[] generateChunks(ChunkCoordinate coord) {
		
		ChunkCoordinate[] generated = new ChunkCoordinate[4];
		
		//check/add the north chunk 
		if(!chunks.containsKey(new ChunkCoordinate(coord.getChunkX(), coord.getChunkY()-1))) {
			
			//add to the list 
			generated[0] = new ChunkCoordinate(coord.getChunkX(), coord.getChunkY()-1);
			
			//create the new chunk
			Chunk n = new Chunk(width, height, numMines, generated[0]); 
			
			//store the new chunk into the hashmap 
			chunks.put(generated[0], new Chunk(width, height, numMines, generated[0]));
			
			//set the adjacent chunk 
			chunks.get(coord).setAdjacentChunk(n, CardinalDirection.NORTH); 
			n.setAdjacentChunk(chunks.get(coord), CardinalDirection.SOUTH);
			
		}
		
		//check/add the south chunk 
		if(!chunks.containsKey(new ChunkCoordinate(coord.getChunkX(), coord.getChunkY()+1))) {
					
			//add to the list 
			generated[1] = new ChunkCoordinate(coord.getChunkX(), coord.getChunkY()+1);
					
			//create the new chunk
			Chunk n = new Chunk(width, height, numMines, generated[1]); 
					
			//store the new chunk into the hashmap 
			chunks.put(generated[1], new Chunk(width, height, numMines, generated[1]));
					
			//set the adjacent chunk 
			chunks.get(coord).setAdjacentChunk(n, CardinalDirection.SOUTH); 
			n.setAdjacentChunk(chunks.get(coord), CardinalDirection.NORTH);
					
		}
	
		//check/add the east chunk 
		if(!chunks.containsKey(new ChunkCoordinate(coord.getChunkX()+1, coord.getChunkY()))) {
					
			//add to the list 
			generated[2] = new ChunkCoordinate(coord.getChunkX()+1, coord.getChunkY());
					
			//create the new chunk
			Chunk n = new Chunk(width, height, numMines, generated[2]); 
					
			//store the new chunk into the hashmap 
			chunks.put(generated[2], new Chunk(width, height, numMines, generated[2]));
					
			//set the adjacent chunk 
			chunks.get(coord).setAdjacentChunk(n, CardinalDirection.EAST); 
			n.setAdjacentChunk(chunks.get(coord), CardinalDirection.WEST);
					
		}
		
		//check/add the west chunk 
		if(!chunks.containsKey(new ChunkCoordinate(coord.getChunkX()-1, coord.getChunkY()))) {
					
			//add to the list 
			generated[3] = new ChunkCoordinate(coord.getChunkX()-1, coord.getChunkY());
					
			//create the new chunk
			Chunk n = new Chunk(width, height, numMines, generated[3]); 
					
			//store the new chunk into the hashmap 
			chunks.put(generated[3], new Chunk(width, height, numMines, generated[3]));
					
			//set the adjacent chunk 
			chunks.get(coord).setAdjacentChunk(n, CardinalDirection.WEST); 
			n.setAdjacentChunk(chunks.get(coord), CardinalDirection.EAST);
					
		} 
		
		return generated; 
		
	}
	
	public Chunk getChunk(int chunkX, int chunkY) {
		
		return chunks.get(new ChunkCoordinate(chunkX, chunkY));
		
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
