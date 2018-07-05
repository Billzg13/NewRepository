package com.example.billzg.recordingappfirsttry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "recordingsdb.db";
    private static final String create_table_recordings = "create table recs (" +
            "_id integer primary key autoincrement," +
            "name text);";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_table_recordings);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS recs");
        onCreate(db);
    }


    public void SaveRecInDb(String finalName) {
        Log.d(TAG, "SaveRecInDb: finalName: "+finalName);
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", finalName);
        db.insert("recs", null, values);
        db.close();

    }

    public int getLatestSize() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c ;
        c = db.rawQuery("select count(*) from recs",null);
        c.moveToFirst();
        int count = c.getInt(c.getColumnIndex("count(*)"));
        Log.d(TAG, "getLatestSize: size is : "+count);
        //close db connection and cursor
        db.close();
        c.close();
        return count;
    }

    public String getExtension(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c ;
        c = db.rawQuery("select name from recs", null);
        c.moveToFirst();
        String name = c.getString(c.getColumnIndex("name"));
        Log.d(TAG, "getExtension: name returned is : "+name);
        db.close();
        c.close();
        return name;
    }

    public ArrayList<String> fetchAllRecs() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c ;
        ArrayList<String> returnArrayList = new ArrayList<>();

        c = db.rawQuery("select name from recs", null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            if (c.getString(c.getColumnIndex("name")) != null){
                returnArrayList.add(c.getString(c.getColumnIndex("name")));
            }
            c.moveToNext();
        }
        db.close();
        c.close();

        return returnArrayList;
    }

}
