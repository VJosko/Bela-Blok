package com.example.belablok.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.belablok.R;
import com.example.belablok.baze.DatabaseGames;
import com.example.belablok.baze.DatabaseLegs;
import com.example.belablok.dialog.DialogIgraci;
import com.example.belablok.klase.Game;
import com.example.belablok.klase.Leg;

public class menuActivity extends AppCompatActivity implements DialogIgraci.Dalje {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private Button oBtnNovaIgra;
    private Button oBtnNastavi;
    private Button oBtnPovijest;
    private ImageView imgPostavke;

    DatabaseGames mDatabaseGames = new DatabaseGames(this);
    DatabaseLegs mDatabaseLegs = new DatabaseLegs(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        imgPostavke = findViewById(R.id.img_postavke);
        imgPostavke.setOnClickListener(new View.OnClickListener() {
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
                /*Game game = new Game("ja", "desni", "suigrac", "ljevi");
                mDatabaseGames.addData(game);
                Leg leg = new Leg(mDatabaseGames.getLastId(), 0);
                mDatabaseLegs.addData(leg);
                startActivity(new Intent(menuActivity.this, MainActivity.class));*/
            }
        });

        oBtnNastavi = findViewById(R.id.btn_nastavi);
        oBtnNastavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(menuActivity.this, MainActivity.class));
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
    public void sendInput(String sSuigrac, String sLjevi, String sDesni, String sJa) {
        Game game = new Game(sJa, sDesni, sSuigrac, sLjevi,0,0);
        mDatabaseGames.addData(game);
        int duplo = Integer.parseInt(mPreferences.getString("duplo", "0"));
        Leg leg = new Leg(mDatabaseGames.getLastId(),0,0,0, duplo,1000,3);
        mDatabaseLegs.addData(leg);
        startActivity(new Intent(menuActivity.this, MainActivity.class));
    }
}
