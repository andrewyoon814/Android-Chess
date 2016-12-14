package model;

/**
 * Created by Andrew Yoon on 12/13/2016.
 */

/**
 *
 * This class is an object that will hold the move data.
 */
public class MoveHolder {

    //variables that show the start and end square for a piece.
    private String from;
    private String to;
    private String piece;
    int click;
    int pieceid;
    int toId;

    /**
     * No arg constructor that creates an empty move info object
     */
    public MoveHolder(){
        this.from = "";
        this.to = "";
        this.piece = "";
        this.click = 1;
    }

    /**
     * 3 Arg constructor that creates information about what piece is being moved, from where to where.
     * @param from
     * @param to
     * @param piece
     */
    public MoveHolder(String from, String to, String piece){
        this.from = from;
        this.to = to;
        this.piece = piece;
        this.click = 1;
    }

    /**
     * Getter for the from square info.
     * @return
     */
    public String getFrom(){
        return this.from;
    }

    /**
     * Getter for the to square info.
     * @return
     */
    public String getTo(){
        return this.to;
    }

    /**
     * Getter for the piece information.
     * @return
     */
    public String getPiece(){
        return this.piece;
    }

    /**
     * returns the click number
     * @return
     */
    public int getClick(){
        return this.click;
    }

    public int getPieceId(){
        return this.pieceid;
    }

    public int getToId(){
        return this.toId;
    }

    public void setToId(int toId){
        this.toId = toId;
    }

    public void setPieceId(int pieceid){
        this.pieceid = pieceid;
    }

    /**
     * gets the click number
     * @param click
     */
    public void setClick(int click){
        this.click = click;
    }

    /**
     * Setter for the piece type.
     * @param piece
     */
    public void setPiece(String piece){
        this.piece = piece;
    }

    /**
     * Setter for the from variable.
     * @param from
     */
    public void setFrom(String from){
        this.from = from;
    }

    /**
     * Setter for the to variable.
     * @param to
     */
    public void setTo(String to){
        this.to = to;
    }

    /**
     * function resets move object in prep for the next move.
     */
    public void reset(){
        this.from = "";
        this.to = "";
        this.piece = "";
        this.click = 1;
        this.pieceid = 0;
    }

    @Override
    public String toString(){
        return "Move " + piece + " From: " + from + " To : " + to + " .";
    }


}
