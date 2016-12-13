package kevinbundschuh.android_chess14;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void squareClick(View view){

        ImageButton clicked = (ImageButton) view;
        Log.d("coord:", "clicked" + clicked.getId());
    }
}
