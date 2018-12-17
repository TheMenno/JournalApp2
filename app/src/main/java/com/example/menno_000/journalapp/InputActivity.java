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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;

public class InputActivity extends AppCompatActivity {

    EntryDatabase entryDatabase;
    String mood = "";
    String title;
    String edit;
    String content;
    int id = -1;

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

        // Find ImageButton views
        buttonhappy = findViewById(R.id.mood_happy);
        buttonneutral = findViewById(R.id.mood_neutral);
        buttontired = findViewById(R.id.mood_tired);
        buttonsad = findViewById(R.id.mood_sad);

        // Receive data from previous screen
        Intent intent = getIntent();

        // Find out from which activity the intent came
        edit = intent.getStringExtra("edit");

        // Open data from previous screen
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        mood = intent.getStringExtra("mood");
        id = intent.getIntExtra("id", -1);

        // Find corresponding views
        EditText editTitle = findViewById(R.id.input_title);
        EditText editContent = findViewById(R.id.input_content);

        // Set the data from the previous screen
        // If the previous screen is MainActivity; nothing is set
        // If the previous screen is DetailActivity; that data is set
        editTitle.setText(title);
        editContent.setText(content);

        // Set the mood from the previous screen
        setMood(mood);

        // Acts when Button is clicked, go to next activity
        Button button = findViewById(R.id.input_submitbutton);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addEntry();
            }
        });

        // Set listeners on ImageButtons
        buttonhappy.setOnClickListener(new Listener());
        buttonneutral.setOnClickListener(new Listener());
        buttontired.setOnClickListener(new Listener());
        buttonsad.setOnClickListener(new Listener());

        // Access the database
        entryDatabase = EntryDatabase.getInstance(getApplicationContext());
    }


    // Save the mood on rotation
    // Edittext saves automatically, so no need to do that here
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("mood", mood);
    }


    // Restore the mood on rotation
    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);

        // Retrieve the mood
        mood = (String) inState.getSerializable("mood");

        // Reset the selected mood
        setMood(mood);
    }


    // Set the mood when something changes
    public void setMood(String mood) {

        switch(mood) {
            case "happy":
                select(buttonhappy);
                break;
            case "neutral":
                select(buttonneutral);
                break;
            case "tired":
                select(buttontired);
                break;
            case "sad":
                select(buttonsad);
                break;
        }
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
        // Insert the new data into the database
        else {
            values.put("title", title);
            values.put("content", content);
            values.put("mood", mood);

            entryDatabase.insert(new JournalEntry(title, content, mood));

            // Delete old entry if it was edited
            if (id > -1) {

                entryDatabase.delete(id);

                // Notify user
                Toast.makeText(this, "Entry was updated", Toast.LENGTH_SHORT).show();
            } else {

                // Notify user
                Toast.makeText(this, "Entry was added", Toast.LENGTH_SHORT).show();
            }

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
