package model;

import controller.Point;

import java.util.ArrayList;

/**
 * This class represents the bishop.
 * 
 * @author Kevin Bundschuh
 * @author Andrew Yoon
 *
 */
public class Bishop extends Piece implements PieceInterface{

	/**
	 * Constructor creates a bishop piece with coordinates and color.
	 * 
	 * @author Andrew Yoon
	 * @param col - piece col
	 * @param row - piece row
	 * @param color -  piece color
	 */
	public Bishop(int col, int row, char color){
		
		this.col = col;
		this.row = row;
		this.color = color;
		this.type = 'B';
	}
	
	/**
	 *Tells whether or not bishop can move to selected spot.
	 * 
	 * @param oldloc -pieces old location
	 * @param newloc - pieces new location
	 * @author Kevin Bundschuh
	 */
	public boolean validMove(Point oldloc, Point newloc, boolean emptySpace) {
		int x,y;
		
		x = Math.abs(newloc.getCol()-oldloc.getCol());
		y = Math.abs(newloc.getRow()-oldloc.getRow());
		
		if (x == y) {
			return true;
		}
		return false;
	}
}
