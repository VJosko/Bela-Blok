package com.example.belablok.baze;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.belablok.adapteri.recAdapterLegs;
import com.example.belablok.klase.Upis;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUpisi extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseUpisi";

    private static final String TABLE_NAME = "Upisi";
    private static final String COL0 = "ID";
    private static final String COL1 = "Leg_ID";
    private static final String COL2 = "rbr";
    private static final String COL3 = "bodovi_mi";
    private static final String COL4 = "bodovi_vi";
    private static final String COL5 = "zvanja_mi";
    private static final String COL6 = "zvanja_vi";
    private static final String COL7 = "mjesao";


    public DatabaseUpisi(@Nullable Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL1 +" INT, " + COL2 +" INT, " + COL3 +" INT, " + COL4 + " INT, " + COL5 +" INT, " + COL6 +" INT, " +
                COL7 + " INT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addData(Upis upis){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, upis.nLegId);
        contentValues.put(COL2, upis.nRbr);
        contentValues.put(COL3, upis.nBodoviMi);
        contentValues.put(COL4, upis.nBodoviVi);
        contentValues.put(COL5, upis.nZvanjaMi);
        contentValues.put(COL6, upis.nZvanjaVi);
        contentValues.put(COL7, upis.nMjesao);

        long result = db.insert(TABLE_NAME, null, contentValues);
    }

    public ArrayList<Upis> getData(int legId){
        ArrayList<Upis> upisi = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL1 + " = " + legId;
        Cursor data = db.rawQuery(query, null);
        while(data.moveToNext()){
            Upis upis = new Upis(data.getInt(1),data.getInt(2),
                    data.getInt(3),data.getInt(4),data.getInt(5),
                    data.getInt(6),data.getInt(7));
            upisi.add(upis);
        }
        return upisi;
    }

    public ArrayList<Upis> getAllData(){
        ArrayList<Upis> upisi = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        while(data.moveToNext()){
            Upis upis = new Upis(data.getInt(1),data.getInt(2),
                    data.getInt(3),data.getInt(4),data.getInt(5),
                    data.getInt(6),data.getInt(7));
            upisi.add(upis);
        }
        return upisi;
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

    public int getLastLegId(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL0 + " DESC LIMIT 1";
        Cursor data = db.rawQuery(query, null);
        int nLegId = -1;
        while(data.moveToNext()){
            nLegId = data.getInt(1);
        }
        return nLegId;
    }

    public int getLastMjesao(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL0 + " DESC LIMIT 1";
        Cursor data = db.rawQuery(query, null);
        int nMjesao = -1;
        while(data.moveToNext()){
            nMjesao = data.getInt(7);
        }
        return nMjesao;
    }

    public int[] getRezultatLegs(int legId){
        int[][] zbroj = {};
        int [] rez = {};
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL1 + " = " + legId;
        Cursor data = db.rawQuery(query, null);
        while(data.moveToNext()){
            zbroj[data.getInt(0)][0] += data.getInt(3) + data.getInt(5);
            zbroj[data.getInt(0)][1] += data.getInt(4) + data.getInt(6);
        }
        for (int[] i: zbroj) {
            if(i[0] > i[1]){
                rez[0] += 1;
            }
            else{
                rez[1] += 1;
            }
        }
        return rez;
    }

    public int getLastUpisId(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL0 + " DESC LIMIT 1";
        Cursor data = db.rawQuery(query, null);
        int nId = -1;
        while(data.moveToNext()){
            nId = data.getInt(0);
        }
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
        return nId;
    }

    public int getNewRbr(int legId){
        if(legId == getLastLegId()){
            return getLastRbr() + 1;
        }
        else {
            return 0;
        }
    }

    public int[] getBodove(int rbr){
        int[] bodovi = {0,0,0,0};
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " +
                COL1 + " = " + getLastLegId() + " AND " + COL2 + " = " + rbr;
        Cursor data = db.rawQuery(query, null);
        while(data.moveToNext()){
            bodovi[0] = data.getInt(3);
            bodovi[1] = data.getInt(4);
            bodovi[2] = data.getInt(5);
            bodovi[3] = data.getInt(6);
        }

        return bodovi;
    }

    public Upis DeleteUpis(int rbr){
        SQLiteDatabase db = this.getWritableDatabase();
        Upis upis = new Upis(0,0,0,0,0,0,0);
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL1 + " = " + getLastLegId() + " AND " +
                COL2 + " = " + rbr;
        Cursor data = db.rawQuery(query, null);
        while(data.moveToNext()){
            upis.nLegId =  data.getInt(1);
            upis.nRbr =  data.getInt(2);
            upis.nBodoviMi =  data.getInt(3);
            upis.nBodoviVi =  data.getInt(4);
            upis.nZvanjaMi =  data.getInt(5);
            upis.nZvanjaVi =  data.getInt(6);
            upis.nMjesao =  data.getInt(7);
        }

        query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL1 + " = " + getLastLegId() + " AND " +
                COL2 + " = " + rbr;
        db.execSQL(query);

        query = "UPDATE " + TABLE_NAME + " SET " + COL2 + " = " + COL2 + " - 1 WHERE " +
                COL1 + " = " + getLastLegId() + " AND " + COL2 + " > " + rbr;
        db.execSQL(query);

        return upis;
    }

    public void updateUpis(int rbr, int bodoviMi, int bodoviVi, int zvanjaMi, int zvanjaVi){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL3 + " = " + bodoviMi + ", " + COL4 + " = " + bodoviVi +
                ", " + COL5 + " = " + zvanjaMi + ", " + COL6 + " = " + zvanjaVi + " WHERE " +
                COL1 + " = " + getLastLegId() + " AND " + COL2 + " = " + rbr;
        db.execSQL(query);
    }

}
