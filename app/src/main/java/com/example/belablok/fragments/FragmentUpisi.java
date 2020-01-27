package com.example.belablok.fragments;

import android.os.Bundle;
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

public class FragmentUpisi extends Fragment implements recAdapterUpisi.OnUpisListener{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upisi_layout, container, false);

        DatabaseUpisi mDatabaseUpisi = new DatabaseUpisi(getActivity());

        //------------Recycler-view------------------
        recyclerView = view.findViewById(R.id.rec_upisi);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new recAdapterUpisi(mDatabaseUpisi.getData(3), this);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onUpisClick(int position) {
    }
}
