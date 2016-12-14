package controller;

import model.Piece;
import model.Queen;
import model.King;
import model.Bishop;
import model.Knight;
import model.Rook;
import model.Pawn;

import java.io.IOException;
import java.util.Scanner;

/**
 * 
 * 
 * @author Andrew Yoon and Kevin Bundschuh
 *
 *
 */
public class Chess {

	static boolean drawProposal=false;
	public static Game game;
	public static boolean changeTurn = true;
	public static String promotion=null;
	public static boolean castle = false;
	public static boolean checkMate = false;
	/**
	 * 
	 * The main method calls utility methods to create board.
	 * Then it loops until a signal is received to stop (resign or losing of game)
	 * It uses other utilities to check if a stalemate, check or checkmate has occured.
	 * @param args
	 */
	public static void main(String[] args) {
		game = new Game();
		game.turn = 'w';
		
		checkMate = false;
		
		//create board and print
		game.PrintBoard(game.board);
		System.out.println();
		
		//set up scanner for input
		Scanner sc = new Scanner(System.in);
		String direction = "";
		
		
		//infinite loop to keep the game going
		while(true){
			boolean check = inCheck();
			
			final Point kingLoc = new Point();
			for (int col = 0; col < 8; col++){
				for (int row = 0; row < 8; row++){
					if (game.board[row][col] != null) {
						if (game.board[row][col].color==game.turn&&game.board[row][col].type=='K') {
							kingLoc.setRow(row);
							kingLoc.setCol(col);
							break;
						}
					}
				}
			}
			
			/*if(inCheck()){
				if(!canMove(game.board[kingLoc.getRow()][kingLoc.getCol()])){
					checkMate = true;
				}
			}
			
			if(checkMate){
				if(game.turn=='w'){
					System.out.println("White king in check mate. Black wins");
					System.exit(1);
				}else{
					System.out.println("Black king in check mate. White wins");
					System.exit(1);
				}
			}*/
			
			if(game.turn =='w' && check){
				System.out.println("Check.");
			}else if(game.turn =='b'&&check){
				System.out.println("Check.");
			}
			
			//determines whose turn it is and prompts for move input
			if(game.turn == 'w'){
				System.out.print("White's move : ");
			}else{
				System.out.print("Black's move : ");
			}
			changeTurn = true;
			//store direction and parse
			direction = sc.nextLine();
			
			handleInput(direction, game.turn);
			/*check = inCheck();
			if(check){
				System.out.println(kingLoc);
				System.out.println(game.board[kingLoc.getRow()][kingLoc.getCol()]);
				if(!canMove(game.board[kingLoc.getRow()][kingLoc.getCol()])){
					checkMate = true;
				}
			}*/
			if(!changeTurn)
				continue;
			
			/*if(checkMate){
				if(game.turn=='w'){
					System.out.println("White king in check mate. Black wins");
					System.exit(1);
				}else{
					System.out.println("Black king in check mate. White wins");
					System.exit(1);
				}
			}*/
			
			
			
			System.out.println();
			game.PrintBoard(game.board);
			System.out.println();
			
			

			//this if statement is done at the very end to switch the turns
			if(game.turn == 'w' && changeTurn){
				game.turn = 'b';
			}else if(game.turn == 'b' && changeTurn){
				game.turn = 'w';
			}
		}
		
		
	}
	


