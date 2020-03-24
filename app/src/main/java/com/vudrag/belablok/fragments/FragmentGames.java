package com.vudrag.belablok.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vudrag.belablok.R;
import com.vudrag.belablok.adapteri.recAdapterGames;
import com.vudrag.belablok.baze.DatabaseGames;
import com.vudrag.belablok.interfaces.IPovijestActivity;

public class FragmentGames extends Fragment implements recAdapterGames.OnGameListener{

    private static final String TAG = "FragmentGames";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private IPovijestActivity mIPovijestActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_games_layout, container, false);

        mIPovijestActivity.setNaslov("Igre");

        DatabaseGames mDatabaseGames = new DatabaseGames(getActivity());

        //------------Recycler-view------------------
        recyclerView = view.findViewById(R.id.rec_games);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new recAdapterGames(mDatabaseGames.getData(), this);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mIPovijestActivity = (IPovijestActivity)getActivity();
    }

    @Override
    public void onGameClick(int position) {
        int nId = position + 1;
        String sId = Integer.toString(nId);
        mIPovijestActivity.inflateFragment(1, sId);
    }
}
