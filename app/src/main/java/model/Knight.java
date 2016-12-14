package model;

import controller.Point;

import java.util.ArrayList;


/**
 * This class represents the knight.
 * 
 * @author Kevin Bundschuh
 * @author Andrew Yoon
 *
 */
public class Knight extends Piece implements PieceInterface{

	/**
	 * Constructor creates a knight piece with coordinates and color.
	 * 
	 * @author Andrew Yoon
	 * @param col - piece col
	 * @param row - piece row
	 * @param color -  piece color
	 */
	public Knight(int col, int row, char color){
		
		this.col = col;
		this.row = row;
		this.color = color;
		this.type = 'N';
	}
	
	/**
	 *Tells whether or not knight can move to selected spot.
	 * 
	 * @author Kevin Bundschuh
	 */
	public boolean validMove(Point oldloc, Point newloc, boolean emptySpace) {
		int x ,y;
		
		
		x = newloc.getCol()-oldloc.getCol();
		y = newloc.getRow()-oldloc.getRow();
		
		if (emptySpace) {

			if (x == 2 && y == 1) {
				return true;
			}
			
			if (x == -2 && y == 1) {
				return true;
			}
			
			if (x == 2 && y == -1) {
				return true;
			}
			
			if (x == -2 && y == -1) {
				return true;
			}
			
			if (x == 1 && y == 2) {
				return true;
			}
			
			if (x == -1 && y == 2) {
				return true;
			}

			if (x == 1 && y == -2) {
				return true;
			}
			
			if (x == -1 && y == -2) {
				return true;
			}
		}
		return false;
	}
}
