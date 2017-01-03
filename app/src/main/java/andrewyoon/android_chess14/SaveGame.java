package andrewyoon.android_chess14;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SaveGame extends AppCompatActivity {

    ArrayList<String> history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_game);

        String result = getIntent().getStringExtra("Result");

        TextView resultView = (TextView) findViewById(R.id.resultView);
        resultView.setText(result);

        history = (ArrayList<String>) getIntent().getSerializableExtra("history");

        int x = 0;
        while(x < history.size()){

            Log.d("move", history.get(x));
            x++;
        }

    }

    /**
     *
     * Called when user wants to save the game. Serializes the movehistory.
     * @param view
     */
    public void saveHandler(View view) throws IOException, ClassNotFoundException {

        Context context = getApplicationContext();

        String path= getFilesDir() + "/games.ser";
        File file = new File (path);

        if ( file.exists() )
        {
//            Toast.makeText(getApplicationContext(),"File exists", Toast.LENGTH_SHORT).show();

            //open the serialized file.
            FileInputStream fis = context.openFileInput("games.ser");
            ObjectInputStream is = new ObjectInputStream(fis);
            ArrayList<ArrayList<String>> games = (ArrayList<ArrayList<String>>) is.readObject();
            is.close();
            fis.close();

            //get the name from the textview
            TextView gameName = (TextView) findViewById(R.id.gameName);
            String name = null;

            //save the name designated by the user as long as its not the default text
            if(gameName.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(),"Please Give the Game a Name!", Toast.LENGTH_LONG).show();
                return;
            }else{
                name = gameName.getText().toString();
            }

            //save the move history arraylist into the game arraylist.
            int count = 0;
            ArrayList<String> tmp = new ArrayList<>();
            tmp.add(name);

            //get the current time
            Calendar c = Calendar.getInstance();
            System.out.println("Current time => " + c.getTime());

            SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String formattedDate1 = df1.format(c.getTime());
            tmp.add(formattedDate1);

            while(count < history.size()){
                tmp.add(history.get(count));
                count++;
            }

            games.add(tmp);

            //serialize games list
            FileOutputStream fos = context.openFileOutput("games.ser", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(games);
            os.close();
            fos.close();

        }
        else //if file does not exist
        {
//            Toast.makeText(getApplicationContext(),"No", Toast.LENGTH_SHORT).show();

            //get the name from the textview
            TextView gameName = (TextView) findViewById(R.id.gameName);
            String name = null;


            //save the name designated by the user as long as its not the default text
            if(gameName.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(),"Please Give the Game a Name!", Toast.LENGTH_LONG).show();
                return;
            }else{
                name = gameName.getText().toString();
            }

            //save the move history arraylist into the game arraylist.
            int count = 0;
            ArrayList<String> tmp = new ArrayList<>();
            tmp.add(name);

            //get the current time
            Calendar c = Calendar.getInstance();
            System.out.println("Current time => " + c.getTime());

            SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String formattedDate1 = df1.format(c.getTime());
            tmp.add(formattedDate1);

            while(count < history.size()){
                tmp.add(history.get(count));
                count++;
            }

            ArrayList<ArrayList<String>> games = new ArrayList<ArrayList<String>>();
            games.add(tmp);

            //if file does not exist create one with an empty arrrylist of string arraylists.
            FileOutputStream fos = context.openFileOutput("games.ser", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(games);
            os.close();
            fos.close();
        }

        finish();
    }

    /**
     * Called when the user does not want to save the game.
     * @param view
     */
    public void cancelHandler(View view){
        finish();

    }
}
