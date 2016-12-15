package kevinbundschuh.android_chess14;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class List_Games extends AppCompatActivity {

    String[] list;
    ArrayList<ArrayList<String>> games = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__games);

        Spinner sortList = (Spinner) findViewById(R.id.spinner);

        sortList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                String sortBy = (String) parent.getItemAtPosition(position);

                if(sortBy.equals("Sort By Date")){
                    sortByDate();
                }else{
                    sortByName();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        String path= getFilesDir() + "/games.ser";
        File file = new File (path);

        if ( file.exists() ) {

            //Toast.makeText(getApplicationContext(), "File exists", Toast.LENGTH_SHORT).show();
            sortByDate();

        }else{

            //if there is no games saved.
            //Toast.makeText(getApplicationContext(), "No", Toast.LENGTH_SHORT).show();
            TextView title = (TextView) findViewById(R.id.textView2);
            title.setText("No Saved Games!");
        }



    }

    /**
     *
     * Back button listener.
     * @param view
     */
    public void goBackHandler(View view){

        finish();
    }

    /**
     *
     * Sort all entries by Name. You need to sort for this one.
     */
    public void sortByName(){

        Context context = getApplicationContext();

        //read the serialized file
        try {

            //open the serialized file.
            FileInputStream fis = context.openFileInput("games.ser");
            ObjectInputStream is = null;
            is = new ObjectInputStream(fis);

            games = (ArrayList<ArrayList<String>>) is.readObject();
            is.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //get the game names from the games arraylist and set into list array
        list = new String[games.size()];

        //get all the game names
        int count = 0;
        while(count < games.size()){
            list[count] = games.get(count).get(0);
            count++;
        }

        //sort list
        Arrays.sort(list);

        //set up the adapter and the listview as well as the click listener
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        ListView listView = (ListView) findViewById(R.id.gameList);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String selectedName = ((TextView)view).getText().toString();
                String viewableName = "";

                //find the sublist that has the selected name
                int count = 0;
                while(count < list.length){

                    viewableName = games.get(count).get(0);
                    if(selectedName.equals(viewableName)){
                        break;
                    }
                    count++;

                }

                //onclick redirect to the replay screen and give game move list
                Intent intent = new Intent(List_Games.this, Replay_Game.class);
                intent.putExtra("moveList", games.get(count));
                startActivity(intent);

            }
        });
    }

    /**
     *
     * Sort by date method. No need to sort anything because the arraylist preserves the order in
     * which things are inserted in.
     */
    public void sortByDate(){
        Context context = getApplicationContext();

        try {

            //open the serialized file.
            FileInputStream fis = context.openFileInput("games.ser");
            ObjectInputStream is = null;
            is = new ObjectInputStream(fis);

            games = (ArrayList<ArrayList<String>>) is.readObject();
            is.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //get the game names from the games arraylist and set into list array
        list = new String[games.size()];

        int count = 0;
        while(count < games.size()){
            list[count] = games.get(count).get(0) + " (" + games.get(count).get(1) + ")";
            count++;
        }

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        ListView listView = (ListView) findViewById(R.id.gameList);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String selectedName = ((TextView)view).getText().toString();
                String viewableName = "";

                //find the sublist that has the selected name
                int count = 0;
                while(count < list.length){

                    viewableName = games.get(count).get(0) + " (" + games.get(count).get(1) + ")";
                    if(selectedName.equals(viewableName)){
                        break;
                    }
                    count++;

                }

                //onclick redirect to the replay screen and give game move list
                Intent intent = new Intent(List_Games.this, Replay_Game.class);
                intent.putExtra("moveList", games.get(count));
                startActivity(intent);

            }
        });
    }
}
