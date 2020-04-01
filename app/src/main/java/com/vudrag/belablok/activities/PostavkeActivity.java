package com.vudrag.belablok.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.vudrag.belablok.R;
import com.vudrag.belablok.baze.DatabaseGames;
import com.vudrag.belablok.baze.DatabaseLegs;
import com.vudrag.belablok.baze.DatabaseUpisi;
import com.vudrag.belablok.dialog.DialogBodovi;
import com.vudrag.belablok.dialog.DialogBrisanjePovijesti;
import com.vudrag.belablok.dialog.DialogMacki;

public class PostavkeActivity extends AppCompatActivity implements DialogBodovi.OdabirBodova,
        DialogBrisanjePovijesti.potvrdaBrisanja, DialogMacki.OdabirMacki {

    private static final String TAG = "PostavkeActivity";

    DatabaseGames mDatabaseGames = new DatabaseGames(this);
    DatabaseLegs mDatabaseLegs = new DatabaseLegs(this);
    DatabaseUpisi mDatabaseUpisi = new DatabaseUpisi(this);

    private CheckBox cbDuplo, cbTema, cbRacunanje;
    private Button btnBodovi, btnMacki;
    private TextView tvBodovi;
    private ImageButton ibtnNatrag;
    private Button btnObrisi;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        String sTema = mPreferences.getString("tema", "tamna");
        if(sTema.equals("tamna")){
            setTheme(R.style.AppThemeDark);
        }
        else if(sTema.equals("svjetla")){
            setTheme(R.style.AppThemeLight);
        }
        setContentView(R.layout.activity_postavke);

        ibtnNatrag = findViewById(R.id.ibtn_natrag);
        ibtnNatrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cbRacunanje = findViewById(R.id.cb_racunanje);
        btnMacki =findViewById(R.id.btn_macki);
        cbDuplo = findViewById(R.id.cb_duplo);
        btnBodovi = findViewById(R.id.btn_bodovi);
        tvBodovi = findViewById(R.id.tv_bodovi);
        cbTema = findViewById(R.id.cb_tema);
        btnObrisi = findViewById(R.id.btn_obrisi_povijest);

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

        //Racunanje ako padas
        cbRacunanje.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mEditor.putString("racunanje", "1");
                }
                else{
                    mEditor.putString("racunanje", "0");
                }
                mEditor.commit();
            }
        });

        //Tema
        cbTema.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mEditor.putString("tema", "tamna");
                    mEditor.commit();
                    recreate();
                }
                else{
                    mEditor.putString("tema", "svjetla");
                    mEditor.commit();
                    recreate();
                }
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

        //Odabir macki
        btnMacki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogMacki dialogMacki = new DialogMacki();
                dialogMacki.show(getSupportFragmentManager(), "DialogMacki");
            }
        });

        //Brisanje povijesti
        btnObrisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogBrisanjePovijesti dialogBrisanjePovijesti = new DialogBrisanjePovijesti();
                dialogBrisanjePovijesti.show(getSupportFragmentManager(), "DialogBrisanjePovijesti");
            }
        });
    }

    private void checkSharedPreferences(){
        String duplo = mPreferences.getString("duplo", "0");
        String bodovi = mPreferences.getString("bodovi", "1001");
        String tema = mPreferences.getString("tema", "tamna");
        String racunanje = mPreferences.getString("racunanje", "1");
        int macki = mPreferences.getInt("macki", 90);

        if(duplo.equals("1")){
            cbDuplo.setChecked(true);
        }

        btnBodovi.setText(bodovi);

        if(tema.equals("tamna")){
            cbTema.setChecked(true);
        }

        if(racunanje.equals("1")){
            cbRacunanje.setChecked(true);
        }

        btnMacki.setText(Integer.toString(macki));
    }

    @Override
    public void sendInput(String sBodovi) {
        mEditor.putString("bodovi", sBodovi);
        mEditor.commit();
        Log.d(TAG, "sendInput: " + mPreferences.getString("bodovi","-1") + "----------------------");
        btnBodovi.setText(sBodovi);
        if(!mDatabaseLegs.isLegGotov(mDatabaseLegs.getLastId())){
            mDatabaseLegs.updateBodoveZaP(mDatabaseLegs.getLastId(), Integer.parseInt(sBodovi));
        }
    }

    @Override
    public void sendPotvrdu() {
        mDatabaseGames.isprazniBazu();
        mDatabaseLegs.isprazniBazu();
        mDatabaseUpisi.isprazniBazu();
    }

    @Override
    public void sendInputMacki(int sBodovi) {
        mEditor.putInt("macki", sBodovi);
        mEditor.commit();
        btnMacki.setText(Integer.toString(sBodovi));
    }
}
