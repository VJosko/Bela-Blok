package com.example.belablok.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

public class RezultatActivity extends AppCompatActivity implements recAdapterRezultati.OnUpisListener, DialogPobjeda.Gumb {

    private static final String TAG = "RezultatActivity";

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private TextView tvLegsMi;
    private TextView tvLegsVi;
    private TextView oTvDijeli;
    private TextView oTvMiRezultat;
    private TextView oTvViRezultat;
    private ImageButton oIbtnNatrag, oIbtnPostavke;
    private Button oBtnNoviUpis;
    private ImageButton oBtnIduci;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String sTema;

    DatabaseGames mDatabaseGames = new DatabaseGames(this);
    DatabaseUpisi mDatabaseUpisi = new DatabaseUpisi(this);
    DatabaseLegs mDataBaseLegs = new DatabaseLegs(this);

    private ArrayList<Upis> rezulati;

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
        setContentView(R.layout.activity_rezultat);

        oIbtnPostavke = findViewById(R.id.ibtn_postavke);
        oIbtnPostavke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RezultatActivity.this, PostavkeActivity.class));
            }
        });

        oIbtnNatrag = findViewById(R.id.ibtn_natrag);
        oBtnNoviUpis = findViewById(R.id.btn_novi_upis);

        oIbtnNatrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RezultatActivity.this, menuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        oBtnNoviUpis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RezultatActivity.this, UpisBodovaActivity.class);
                intent.putExtra("uredi", "-1");
                startActivity(intent);
            }
        });

        oBtnIduci = findViewById(R.id.ibtn_iduci);
        oBtnIduci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Iduci();
            }
        });

        //-----------------Dijeli--------------------
        Dijeli();

        //------------------Rezultat-----------------
        Rezultati();

        //------------Recycler-view------------------
        rezulati = mDatabaseUpisi.getData(mDataBaseLegs.getLastId());

        recyclerView = findViewById(R.id.recycler_rezultati);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new recAdapterRezultati(rezulati, this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.smoothScrollToPosition(rezulati.size());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sTema != mPreferences.getString("tema", "tamna")){
            recreate();
        }
    }

    public void Dijeli(){
        oTvDijeli = findViewById(R.id.tv_dijeli);
        ArrayList<String> igraci = mDatabaseGames.getCurrentPlayers();
        int nMjesa;
        if(mDataBaseLegs.getLastId() == mDatabaseUpisi.getLastLegId()){
            nMjesa = mDatabaseUpisi.getLastMjesao() + 1;
            if(nMjesa == 4){
                nMjesa = 0;
            }
        }
        else{
            nMjesa = Integer.parseInt(mPreferences.getString("mjesa", "0"));
        }
        oTvDijeli.setText(igraci.get(nMjesa));
    }

    public void Iduci(){
        ArrayList<String> igraci = mDatabaseGames.getCurrentPlayers();
        int nMjesa = Integer.parseInt(mPreferences.getString("mjesa", "0")) + 1;
        if(nMjesa > 3){
            nMjesa = 0;
        }
        String sMjesa = Integer.toString(nMjesa);
        if(mDataBaseLegs.getLastId() == mDatabaseUpisi.getLastLegId()){
            mDatabaseUpisi.updateLastMjesao();
            mEditor.putString("mjesa", sMjesa);
        }
        else{
            mEditor.putString("mjesa", sMjesa);
        }
        oTvDijeli = findViewById(R.id.tv_dijeli);
        oTvDijeli.setText(igraci.get(nMjesa));
        mEditor.commit();
    }

    public void Rezultati(){
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
            int isDuplo = 0;
            int bodoviZaP = Integer.parseInt(mPreferences.getString("bodovi","1001"));
            if(mDataBaseLegs.getLastLegDuplo() == 1 && nVi * 2 < bodoviZaP){
                isDuplo = 1;
            }
            mDatabaseGames.updateRezultat(mDatabaseGames.getLastId(), true, isDuplo);
            int nDupli = Integer.parseInt(mPreferences.getString("duplo","0"));
            int nBodovi = Integer.parseInt(mPreferences.getString("bodovi","1001"));
            Leg leg = new Leg(mDatabaseGames.getLastId(), mDataBaseLegs.getLastRbr() + 1, 0, 0, nDupli, nBodovi,0);
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
            int isDuplo = 0;
            int bodoviZaP = Integer.parseInt(mPreferences.getString("bodovi","1001"));
            if(mDataBaseLegs.getLastLegDuplo() == 1 && nMi * 2 < bodoviZaP){
                isDuplo = 1;
            }
            mDatabaseGames.updateRezultat(mDatabaseGames.getLastId(), false, isDuplo);
            int nDupli = Integer.parseInt(mPreferences.getString("duplo","0"));
            int nBodovi = Integer.parseInt(mPreferences.getString("bodovi","1001"));
            Leg leg = new Leg(mDatabaseGames.getLastId(), mDataBaseLegs.getLastRbr() + 1, 0, 0, nDupli, nBodovi,0);
            mDataBaseLegs.addData(leg);
            oTvMiRezultat.setText("0");
            oTvViRezultat.setText("0");
            DialogPobjeda dialogPobjeda = new DialogPobjeda();
            Bundle bundle = new Bundle();
            bundle.putString("Text", "Vi ste pobjedili!");
            dialogPobjeda.setArguments(bundle);
            dialogPobjeda.show(getSupportFragmentManager(), "DialogPobjeda");
        }

        tvLegsMi = findViewById(R.id.tv_legs_mi);
        tvLegsVi = findViewById(R.id.tv_legs_vi);
        int rez[] = mDatabaseGames.getRezultat(mDatabaseGames.getLastId());
        tvLegsMi.setText(Integer.toString(rez[0]));
        tvLegsVi.setText(Integer.toString(rez[1]));
    }

    @Override
    public void onUpisClick(int position){
        Intent intent = new Intent(RezultatActivity.this, UpisBodovaActivity.class);
        intent.putExtra("uredi", Integer.toString(position));
        startActivity(intent);
    }

    @Override
    public void onIzbrisiClick(int position) {
        Upis upis = mDatabaseUpisi.DeleteUpis(position);
        mDataBaseLegs.updateRezultate(mDataBaseLegs.getLastId(),-(upis.nBodoviMi + upis.nZvanjaMi),-(upis.nBodoviVi + upis.nZvanjaVi));
        rezulati.remove(position);
        mAdapter.notifyItemRemoved(position);
        Dijeli();
        Rezultati();
    }

    @Override
    public void sendOdabir(boolean odabir) {
        if(odabir) {
            mDataBaseLegs.deleteLeg(mDataBaseLegs.getLastId());
            startActivity(new Intent(RezultatActivity.this, menuActivity.class));
        }
        else
        {
        }
    }
}
