	/**
	 * converts input string to a new bound-appropriate Point coordinate
	 * 
	 * @param str - input string
	 * @return Point - input string converted to Point
	 * @author kevin bundschuh
	 */
	public static Point strToCoord(String str) {
		
		//input for move is improperly formatted
		if (str.length() != 2) {
			System.out.println("Illegal move, try again");
			changeTurn = false;
			return null;
		}

		Point temp = new Point();
		temp.setRow(str.charAt(1));		
		
		switch (str.charAt(0)) {
		case 'a':
			temp.setCol(0);
			break;
		case 'b':
			temp.setCol(1);
			break;
		case 'c':
			temp.setCol(2);
			break;
		case 'd':
			temp.setCol(3);
			break;
		case 'e':
			temp.setCol(4);
			break;
		case 'f':
			temp.setCol(5);
			break;
		case 'g':
			temp.setCol(6);
			break;
		case 'h':
			temp.setCol(7);
			break;
		default:
			changeTurn = false;
			return null;
		}
		switch (str.charAt(1)) {
		case '1':
			temp.setRow(7);
			break;
		case '2':
			temp.setRow(6);
			break;
		case '3':
			temp.setRow(5);
			break;
		case '4':
			temp.setRow(4);
			break;
		case '5':
			temp.setRow(3);
			break;
		case '6':
			temp.setRow(2);
			break;
		case '7':
			temp.setRow(1);
			break;
		case '8':
			temp.setRow(0);
			break;
		default:
			changeTurn = false;
			return null;
			
		}
		return temp;
	}
	
	
	/**
	 * accepts user's input and acts accordingly based on what is input and who's turn it is
	 * 
	 * @param input - input string
	 * @param turn - current player's turn
	 * @author kevin bundschuh
	 */
	public static void handleInput(String input, char turn){
		String in = input.toLowerCase();
		String [] in2 = in.split("\\s+");
		
		if(in2.length==1){
			String temp = in2[0];
			if(temp.equalsIgnoreCase("resign")){
				if(turn=='w'){
					System.out.println("White has resigned");
					System.out.println("Black wins.");
					System.exit(1);
				} else {
					System.out.println("Black has resigned");
					System.out.println("White wins.");
					System.exit(1);
				}
			} else if(temp.equalsIgnoreCase("draw")){
				if (drawProposal == true) {
					System.out.println("Draw.");
					System.exit(1);
				}else{
					System.out.println("Illegal move, try again");
					changeTurn = false;
					return;
				}
					
			}else{
				System.out.println(" invalid, try again");
				changeTurn = false;
				return;
			}
		}else
		if(in2.length==2){
			//checks if first element of each move string is a letter
			if(!(Character.isLetter(in2[0].charAt(0)))&&!(Character.isLetter(in2[1].charAt(0)))){
				System.out.println("Illegal move, try again");
				changeTurn = false;
				return;
			}
			if(!(Character.isDigit(in2[0].charAt(1)))&&!(Character.isDigit(in2[1].charAt(1)))){
				System.out.println("Illegal move, try again");
				changeTurn = false;
				return;
			}
			if(strToCoord(in2[0])==null||strToCoord(in2[1])==null){
				System.out.println("Illegal move, try again");
				changeTurn = false;
				return;
			}
			Point oldPt = new Point(strToCoord(in2[0]));
			Point newPt = new Point(strToCoord(in2[1]));
			
		
			if(game.board[oldPt.getRow()][oldPt.getCol()]==null){
				System.out.println("Illegal move, try again");
				changeTurn = false;
				return;
			}
			if(newPt ==null || oldPt ==null){
				System.out.println("Illegal move, try again");
				changeTurn = false;
				return;
			}
			if(validMoveInput(oldPt,newPt)){
				makeMove(oldPt,newPt);
			}
		} else if(in2.length==3){
			if(in2[2].equalsIgnoreCase("draw?")){
				drawProposal=true;
				//draw proposal subroutine
				changeTurn = true;
				return;
			} else
			
			if(in2[2].equalsIgnoreCase("q")||in2[2].equalsIgnoreCase("r")||in2[2].equalsIgnoreCase("b")||in2[2].equalsIgnoreCase("n")){
				promotion = in2[2];
				//checks if first element of each move string is a letter
				if(!(Character.isLetter(in2[0].charAt(0)))&&!(Character.isLetter(in2[1].charAt(0)))){
					System.out.println("Illegal move, try again");
					changeTurn = false;
					return;
				}
				if(!(Character.isDigit(in2[0].charAt(1)))&&!(Character.isDigit(in2[1].charAt(1)))){
					System.out.println("Illegal move, try again");
					changeTurn = false;
					return;
				}
				if(strToCoord(in2[0])==null||strToCoord(in2[1])==null){
					System.out.println("Illegal move, try again");
					changeTurn = false;
					return;
				}
				Point oldPt = new Point(strToCoord(in2[0]));
				Point newPt = new Point(strToCoord(in2[1]));
				
			
				if(game.board[oldPt.getRow()][oldPt.getCol()]==null){
					System.out.println("Illegal move, try again");
					changeTurn = false;
					return;
				}
				if(newPt ==null || oldPt ==null){
					System.out.println("Illegal move, try again");
					changeTurn = false;
					return;
				}
				if(validMoveInput(oldPt,newPt)){
					makeMove(oldPt,newPt);
				}
			}else{
				System.out.println("Illegal move, try again");
				changeTurn = false;
				return;
			}
			
		}else{
			System.out.println("Illegal move, try again");
			changeTurn = false;
			return;
		}
		
	}
	
