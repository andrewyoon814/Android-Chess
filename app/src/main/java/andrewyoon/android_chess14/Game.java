package andrewyoon.android_chess14;

import model.King;


import model.Queen;
import model.Knight;
import model.Bishop;
import model.Rook;
import model.Pawn;
import model.Piece;

/**
 * This class is the game class.
 * Contains all utilities to create and print the boards.
 * 
 * 
 * @author Andrew Yoon
 * @author Kevin Bundschuh
 */
public class Game {

	/**
	 * 
	 * chess board array
	 * and char to keep track of whos turn it is
	 */

	Piece[][] board;
	char turn;
	
	
	public Game(){
		
		board = new Piece[8][8];
		createBoard(board);
		this.turn = 'w';
	}

	/**
	 * 
	 * This method recieves the board and will populate it with the correct pieces.
	 * It will call the 3 arg constructor for each piece and give it its coordiantes and color
	 * 
	 * @author Andrew Yoon
	 * @param board the chess board
	 */
	public static void createBoard(Piece[][] board){
		
		//set up black pieces first on ranks 1 and 2
		board[0][0] = new Rook(0,0,'b');
		board[0][1] = new Knight(0,1,'b');
		board[0][2] = new Bishop(0,2,'b');
		board[0][3] = new King(0,3,'b');
		board[0][4] = new Queen(0,4,'b');
		board[0][5] = new Bishop(0,5,'b');
		board[0][6] = new Knight(0,6,'b');
		board[0][7] = new Rook(0,7,'b');
		
		for(int y = 0; y < 8; y++){
			board[1][y] = new Pawn(0,y,'b');
		}
		
		//set up black pieces first on ranks 1 and 2
		board[7][0] = new Rook(7,0,'w');
		board[7][1] = new Knight(7,1,'w');
		board[7][2] = new Bishop(7,2,'w');
		board[7][3] = new King(7,3,'w');
		board[7][4] = new Queen(7,4,'w');
		board[7][5] = new Bishop(7,5,'w');
		board[7][6] = new Knight(7,6,'w');
		board[7][7] = new Rook(7,7,'w');
		
		for(int y = 0; y < 8; y++){
			board[6][y] = new Pawn(6,y,'w');
		}
		
	}
	
	/**
	 * 
	 * This method prints the board.
	 * If a space contains a piece, print it out.
	 * If a space is empty then print out a space or ## depending on what position it is.
	 * 
	 * @author Andrew Yoon
	 * @param board - game board to be played on
	 */
	public void PrintBoard(Piece[][] board){
		
		for(int x = 0; x < 8 ; x++){
			for(int y = 0; y < 8; y++){
				
				//on null spots these if statements decide whether or not to print spaces or hashes
				if(board[x][y] == null){
					
					//to have alternating checks based on row
					if( x%2 == 0){
						if(y%2 == 0){
							System.out.print("    ");
						}else{
							System.out.print(" ## ");;
						}
						
					}else{
						if(y%2 == 0){
							System.out.print(" ## ");
						}else{
							System.out.print("    ");;
						}
					}
					
				}else{
					System.out.print(" " + board[x][y].color + "" + board[x][y].type + " ");
				}
			}
			
			System.out.println(" " + (8-x));
		}
		
		System.out.println("  a   b   c   d   e   f   g   h");
	}
	
	/**
	 * checks to see if moving piece's path is clear to move to the desired spot
	 * 
	 * @param oldPt - starting point
	 * @param newPt - desired move
	 * @return true if clear
	 * @author kevin bundschuh
	 */
	public boolean clearPath(Point oldPt, Point newPt){
		//Piece piece = board[oldPt.getRow()][newPt.getCol()];
		
		//knights and kings do not need a clear path to move regularly
		if(board[oldPt.getRow()][oldPt.getCol()].type=='K'||board[oldPt.getRow()][oldPt.getCol()].type=='N'){
			return true;
		}
		

		int dx = newPt.getCol()-oldPt.getCol();
		int dy = newPt.getRow()-oldPt.getRow();
		
		//movement is vertical
		if(dx==0){
			//path = dy;
			if (dy > 0) {
				for (int i = oldPt.getRow()+1; i < newPt.getRow(); i++) {
					if (board[i][oldPt.getCol()] != null) {
						return false;
					}
				}
			}else if(dy<0){
				//path =-dy;
				for (int i = oldPt.getRow()-1; i > newPt.getRow(); i--) {
					if (board[i][oldPt.getCol()] != null) {
						return false;
					}
				}
			}
			//movement is horizontal
		}else if(dy==0){
			//path = dx;
			if (dx > 0) {
				for (int i = oldPt.getCol()+1; i < newPt.getCol(); i++) {
					if (board[oldPt.getRow()][i] != null) {
						return false;
					}
				}
			}else if(dx<0){
				for (int i = oldPt.getCol()-1; i > newPt.getCol(); i--){
					if (board[oldPt.getRow()][i] != null) {
						return false ;
					} 
				}
			}
			//movement is diagonal
		}else{
			
			if(dx>0&&dy>0){
				int i =oldPt.getRow()+1;
				int k = oldPt.getCol()+1;
				while( i<newPt.getRow()&& k<newPt.getCol()){
					if(board[i][k]!=null){
						return false;
					}
					i++;k++;
				}
			}else if(dx>0 &&dy<0){
				int i =oldPt.getRow()-1;
				int k = oldPt.getCol()+1;
				
				while( i>newPt.getRow()&& k<newPt.getCol()){
					if(board[i][k]!=null){
						return false;
					}
					i--;k++;
				}
			}else if(dx<0&&dy>0){
				int i =oldPt.getRow()+1;
				int k = oldPt.getCol()-1;
				
				while( i<newPt.getRow()&& k>newPt.getCol()){
					if(board[i][k]!=null){
						return false;
					}
					i++;k--;
				}
			}else if(dx<0&&dy<0){
				int i =oldPt.getRow()-1;
				int k = oldPt.getCol()-1;
				
				while( i>newPt.getRow()&& k>newPt.getCol()){
					if(board[i][k]!=null){
						return false;
					}
					i--;k--;
				}
			}
		}
		return true;
	}


}
