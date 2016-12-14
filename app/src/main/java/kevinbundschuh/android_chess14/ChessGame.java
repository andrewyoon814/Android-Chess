package kevinbundschuh.android_chess14;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import controller.Chess;
import controller.moveValidator;

import model.MoveHolder;


public class ChessGame extends AppCompatActivity {

    //requested move is filled in on click and previous move holds the information from the previous turn for the undo button.
    MoveHolder requestedMove;
    MoveHolder previousMove;
    char turn = 'w';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess_game);

        //create requested move object.
        requestedMove = new MoveHolder();

    }

    public void pieceClick(View view) throws IllegalAccessException {

        //set the image button that was clicked
        ImageButton clicked = (ImageButton) view;

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
                gameOver();
            }

            //this else block is reached if and only if this is the second click
            requestedMove.setTo(getResources().getResourceEntryName(clicked.getId()));

            //save the destination images id num
            requestedMove.setToId(clicked.getId());

            String valid;

            /**
             *
             * Put move validation code here.
             * I just set valid to true for debugging purposes
             *
             * Send the requestMove data to validator method and set valid equal to the return value
             */

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

                //save this current move as previous move so that you can use for undo's
                previousMove = new MoveHolder();
                previousMove.setPieceId(requestedMove.getPieceId());
                previousMove.setPiece(requestedMove.getPiece());
                previousMove.setToId(requestedMove.getToId());
                previousMove.setFrom(requestedMove.getFrom());
                previousMove.setTo(requestedMove.getTo());
                previousMove.setClick(requestedMove.getClick());
                previousMove.setToTag(requestedMove.getToTag());

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

            ImageView start = (ImageView) findViewById(previousMove.getToId());
            ImageView end = (ImageView) findViewById(previousMove.getPieceId());

            //tag switch and reset
            end.setTag(start.getTag());
            start.setTag(previousMove.getToTag());

            //sets the end tile's image
            switch (previousMove.getPiece()) {
                case "bking":
                    end.setImageResource(R.drawable.bking);
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

                Intent intent = new Intent(ChessGame.this, SaveGame.class);
                intent.putExtra("Result", "Game Ended in Draw!");
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
        startActivity(intent);

       //see who won the game
        String result;

        if(turn == 'b'){
            result= "Resign by Black!";
        }else{
            result = "Resign by White!";
        }

        //pass result as extra to next screen
        intent.putExtra("Result", result);
        startActivity(intent);

        finish();

    }

    /**
     * onclick method called when the ai button is clicked
     * @param view
     */
    public void aiListener(View view){
        Log.d("Debug Msg:", "ai button pressed");
    }

}
