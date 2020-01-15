package com.example.belablok.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.belablok.R;
import com.example.belablok.UpisStorage;
import com.example.belablok.baze.DatabaseLegs;
import com.example.belablok.baze.DatabaseUpisi;
import com.example.belablok.klase.Upis;

public class UpisBodovaActivity extends AppCompatActivity {

    private Button oBtnNoviUpis;
    private EditText oBodoviMi;
    private EditText oBodoviVi;
    private EditText oZvanjaMi;
    private EditText oZvanjaVi;
    private int nBodoviMi;
    private int nBodoviVi;
    private int nZvanjaMi;
    private int nZvanjaVi;

    DatabaseUpisi mDatabaseUpisi = new DatabaseUpisi(this);
    DatabaseLegs mDatabaseLegs = new DatabaseLegs(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upis_bodova);

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
                Upis upis =new Upis(mDatabaseLegs.getLastId(), nBodoviMi, nBodoviVi, nZvanjaMi, nZvanjaVi);
                //UpisStorage.getInstance().addUpis(upis);
                mDatabaseUpisi.addData(upis);
                startActivity(new Intent(UpisBodovaActivity.this, MainActivity.class));
            }
        });
    }
}
