package com.example.menno_000.journalapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EntryDatabase db;
    ListView listview;

    // A listener that goes to a new view after an entry is chosen
    private class OnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            // Retrieve chosen Entry
            Cursor clickedCursor = (Cursor) adapterView.getItemAtPosition(i);


            // Give info to new view
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            //notifyUser(clickedCursor.toString());
            //intent.putExtra("clickedEntry", clickedEntry);

            startActivity(intent);
        }
    }

    // Deletes entry on long click
    private class OnLongItemClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

            // Delete chosen entry
            db.delete(l);

            // Update the listview
            EntryAdapter adapter = new EntryAdapter(MainActivity.this, db.selectAll());
            listview.setAdapter(adapter);

            // Notify the user
            notifyUser("Entry was deleted");

            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the database
        db = EntryDatabase.getInstance(getApplicationContext());

        // Acts when floatingActionButton is clicked, go to next activity
        FloatingActionButton button = findViewById(R.id.main_newbutton);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toInputActivity();
            }
        });

        // Set listeners on the listview
        listview = findViewById(R.id.main_list);
        listview.setOnItemClickListener(new OnItemClickListener());
        listview.setOnItemLongClickListener(new OnLongItemClickListener());

        EntryAdapter adapter = new EntryAdapter(MainActivity.this, db.selectAll());
        listview.setAdapter(adapter);
    }

    // Go to the next activity
    private void toInputActivity() {
        Intent intent = new Intent(this, InputActivity.class);
        this.startActivity(intent);
    }

    public void notifyUser(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
