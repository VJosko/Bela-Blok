package com.example.belablok.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.belablok.R;
import com.example.belablok.baze.DatabaseGames;
import com.example.belablok.baze.DatabaseLegs;
import com.example.belablok.klase.Game;
import com.example.belablok.klase.Leg;

public class menuActivity extends AppCompatActivity {

    private Button oBtnNovaIgra;
    private Button oBtnNastavi;
    private Button oBtnPovijest;

    DatabaseGames mDatabaseGames = new DatabaseGames(this);
    DatabaseLegs mDatabaseLegs = new DatabaseLegs(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        oBtnNovaIgra = findViewById(R.id.btn_nova_igra);
        oBtnNovaIgra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Game game = new Game("ja", "desni", "suigrac", "ljevi");
                mDatabaseGames.addData(game);
                Leg leg = new Leg(mDatabaseGames.getLastId(), 0);
                mDatabaseLegs.addData(leg);
                startActivity(new Intent(menuActivity.this, MainActivity.class));
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
}