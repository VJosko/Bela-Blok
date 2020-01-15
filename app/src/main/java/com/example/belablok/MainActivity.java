package com.example.belablok;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView oTvMiRezultat;
    private TextView oTvViRezultat;
    private ImageView oImgNatrag;
    private Button oBtnNoviUpis;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        //------------Recycler-view------------------
        recyclerView = findViewById(R.id.recycler_rezultati);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new recAdapterRezultati();
        recyclerView.setAdapter(mAdapter);

        //------------------Rezultat-----------------
        List<Upis> upisi = UpisStorage.getInstance().readUpise();
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
    }
}
