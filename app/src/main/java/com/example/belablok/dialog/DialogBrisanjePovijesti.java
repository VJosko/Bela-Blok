package com.example.belablok.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.belablok.R;

public class DialogBrisanjePovijesti extends DialogFragment {

    private static final String TAG = "DialogBrisanjePovijesti";

    public interface potvrdaBrisanja{
        void sendPotvrdu();
    }
    public potvrdaBrisanja mPotvrdaBrisanja;

    private Button btnNe, btnDa;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_brisanje_povijesti_layout, container, false);

        btnNe = view.findViewById(R.id.ne);
        btnDa = view.findViewById(R.id.da);

        btnNe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        btnDa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPotvrdaBrisanja.sendPotvrdu();
                getDialog().dismiss();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPotvrdaBrisanja = (potvrdaBrisanja) getActivity();
    }
}