	/**
	 * checks to make sure moves are within appropriate bounds
	 * 
	 * @param point1 - first part of user input
	 * @param point2 - second part of user input
	 * @return true if valid
	 * @author kevin bundschuh
	 */
	public static boolean validMoveInput(Point point1, Point point2){
		if(point1.getCol()<=7&&point1.getCol()>=0&&point1.getRow()>=0&&point1.getRow()<=7
				&&point2.getCol()<=7&&point2.getCol()>=0&&point2.getRow()>=0&&point2.getRow()<=7){
			return true;
		}
		return false;
	}
	
	/**
	 * performs piece movement based on selected piece at oldPt
	 * 
	 * @param oldPt - pieces start point
	 * @param newPt - pieces end point
	 * @author kevin bundschuh
	 */
	
	public static void makeMove(Point oldPt, Point newPt){
		boolean empty=false;
		boolean clear = false;
		boolean valid = false;
		//boolean castle = false;
		//boolean enPassant = false;
		//boolean firstMove = false;
		char color,pieceType;
		
		
		color = game.board[oldPt.getRow()][oldPt.getCol()].color;
		pieceType = game.board[oldPt.getRow()][oldPt.getCol()].type;
		
		Piece selected=game.board[oldPt.getRow()][oldPt.getCol()];
		
		if(game.turn != selected.color){
			System.out.println("Illegal move, try again");
			changeTurn = false;
			return;
		}
		
		//treat capturing a piece of the other color as if it is an empty space it can be moved into
		//this lets pawns capture forward, need to fix this.
		if (game.board[newPt.getRow()][newPt.getCol()] == null
				|| game.turn != game.board[newPt.getRow()][newPt.getCol()].color) {
				empty = true;
		} else {
			System.out.println("Illegal move, try again");
			changeTurn = false;
			return;
		}
	//	if(selected.validMove)
		
		if(game.clearPath(oldPt, newPt)){
			clear = true;
		} else{
			System.out.println("Illegal move, try again.");
			changeTurn = false;
			return;
		}
		
		
		switch(pieceType){
		case 'K':
			if(!selected.hasMoved)
				selected = new King(oldPt.getRow(),oldPt.getCol(), color);
			valid = ((King) selected).validMove(oldPt,newPt,empty);
			if(!valid){
				int dx=newPt.getCol()-oldPt.getCol();
				if(dx==2){
					if (selected.color=='w'){
						checkCastle(oldPt, newPt, selected, game.board[7][7]);
					}
						
					else{
						checkCastle(oldPt,newPt,selected,game.board[0][7]);
					}
				}else if(dx==-3){
					if(selected.color=='w')
						checkCastle(oldPt,newPt,selected,game.board[7][0]);
					else
						checkCastle(oldPt,newPt,selected,game.board[0][0]);
				}else{
					System.out.println("Illegal move, try again");
					changeTurn = false;
					return;
				}
			}else if (empty && clear &&valid) {
				game.board[oldPt.getRow()][oldPt.getCol()] = null;
				game.board[newPt.getRow()][newPt.getCol()] = selected;
				if(inCheck()){
					game.board[oldPt.getRow()][oldPt.getCol()] = selected;
					game.board[newPt.getRow()][newPt.getCol()] = null;
					System.out.println("Illegal move, try again");
					changeTurn = false;

					return;
				}
			}
			break;
		case 'Q':
			if(!selected.hasMoved)
				selected = new Queen(oldPt.getRow(),oldPt.getCol(), color);
			valid = ((Queen) selected).validMove(oldPt,newPt,empty);
			if (empty && clear &&valid) {
				game.board[oldPt.getRow()][oldPt.getCol()] = null;
				game.board[newPt.getRow()][newPt.getCol()] = selected;
				if(inCheck()){
					game.board[oldPt.getRow()][oldPt.getCol()] = selected;
					game.board[newPt.getRow()][newPt.getCol()] = null;
					System.out.println("Illegal move, try again");
					changeTurn = false;

					return;
				}
			}
			break;
		case 'B':
			if(!selected.hasMoved)
				selected = new Bishop(oldPt.getRow(),oldPt.getCol(), color);
			valid = ((Bishop) selected).validMove(oldPt,newPt,empty);
			if (empty && clear &&valid) {
				game.board[oldPt.getRow()][oldPt.getCol()] = null;
				game.board[newPt.getRow()][newPt.getCol()] = selected;
				if(inCheck()){
					game.board[oldPt.getRow()][oldPt.getCol()] = selected;
					game.board[newPt.getRow()][newPt.getCol()] = null;
					System.out.println("Illegal move, try again");
					changeTurn = false;

					return;
				}
			}
			break;
		case 'N':
			if(!selected.hasMoved)
				selected = new Knight(oldPt.getRow(),oldPt.getCol(), color);
			valid = ((Knight) selected).validMove(oldPt,newPt,empty);
			//System.out.println(x);
			if (empty && clear &&valid) {
				game.board[oldPt.getRow()][oldPt.getCol()] = null;
				game.board[newPt.getRow()][newPt.getCol()] = selected;
				if(inCheck()){
					game.board[oldPt.getRow()][oldPt.getCol()] = selected;
					game.board[newPt.getRow()][newPt.getCol()] = null;
					System.out.println("Illegal move, try again");
					changeTurn = false;

					return;
				}
			}
			
			break;
		case 'R':
			if(!selected.hasMoved)
				selected = new Rook(oldPt.getRow(),oldPt.getCol(), color);
			valid = ((Rook) selected).validMove(oldPt,newPt,empty);
	
			if (empty && clear &&valid) {
				game.board[oldPt.getRow()][oldPt.getCol()] = null;
				game.board[newPt.getRow()][newPt.getCol()] = selected;
				if(inCheck()){
					game.board[oldPt.getRow()][oldPt.getCol()] = selected;
					game.board[newPt.getRow()][newPt.getCol()] = null;
					System.out.println("Illegal move, try again");
					changeTurn = false;
					return;
				}
			}
			
			break;
		case 'p':
			boolean enPassant=false , promo = false;
			if(!selected.hasMoved)
				selected = new Pawn(oldPt.getRow(),oldPt.getCol(), color);
			int dx = newPt.getCol()-oldPt.getCol();
			int dy = newPt.getRow()-oldPt.getRow();

			
			valid = ((Pawn) selected).validMove(oldPt,newPt,empty);	
			if(checkEnPassant(newPt, selected))
				enPassant=true;
			if(game.board[newPt.getRow()][newPt.getCol()]!=null&&dx==0&&(dy==1||dy==-1||dy==2||dy==-2)){
				valid = false;
			}
			
			if(game.board[newPt.getRow()][newPt.getCol()]==null&&(dx==1||dx==-1)&&!enPassant){
				valid = false;
			}
			
			if(checkPromotion(newPt, selected)){
				promo = true;
			}
			//if(checkEnPassant(newPt, selected))
				//enPassant=true;
			if (empty && clear &&(valid||enPassant||promo)) {
				if (enPassant) {
					if (selected.color == 'w') {
						game.board[newPt.getRow() + 1][newPt.getCol()] = null;
					} else	
						game.board[newPt.getRow() - 1][newPt.getCol()] = null;
				}
				if(promo){
					selected = makePromotion(selected);
				}
				game.board[oldPt.getRow()][oldPt.getCol()] = null;
				game.board[newPt.getRow()][newPt.getCol()] = selected;
				if(inCheck()){
					game.board[oldPt.getRow()][oldPt.getCol()] = selected;
					game.board[newPt.getRow()][newPt.getCol()] = null;
					System.out.println("Illegal move, try again");
					changeTurn = false;

					return;
				}
				
				if(selected.hasMoved == false){
					selected.firstMove = true;
				}
				if(selected.hasMoved &&selected.firstMove){
					selected.firstMove = false;
				}
			}
			break;
		}
		if(!valid && !castle){
			System.out.println("Illegal move, try again");
			changeTurn = false;
			return;
		}
		selected.hasMoved=true;
	}
	
	
	/**
	 * detects if a pawn can perform en passant as a valid move
	 *  
	 * @param newPt - spot pawn looks to move to
	 * @param piece - a pawn piece should be passed in
	 * @return true if en passant is to be performed
	 * @author kevin bundschuh
	 */
	public static boolean checkEnPassant(Point newPt, Piece piece){
		if(piece.type!='p')
			return false;
		
		if(piece.color=='w'){
			if(game.board[newPt.getRow()+1][newPt.getCol()]==null||game.board[newPt.getRow()+1][newPt.getCol()].type !='p'
					||game.board[newPt.getRow()+1][newPt.getCol()].color=='w'){
				return false;
			}else if(game.board[newPt.getRow()+1][newPt.getCol()]!=null&&game.board[newPt.getRow()+1][newPt.getCol()].type=='p'
					&&game.board[newPt.getRow()+1][newPt.getCol()].firstMove){
				//game.board[newPt.getRow()][newPt.getCol()]=null;

				return true;
			}
		}else{
			if(game.board[newPt.getRow()-1][newPt.getCol()]==null||game.board[newPt.getRow()-1][newPt.getCol()].type !='p'
					||game.board[newPt.getRow()-1][newPt.getCol()].color=='b'){
				return false;
			}else if(game.board[newPt.getRow()-1][newPt.getCol()]!=null&&game.board[newPt.getRow()-1][newPt.getCol()].type=='p'
					&&game.board[newPt.getRow()-1][newPt.getCol()].firstMove){
				return true;
			}
		}
		return false;	
	}
	
	
	/**
	 * checks if a pawns move was eligible for a promotion.
	 * promotes to piece designated by user
	 * if no piece specified, pawn becomes queen by default
	 * 
	 * @param dest - should be row 8 or 1
	 * @param piece - should be a pawn
	 * @return true if pawn is to be promoted
	 * @author kevin bundschuh
	 */
	public static boolean checkPromotion(Point dest, Piece piece){
		if(piece.type != 'p')
			return false;
		if(piece.color == 'w'){
			if (dest.getRow()==0){
				return true;
			}
		}else if(piece.color =='b'){
			if(dest.getRow()==7){
				return true;
			}
		}
		
		return false;
	}
	

