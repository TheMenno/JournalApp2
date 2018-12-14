package com.example.menno_000.journalapp;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class InputActivity extends AppCompatActivity {

    EntryDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        // Acts when Button is clicked, go to next activity
        Button button = findViewById(R.id.input_submitbutton);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addEntry();
            }
        });

        // Access the database
        db = EntryDatabase.getInstance(getApplicationContext());
    }

    private void addEntry() {

        ContentValues values = new ContentValues();

        TextView Title = findViewById(R.id.input_title);
        TextView Content = findViewById(R.id.input_content);

        String title = Title.getText().toString();
        String content = Content.getText().toString();
        String mood = "happy";

        if (title.length() == 0 || content.length() == 0 || mood.length() == 0) {
            Toast.makeText(this, "You forgot to fill something in", Toast.LENGTH_SHORT)
                    .show();
        }
        else {
            values.put("title", title);
            values.put("content", content);
            values.put("mood", mood);

            JournalEntry entry = new JournalEntry(title, content, "happy");
            //db.insert("Entries", null, values);
            db.insert(new JournalEntry(title, content, mood));

            // Notify user
            Toast.makeText(this, "Entry was added", Toast.LENGTH_SHORT).show();

            // Go back to the homescreen
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);

        }
    }
}
