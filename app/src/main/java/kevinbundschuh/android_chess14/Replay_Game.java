package kevinbundschuh.android_chess14;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Replay_Game extends AppCompatActivity {

    ArrayList<String> history;
    int count = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replay__game);

        //set history arraylist to the list that was passed by the previous activity
        history = (ArrayList<String>) getIntent().getSerializableExtra("moveList");

        //set title to the name of the game
        TextView gameName = (TextView) findViewById(R.id.replayGameName);
        gameName.setText(history.get(0));

//        int resID = getResources().getIdentifier("a0", "id", getPackageName());
//        ImageView i = (ImageView) findViewById(resID);
//        i.setImageResource(R.drawable.wpawn);
    }

    /**
     * Listener for the go home button.
     */
    public void goHomeHandler(View view){
        finish();
    }

    /**
     * Listener for the previous move button.
     */
    public void prevButtonHandler(View view){

        //dont let the counter go below 2 bc 0 is name and 1 is date
        if(count == 2){
            Toast.makeText(getApplicationContext(),"No Previous Move", Toast.LENGTH_SHORT).show();
            return;
        }

        count--;

        String direction = history.get(count);

        //set the from spot's image to transparent
        int resID = getResources().getIdentifier(direction.substring(9,11), "id", getPackageName());
        ImageView i = (ImageView) findViewById(resID);
        i.setImageResource(R.drawable.transparent);

        //set the to spot's image to corresponding image
        setImage(direction.substring(0,5),direction.substring(6,8));

//        Log.d("piece ", direction.substring(0,5));
//        Log.d("from ", direction.substring(6,8));
//        Log.d("to ", direction.substring(9,11));

    }

    /**
     * Listener for the next move button.
     */
    public void nextHandler(View view){

        //block count from going above the size. Ask user if they would likd to replay or exit.
        if(count == history.size()){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("End of Replay");

            builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            builder.setNegativeButton("Replay?", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    count = 2;
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
            return;
        }

        String direction = history.get(count);

        //chck to see if this is the last move and print out result
        if(checkEndGame(direction)){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("End of Replay");
            builder.setMessage("Result : " + direction + " Replay Again?");

            builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            builder.setNegativeButton("Replay?", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                    //restart this activity
                    recreate();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();

            return;
        }



        //set the from spot's image to transparent
        int resID = getResources().getIdentifier(direction.substring(6,8), "id", getPackageName());
        ImageView i = (ImageView) findViewById(resID);
        i.setImageResource(R.drawable.transparent);

        //set the to spot's image to corresponding image
        setImage(direction.substring(0,5),direction.substring(9,11));

        count++;
    }

    public boolean checkEndGame(String dir){

       switch(dir){
           case "Resign by Black!" :
               return true;
           case "Resign by White!" :
               return true;
           case "Game ends in Draw!" :
               return true;
           case "Game won by Black!" :
               return true;
           case "Game won by White!" :
               return true;
       }

        return false;
    }

    public void setImage(String piece, String tile){

        //get the appropriate tile by the id given and set imageview
        int resID = getResources().getIdentifier(tile, "id", getPackageName());
        ImageView square = (ImageView) findViewById(resID);

        //switch to correctly set image
        switch (piece) {
            case "bking":
                square.setImageResource(R.drawable.bking);
                break;
            case "bqueen":
                square.setImageResource(R.drawable.bqueen);
                break;
            case "brook":
                square.setImageResource(R.drawable.brook);
                break;
            case "bhorse":
                square.setImageResource(R.drawable.bhorse);
                break;
            case "bbishop":
                square.setImageResource(R.drawable.bbishop);
                break;
            case "bpawn":
                square.setImageResource(R.drawable.bpawn);
                break;
            case "wqueen":
                square.setImageResource(R.drawable.wqueen);
                break;
            case "wking":
                square.setImageResource(R.drawable.wking);
                break;
            case "wrook":
                square.setImageResource(R.drawable.wrook);
                break;
            case "whorse":
                square.setImageResource(R.drawable.whorse);
                break;
            case "wpawn":
                square.setImageResource(R.drawable.wpawn);
                break;
            case "wbishop":
                square.setImageResource(R.drawable.wbishop);
                break;
        }
    }
}
