public class Cell {
	private boolean isMine;
	private boolean isCleared;
	private boolean isFlagged;
	private int adjacentMines;
	private int probability;
	
	public Cell() {
		isMine = false;
		isCleared = false;
		adjacentMines = 0;
		probability = -1;
	}
	
	public boolean getIsMine() {
		return isMine;
	}
	
	public void setIsMine(boolean isMine) {
		this.isMine = isMine;
	}
	
	public boolean getIsCleared() {
		return isCleared;
	}
	
	public void setIsCleared(boolean cleared) {
		isCleared = cleared;
	}
	
	public boolean getIsFlagged() {
		return isFlagged;
	}
	
	public void setIsFlagged(boolean flagged) {
		isFlagged = flagged;
	}
	
	public void setAdjacentMines(int mines) {
		adjacentMines = mines;
	}
	
	public int getAdjacentMines() {
		return adjacentMines;
	}
	
	public int getProbability() {
		return probability;
	}
	
	public void setProbability(int prob) {
		probability = prob;
	}
	
}
