import java.util.*;

public class MinesweeperGrid {
	private int size;
	private int mines;
	private boolean areMinesGenerated;
	private boolean isGameOver;
	private Cell[][] grid;
	
	public MinesweeperGrid(int size, int mines) {
		this.size = size;
		this.mines = mines;
		areMinesGenerated = false;
		isGameOver = false;
		grid = new Cell[size][size];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				grid[i][j] = new Cell();
			}
		}
	}
	
	public Cell[][] getGrid() {
		return grid;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getMines() {
		return mines;
	}
	
	public boolean getAreMinesGenerated() {
		return areMinesGenerated;
	}
	
	public boolean getIsGameOver() {
		return isGameOver;
	}
	
	public void generateProbabilities() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				grid[i][j].setProbability(-1);
			}
		}
		
		ArrayList<Integer> edgeCells = new ArrayList<Integer>();
		ArrayList<Integer> edgeCleareds = new ArrayList<Integer>();
		
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				if (grid[y][x].getIsCleared()) {
					if (grid[y][x].getAdjacentMines() > 0 && grid[y][x].getIsCleared()) {
						edgeCleareds.add(y*size + x);
					}
					
					// top left
					if (y - 1 >= 0 && x - 1 >= 0 && !grid[y-1][x-1].getIsCleared() && !edgeCells.contains((y-1) * size + (x-1))) {
						edgeCells.add((y-1) * size + (x-1));
					}
					
					// top middle
					if (y - 1 >= 0 && !grid[y-1][x].getIsCleared() && !edgeCells.contains((y-1) * size + (x))) {
						edgeCells.add((y-1) * size + (x));
					}
					
					// top right
					if (y - 1 >= 0 && x + 1 < size && !grid[y-1][x+1].getIsCleared() && !edgeCells.contains((y-1) * size + (x+1))) {
						edgeCells.add((y-1) * size + (x+1));
					}
					
					// middle left
					if (x - 1 >= 0 && !grid[y][x-1].getIsCleared() && !edgeCells.contains((y) * size + (x-1))) {
						edgeCells.add((y) * size + (x-1));
					}
						
					// middle right
					if (x + 1 < size && !grid[y][x+1].getIsCleared() && !edgeCells.contains((y) * size + (x+1))) {
						edgeCells.add((y) * size + (x+1));
					}
						
					// bottom right
					if (y + 1 < size && x - 1 >= 0 && !grid[y+1][x-1].getIsCleared() && !edgeCells.contains((y+1) * size + (x-1))) {
						edgeCells.add((y+1) * size + (x-1));
					}
					
					// bottom middle
					if (y + 1 < size && !grid[y+1][x].getIsCleared() && !edgeCells.contains((y+1) * size + (x))) {
						edgeCells.add((y+1) * size + (x));
					}
					
					// bottom right
					if (y + 1 < size && x + 1 < size && !grid[y+1][x+1].getIsCleared() && !edgeCells.contains((y+1) * size + (x+1))) {
						edgeCells.add((y+1) * size + (x+1));
					}
					
				}
			}
		}
		
		for (int i = 0; i < edgeCells.size(); i++) {
			grid[edgeCells.get(i)/size][edgeCells.get(i)%size].setProbability(100);
		}
		
		for (int i = 0; i < edgeCleareds.size(); i++) {
			grid[edgeCleareds.get(i)/size][edgeCleareds.get(i)%size].setProbability(0);
		}
		
	}
	
	public void clear(int y, int x) {
		if (grid[y][x].getIsFlagged()) return;
		
		if (grid[y][x].getIsMine()) {
			isGameOver = true;
			return;
		}
		
		grid[y][x].setIsCleared(true);
		
		if (!grid[y][x].getIsMine() && grid[y][x].getAdjacentMines() == 0) {
			// top left
			if (y - 1 >= 0 && x - 1 >= 0 && !grid[y-1][x-1].getIsCleared()) {
				clear(y-1, x-1);
			}
			
			// top middle
			if (y - 1 >= 0 && !grid[y-1][x].getIsCleared()) {
				clear(y-1, x);
			}
			
			// top right
			if (y - 1 >= 0 && x + 1 < size && !grid[y-1][x+1].getIsCleared()) {
				clear(y-1, x+1);
			}
			
			// middle left
			if (x - 1 >= 0 && !grid[y][x-1].getIsCleared()) {
				clear(y, x-1);
			}
				
			// middle right
			if (x + 1 < size && !grid[y][x+1].getIsCleared()) {
				clear(y, x+1);
			}
				
			// bottom right
			if (y + 1 < size && x - 1 >= 0 && !grid[y+1][x-1].getIsCleared()) {
				clear(y+1, x-1);
			}
			
			// bottom middle
			if (y + 1 < size && !grid[y+1][x].getIsCleared()) {
				clear(y+1, x);
			}
			
			// bottom right
			if (y + 1 < size && x + 1 < size && !grid[y+1][x+1].getIsCleared()) {
				clear(y+1, x+1);
			}
			
		}
		
		generateProbabilities();
	}
	
	public void flag(int y, int x) {
		if (!grid[y][x].getIsCleared()) {
			grid[y][x].setIsFlagged(!grid[y][x].getIsFlagged());
		}
	}
	
	public void chord(int y, int x) {
		if (!grid[y][x].getIsCleared()) return;
		
		int flagsAround = 0;
		
		// top left
		if (y - 1 >= 0 && x - 1 >= 0 && grid[y-1][x-1].getIsFlagged()) {
			flagsAround++;
		}
		
		// top middle
		if (y - 1 >= 0 && grid[y-1][x].getIsFlagged()) {
			flagsAround++;
		}
		
		// top right
		if (y - 1 >= 0 && x + 1 < size && grid[y-1][x+1].getIsFlagged()) {
			flagsAround++;
		}
		
		// middle left
		if (x - 1 >= 0 && grid[y][x-1].getIsFlagged()) {
			flagsAround++;
		}
			
		// middle right
		if (x + 1 < size && grid[y][x+1].getIsFlagged()) {
			flagsAround++;
		}
			
		// bottom right
		if (y + 1 < size && x - 1 >= 0 && grid[y+1][x-1].getIsFlagged()) {
			flagsAround++;
		}
		
		// bottom middle
		if (y + 1 < size && grid[y+1][x].getIsFlagged()) {
			flagsAround++;
		}
		
		// bottom right
		if (y + 1 < size && x + 1 < size && grid[y+1][x+1].getIsFlagged()) {
			flagsAround++;
		}
		
		if (flagsAround != grid[y][x].getAdjacentMines()) return;
		
		// top left
		if (y - 1 >= 0 && x - 1 >= 0) {
			clear(y-1, x-1);
		}
		
		// top middle
		if (y - 1 >= 0) {
			clear(y-1, x);
		}
		
		// top right
		if (y - 1 >= 0 && x + 1 < size) {
			clear(y-1, x+1);
		}
		
		// middle left
		if (x - 1 >= 0) {
			clear(y, x-1);
		}
			
		// middle right
		if (x + 1 < size) {
			clear(y, x+1);
		}
			
		// bottom right
		if (y + 1 < size && x - 1 >= 0) {
			clear(y+1, x-1);
		}
		
		// bottom middle
		if (y + 1 < size) {
			clear(y+1, x);
		}
		
		// bottom right
		if (y + 1 < size && x + 1 < size) {
			clear(y+1, x+1);
		}
		
	}
	
	public void generateMines(int y, int x) {
		if (grid[y][x].getIsFlagged()) return;
		
		ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < (size*size); i++) {
        	if (i == y * size + x ||
    			// top left
    			(y - 1 >= 0 && x - 1 >= 0 && i == (y-1)*size + (x-1)) ||
    			
    			// top middle
    			(y - 1 >= 0 && i == (y-1)*size + x) ||
    			
    			// top right
    			(y - 1 >= 0 && x + 1 < size && i == (y-1)*size + (x+1)) ||
    			
    			// middle left
    			(x - 1 >= 0 && i == y*size + (x-1)) ||
    				
    			// middle right
    			(x + 1 < size && i == y*size + (x+1)) ||
    				
    			// bottom right
    			(y + 1 < size && x - 1 >= 0 && i == (y+1)*size + (x-1)) ||
    			
    			// bottom middle
    			(y + 1 < size && i == (y+1)*size + x) ||
    			
    			// bottom right
    			(y + 1 < size && x + 1 < size && i == (y+1)*size + (x+1))) continue;
        	list.add(i);
        }
        
        Collections.shuffle(list);
        ArrayList<Integer> mineList = new ArrayList<Integer>();
        for (int i = 0; i < mines; i++) {
        	grid[list.get(i)/size][list.get(i)%size].setIsMine(true);
        	mineList.add(list.get(i));
        }
        
        
        for (int i = 0; i < (size*size); i++) {
        	
        	if (mineList.contains(i)) continue;
        	
        	int numMines = 0;
        	
        	// top left
        	if ((i/size - 1 >= 0 && i%size - 1 >= 0) && grid[i/size - 1][i%size - 1].getIsMine()) {
        		numMines++;
        	}
        	
        	// top middle
        	if (i/size - 1 >= 0 && grid[i/size - 1][i%size].getIsMine()) {
        		numMines++;
        	}
        	
        	// top right
        	if ((i/size - 1 >= 0 && i%size + 1 < size) && grid[i/size - 1][i%size + 1].getIsMine()) {
        		numMines++;
        	}
        	
        	// middle left
        	if (i%size - 1 >= 0 && grid[i/size][i%size - 1].getIsMine()) {
        		numMines++;
        	}
        	
        	// middle right
        	if (i%size + 1 < size && grid[i/size][i%size + 1].getIsMine()) {
        		numMines++;
        	}
        	
        	// bottom left
        	if ((i/size + 1 < size && i%size - 1 >= 0) && grid[i/size + 1][i%size - 1].getIsMine()) {
        		numMines++;
        	}
        	
        	// bottom middle
        	if (i/size + 1 < size && grid[i/size + 1][i%size].getIsMine()) {
        		numMines++;
        	}
        	
        	// bottom right
        	if ((i/size + 1 < size && i%size + 1 < size) && grid[i/size + 1][i%size + 1].getIsMine()) {
        		numMines++;
        	}
        	
        	grid[i/size][i%size].setAdjacentMines(numMines);
        }
        
        areMinesGenerated = true;
	}
}
