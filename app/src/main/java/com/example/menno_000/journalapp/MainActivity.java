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

import java.sql.Timestamp;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EntryDatabase entryDatabase;
    ListView listview;

    // A listener that goes to a new view after an entry is chosen
    private class OnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            // Retrieve chosen Entry
            Cursor clickedEntry = (Cursor) adapterView.getItemAtPosition(i);

            // Retrieve data from chosen Entry
            String title = clickedEntry.getString(clickedEntry.getColumnIndex("title"));
            String content = clickedEntry.getString(clickedEntry.getColumnIndex("content"));
            String timestamp = (Timestamp.valueOf(clickedEntry.getString(4))).toString();
            String mood = clickedEntry.getString(clickedEntry.getColumnIndex("mood"));

            // Give info to new view
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("content", content);
            intent.putExtra("timestamp", timestamp);
            intent.putExtra("mood", mood);

            startActivity(intent);
        }
    }

    // Deletes entry on long click
    private class OnLongItemClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

            // Delete chosen entry
            entryDatabase.delete(l);

            // Update the listview
            EntryAdapter adapter = new EntryAdapter(MainActivity.this, entryDatabase
                    .selectAll());
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
        entryDatabase = EntryDatabase.getInstance(getApplicationContext());

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

        EntryAdapter adapter = new EntryAdapter(MainActivity.this, entryDatabase
                .selectAll());
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
