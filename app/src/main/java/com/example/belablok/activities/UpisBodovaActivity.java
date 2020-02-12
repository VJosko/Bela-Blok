package com.example.belablok.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.belablok.R;
import com.example.belablok.baze.DatabaseLegs;
import com.example.belablok.baze.DatabaseUpisi;
import com.example.belablok.klase.Upis;

public class UpisBodovaActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private TextView tvZbrojeniRezultatMi;
    private TextView tvZbrojeniRezultatVi;
    private Button oBtnNoviUpis;
    private EditText oBodoviMi;
    private EditText oBodoviVi;
    private EditText oZvanjaMi;
    private EditText oZvanjaVi;
    private int nBodoviMi;
    private int nBodoviVi;
    private int nZvanjaMi;
    private int nZvanjaVi;
    private int nZbrojMi;
    private int nZbrojVi;

    DatabaseUpisi mDatabaseUpisi = new DatabaseUpisi(this);
    DatabaseLegs mDatabaseLegs = new DatabaseLegs(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upis_bodova);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        tvZbrojeniRezultatMi = findViewById(R.id.tv_zbrojeni_rezultat_mi);
        tvZbrojeniRezultatVi = findViewById(R.id.tv_zbrojeni_rezultat_vi);
        oBtnNoviUpis = findViewById(R.id.btn_upisi);
        oBodoviMi = findViewById(R.id.text_bodovi_mi);
        oBodoviVi = findViewById(R.id.text_bodovi_vi);
        oZvanjaMi = findViewById(R.id.text_zvanja_mi);
        oZvanjaVi = findViewById(R.id.text_zvanja_vi);

        oBodoviMi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Zbroj();
                try {
                    nBodoviMi = Integer.parseInt(oBodoviMi.getText().toString());
                }
                catch (NumberFormatException ex){
                    nBodoviMi = 0;
                }
                nBodoviVi = 162 - nBodoviMi;
                if(nBodoviVi < 0){
                    nBodoviVi = 0;
                }
                int nBodoviVi1;
                try {
                    nBodoviVi1 = Integer.parseInt(oBodoviVi.getText().toString());
                }
                catch (NumberFormatException ex){
                    nBodoviVi1 = 0;
                }
                if(nBodoviVi != nBodoviVi1) {
                    oBodoviVi.setText(Integer.toString(nBodoviVi));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        oBodoviVi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Zbroj();
                try {
                    nBodoviVi = Integer.parseInt(oBodoviVi.getText().toString());
                }
                catch (NumberFormatException ex){
                    nBodoviVi = 0;
                }
                nBodoviMi = 162 - nBodoviVi;
                if(nBodoviMi < 0){
                    nBodoviMi = 0;
                }
                int nBodoviMi1;
                try {
                    nBodoviMi1 = Integer.parseInt(oBodoviMi.getText().toString());
                }
                catch (NumberFormatException ex){
                    nBodoviMi1 = 0;
                }
                if(nBodoviMi != nBodoviMi1) {
                    oBodoviMi.setText(Integer.toString(nBodoviMi));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        oZvanjaMi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Zbroj();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        oZvanjaVi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Zbroj();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        oBtnNoviUpis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    nBodoviMi = Integer.parseInt(oBodoviMi.getText().toString());
                } catch (NumberFormatException ex){
                    nBodoviMi = 0;
                }
                try {
                    nBodoviVi = Integer.parseInt(oBodoviVi.getText().toString());
                } catch (NumberFormatException ex){
                    nBodoviVi = 0;
                }
                try {
                    nZvanjaMi = Integer.parseInt(oZvanjaMi.getText().toString());
                } catch (NumberFormatException ex){
                    nZvanjaMi = 0;
                }
                try {
                    nZvanjaVi = Integer.parseInt(oZvanjaVi.getText().toString());
                } catch (NumberFormatException ex){
                    nZvanjaVi = 0;
                }
                int nMjesa = 0;
                if(mDatabaseLegs.getLastId() == mDatabaseUpisi.getLastLegId()){
                    nMjesa = mDatabaseUpisi.getLastMjesao() + 1;
                    if(nMjesa == 4){
                        nMjesa = 0;
                    }
                }

                //rezultat
                mDatabaseLegs.updateRezultate(mDatabaseLegs.getLastId(), nBodoviMi + nZvanjaMi, nBodoviVi + nZvanjaVi );
                mDatabaseLegs.updateZadnjiMjesao(mDatabaseLegs.getLastId(), nMjesa);

                Upis upis =new Upis(mDatabaseLegs.getLastId(), mDatabaseUpisi.getNewRbr(mDatabaseLegs.getLastId()), nBodoviMi, nBodoviVi, nZvanjaMi, nZvanjaVi, nMjesa);
                //UpisStorage.getInstance().addUpis(upis);
                mDatabaseUpisi.addData(upis);
                startActivity(new Intent(UpisBodovaActivity.this, RezultatActivity.class));
            }
        });
    }

    void Zbroj(){
        try {
            nBodoviMi = Integer.parseInt(oBodoviMi.getText().toString());
        }
        catch (NumberFormatException ex){
            nBodoviMi = 0;
        }
        try {
            nZvanjaMi = Integer.parseInt(oZvanjaMi.getText().toString());
        } catch (NumberFormatException ex){
            nZvanjaMi = 0;
        }
        nZbrojMi = nBodoviMi + nZvanjaMi;
        tvZbrojeniRezultatMi.setText(Integer.toString(nZbrojMi));

        try {
            nBodoviVi = Integer.parseInt(oBodoviVi.getText().toString());
        }
        catch (NumberFormatException ex){
            nBodoviVi = 0;
        }
        try {
            nZvanjaVi = Integer.parseInt(oZvanjaVi.getText().toString());
        } catch (NumberFormatException ex){
            nZvanjaVi = 0;
        }
        nZbrojVi = nBodoviVi + nZvanjaVi;
        tvZbrojeniRezultatVi.setText(Integer.toString(nZbrojVi));
    }
}
