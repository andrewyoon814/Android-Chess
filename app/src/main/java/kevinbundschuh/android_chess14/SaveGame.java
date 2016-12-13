package kevinbundschuh.android_chess14;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SaveGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_game);

        Button cancel = (Button) findViewById(R.id.cancel_btn);

        cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(SaveGame.this, MainActivity.class));
            }
        });

        Button cont = (Button) findViewById(R.id.start_btn);

        cont.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(SaveGame.this, ChessGame.class));
            }
        });

    }
}
