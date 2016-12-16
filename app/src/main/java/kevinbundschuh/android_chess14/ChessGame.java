package kevinbundschuh.android_chess14;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;



import java.util.ArrayList;

import model.Bishop;
import model.King;
import model.Knight;
import model.Pawn;
import model.Piece;

import model.MoveHolder;
import model.Queen;
import model.Rook;


public class ChessGame extends AppCompatActivity {
    public static Game game = new Game();
    public static boolean castle = false;
    public static String promotion = null;
    //public static Piece[][] board = game.board;
    public static boolean enPassant = false;
    public Piece[][] saveBoard =game.board;
    public static boolean promo = false;

    //requested move is filled in on click and previous move holds the information from the previous turn for the undo button.
    static MoveHolder requestedMove;
    MoveHolder previousMove;


    ArrayList<String> moveHistory = new ArrayList<String>();

    static char turn = 'w';
    boolean checkMate = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess_game);

        turn = 'w';

        //create requested move object.
        requestedMove = new MoveHolder();

    }

    public void pieceClick(View view) throws IllegalAccessException {

        game.PrintBoard(game.board);
        //set the image button that was clicked
        ImageButton clicked = (ImageButton) view;
        //final ImageButton pro = clicked;
        final ImageView pro = (ImageView)findViewById(clicked.getId());

        //if it is the first click
        if(requestedMove.getClick() == 1){

            //check if spot is empty
            if(clicked.getTag().equals("empty")){
                Toast.makeText(getApplicationContext(),"Can't select an empty tile.", Toast.LENGTH_SHORT).show();
                return;
            }

            //check if the piece selected is the corresponding turn
            if(clicked.getTag().toString().charAt(0) != turn){
                Toast.makeText(getApplicationContext(),"Can't select opponent's piece.", Toast.LENGTH_SHORT).show();
                return;
            }

            /**set the information regarding the first click such as what piece was clicked
             * to be moved and what block*/
            requestedMove.setPiece(view.getTag().toString());
            requestedMove.setFrom(getResources().getResourceEntryName(clicked.getId()));
            requestedMove.setPieceId(clicked.getId());

            //change the background color to green ot indicated that it was picked
            clicked.setBackgroundColor(Color.GREEN);

            //change the click number to 2 to indicate that this is the second click
            requestedMove.setClick(2);
        }else{

            //make sure the destination is not your own peice. if its occupied reset the move
            if(clicked.getTag().toString().charAt(0) == turn ){
                Toast.makeText(getApplicationContext(),"Spot is occupied by your own piece.", Toast.LENGTH_SHORT).show();

                ImageView prev =(ImageView)findViewById(requestedMove.getPieceId());
                prev.setBackgroundColor(Color.TRANSPARENT);

                requestedMove.reset();
                return;
            }

            //check if it is the opposing king
            if(clicked.getTag().toString().substring(1).equals("king")){
                moveHistory.add(requestedMove.getPiece() + "," + requestedMove.getFrom() + "," + getResources().getResourceEntryName(clicked.getId()));
                gameOver();
            }

            //this else block is reached if and only if this is the second click
            requestedMove.setTo(getResources().getResourceEntryName(clicked.getId()));

            //save the destination images id num
            requestedMove.setToId(clicked.getId());

            String valid;

            valid = moveValidator.validator(requestedMove);


            //if validation is true... move the piece to that spot. else highlight red and put up an alert saying invalid move.
            if(valid.equals("true")){

                //saves what was at that position to the toTag var and then resets to new tag
                requestedMove.setToTag(clicked.getTag().toString());
                clicked.setTag(requestedMove.getPiece());

                //this switch statement will correctly assign the new square with correct picture
                switch (requestedMove.getPiece()) {
                    case "bking":
                        clicked.setImageResource(R.drawable.bking);
                        break;
                    case "bqueen":
                        clicked.setImageResource(R.drawable.bqueen);
                        break;
                    case "brook":
                        clicked.setImageResource(R.drawable.brook);
                        break;
                    case "bhorse":
                        clicked.setImageResource(R.drawable.bhorse);
                        break;
                    case "bbishop":
                        clicked.setImageResource(R.drawable.bbishop);
                        break;
                    case "bpawn":
                        clicked.setImageResource(R.drawable.bpawn);
                        break;
                    case "wqueen":
                        clicked.setImageResource(R.drawable.wqueen);
                        break;
                    case "wking":
                        clicked.setImageResource(R.drawable.wking);
                        break;
                    case "wrook":
                        clicked.setImageResource(R.drawable.wrook);
                        break;
                    case "whorse":
                        clicked.setImageResource(R.drawable.whorse);
                        break;
                    case "wpawn":
                        clicked.setImageResource(R.drawable.wpawn);
                        break;
                    case "wbishop":
                        clicked.setImageResource(R.drawable.wbishop);
                        break;
                }

                //get the previous tile by using the saved id and set its tag to empty and background color to transparent
                ImageView prev =(ImageView)findViewById(requestedMove.getPieceId());
                prev.setImageResource(R.drawable.transparent);
                prev.setTag("empty");
                prev.setBackgroundColor(Color.TRANSPARENT);

                //add this move the history arraylist
                moveHistory.add(requestedMove.getPiece() + "," + requestedMove.getFrom() + "," + requestedMove.getTo());

                //save this current move as previous move so that you can use for undo's
                previousMove = new MoveHolder();
                previousMove.setPieceId(requestedMove.getPieceId());
                previousMove.setPiece(requestedMove.getPiece());
                previousMove.setToId(requestedMove.getToId());
                previousMove.setFrom(requestedMove.getFrom());
                previousMove.setTo(requestedMove.getTo());
                previousMove.setClick(requestedMove.getClick());
                previousMove.setToTag(requestedMove.getToTag());

                //sets the images correct if en passant is in effect
                if(enPassant){
                    System.out.println(requestedMove.getTo());
                    String del= "";
                    del = requestedMove.getTo().substring(0,1) + requestedMove.getFrom().substring(1);
                    /*Point pieceToDel = strToCoord(requestedMove.getTo());
                    if(requestedMove.getPiece().charAt(0)=='w'){
                        pieceToDel.setRow(pieceToDel.getRow()+1);
                    } else
                        pieceToDel.setRow(pieceToDel.getRow()-1);
                    System.out.println("coord of piece to delete=" +pieceToDel);*/
                    int idToDel = getResources().getIdentifier(del, "id", getPackageName());
                    ImageView toDel =(ImageView)findViewById(idToDel);

                    toDel.setImageResource(R.drawable.transparent);
                    toDel.setTag("empty");
                    toDel.setBackgroundColor(Color.TRANSPARENT);
                    enPassant = false;
                }

                //sets the images correct if castling is in effect
                if(castle){
                    String rookPos="";
                    String del = "";
                    int dx;
                    Point p1,p2;
                    p1=strToCoord(requestedMove.getTo());
                    p2=strToCoord(requestedMove.getFrom());
                    dx = p1.getCol()-p2.getCol();
                    if(requestedMove.getPiece().charAt(0)=='w'){
                        if(dx == 3){
                            rookPos = "f7";
                            del = "h7";
                        }else if (dx == -2) {
                            rookPos = "c7";
                            del = "a7";
                        }
                    } else {
                        if (dx == 3) {
                            rookPos = "f0";
                            del = "h0";
                        } else if (dx == -2) {
                            rookPos = "c0";
                            del = "a0";
                        }
                    }

                    int idToDel = getResources().getIdentifier(del, "id", getPackageName());
                    ImageView toDel =(ImageView)findViewById(idToDel);
                    toDel.setImageResource(R.drawable.transparent);
                    toDel.setTag("empty");
                    toDel.setBackgroundColor(Color.TRANSPARENT);

                    int newRookPos = getResources().getIdentifier(rookPos, "id", getPackageName());
                    ImageView rook = (ImageView)findViewById(newRookPos);

                    if(requestedMove.getPiece().charAt(0)=='w'){
                        rook.setImageResource(R.drawable.wrook);
                        rook.setTag("wrook");
                    } else {
                        rook.setImageResource(R.drawable.brook);
                        rook.setTag("brook");
                    }

                    rook.setBackground(null);

                    castle = false;


                }

                if(promo){
                    final Point newloc = strToCoord(requestedMove.getTo());
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    alertDialogBuilder.setTitle("Pawn Promotion!");
                    alertDialogBuilder.setMessage("Choose a piece (no Kings or Pawns)");
                    final EditText txt = new EditText(this);
                    alertDialogBuilder.setView(txt);
                    alertDialogBuilder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int button) {
                                    String promotion = txt.getText().toString().toLowerCase();
                                    Piece piece;
                                    if(turn == 'b'){
                                         piece = makePromotion(newloc, 'w', promotion);
                                    } else {
                                        piece = makePromotion(newloc, 'b', promotion);
                                    }
                                    game.board[newloc.getRow()][newloc.getCol()]= piece;
                                    switch(promotion){
                                        case "queen":
                                            if(turn =='b'){
                                                pro.setImageResource(R.drawable.wqueen);
                                                pro.setTag("wqueen");
                                            }else {
                                                pro.setImageResource(R.drawable.bqueen);
                                                pro.setTag("bqueen");
                                            }
                                            break;

                                        case "bishop":
                                            if(turn =='w') {
                                                pro.setImageResource(R.drawable.wbishop);
                                                pro.setTag("wbishop");
                                            }
                                            else {
                                                pro.setImageResource(R.drawable.bbishop);
                                                pro.setTag("bbishop");
                                            }
                                            break;

                                        case "rook":
                                            if(turn =='w') {
                                                pro.setImageResource(R.drawable.wrook);
                                                pro.setTag("wrook");
                                            }
                                            else {
                                                pro.setImageResource(R.drawable.brook);
                                                pro.setTag("brook");
                                            }
                                            break;

                                        case "knight":
                                            if(turn =='w') {
                                                pro.setImageResource(R.drawable.whorse);
                                                pro.setTag("whorse");
                                            }
                                            else {
                                                pro.setImageResource(R.drawable.bhorse);
                                                pro.setTag("bhorse");
                                            }
                                            break;
                                    }
                                }
                            });

                    alertDialogBuilder.create().show();
                    promo = false;
                }

                //reset for future use
                requestedMove.reset();

                //switch textbox text to show the turn
                TextView turnView = (TextView) findViewById(R.id.turnView);
                if(turn == 'w'){
                    turn = 'b';
                    turnView.setText("Black Turn");
                }else{
                    turn = 'w';
                    turnView.setText("White Turn");
                }



            }else{

                //if you get here then the validation was false. Put up a toast saying so..
                Toast.makeText(getApplicationContext(),"Invalid move", Toast.LENGTH_SHORT).show();

                //set previous's background color to transparent
                ImageView prev =(ImageView)findViewById(requestedMove.getPieceId());
                prev.setBackgroundColor(Color.TRANSPARENT);

                //reset
                requestedMove.reset();
            }

        }

    }

    /**
     * onclick method called when undo button is clicked
     * @param view
     */
    public void undoListener(View view){

        if(previousMove == null){
            Toast.makeText(getApplicationContext(),"No previous move.", Toast.LENGTH_SHORT).show();
        }else{

            //imageview that control the ui
            ImageView start = (ImageView) findViewById(previousMove.getToId());
            ImageView end = (ImageView) findViewById(previousMove.getPieceId());

            //point objeects control the backend chessboard
            Point oldPt = strToCoord(previousMove.getFrom());
            Point newPt = strToCoord(previousMove.getTo());

            //tag switch and reset
            end.setTag(start.getTag());
            start.setTag(previousMove.getToTag());

            //sets the end tile's image
            switch (previousMove.getPiece()) {
                case "bking":
                    end.setImageResource(R.drawable.bking);
                    /*piece = new King(oldPt.getRow(),oldPt.getCol(), 'b');
                    game.board[oldPt.getRow()][oldPt.getCol()]=piece;*/
                    break;
                case "bqueen":
                    end.setImageResource(R.drawable.bqueen);
                    break;
                case "brook":
                    end.setImageResource(R.drawable.brook);
                    break;
                case "bhorse":
                    end.setImageResource(R.drawable.bhorse);
                    break;
                case "bbishop":
                    end.setImageResource(R.drawable.bbishop);
                    break;
                case "bpawn":
                    end.setImageResource(R.drawable.bpawn);
                    break;
                case "wqueen":
                    end.setImageResource(R.drawable.wqueen);
                    break;
                case "wking":
                    end.setImageResource(R.drawable.wking);
                    break;
                case "wrook":
                    end.setImageResource(R.drawable.wrook);
                    break;
                case "whorse":
                    end.setImageResource(R.drawable.whorse);
                    break;
                case "wpawn":
                    end.setImageResource(R.drawable.wpawn);
                    break;
                case "wbishop":
                    end.setImageResource(R.drawable.wbishop);
                    break;
            }

            //sets the start tiles's image to previous state
            switch (previousMove.getToTag()) {
                case "empty":
                    start.setImageResource(R.drawable.transparent);
                    break;
                case "bking":
                    start.setImageResource(R.drawable.bking);
                    break;
                case "bqueen":
                    start.setImageResource(R.drawable.bqueen);
                    break;
                case "brook":
                    start.setImageResource(R.drawable.brook);
                    break;
                case "bhorse":
                    start.setImageResource(R.drawable.bhorse);
                    break;
                case "bbishop":
                    start.setImageResource(R.drawable.bbishop);
                    break;
                case "bpawn":
                    start.setImageResource(R.drawable.bpawn);
                    break;
                case "wqueen":
                    start.setImageResource(R.drawable.wqueen);
                    break;
                case "wking":
                    start.setImageResource(R.drawable.wking);
                    break;
                case "wrook":
                    start.setImageResource(R.drawable.wrook);
                    break;
                case "whorse":
                    start.setImageResource(R.drawable.whorse);
                    break;
                case "wpawn":
                    start.setImageResource(R.drawable.wpawn);
                    break;
                case "wbishop":
                    start.setImageResource(R.drawable.wbishop);
                    break;
            }

            previousMove = null;

            //switch textbox text to show the turn
            TextView turnView = (TextView) findViewById(R.id.turnView);
            if(turn == 'w'){
                turn = 'b';
                turnView.setText("Black Turn");
            }else{
                turn = 'w';
                turnView.setText("White Turn");
            }

            moveHistory.remove(moveHistory.size() - 1);

            //game.board = saveBoard;
            //revers the two positions
            game.board[oldPt.getRow()][oldPt.getCol()] = game.board[newPt.getRow()][newPt.getCol()];
            game.board[newPt.getRow()][newPt.getCol()] = null;

        }
    }

    /**
     * Method called when king is captured.
     */
    public void gameOver(){

        Intent intent = new Intent(ChessGame.this, SaveGame.class);

        //see who won the game
        String result;

        if(turn == 'b'){
            result= "Game won by Black!";
        }else{
            result = "Game won by White!";
        }

        moveHistory.add(result);

        intent.putExtra("history", moveHistory);

        //pass result as extra to next screen
        intent.putExtra("Result", result);
        startActivity(intent);

        finish();
    }

    /**
     * onclick method called when draw button is clicked
     * @param view
     */
    public void drawListener(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Draw?");

        String result;
        if(turn == 'b'){
            builder.setMessage("Draw Proposed by Black");
        }else{
            builder.setMessage("Draw Proposed by White");
        }

        builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                moveHistory.add("Game ends in Draw!");

                Intent intent = new Intent(ChessGame.this, SaveGame.class);
                intent.putExtra("Result", "Game Ended in Draw!");
                intent.putExtra("history", moveHistory);
                startActivity(intent);
                finish();
            }
        });

        builder.setNegativeButton("Decline", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * onclick method called when resign button is clicked
     * @param view
     */
    public void resignListener(View view){


        Intent intent = new Intent(ChessGame.this, SaveGame.class);

       //see who won the game
        String result;

        if(turn == 'b'){
            result= "Resign by Black!";
        }else{
            result = "Resign by White!";
        }

        moveHistory.add(result);

        //pass result as extra to next screen
        intent.putExtra("Result", result);
        intent.putExtra("history", moveHistory);
        startActivity(intent);

        finish();

    }

    /**
     * onclick method called when the ai button is clicked
     * @param view
     */
    public void aiListener(View view){

        Toast.makeText(getApplicationContext(), "No Possible Moves!", Toast.LENGTH_SHORT).show();
    }


    public static boolean makeMove(Point oldPt, Point newPt){
        boolean empty=false;
        boolean clear = false;
        boolean valid = false;
        //boolean castle = false;
        //boolean enPassant = false;
        //boolean firstMove = false;
        char color,pieceType;
        //System.out.println(board[oldPt.getRow()][oldPt.getCol()].color);

        color = game.board[oldPt.getRow()][oldPt.getCol()].color;
        pieceType = game.board[oldPt.getRow()][oldPt.getCol()].type;

        Piece selected=game.board[oldPt.getRow()][oldPt.getCol()];

        System.out.println("selected piece moving: "+selected.toString());
        System.out.println("piece has moved? :" +selected.hasMoved);
        //treat capturing a piece of the other color as if it is an empty space it can be moved into
        //this lets pawns capture forward, need to fix this.
        if (game.board[newPt.getRow()][newPt.getCol()] == null
                || turn != game.board[newPt.getRow()][newPt.getCol()].color) {
            empty = true;
        }
        //	if(selected.validMove)

        if(game.clearPath(oldPt, newPt)){
            clear = true;
        }else if(selected.type != 'p')
            return false;

        switch(pieceType){
            case 'K':
                if(!selected.hasMoved)
                    selected = new King(oldPt.getRow(),oldPt.getCol(), color);
                valid = ((King) selected).validMove(oldPt,newPt,empty);
                if(!valid){
                    int dx=newPt.getCol()-oldPt.getCol();
                    if(dx==-2){
                        if (selected.color=='w'){
                            checkCastle(oldPt, newPt, selected, game.board[7][7]);
                        }

                        else{
                            checkCastle(oldPt,newPt,selected,game.board[0][7]);
                        }
                    }else if(dx==3){
                        if(selected.color=='w')
                            checkCastle(oldPt,newPt,selected,game.board[7][0]);
                        else
                            checkCastle(oldPt,newPt,selected,game.board[0][0]);
                    }
                }else if (empty && clear &&valid) {
                    game.board[oldPt.getRow()][oldPt.getCol()] = null;
                    game.board[newPt.getRow()][newPt.getCol()] = selected;
                    if(inCheck()){
                        game.board[oldPt.getRow()][oldPt.getCol()] = selected;
                        game.board[newPt.getRow()][newPt.getCol()] = null;
                        System.out.println("Illegal move, try again");
                        return false;
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
                        return false;
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

                        return false;
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

                        return false;
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

                        return false;
                    }

                }

                break;
            case 'p':
                //boolean promo = false;
                if(!selected.hasMoved)
                    selected = new Pawn(oldPt.getRow(),oldPt.getCol(), color);
                int dx = newPt.getCol()-oldPt.getCol();
                int dy = newPt.getRow()-oldPt.getRow();

                if(Math.abs(dy)==2 && selected.firstMove == false){
                    System.out.println("invalid move");
                    return false;
                }

                valid = ((Pawn) selected).validMove(oldPt,newPt,empty);

                //special case fix
                if(oldPt.getRow()-1>-1&&oldPt.getRow()+1<8) {
                    if (game.board[oldPt.getRow() - 1][oldPt.getCol()] != null)
                        if (game.board[oldPt.getRow()][oldPt.getCol()].color == 'w' && game.board[oldPt.getRow() - 1][oldPt.getCol()].type == 'K')
                            valid = false;
                    if (game.board[oldPt.getRow() + 1][oldPt.getCol()] != null)
                        if (game.board[oldPt.getRow()][oldPt.getCol()].color == 'b' && game.board[oldPt.getRow() + 1][oldPt.getCol()].type == 'K')
                            valid = false;
                }

                if(checkEnPassant(newPt, selected))
                    enPassant=true;
                if(game.board[newPt.getRow()][newPt.getCol()]!=null&&dx==0&&(dy==1||dy==-1||dy==2||dy==-2)){
                    valid = false;
                    return false;
                }

                if(game.board[newPt.getRow()][newPt.getCol()]==null&&(dx==1||dx==-1)&&!enPassant){
                    valid = false;
                    return false;
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
                        } else {
                            game.board[newPt.getRow() - 1][newPt.getCol()] = null;
                        }
                    }
                    /*if(promo){
                        selected = makePromotion(selected);
                    }*/
                    game.board[oldPt.getRow()][oldPt.getCol()] = null;
                    game.board[newPt.getRow()][newPt.getCol()] = selected;
                    if(inCheck()){
                        game.board[oldPt.getRow()][oldPt.getCol()] = selected;
                        game.board[newPt.getRow()][newPt.getCol()] = null;
                        System.out.println("Illegal move, try again");
                        return false;
                    }
                    if(selected.hasMoved == false){
                        selected.firstMove = true;
                    }
                    if(selected.hasMoved &&selected.firstMove){
                        selected.firstMove = false;
                    }
                }
                selected.hasMoved=true;
                selected.firstMove=false;
                break;
        }
        if(!valid && !castle){
            System.out.println("Illegal move, try again");
            return false;
        }
        selected.hasMoved=true;
        return true;
    }

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
     * @param point, color - pawn to be promoted
     * @return promoted piece - piece the pawn is to be promoted to
     * @author kevin bundschuh
     */
    public static Piece makePromotion(Point point, char color, String p){
        Piece temp;
        if(p == null){
            temp = new Queen(point.getRow(),point.getCol(),color);
        } else {
            switch (p) {
                case "queen":
                    temp = new Queen(point.getRow(), point.getRow(), color);
                    break;
                case "rook":
                    temp = new Rook(point.getRow(), point.getCol(), color);
                    break;
                case "bishop":
                    temp = new Bishop(point.getRow(), point.getCol(), color);
                    break;
                case "knight":
                    temp = new Knight(point.getRow(), point.getCol(), color);
                    break;
                default:
                    temp = new Queen(point.getRow(), point.getCol(), color);
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
            if(dx==3){
                //System.out.println("in dx ")
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

                        return;
                    }
                    castle = true;
                    return;
                }
            }
            if(dx==-2){
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

                        return;
                    }
                    castle = true;
                    return;
                }
            }
        }
        if(king.type =='K'&&king.hasMoved==false&&king.color=='w'){
            if (dx == 3) {
                if (game.board[7][7] != null && game.board[7][7].type == 'R' && game.board[7][7].color == 'w'
                        && game.board[7][7].hasMoved == false && game.clearPath(oldPt, newPt)) {
                    game.board[7][7]=null;
                    game.board[oldPt.getRow()][oldPt.getCol()]=null;

                    game.board[newPt.getRow()][newPt.getCol()]=king;
                    game.board[newPt.getRow()][newPt.getCol()-1]=rook;
                    if(inCheck()){
                        game.board[7][7]=rook;
                        game.board[oldPt.getRow()][oldPt.getCol()]=king;

                        game.board[newPt.getRow()][newPt.getCol()]=null;
                        game.board[newPt.getRow()][newPt.getCol()-1]=null;
                        System.out.println("Illegal move, try again");

                        return;
                    }
                    castle = true;
                    return;
                }
            }
            if(dx==-2){
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
                    if (game.board[row][col].color==turn&&game.board[row][col].type=='K') {
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
                    if (game.board[row][col].color != turn){
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
                                if(game.board[row][col].color =='w'&&game.board[row-1][col].type=='K')
                                    return false;
                                if(game.board[row][col].color =='b'&&game.board[row+1][col].type=='K')
                                    return false;
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




    public void redrawPromo(Point point, Piece [][] board){

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




}
