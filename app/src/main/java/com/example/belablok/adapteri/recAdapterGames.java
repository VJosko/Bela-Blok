package com.example.belablok.adapteri;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.belablok.R;
import com.example.belablok.klase.Game;

import java.util.List;

public class recAdapterGames extends RecyclerView.Adapter<recAdapterGames.MyViewHolder> {

    List<Game> games;
    private OnGameListener mOnGameListener;

    //private List<Upis> upisi = UpisStorage.getInstance().readUpise();
    private List<Object> holderi;
    private String title = "Upisi";

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textViewRbr;
        public TextView textViewBodoviMi;
        public TextView textViewBodoviVi;
        OnGameListener onGameListener;

        public MyViewHolder(View v, OnGameListener onGameListener) {
            super(v);
            textViewRbr = v.findViewById(R.id.tv_rbr);
            textViewBodoviMi = v.findViewById(R.id.tv_bodovi_mi);
            textViewBodoviVi = v.findViewById(R.id.tv_bodovi_vi);
            this.onGameListener = onGameListener;

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onGameListener.onGameClick(getAdapterPosition());

        }
    }

    public interface OnGameListener{
        void onGameClick(int position);
    }


    public recAdapterGames(List<Game> game, OnGameListener onGameListener) {

        games = game;
        this.mOnGameListener = onGameListener;
    }

    @NonNull
    @Override
    public recAdapterGames.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_games, parent, false);
        MyViewHolder vh = new MyViewHolder(v, mOnGameListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull recAdapterGames.MyViewHolder holder, int position) {
        String sPosition = Integer.toString(position + 1);
        holder.textViewRbr.setText(sPosition);
        /*int nBodoviMi = games.get(position).nBodoviMi;
        int nZvanjaMi = games.get(position).nZvanjaMi;
        int nBodoviVi = games.get(position).nBodoviVi;
        int nZvanjaVi = games.get(position).nZvanjaVi;
        String sBodoviMi = Integer.toString(nBodoviMi + nZvanjaMi);
        String sBodoviVi = Integer.toString(nBodoviVi + nZvanjaVi);
        holder.textViewBodoviMi.setText(sBodoviMi);
        holder.textViewBodoviVi.setText(sBodoviVi);*/
    }

    @Override
    public int getItemCount() {
        return games.size();
    }
}
