package com.example.menno_000.journalapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;

public class InputActivity extends AppCompatActivity {

    EntryDatabase entryDatabase;
    String mood = "";

    ImageView buttonhappy;
    ImageView buttonneutral;
    ImageView buttontired;
    ImageView buttonsad;


    // Finds the clicked mood and selects it
    public class Listener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            switch(v.getId()) {
                case R.id.mood_happy:
                    select(buttonhappy);
                    mood = "happy";
                    break;
                case R.id.mood_neutral:
                    select(buttonneutral);
                    mood = "neutral";
                    break;
                case R.id.mood_tired:
                    select(buttontired);
                    mood = "tired";
                    break;
                case R.id.mood_sad:
                    select(buttonsad);
                    mood = "sad";
                    break;
                default:
                    throw new RuntimeException("Unknown button");
            }
        }
    }

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

        // Find ImageButton views
        buttonhappy = findViewById(R.id.mood_happy);
        buttonneutral = findViewById(R.id.mood_neutral);
        buttontired = findViewById(R.id.mood_tired);
        buttonsad = findViewById(R.id.mood_sad);

        // Set listeners on ImageButtons
        buttonhappy.setOnClickListener(new Listener());
        buttonneutral.setOnClickListener(new Listener());
        buttontired.setOnClickListener(new Listener());
        buttonsad.setOnClickListener(new Listener());

        // Access the database
        entryDatabase = EntryDatabase.getInstance(getApplicationContext());
    }

    private void addEntry() {

        // Container for variables of an entry
        ContentValues values = new ContentValues();

        // Find Views
        TextView Title = findViewById(R.id.input_title);
        TextView Content = findViewById(R.id.input_content);

        // Retrieve entered data
        String title = Title.getText().toString();
        String content = Content.getText().toString();
        String current_mood = mood;

        // Make sure something has been filled in
        if (title.length() == 0 || content.length() == 0) {
            Toast.makeText(this, "You forgot to fill something in", Toast.LENGTH_SHORT)
                    .show();
        }
        else if (current_mood == "") {
            Toast.makeText(this, "Don't forget to select a mood!", Toast.LENGTH_SHORT)
                    .show();
        }
        else {
            values.put("title", title);
            values.put("content", content);
            values.put("mood", mood);

            entryDatabase.insert(new JournalEntry(title, content, mood));

            // Notify user
            Toast.makeText(this, "Entry was added", Toast.LENGTH_SHORT).show();

            // Go back to the homescreen
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);

        }
    }


    // Allows the user to see the selected mood
    public void select(ImageView v) {
        buttonhappy.clearColorFilter();
        buttonneutral.clearColorFilter();
        buttontired.clearColorFilter();
        buttonsad.clearColorFilter();

        v.setColorFilter(Color.argb(140, 204, 255, 204));
    }
}
