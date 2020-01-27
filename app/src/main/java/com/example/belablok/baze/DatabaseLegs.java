package com.example.belablok.baze;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.belablok.klase.Leg;

import java.util.ArrayList;

public class DatabaseLegs extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseLegs";

    private static final String TABLE_NAME = "Legs";
    private static final String COL0 = "ID";
    private static final String COL1 = "game_ID";
    private static final String COL2 = "rbr";


    public DatabaseLegs(@Nullable Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL1 +" INT, " + COL2 +" INT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addData(Leg leg){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, leg.nGameID);
        contentValues.put(COL2, leg.nRbr);

        long result = db.insert(TABLE_NAME, null, contentValues);
    }

    public ArrayList<Leg> getData(){
        ArrayList<Leg> legs = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        while(data.moveToNext()){
            Leg leg = new Leg(data.getInt(1),data.getInt(2));
            legs.add(leg);
        }
        return legs;
    }

    public int getLastId(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL0 + " DESC LIMIT 1";
        Cursor data = db.rawQuery(query, null);
        int nId = -1;
        while(data.moveToNext()){
            nId = data.getInt(0);
        }
        return nId;
    }
}
