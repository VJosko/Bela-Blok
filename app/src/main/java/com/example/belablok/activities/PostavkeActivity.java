package com.example.belablok.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.belablok.R;
import com.example.belablok.baze.DatabaseLegs;

public class PostavkeActivity extends AppCompatActivity {

    private static final String TAG = "PostavkeActivity";

    DatabaseLegs mDatabaseLegs = new DatabaseLegs(this);

    CheckBox cbDuplo, cbBodovi, cbEkran;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postavke);

        cbDuplo = findViewById(R.id.cb_duplo);
        cbBodovi = findViewById(R.id.cb_bodovi);
        cbEkran = findViewById(R.id.cb_ekran);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        checkSharedPreferences();

        cbDuplo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mEditor.putString("duplo", "1");
                    if(!mDatabaseLegs.isLegGotov(mDatabaseLegs.getLastId())){
                        mDatabaseLegs.updateDuplo(mDatabaseLegs.getLastId(), "1");
                    }
                }
                else{
                    mEditor.putString("duplo", "0");
                    if(!mDatabaseLegs.isLegGotov(mDatabaseLegs.getLastId())){
                        mDatabaseLegs.updateDuplo(mDatabaseLegs.getLastId(), "0");
                    }
                }
                mEditor.commit();
            }
        });

    }

    private void checkSharedPreferences(){
        String duplo = mPreferences.getString("duplo", "0");
        String bodovi = mPreferences.getString("bodovi", "1000");
        String ekran = mPreferences.getString("ekran", "0");

        if(duplo.equals("1")){
            cbDuplo.setChecked(true);
        }
        if(bodovi.equals("1000")){
            cbBodovi.setChecked(true);
        }
        if(ekran.equals("1")){
            cbEkran.setChecked(true);
        }
    }
}
