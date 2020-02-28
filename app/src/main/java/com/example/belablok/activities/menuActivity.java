package com.example.belablok.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.belablok.R;
import com.example.belablok.baze.DatabaseGames;
import com.example.belablok.baze.DatabaseLegs;
import com.example.belablok.dialog.DialogIgraci;
import com.example.belablok.klase.Game;
import com.example.belablok.klase.Leg;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class menuActivity extends AppCompatActivity implements DialogIgraci.Dalje {

    private static final String TAG = "menuActivity";

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private Button oBtnNovaIgra;
    private Button oBtnNastavi;
    private Button oBtnPovijest;
    private ImageButton ibtnPostavke;
    private String sTema;

    DatabaseGames mDatabaseGames = new DatabaseGames(this);
    DatabaseLegs mDatabaseLegs = new DatabaseLegs(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        sTema = mPreferences.getString("tema", "tamna");
        if(sTema.equals("tamna")){
            setTheme(R.style.AppThemeDark);
        }
        else if(sTema.equals("svjetla")){
            setTheme(R.style.AppThemeLight);
        }
        setContentView(R.layout.activity_menu);

        ibtnPostavke = findViewById(R.id.ibtn_postavke);
        ibtnPostavke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(menuActivity.this, PostavkeActivity.class));
            }
        });

        oBtnNovaIgra = findViewById(R.id.btn_nova_igra);
        oBtnNovaIgra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogIgraci dialogIgraci = new DialogIgraci();
                dialogIgraci.show(getSupportFragmentManager(), "DialogIgraci");
            }
        });

        oBtnNastavi = findViewById(R.id.btn_nastavi);
        oBtnNastavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDatabaseLegs.getData().size() > 0 && !mDatabaseLegs.isLegGotov(mDatabaseLegs.getLastId())){
                    startActivity(new Intent(menuActivity.this, RezultatActivity.class));
                }
            }
        });

        oBtnPovijest = findViewById(R.id.btn_povijest);
        oBtnPovijest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(menuActivity.this, PovijestActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sTema != mPreferences.getString("tema", "tamna")){
            recreate();
        }
    }

    @Override
    public void sendInput(String sSuigrac, String sLjevi, String sDesni, String sJa) {
        SimpleDateFormat normalniFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Calendar calendar = Calendar.getInstance();
        String datum = normalniFormat.format(calendar.getTime());
        Game game = new Game(sJa, sDesni, sSuigrac, sLjevi,0,0, datum);
        mDatabaseGames.addData(game);
        int duplo = Integer.parseInt(mPreferences.getString("duplo", "0"));
        int bodoviZaP = Integer.parseInt(mPreferences.getString("bodovi", "1001"));
        Leg leg = new Leg(mDatabaseGames.getLastId(),0,0,0, duplo, bodoviZaP,3);
        mDatabaseLegs.addData(leg);
        mEditor.putString("mjesa", "0");
        mEditor.commit();
        startActivity(new Intent(menuActivity.this, RezultatActivity.class));
    }
}
