package com.example.belablok.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.belablok.R;
import com.example.belablok.baze.DatabaseLegs;
import com.example.belablok.baze.DatabaseUpisi;
import com.example.belablok.klase.Upis;

public class UpisBodovaActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private ImageButton ibtn_natrag, ibtn_postavke;
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
    private int uredi;
    private String sTema;

    DatabaseUpisi mDatabaseUpisi = new DatabaseUpisi(this);
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
        setContentView(R.layout.activity_upis_bodova);

        tvZbrojeniRezultatMi = findViewById(R.id.tv_zbrojeni_rezultat_mi);
        tvZbrojeniRezultatVi = findViewById(R.id.tv_zbrojeni_rezultat_vi);
        oBtnNoviUpis = findViewById(R.id.btn_upisi);
        oBodoviMi = findViewById(R.id.text_bodovi_mi);
        oBodoviVi = findViewById(R.id.text_bodovi_vi);
        oZvanjaMi = findViewById(R.id.text_zvanja_mi);
        oZvanjaVi = findViewById(R.id.text_zvanja_vi);

        ibtn_natrag = findViewById(R.id.ibtn_natrag);
        ibtn_natrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpisBodovaActivity.this, RezultatActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        ibtn_postavke = findViewById(R.id.ibtn_postavke);
        ibtn_postavke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpisBodovaActivity.this, PostavkeActivity.class));
            }
        });

        uredi = Integer.parseInt(getIntent().getStringExtra("uredi"));

        if(uredi > -1){
            int[] bodovi = mDatabaseUpisi.getBodove(uredi);
            tvZbrojeniRezultatMi.setText(Integer.toString(bodovi[0] + bodovi[2]));
            tvZbrojeniRezultatVi.setText(Integer.toString(bodovi[1] + bodovi[3]));
            if(bodovi[0] != 0){
                oBodoviMi.setText(Integer.toString(bodovi[0]));
            }
            if(bodovi[1] != 0){
                oBodoviVi.setText(Integer.toString(bodovi[1]));
            }
            if(bodovi[2] != 0){
                oZvanjaMi.setText(Integer.toString(bodovi[2]));
            }
            if(bodovi[3] != 0){
                oZvanjaVi.setText(Integer.toString(bodovi[3]));
            }
        }

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
                //vracanje----cursora
                String nula = "0";
                int cursor;
                try {
                    nula = oBodoviMi.getText().toString();
                    cursor = Integer.parseInt(nula);
                }
                catch (NumberFormatException ex){
                    nula = "0";
                    cursor = -1;
                }

                if(cursor > 100){
                    oBodoviMi.setSelection(3);
                }
                if(cursor > 162){
                    oBodoviMi.setText("162");
                }
                //makivanje----nule
                if(nula.charAt(0) == '0' && nula.length() > 1){
                    int duljina = nula.length();
                    String broj = "";
                    for(int i = 1; i < duljina; i++){
                        broj = broj + nula.charAt(i);
                    }
                    oBodoviMi.setText(broj);
                    oBodoviMi.setSelection(duljina - 1);
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
                //vracanje----cursora
                String nula = "0";
                int cursor;
                try {
                    nula = oBodoviVi.getText().toString();
                    cursor = Integer.parseInt(nula);
                }
                catch (NumberFormatException ex){
                    nula = "0";
                    cursor = -1;
                }

                if(cursor > 100){
                    oBodoviVi.setSelection(3);
                }
                if(cursor > 162){
                    oBodoviVi.setText("162");
                }
                //makivanje----nule
                if(nula.charAt(0) == '0' && nula.length() > 1){
                    int duljina = nula.length();
                    String broj = "";
                    for(int i = 1; i < duljina; i++){
                        broj = broj + nula.charAt(i);
                    }
                    oBodoviVi.setText(broj);
                    oBodoviVi.setSelection(duljina - 1);
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
                if(uredi == -1){
                    NoviUpis();
                }
                else{
                    UrediUpis();
                }
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

    void UrediUpis(){
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
        int[] bodovi = mDatabaseUpisi.getBodove(uredi);
        mDatabaseLegs.updateRezultate(mDatabaseLegs.getLastId(),-(bodovi[0] + bodovi[2]), -(bodovi[1] + bodovi[3]));
        mDatabaseLegs.updateRezultate(mDatabaseLegs.getLastId(), nBodoviMi + nZvanjaMi, nBodoviVi + nZvanjaVi);
        mDatabaseUpisi.updateUpis(uredi, nBodoviMi, nBodoviVi, nZvanjaMi, nZvanjaVi);
        startActivity(new Intent(UpisBodovaActivity.this, RezultatActivity.class));
    }

    void NoviUpis()
    {
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
        if(nBodoviMi != nBodoviVi) {
            int nMjesa;
            if (mDatabaseLegs.getLastId() == mDatabaseUpisi.getLastLegId()) {
                nMjesa = mDatabaseUpisi.getLastMjesao() + 1;
                if (nMjesa == 4) {
                    nMjesa = 0;
                }
            } else {
                nMjesa = Integer.parseInt(mPreferences.getString("mjesa", "0"));
            }

            //rezultat
            mDatabaseLegs.updateRezultate(mDatabaseLegs.getLastId(), nBodoviMi + nZvanjaMi, nBodoviVi + nZvanjaVi);
            mDatabaseLegs.updateZadnjiMjesao(mDatabaseLegs.getLastId(), nMjesa);

            int Mjesa = Integer.parseInt(mPreferences.getString("mjesa", "0")) + 1;
            if (Mjesa > 3) {
                Mjesa = 0;
            }
            String sMjesa = Integer.toString(Mjesa);
            mEditor.putString("mjesa", sMjesa);
            mEditor.commit();

            Upis upis = new Upis(mDatabaseLegs.getLastId(), mDatabaseUpisi.getNewRbr(mDatabaseLegs.getLastId()), nBodoviMi, nBodoviVi, nZvanjaMi, nZvanjaVi, nMjesa);
            mDatabaseUpisi.addData(upis);
            Intent intent = new Intent(UpisBodovaActivity.this, RezultatActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else{
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast,
                    (ViewGroup) findViewById(R.id.custom_toast_container));

            TextView text = (TextView) layout.findViewById(R.id.text);
            text.setText("Pogrešan upis");

            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        }
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
