package com.example.belablok.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.belablok.R;

public class DialogBodovi extends DialogFragment {

    private static final String TAG = "DialogBodovi";

    public interface OdabirBodova{
        void sendInput(String sBodovi);
    }
    public OdabirBodova mOdabirBodova;

    private EditText txtBodovi;
    private Button btnPotvrdi, btnNatrag;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_bodovi_layout, container, false);

        txtBodovi = view.findViewById(R.id.txt_bodovi);
        btnPotvrdi = view.findViewById(R.id.btn_potvrdi);
        btnNatrag = view.findViewById(R.id.btn_natrag);

        btnPotvrdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sBodovi = "1001";
                try {
                    sBodovi = txtBodovi.getText().toString();
                }
                catch (NumberFormatException ex){
                    sBodovi = "1001";
                }
                mOdabirBodova.sendInput(sBodovi);
                getDialog().dismiss();
            }
        });

        btnNatrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOdabirBodova = (OdabirBodova) getActivity();
    }
}
