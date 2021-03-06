package com.vudrag.belablok.dialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.vudrag.belablok.R;

public class DialogMacki extends DialogFragment {

    private static final String TAG = "DialogMacki";

    public interface OdabirMacki{
        void sendInputMacki(int sBodovi);
    }
    public OdabirMacki mOdabirBodova;

    private EditText txtBodovi;
    private Button btnPotvrdi, btnNatrag;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_macki_layout, container, false);

        txtBodovi = view.findViewById(R.id.txt_bodovi);
        btnPotvrdi = view.findViewById(R.id.btn_potvrdi);
        btnNatrag = view.findViewById(R.id.btn_natrag);

        btnPotvrdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sBodovi = "90";
                try {
                    sBodovi = txtBodovi.getText().toString();
                }
                catch (NumberFormatException ex){
                    sBodovi = "90";
                }
                mOdabirBodova.sendInputMacki(Integer.parseInt(sBodovi));
                getDialog().dismiss();
            }
        });

        btnNatrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        mPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEditor = mPreferences.edit();

        txtBodovi.setHint(Integer.toString(mPreferences.getInt("macki", 90)));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOdabirBodova = (OdabirMacki) getActivity();
    }
}
