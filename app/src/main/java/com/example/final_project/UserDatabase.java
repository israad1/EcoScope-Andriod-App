package com.example.final_project;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDatabase extends SQLiteOpenHelper {
    private static final String USERNAME_COL = "username";
    private String TABLE_NAME = "UserDB";
    private static final String PASSWORD_COL = "password";
    private static final String NAME_COL = "name";
    private static final String AGE_COL = "age";
    private static final String GENDER_COL = "gender";
    private static final String DATE_TIME_CREATED_COL = "created";


    public UserDatabase(Context context, String DATABASE_NAME) {
        super(context, DATABASE_NAME, null, 1);
    }

    public UserDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + USERNAME_COL + " TEXT PRIMARY KEY, "
                + PASSWORD_COL + " VARBINARY, "
                + NAME_COL + " TEXT, "
                + AGE_COL + " INTEGER,"
                + GENDER_COL + " TEXT,"
                + DATE_TIME_CREATED_COL + " DATETIME)";


        db.execSQL(query);
    }

    public List<String> getColumnHeaders() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> columnNames = new ArrayList<>();
        // Query that returns at least one row. The actual data doesn't matter as we're just after the structure.
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " LIMIT 1", null);

        // Ensure the cursor is not null and contains at least one column.
        if (cursor != null && cursor.getColumnCount() > 0) {
            // Loop through all columns and add their names to the list.
            for (String name : cursor.getColumnNames()) {
                columnNames.add(name);
            }
            cursor.close();
        }

        db.close();
        return columnNames;
    }

    public void addInfo(String username, byte[] password, String name, String age, String gender, String created) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        values.put(USERNAME_COL, username);
        values.put(PASSWORD_COL, password);
        values.put(NAME_COL, name);
        values.put(AGE_COL, Integer.parseInt(age));
        values.put(GENDER_COL, gender);
        values.put(DATE_TIME_CREATED_COL, created);

        Log.d("===== add info =======", username + ", "+ password);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public  String checkUniqueUser(String username) {
        String query = "SELECT username FROM " + TABLE_NAME + " WHERE username = \"" + username + "\";";
        Log.d("=====  Check Unique User - query =======", query);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor extract = db.rawQuery(query, null);

        Log.d("=====  Check Unique User - count =======", String.valueOf(extract.getCount()));

        if (extract.getCount() > 0) {

            extract.moveToLast();

            Log.d("=====  Check Unique User - string =======", extract.getString(0));
            //i is column id. Here, it doesn't matter as we are only selecting the specific fieldname (input argument).
            return extract.getString(0);
        }

        else {
            return "";
        }
    }

    public String checkPassword(String username) {
        String query = "SELECT password FROM " + TABLE_NAME + " WHERE username = \"" + username + "\";";
        Log.d("=====  Check Password - query =======", query);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor extract = db.rawQuery(query, null);

        Log.d("=====  Check Password - count =======", String.valueOf(extract.getCount()));

        if (extract.getCount() > 0) {

            extract.moveToLast();

            Log.d("=====  Check Password - string =======", Arrays.toString(extract.getBlob(0)));
            //i is column id. Here, it doesn't matter as we are only selecting the specific fieldname (input argument).
            return Arrays.toString(extract.getBlob(0));
        }

        else {
            return "";
        }
    }

    public String getName(String username) {
        String query = "SELECT name FROM " + TABLE_NAME + " WHERE username = \"" + username + "\";";
        Log.d("=====  Get Name - query =======", query);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor extract = db.rawQuery(query, null);

        Log.d("=====  Get Name - count =======", String.valueOf(extract.getCount()));

        if (extract.getCount() > 0) {

            extract.moveToLast();

            Log.d("=====  Get Name - string =======", Arrays.toString(extract.getBlob(0)));
            //i is column id. Here, it doesn't matter as we are only selecting the specific fieldname (input argument).
            return extract.getString(0);
        }

        else {
            return "";
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

}

