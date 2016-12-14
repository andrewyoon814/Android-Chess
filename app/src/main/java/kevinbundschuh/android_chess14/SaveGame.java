package kevinbundschuh.android_chess14;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SaveGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_game);

        String result = getIntent().getStringExtra("Result");

        TextView resultView = (TextView) findViewById(R.id.resultView);
        resultView.setText(result);

    }

    public void saveHandler(View view){
        finish();
    }

    public void cancelHandler(View view){
        finish();

    }
}
