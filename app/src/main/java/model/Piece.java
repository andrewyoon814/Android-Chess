package model;

/**
 * 
 * Superclass for all pieces in the chess game.
 * 
 * @author Andrew Yoon
 *
 */

public class Piece {

	public int col;
	public int row;
	public char color;
	public char type;
	public boolean hasMoved=false;
	public boolean firstMove = true;
	
	/**
	 * Gets boolean has Moved
	 * 
	 * @author Kevin Bundschuh
	 * @return - if piece has moved
	 */
	public boolean isHasMoved() {
		return hasMoved;
	}
	
	/**
	 * Sets boolean has Moved
	 * 
	 * @author Kevin Bundschuh
	 * @param hasMoved - sets if piece has moved
	 */
	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}

}
