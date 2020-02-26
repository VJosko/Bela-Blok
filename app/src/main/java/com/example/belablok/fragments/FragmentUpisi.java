package com.example.belablok.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.belablok.R;
import com.example.belablok.activities.PovijestActivity;
import com.example.belablok.adapteri.recAdapterGames;
import com.example.belablok.adapteri.recAdapterRezultati;
import com.example.belablok.adapteri.recAdapterUpisi;
import com.example.belablok.baze.DatabaseGames;
import com.example.belablok.baze.DatabaseLegs;
import com.example.belablok.baze.DatabaseUpisi;
import com.example.belablok.interfaces.IPovijestActivity;

public class FragmentUpisi extends Fragment implements recAdapterUpisi.OnUpisListener{

    private static final String TAG = "FragmentUpisi";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private IPovijestActivity mIPovijestActivity;
    private String mIncomingMessage = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            mIncomingMessage = bundle.getString(getString(R.string.intent_message));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upisi_layout, container, false);

        mIPovijestActivity.setNaslov("Upisi");

        DatabaseUpisi mDatabaseUpisi = new DatabaseUpisi(getActivity());

        //------------Recycler-view------------------
        recyclerView = view.findViewById(R.id.rec_upisi);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new recAdapterUpisi(mDatabaseUpisi.getData(Integer.parseInt(mIncomingMessage)), this);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mIPovijestActivity = (PovijestActivity)getActivity();
    }

    @Override
    public void onUpisClick(int position) {
    }
}
