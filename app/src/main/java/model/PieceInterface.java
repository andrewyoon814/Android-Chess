package model;

import kevinbundschuh.android_chess14.Point;

/**
 * This interface is implemented by all Piece classes.
 * The validMpves method is to check if an entered move is valid.
 * 
 * @author Andrew Yoon
 *
 */
public interface PieceInterface {
	
	
	/**
	 * 
	 * Takes a Point object with the start location, and another Point object with the end location.
	 * Method will see if entered move is valid or not.
	 * 
	 * @param oldloc - old piece location
	 * @param newloc - new piece location
	 * @param emptySpace - used for pawn capturing
	 * @return true if valid
	 */
	public boolean validMove(Point oldloc, Point newloc, boolean emptySpace);
	
}
