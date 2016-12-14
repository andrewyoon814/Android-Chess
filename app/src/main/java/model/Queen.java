package model;

import controller.Point;
import model.Rook;

import java.util.ArrayList;

/**
 * This class represents the queen. 
 * 
 * @author Kevin Bundschuh
 * @author Andrew Yoon
 *
 */
public class Queen extends Piece implements PieceInterface{

	/**
	 * Creates Queen and sets coordiantes and color.
	 * 
	 * @author Andrew yoon
	 * @param col - piece col
	 * @param row - piece row
	 * @param color -  piece color
	 */
	public Queen(int col, int row, char color){
		
		this.col = col;
		this.row = row;
		this.color = color;
		this.type = 'Q';
	}
	
	

	/**
	 * Calls the rook and bishop's valid moves bc they are the same things.
	 * 
	 * 
	 * @param oldloc -pieces old location
	 * @param newloc - pieces new location
	 * @author Kevin Bundschuh
	 */
	@Override
	public boolean validMove(Point oldloc, Point newloc, boolean emptySpace) {

		
		Rook rook = new Rook(oldloc.getCol(), oldloc.getRow(), this.color);
		Bishop bishop = new Bishop(oldloc.getCol(), oldloc.getRow(), this.color);
				
		if(rook.validMove(oldloc, newloc, emptySpace)){
			return true;
		}
		
		if(bishop.validMove(oldloc, newloc, emptySpace)){
			return true;
		}
		
		return false;
	}
}
