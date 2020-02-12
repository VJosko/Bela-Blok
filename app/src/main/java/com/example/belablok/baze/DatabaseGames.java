package com.example.belablok.baze;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.belablok.klase.Game;

import java.util.ArrayList;
import java.util.List;

public class DatabaseGames extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseGames";

    private static final String TABLE_NAME = "Games";
    private static final String COL0 = "ID";
    private static final String COL1 = "igrac_1";
    private static final String COL2 = "igrac_2";
    private static final String COL3 = "igrac_3";
    private static final String COL4 = "igrac_4";
    private static final String COL5 = "pobjede_mi";
    private static final String COL6 = "pobjede_vi";


    public DatabaseGames(@Nullable Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL1 +" TEXT, " + COL2 +" TEXT, " + COL3 + " TEXT, " + COL4 + " TEXT, " + COL5 + " INT, " + COL6 + " INT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addData(Game game){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, game.sIgrac1);
        contentValues.put(COL2, game.sIgrac2);
        contentValues.put(COL3, game.sIgrac3);
        contentValues.put(COL4, game.sIgrac4);
        contentValues.put(COL5, game.nPobjedeMi);
        contentValues.put(COL6, game.nPobjedeVi);

        long result = db.insert(TABLE_NAME, null, contentValues);
    }

    public ArrayList<Game> getData(){
        ArrayList<Game> games = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        while(data.moveToNext()){
            Game game = new Game(data.getString(1),data.getString(2),
                    data.getString(3),data.getString(4),data.getInt(5),data.getInt(6));
            games.add(game);
        }
        return games;
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

    public ArrayList<String> getCurrentPlayers(){
        ArrayList<String> igraci = new ArrayList<>();
        String sId = Integer.toString(getLastId());
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL0 + " = " + sId;
        Cursor data = db.rawQuery(query, null);
        while(data.moveToNext()){
            igraci.add(data.getString(1));
            igraci.add(data.getString(2));
            igraci.add(data.getString(3));
            igraci.add(data.getString(4));
        }
        return igraci;
    }

    public int[] getRezultat(int id){
        int[] rez = {0,0};
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL0 + " = " + id;
        Cursor data = db.rawQuery(query, null);
        while(data.moveToNext()){
            rez[0] = data.getInt(5);
            rez[1] = data.getInt(6);
        }
        return rez;
    }

    public void updateRezultat(int id, boolean pobjednik, int nDuplo){
        SQLiteDatabase db = this.getWritableDatabase();
        int nBodovi = 1 + nDuplo;
        if(pobjednik) {
            String query = "UPDATE " + TABLE_NAME + " SET " + COL5 + " = (" + COL5 + " + " + nBodovi +
                    ") WHERE ID = " + id;
            db.execSQL(query);
        }
        else {
            String query = "UPDATE " + TABLE_NAME + " SET " + COL6 + " = (" + COL6 + " + " + nBodovi +
                    ") WHERE ID = " + id;
            db.execSQL(query);
        }
    }
}
