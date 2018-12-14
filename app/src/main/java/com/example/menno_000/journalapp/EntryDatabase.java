package com.example.menno_000.journalapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import org.w3c.dom.EntityReference;

import java.util.Date;
import java.util.Map;

import static java.sql.Types.INTEGER;
import static java.text.Collator.PRIMARY;
import static android.provider.Contacts.SettingsColumns.KEY;

public class EntryDatabase extends SQLiteOpenHelper {

    private static final String NAME = "entries.db";
    private static final int VERSION = 1;
    private static final String TABLE = "Entries";
    private static final String ID = "_id";
    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private static final String MOOD = "mood";
    private static final String TIMESTAMP = "timestamp";
    private static EntryDatabase instance = null;

    private EntryDatabase(Context context) {
        super(context, TABLE, null, VERSION);
    }

    // Makes sure only one instance of the database exists
    public static EntryDatabase getInstance(Context context) {
        if (instance != null) {
            return instance;
        } else {
            instance = new EntryDatabase(context);
            return instance;
        }
    }


    // Creates the database table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE " + TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TITLE + " TEXT NOT NULL, " + CONTENT + " TEXT NOT NULL, " + MOOD + " TEXT, " +
                TIMESTAMP + "DATETIME DEFAULT CURRENT_TIMESTAMP);";

        sqLiteDatabase.execSQL(sql);
    }


    // Reset the database
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(sqLiteDatabase);
    }


    // Add a new instance
    public void insert(JournalEntry entry){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TITLE, entry.getTitle());
        values.put(CONTENT, entry.getContent());
        values.put(MOOD, entry.getMood());

        sqLiteDatabase.insert(TABLE,null,values);
        sqLiteDatabase.close();
    }


    // Retrieve an instance
    public Cursor selectAll() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE,null);
        return cursor;
    }


    // Delete an instance
    public void delete(long id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(TABLE,ID + "=" + id,null);
        sqLiteDatabase.close();
    }
}
