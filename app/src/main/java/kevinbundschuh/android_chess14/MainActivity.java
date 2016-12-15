package kevinbundschuh.android_chess14;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    /**
     * Main Activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startGame = (Button) findViewById(R.id.new_game);

        //start game button listener to start the game.
        startGame.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, ChessGame.class));
            }
        });
    }

    public void listHandler(View view){

        Intent intent = new Intent(MainActivity.this, List_Games.class);
        startActivity(intent);
    }
}
