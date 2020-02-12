package com.example.belablok.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.belablok.R;
import com.example.belablok.baze.DatabaseLegs;
import com.example.belablok.dialog.DialogBodovi;

public class PostavkeActivity extends AppCompatActivity implements DialogBodovi.OdabirBodova {

    private static final String TAG = "PostavkeActivity";

    DatabaseLegs mDatabaseLegs = new DatabaseLegs(this);

    private CheckBox cbDuplo, cbEkran;
    private Button btnBodovi;
    private TextView tvBodovi;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postavke);

        cbDuplo = findViewById(R.id.cb_duplo);
        btnBodovi = findViewById(R.id.btn_bodovi);
        tvBodovi = findViewById(R.id.tv_bodovi);
        cbEkran = findViewById(R.id.cb_ekran);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        checkSharedPreferences();

        //Dupli bodovi ako je ispod male
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

        //Odabir bodova
        btnBodovi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogBodovi dialogBodovi = new DialogBodovi();
                dialogBodovi.show(getSupportFragmentManager(), "DialogBodovi");
            }
        });
    }

    private void checkSharedPreferences(){
        String duplo = mPreferences.getString("duplo", "0");
        String bodovi = mPreferences.getString("bodovi", "1001");
        String ekran = mPreferences.getString("ekran", "0");

        if(duplo.equals("1")){
            cbDuplo.setChecked(true);
        }
        tvBodovi.setText(bodovi);
        if(ekran.equals("1")){
            cbEkran.setChecked(true);
        }
    }

    @Override
    public void sendInput(String sBodovi) {
        mEditor.putString("bodovi", sBodovi);
        mEditor.commit();
        Log.d(TAG, "sendInput: " + mPreferences.getString("bodovi","-1") + "----------------------");
        tvBodovi.setText(sBodovi);
        if(!mDatabaseLegs.isLegGotov(mDatabaseLegs.getLastId())){
            mDatabaseLegs.updateBodoveZaP(mDatabaseLegs.getLastId(), Integer.parseInt(sBodovi));
        }
    }
}
