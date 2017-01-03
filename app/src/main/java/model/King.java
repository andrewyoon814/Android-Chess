package model;

import andrewyoon.android_chess14.Point;
//import chess.Game;


/**
 * This class represents the knight.
 * 
 * @author Kevin Bundschuh
 * @author Andrew Yoon
 *
 */
public class King extends Piece implements PieceInterface {

	/**
	 * Constructor creates a king piece with coordinates and color.
	 * 
	 * @author Andrew Yoon
	 * @param col - piece col
	 * @param row - piece row
	 * @param color -  piece color
	 */
	public King(int col, int row, char color){
		
		this.col = col;
		this.row = row;
		this.color = color;
		this.type = 'K';
	}
	
	/**
	 *Tells whether or not king can move to selected spot.
	 * 
	 * 
	 * @param oldloc -pieces old location
	 * @param newloc - pieces new location
	 *  Kevin Bundschuh
	 */
	@Override
	public boolean validMove(Point oldloc, Point newloc, boolean emptySpace) {
		int x,y;
		
		x= newloc.getCol()-oldloc.getCol();
		y = newloc.getRow()-oldloc.getRow();
		
		
		if(x==1 && y==0){
			return true;
		}
		
		if(x==1 && y==1){
			return true;
		}
		
		if(x==1 && y==-1){
			return true;
		}
		
		if(x==0 && y==1){
			return true;
		}
		
		if(x==0 && y==-1){
			return true;
		}
		
		if(x==-1 && y==1){
			return true;
		}
		
		if(x==-1&&y==0){
			return true;
		}
		
		if(x==-1 && y==-1){
			return true;
		}
		
		return false;
	}
	

}
