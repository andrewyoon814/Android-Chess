package kevinbundschuh.android_chess14;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import controller.moveValidator;

import model.MoveHolder;


public class ChessGame extends AppCompatActivity {

    //requested move is filled in on click and previous move holds the information from the previous turn for the undo button.
    MoveHolder requestedMove;
    MoveHolder previousMove;

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

            //this else block is reached if and only if this is the second click
            requestedMove.setTo(getResources().getResourceEntryName(clicked.getId()));

            boolean valid;

            /**
             *
             * Put move validation code here.
             * I just set valid to true for debugging purposes
             *
             * Send the requestMove data to validator method and set valid equal to the return value
             */

            valid = moveValidator.validator(requestedMove);


            //if validation is true... move the piece to that spot. else highlight red and put up an alert saying invalid move.
            if(valid == true){

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
                previousMove = requestedMove;

                //reset for future use
                requestedMove.reset();

            }else{

                //if you get here then the validation was false. Put up a toast saying so..
                Toast.makeText(getApplicationContext(),"Invalid move", Toast.LENGTH_LONG).show();

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
        Log.d("Debug Msg:", "undo button pressed");
    }

    /**
     * onclick method called when draw button is clicked
     * @param view
     */
    public void drawListener(View view){
        Log.d("Debug Msg:", "draw button pressed");
    }

    /**
     * onclick method called when resign button is clicked
     * @param view
     */
    public void resignListener(View view){
        Log.d("Debug Msg:", "resign button pressed");
    }

    /**
     * onclick method called when the ai button is clicked
     * @param view
     */
    public void aiListener(View view){
        Log.d("Debug Msg:", "ai button pressed");
    }

}
