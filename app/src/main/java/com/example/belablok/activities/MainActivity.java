package com.example.belablok.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.belablok.R;
import com.example.belablok.baze.DatabaseGames;
import com.example.belablok.baze.DatabaseLegs;
import com.example.belablok.baze.DatabaseUpisi;
import com.example.belablok.dialog.DialogPobjeda;
import com.example.belablok.klase.Leg;
import com.example.belablok.klase.Upis;
import com.example.belablok.adapteri.recAdapterRezultati;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements recAdapterRezultati.OnUpisListener, DialogPobjeda.gumb {

    private TextView oTvDijeli;
    private TextView oTvMiRezultat;
    private TextView oTvViRezultat;
    private ImageView oImgNatrag;
    private ImageView imgPostavke;
    private Button oBtnNoviUpis;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    DatabaseGames mDatabaseGames = new DatabaseGames(this);
    DatabaseUpisi mDatabaseUpisi = new DatabaseUpisi(this);
    DatabaseLegs mDataBaseLegs = new DatabaseLegs(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgPostavke = findViewById(R.id.img_postavke);
        imgPostavke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PostavkeActivity.class));
            }
        });

        oImgNatrag = findViewById(R.id.img_natrag);
        oBtnNoviUpis = findViewById(R.id.btn_novi_upis);

        oImgNatrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, menuActivity.class));
            }
        });

        oBtnNoviUpis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UpisBodovaActivity.class));
            }
        });

        //-----------------Dijeli--------------------
        oTvDijeli = findViewById(R.id.tv_dijeli);
        ArrayList<String> igraci = mDatabaseGames.getCurrentPlayers();
        oTvDijeli.setText(igraci.get(0));

        //------------------Rezultat-----------------
        List<Upis> upisi = mDatabaseUpisi.getData(mDataBaseLegs.getLastId());
        int nMi = 0;
        int nVi = 0;
        for (Upis u: upisi) {
            nMi += u.nBodoviMi + u.nZvanjaMi;
            nVi += u.nBodoviVi + u.nZvanjaVi;
        }
        oTvMiRezultat = findViewById(R.id.tv_mi_rezultat);
        oTvViRezultat = findViewById(R.id.tv_vi_rezultat);

        oTvMiRezultat.setText(Integer.toString(nMi));
        oTvViRezultat.setText(Integer.toString(nVi));

        if(mDataBaseLegs.isLegGotov(mDataBaseLegs.getLastId()) && nVi < nMi){
            mDatabaseGames.updateRezultat(mDatabaseGames.getLastId(), true);
            Leg leg = new Leg(mDatabaseGames.getLastId(), mDataBaseLegs.getLastRbr() + 1, nMi,nVi,0,100,0);
            mDataBaseLegs.addData(leg);
            oTvMiRezultat.setText("0");
            oTvViRezultat.setText("0");
            DialogPobjeda dialogPobjeda = new DialogPobjeda();
            Bundle bundle = new Bundle();
            bundle.putString("Text", "Mi smo pobjedili!");
            dialogPobjeda.setArguments(bundle);
            dialogPobjeda.show(getSupportFragmentManager(), "DialogPobjeda");
        }
        else if(mDataBaseLegs.isLegGotov(mDataBaseLegs.getLastId()) && nMi < nVi){
            mDatabaseGames.updateRezultat(mDatabaseGames.getLastId(), false);
            Leg leg = new Leg(mDatabaseGames.getLastId(), mDataBaseLegs.getLastRbr() + 1, nMi, nVi,0,100,0);
            mDataBaseLegs.addData(leg);
            oTvMiRezultat.setText("0");
            oTvViRezultat.setText("0");
            DialogPobjeda dialogPobjeda = new DialogPobjeda();
            Bundle bundle = new Bundle();
            bundle.putString("Text", "Vi ste pobjedili!");
            dialogPobjeda.setArguments(bundle);
            dialogPobjeda.show(getSupportFragmentManager(), "DialogPobjeda");
        }

        //------------Recycler-view------------------
        recyclerView = findViewById(R.id.recycler_rezultati);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new recAdapterRezultati(mDatabaseUpisi.getData(mDataBaseLegs.getLastId()), this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onUpisClick(int position){
        startActivity(new Intent(MainActivity.this, UpisBodovaActivity.class));
    }

    @Override
    public void sendOdabir(boolean odabir) {

    }
}
