package kevinbundschuh.android_chess14;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;

public class ChessGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess_game);

    }

    public void pieceClick(View view){
        ImageButton clicked = (ImageButton) view;
        Log.d("Degug msg:", "" +  getResources().getResourceEntryName(clicked.getId()));
        Log.d("dbg msg:", view.getTag().toString());

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