	/**
	 * if a pawn is to be promoted, this sets the pawn to the proper piece according to user input
	 * 
	 * @param piece - pawn to be promoted
	 * @return promoted piece - piece the pawn is to be promoted to
	 * @author kevin bundschuh
	 */
	public static Piece makePromotion(Piece piece){
		Piece temp;
		if(promotion == null){
			temp = new Queen(piece.row,piece.col,piece.color);
		} else {
			switch (promotion) {
			case "q":
			case "Q":
				temp = new Queen(piece.row, piece.col, piece.color);
				break;
			case "r":
			case "R":
				temp = new Rook(piece.row, piece.col, piece.color);
				break;
			case "b":
			case "B":
				temp = new Bishop(piece.row, piece.col, piece.color);
				break;
			case "n":
			case "N":
				temp = new Knight(piece.row, piece.col, piece.color);
				break;
			default:
				temp = new Queen(piece.row, piece.col, piece.color);
				break;
			}
		}
		return temp;
		
	}	
	
	/**
	 * checks to see if kings intended movement was to castle
	 * 
	 * @param oldPt - king's position
	 * @param newPt - point king is to move to
	 * @param king - king piece passed in
	 * @param rook - rook piece passed in
	 * @author kevin bundschuh
	 */
	
	public static void checkCastle(Point oldPt, Point newPt, Piece king, Piece rook){
		int dx = newPt.getCol()-oldPt.getCol();
		
		if(king.type =='K'&&king.hasMoved==false&&king.color=='b'){
			if(dx==-3){
				if(game.board[0][0]!=null&&game.board[0][0].type=='R'&&game.board[0][0].color=='b'
						&&game.board[0][0].hasMoved==false&&game.clearPath(oldPt,newPt)){
					game.board[0][0]=null;
					game.board[oldPt.getRow()][oldPt.getCol()]=null;
					
					game.board[newPt.getRow()][newPt.getCol()]=king;
					game.board[newPt.getRow()][newPt.getCol()+1]=rook;
					if(inCheck()){
						game.board[0][0]=rook;
						game.board[oldPt.getRow()][oldPt.getCol()]=king;
						
						game.board[newPt.getRow()][newPt.getCol()]=null;
						game.board[newPt.getRow()][newPt.getCol()+1]=null;
						System.out.println("Illegal move, try again");

						changeTurn = false;
						return;
					}
					castle = true;
					return;
				}
			}
			if(dx==2){
				if(game.board[0][7]!=null&&game.board[0][7].type=='R'&&game.board[0][7].color=='b'
						&&game.board[0][7].hasMoved==false&&game.clearPath(oldPt,newPt)){
					game.board[0][7]=null;
					game.board[oldPt.getRow()][oldPt.getCol()]=null;
					
					game.board[newPt.getRow()][newPt.getCol()]=king;
					game.board[newPt.getRow()][newPt.getCol()-1]=rook;
					if(inCheck()){
						game.board[0][7]=rook;
						game.board[oldPt.getRow()][oldPt.getCol()]=king;
						
						game.board[newPt.getRow()][newPt.getCol()]=null;
						game.board[newPt.getRow()][newPt.getCol()-1]=null;
						System.out.println("Illegal move, try again");

						changeTurn = false;
						return;
					}
					castle = true;
					return;
				}
			}
		}
		if(king.type =='K'&&king.hasMoved==false&&king.color=='w'){
			if (dx == -3) {
				if (game.board[7][0] != null && game.board[7][0].type == 'R' && game.board[7][0].color == 'w'
						&& game.board[7][0].hasMoved == false && game.clearPath(oldPt, newPt)) {
					game.board[7][0]=null;
					game.board[oldPt.getRow()][oldPt.getCol()]=null;
					
					game.board[newPt.getRow()][newPt.getCol()]=king;
					game.board[newPt.getRow()][newPt.getCol()+1]=rook;
					if(inCheck()){
						game.board[7][0]=rook;
						game.board[oldPt.getRow()][oldPt.getCol()]=king;
						
						game.board[newPt.getRow()][newPt.getCol()]=null;
						game.board[newPt.getRow()][newPt.getCol()+1]=null;
						System.out.println("Illegal move, try again");

						changeTurn = false;
						return;
					}
					castle = true;
					return;
				}
			}
			if(dx==2){
				if (game.board[7][7] != null && game.board[7][7].type == 'R' && game.board[7][7].color == 'w'
						&& game.board[7][7].hasMoved == false && game.clearPath(oldPt, newPt)) {
					game.board[7][7]=null;
					game.board[oldPt.getRow()][oldPt.getCol()]=null;
					
					game.board[newPt.getRow()][newPt.getCol()]=king;
					game.board[newPt.getRow()][newPt.getCol()-1]=rook;
					if(inCheck()){
						game.board[0][0]=rook;
						game.board[oldPt.getRow()][oldPt.getCol()]=king;
						
						game.board[newPt.getRow()][newPt.getCol()]=null;
						game.board[newPt.getRow()][newPt.getCol()-1]=null;
						System.out.println("Illegal move, try again");
						changeTurn = false;
						return;
					}
					castle = true;
					return;
				}
			}
		}
		castle = false;
	}
	
	
	/**
	 * detects if piece movement puts the king in check
	 * 
	 * @return true if king is in check
	 * @author kevin bundschuh
	 */
	public static boolean inCheck(){

		
		//find king location
		Point kingLoc = new Point();
		for (int row = 0; row < 8; row++){
			for (int col = 0; col < 8; col++){
				if (game.board[row][col] != null) {
					if (game.board[row][col].color==game.turn&&game.board[row][col].type=='K') {
						kingLoc.setRow(row);
						kingLoc.setCol(col);
						break;
					}
				}
			}
		}

		
		//check to see if any of the pieces put king in check
		for (int row = 0; row < 8; row++){
			for (int col = 0; col < 8; col++){
				if (game.board[row][col] != null){
					if (game.board[row][col].color != game.turn){
						Point temp = new Point(row,col);
						if (game.board[row][col].type =='K'){
							if(((King) game.board[row][col]).validMove(temp, kingLoc, true)){
								if(game.clearPath(temp, kingLoc)){
									return true;
								}
							}
						}else if(game.board[row][col].type =='Q'){
							if(((Queen) game.board[row][col]).validMove(temp, kingLoc, true)){
								if(game.clearPath(temp, kingLoc)){
									return true;
								}
							}
						}else if(game.board[row][col].type =='B'){
							if(((Bishop) game.board[row][col]).validMove(temp, kingLoc, true)){
								if(game.clearPath(temp, kingLoc)){
									return true;
								}
							}
						}else if(game.board[row][col].type =='N'){
							if(((Knight) game.board[row][col]).validMove(temp, kingLoc, true)){
								if(game.clearPath(temp, kingLoc)){
									return true;
								}
							}
						}else if(game.board[row][col].type =='R'){
							if(((Rook) game.board[row][col]).validMove(temp, kingLoc, true)){
								if(game.clearPath(temp, kingLoc)){
									return true;
								}
							}
						}else if(game.board[row][col].type =='p'){
							if(((Pawn) game.board[row][col]).validMove(temp, kingLoc, true)){
								if(game.clearPath(temp, kingLoc)){
									return true;
								}
							}
						}
					}
				}
			}
		}	
		return false;
	}
	
