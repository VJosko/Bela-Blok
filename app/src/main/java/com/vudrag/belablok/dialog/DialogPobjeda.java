package com.vudrag.belablok.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.vudrag.belablok.R;

public class DialogPobjeda extends DialogFragment {

    private static final String TAG = "DialogPobjeda";

    public interface Gumb{
        void sendOdabir(boolean odabir);
    }
    public Gumb gumb;

    private TextView tvPobjeda;
    private Button btnNastavi, btnIzadi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_pobjeda_layout, container, false);

        btnNastavi = view.findViewById(R.id.btn_nastavi);
        btnIzadi = view.findViewById(R.id.btn_izadi);

        Bundle bundle = getArguments();
        String sPobjeda = bundle.getString("Text", "Greška");
        tvPobjeda = view.findViewById(R.id.tv_pobjednik);
        tvPobjeda.setText(sPobjeda);

        btnNastavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        btnIzadi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gumb.sendOdabir(true);
                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        gumb = (Gumb) getActivity();
    }
}
