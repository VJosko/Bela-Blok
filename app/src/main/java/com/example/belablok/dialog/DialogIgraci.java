package com.example.belablok.dialog;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.belablok.R;

public class DialogIgraci extends DialogFragment {

    private static final String TAG = "DialogIgraci";

    public interface Dalje{
        void sendInput(String sSuigrac, String sLjevi, String sDesni, String sJa);
    }
    public Dalje mDalje;

    private EditText mSuigrac, mLjevi, mDesni, mJa;
    private String sSuigrac = "Suigrac";
    private String sLjevi = "Ljevi";
    private String sDesni = "Desni";
    private String sJa = "Ja";
    private Button btnDalje;
    private Button btnOdustani;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_igraci_layout, container, false);

        mSuigrac = view.findViewById(R.id.text_suigrac);
        mLjevi = view.findViewById(R.id.text_ljevi);
        mDesni = view.findViewById(R.id.text_desni);
        mJa = view.findViewById(R.id.text_ja);

        btnOdustani = view.findViewById(R.id.btn_odustani);
        btnOdustani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        btnDalje = view.findViewById(R.id.btn_dalje);
        btnDalje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String x;
                x = mSuigrac.getText().toString();
                if(!x.equals("")){
                    sSuigrac = x;
                }
                x = mLjevi.getText().toString();
                if(!x.equals("")){
                    sLjevi = x;
                }
                x = mDesni.getText().toString();
                if(!x.equals("")){
                    sDesni = x;
                }
                x = mJa.getText().toString();
                if(!x.equals("")){
                    sJa = x;
                }
                mDalje.sendInput(sSuigrac, sLjevi, sDesni, sJa);
                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mDalje = (Dalje) getActivity();
    }
}
