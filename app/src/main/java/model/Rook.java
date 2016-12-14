package model;

import java.util.ArrayList;

import controller.Point;

/**
 * Rook class represents the rook,
 * 
 * @author Kevin Bundschuh
 * @author Andrew Yoon
 *
 */
public class Rook extends Piece implements PieceInterface{

	/**
	 * This is the only constructor which creates a rook and sets the position and its color.
	 * 
	 * @author Andrew Yoon
	 * @param col - piece col
	 * @param row - piece row
	 * @param color -  piece color
	 */
	public Rook(int col, int row, char color){
		
		this.col = col;
		this.row = row;
		this.color = color;
		this.type = 'R';
	}
	
   /**
    * 
    * tells whether or not Rook can move to specified location
    *
    * @param oldloc -pieces old location
    * @param newloc - pieces new location
    * @author Kevin Bundschuh
    */
	public boolean validMove(Point oldloc, Point newloc, boolean emptySpace) {
		int x,y;
		
		x = newloc.getCol()-oldloc.getCol();
		y = newloc.getRow()-oldloc.getRow();
		
		if (emptySpace) {
			
			// vertical moves
			if (x == 0 && y != 0) {
				return true;
			}

			// horizontal moves
			if (x != 0 && y == 0) {
				return true;
			}
		}
		
		return false;
	}
	
}