	/**
	 * if king is in check, see if there are any other viable moves to be made 
	 * 
	 * @param king
	 * @return true if move can be made, false if in checkMate
	 */
	public static boolean canMove(Piece king){
		int row = king.row;
		int col = king.col;
		if (row + 1 < 8) {
			if (game.board[row + 1][col] == null || game.board[row + 1][col].color == game.turn) {
				game.board[row + 1][col] = king;
				if (inCheck()) {
					game.board[row + 1][col] = null;
					game.board[row][col] = king;
				} else {
					game.board[row + 1][col] = null;
					game.board[row][col] = king;
					return true;
				}
			}
		}
		if (row + 1 < 8 && col + 1 < 8) {
			if (game.board[row + 1][col + 1] == null || game.board[row + 1][col + 1].color == game.turn) {
				game.board[row + 1][col + 1] = king;
				if (inCheck()) {
					game.board[row + 1][col + 1] = null;
					game.board[row][col] = king;
				} else {
					game.board[row + 1][col + 1] = null;
					game.board[row][col] = king;
					return true;
				}
			}
		}
		if (row + 1 < 8 && col - 1 > -1) {
			if (game.board[row + 1][col - 1] == null || game.board[row + 1][col - 1].color == game.turn) {
				game.board[row + 1][col - 1] = king;
				if (inCheck()) {
					game.board[row + 1][col - 1] = null;
					game.board[row][col] = king;
				} else {
					game.board[row + 1][col - 1] = null;
					game.board[row][col] = king;
					return true;
				}
			}
		}
		if (col + 1 < 8) {
			if (game.board[row][col + 1] == null || game.board[row][col + 1].color == game.turn) {
				game.board[row][col + 1] = king;
				if (inCheck()) {
					game.board[row][col + 1] = null;
					game.board[row][col] = king;
				} else {
					game.board[row][col + 1] = null;
					game.board[row][col] = king;
					return true;
				}
			}
		}
		if (col - 1 > -1) {
			if (game.board[row][col - 1] == null || game.board[row][col - 1].color == game.turn) {
				game.board[row][col - 1] = king;
				if (inCheck()) {
					game.board[row][col - 1] = null;
					game.board[row][col] = king;
				} else {
					game.board[row][col - 1] = null;
					game.board[row][col] = king;
					return true;
				}
			}
		}
		if (row - 1 > -1 && col + 1 < 8) {
			if (game.board[row - 1][col + 1] == null || game.board[row - 1][col + 1].color == game.turn) {
				game.board[row - 1][col + 1] = king;
				if (inCheck()) {
					game.board[row - 1][col + 1] = null;
					game.board[row][col] = king;
				} else {
					game.board[row - 1][col + 1] = null;
					game.board[row][col] = king;
					return true;
				}
			}
		}
		if (row - 1 > -1) {
			if (game.board[row - 1][col] == null || game.board[row - 1][col].color == game.turn) {
				game.board[row - 1][col] = king;
				if (inCheck()) {
					game.board[row - 1][col] = null;
					game.board[row][col] = king;
				} else {
					game.board[row - 1][col] = null;
					game.board[row][col] = king;
					return true;
				}
			}
		}
		if (row - 1 > -1 && col - 1 > -1) {
			if (game.board[row - 1][col - 1] == null || game.board[row - 1][col - 1].color == game.turn) {
				game.board[row - 1][col - 1] = king;
				if (inCheck()) {
					game.board[row - 1][col - 1] = null;
					game.board[row][col] = king;
				} else {
					game.board[row - 1][col - 1] = null;
					game.board[row][col] = king;
					return true;
				}
			}
		}
		return false;
	}
}


