package com.vudrag.belablok.baze;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.vudrag.belablok.klase.Leg;

import java.util.ArrayList;

public class DatabaseLegs extends SQLiteOpenHelper{

    private static final String TAG = "DatabaseLegs";

    private static final String TABLE_NAME = "Legs";
    private static final String COL0 = "ID";
    private static final String COL1 = "game_ID";
    private static final String COL2 = "rbr";
    private static final String COL3 = "bodovi_mi";
    private static final String COL4 = "bodovi_vi";
    private static final String COL5 = "dupli";
    private static final String COL6 = "bodovi_za_p";
    private static final String COL7 = "zadnji_mjesao";


    public DatabaseLegs(@Nullable Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL1 +" INT, " + COL2 +" INT, " + COL3 +" INT, " + COL4 +" INT, " + COL5 +" INT, " + COL6 +" INT, " + COL7 +" INT)";
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
        contentValues.put(COL3, leg.nBodoviMi);
        contentValues.put(COL4, leg.nBodoviVi);
        contentValues.put(COL5, leg.nDupli);
        contentValues.put(COL6, leg.nBodoviZaP);
        contentValues.put(COL7, leg.nZadnjiMjesao);

        long result = db.insert(TABLE_NAME, null, contentValues);
    }

    public ArrayList<Leg> getData(){
        ArrayList<Leg> legs = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        while(data.moveToNext()){
            Leg leg = new Leg(data.getInt(1),data.getInt(2),data.getInt(3),
                    data.getInt(4),data.getInt(5),data.getInt(6),data.getInt(7));
            legs.add(leg);
        }
        data.close();
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
        data.close();
        return nId;
    }

    public int getLastRbr(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL0 + " DESC LIMIT 1";
        Cursor data = db.rawQuery(query, null);
        int nId = -1;
        while(data.moveToNext()){
            nId = data.getInt(2);
        }
        data.close();
        return nId;
    }

    public ArrayList<Leg> getLegsByGameId(int id){
        ArrayList<Leg> legs = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL1 + " = " + id;
        Cursor data = db.rawQuery(query, null);
        while(data.moveToNext()){
            Leg leg = new Leg(data.getInt(1),data.getInt(2),data.getInt(3),
                    data.getInt(4),data.getInt(5),data.getInt(6),data.getInt(7));
            legs.add(leg);
        }
        data.close();
        return legs;
    }

    public int getLegId(int rbr, int gameId){
        int id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT ID FROM " + TABLE_NAME + " WHERE " + COL2 + " = " + rbr + " AND " + COL1 + " = " + gameId;
        Cursor data = db.rawQuery(query, null);
        while(data.moveToNext()){
            id = data.getInt(0);
        }
        data.close();
        return id;
    }

    public int getLastLegDuplo(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL0 + " DESC LIMIT 1";
        Cursor data = db.rawQuery(query, null);
        int nDuplo = 0;
        while(data.moveToNext()){
            nDuplo = Integer.parseInt(data.getString(5));
        }
        data.close();
        return nDuplo;
    }

    public boolean isLegGotov(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL0 + " = " + id;
        Cursor data = db.rawQuery(query, null);
        int Mi = 0;
        int Vi = 0;
        int x = 1000;
        while(data.moveToNext()){
            Mi = data.getInt(3);
            Vi = data.getInt(4);
            x = data.getInt(6);
        }
        data.close();
        if((Mi >= x || Vi >= x) && (Mi != Vi)){
            return true;
        }
        else{
            return false;
        }
    }

    public void updateDuplo(int id, String nDuplo){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL5 + " = " + nDuplo +
                " WHERE ID = " + id;
        db.execSQL(query);
    }

    public void updateRezultate(int id, int Mi, int Vi){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL3 + " = (" + COL3 + " + " + Mi +
                ") WHERE ID = " + id;
        db.execSQL(query);
        query = "UPDATE " + TABLE_NAME + " SET " + COL4 + " = (" + COL4 + " + " + Vi +
                ") WHERE ID = " + id;
        db.execSQL(query);
    }

    public void updateBodoveZaP(int id, int nBodovi){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL6 + " = " + nBodovi +
                " WHERE ID = " + id;
        db.execSQL(query);
    }

    public void updateZadnjiMjesao(int id, int nMjesao){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL7 + " = " + nMjesao +
                " WHERE ID = " + id;
        db.execSQL(query);
    }
    public void deleteLeg(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL0 + " = " + id;
        db.execSQL(query);
    }

    public void isprazniBazu(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME;
        db.execSQL(query);
    }
}
