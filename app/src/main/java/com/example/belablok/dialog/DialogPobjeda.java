package com.example.belablok.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.belablok.R;

public class DialogPobjeda extends DialogFragment {

    private static final String TAG = "DialogPobjeda";

    public interface gumb{
        void sendOdabir(boolean odabir);
    }

    private TextView tvPobjeda;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_pobjeda_layout, container, false);

        Bundle bundle = getArguments();
        String sPobjeda = bundle.getString("Text", "Greška");
        tvPobjeda = view.findViewById(R.id.tv_pobjednik);
        tvPobjeda.setText(sPobjeda);

        return view;
    }
}
