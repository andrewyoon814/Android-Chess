package model;

//import chess.Game;
import kevinbundschuh.android_chess14.Point;

/**
 * This class represents the Pawn.
 * 
 * @author Kevin Bundscuh
 * @author Andrew Yoon
 *
 */
public class Pawn extends Piece implements PieceInterface{

	/**
	 * Constructor sets the coordinates and color
	 * 
	 * @author Andrew Yoon
	 * @param col - piece col
	 * @param row - piece row
	 * @param color -  piece color
	 */
	public Pawn(int col, int row,char color){
		this.col = col;
		this.row = row;
		this.color = color;
		this.type = 'p';
		boolean firstMove = true;
	}

	/**
	 * Checks if pawn is advancing only 1 spot.
	 * Or if it is going diagonally to capture.
	 *
	 * 
	 * @param oldloc -pieces old location
	 * @param newloc - pieces new location
	 * @author Kevin Bundschuh
	 */
	@Override
	public boolean validMove(Point oldloc, Point newloc, boolean emptySpace) {
		int x,y;
		
		x = newloc.getCol() - oldloc.getCol();
		y = newloc.getRow() - oldloc.getRow();
		

		//black movement
		if(x==0 && this.color == 'b' && y==1){
			return true;
		}
		
		//white movement
		if(x==0 && this.color == 'w' && y==-1){
			return true;
		}
		
		//capturing for black. make empty space true b/c it is handled in calling method
		if((x==1 || x==-1) && this.color=='b' && y==1){
			return true;
		}
		
		//capturing for white
		if((x==1 || x==-1) && this.color=='w' && y==-1){
			return true;
		}

		//need to handle moving double from original spot. maybe a piece move count?
		if((y==2||y==-2) && x ==0){
			return true;
		}

		return false;
	}

	public boolean aivalidMove(Point oldloc, Point newloc, boolean emptySpace) {
		int x,y;

		x = newloc.getCol() - oldloc.getCol();
		y = newloc.getRow() - oldloc.getRow();


		//black movement
		if(x==0 && this.color == 'b' && y==1){
			return true;
		}

		//white movement
		if(x==0 && this.color == 'w' && y==-1){
			return true;
		}

		//capturing for black. make empty space true b/c it is handled in calling method
		if((x==1 || x==-1) && this.color=='b' && y==1){
			return true;
		}

		//capturing for white
		if((x==1 || x==-1) && this.color=='w' && y==-1) {
			return true;
		}

		return false;
	}
	
	

}
