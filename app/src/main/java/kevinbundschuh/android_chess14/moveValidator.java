package kevinbundschuh.android_chess14;

import model.MoveHolder;

/**
 * Created by Andrew Yoon on 12/13/2016.
 */

public class moveValidator {


    public static String validator(MoveHolder info){
        System.out.println(info.toString());

        Point oldPt = strToCoord(info.getFrom());
        Point newPt = strToCoord(info.getTo());
        String pieceType = info.getPiece().substring(1);
        //boolean valid = checkValid(pieceType, oldPt, newPt);
        boolean goodMove = ChessGame.makeMove(oldPt, newPt);
        System.out.println("goodMove " +goodMove);
        if( goodMove)
            return "true";
        else
            return "false";
    }

    public static Point strToCoord(String str) {

        Point temp = new Point();
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
        }
        switch (str.charAt(1)) {
            case '0':
                temp.setRow(0);
                break;
            case '1':
                temp.setRow(1);
                break;
            case '2':
                temp.setRow(2);
                break;
            case '3':
                temp.setRow(3);
                break;
            case '4':
                temp.setRow(4);
                break;
            case '5':
                temp.setRow(5);
                break;
            case '6':
                temp.setRow(6);
                break;
            case '7':
                temp.setRow(7);
                break;

        }
        return temp;
    }

    public static boolean checkValid(String piece, Point oldloc, Point newloc){
        int x,y;

        switch(piece){
            case "pawn":
                x = newloc.getCol()-oldloc.getCol();
                y = newloc.getRow()-oldloc.getRow();

            case "rook":
                x = newloc.getCol()-oldloc.getCol();
                y = newloc.getRow()-oldloc.getRow();

                // vertical moves
                if (x == 0 && y != 0) {
                    return true;
                }

                // horizontal moves
                if (x != 0 && y == 0) {
                    return true;
                }
                break;

            case "bishop":
                x = Math.abs(newloc.getCol()-oldloc.getCol());
                y = Math.abs(newloc.getRow()-oldloc.getRow());

                if (x == y) {
                    return true;
                }
                break;
            case "horse":
                x = newloc.getCol()-oldloc.getCol();
                y = newloc.getRow()-oldloc.getRow();

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
                break;

            case "king":

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
                break;
            case "queen":
                x = Math.abs(newloc.getCol()-oldloc.getCol());
                y = Math.abs(newloc.getRow()-oldloc.getRow());

                // vertical moves
                if (x == 0 && y != 0) {
                    return true;
                }else if (x != 0 && y == 0) {
                    return true;
                }else if(x==y){
                    return true;
                }
                break;

        }
        return false;
    }

}
