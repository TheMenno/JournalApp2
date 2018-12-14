package com.example.menno_000.journalapp;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import java.sql.Timestamp;

public class EntryAdapter extends ResourceCursorAdapter {
    public EntryAdapter(Context context, Cursor cursor) {
        super(context, R.layout.entry_row, cursor);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String title = cursor.getString(cursor.getColumnIndex("title"));
        String timestamp = (Timestamp.valueOf(cursor.getString(4))).toString();
        String mood = cursor.getString(cursor.getColumnIndex("mood"));;

        TextView EntryTitle = view.findViewById(R.id.entry_title);
        TextView TimeStamp = view.findViewById(R.id.entry_date);
        ImageView Mood = view.findViewById(R.id.entry_img);

        EntryTitle.setText(title);
        TimeStamp.setText(timestamp);
        switch(mood) {
            case "happy":
                Mood.setImageResource(R.drawable.happy);
                break;
            case "neutral":
                Mood.setImageResource(R.drawable.neutral);
                break;
            case "tired":
                Mood.setImageResource(R.drawable.tired);
                break;
            case "sad":
                Mood.setImageResource(R.drawable.sad);
                break;
        }

        Mood.setRotation(90);
    }
}
