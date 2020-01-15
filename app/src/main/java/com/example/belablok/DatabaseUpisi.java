package com.example.belablok;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class DatabaseUpisi extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseUpisi";

    private static final String TABLE_NAME = "Upisi";
    private static final String COL0 = "ID";
    private static final String COL1 = "Leg_ID";
    private static final String COL2 = "bodovi_mi";
    private static final String COL3 = "bodovi_vi";
    private static final String COL4 = "zvanja_mi";
    private static final String COL5 = "zvanja_vi";


    public DatabaseUpisi(@Nullable Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL1 +" INT, " + COL2 +" INT, " + COL3 +" INT, " + COL4 + " INT, " + COL5 +" INT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(Upis upis){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, upis.nLegId);
        contentValues.put(COL2, upis.nBodoviMi);
        contentValues.put(COL3, upis.nBodoviVi);
        contentValues.put(COL4, upis.nZvanjaMi);
        contentValues.put(COL5, upis.nZvanjaVi);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //greska = -1
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
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
