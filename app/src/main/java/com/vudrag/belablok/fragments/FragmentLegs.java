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
import com.vudrag.belablok.activities.PovijestActivity;
import com.vudrag.belablok.adapteri.recAdapterLegs;
import com.vudrag.belablok.baze.DatabaseLegs;
import com.vudrag.belablok.interfaces.IPovijestActivity;

public class FragmentLegs extends Fragment implements recAdapterLegs.OnLegListener{

    private static final String TAG = "FragmentLegs";

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
        View view = inflater.inflate(R.layout.fragment_legs_layout, container, false);

        mIPovijestActivity.setNaslov("Runde");


        DatabaseLegs mDatabaseLegs = new DatabaseLegs(getActivity());

        //------------Recycler-view------------------
        recyclerView = view.findViewById(R.id.rec_legs);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new recAdapterLegs(mDatabaseLegs.getLegsByGameId(Integer.parseInt(mIncomingMessage)), this);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mIPovijestActivity = (PovijestActivity)getActivity();
    }

    @Override
    public void onLegClick(int position) {
        int nId = position;
        DatabaseLegs mDatabaseLegs = new DatabaseLegs(getActivity());
        String sId = Integer.toString(mDatabaseLegs.getLegId(nId, Integer.parseInt(mIncomingMessage)));
        mIPovijestActivity.inflateFragment(2, sId);
    }
}
