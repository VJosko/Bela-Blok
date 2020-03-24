package com.vudrag.belablok.adapteri;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vudrag.belablok.R;
import com.vudrag.belablok.klase.Game;

import java.util.List;

public class recAdapterGames extends RecyclerView.Adapter<recAdapterGames.MyViewHolder>{

    List<Game> games;
    private OnGameListener mOnGameListener;


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageViewSlika;
        public TextView textViewPobjedeMi;
        public TextView textViewPobjedeVi;
        public TextView textViewDatum;
        OnGameListener onGameListener;

        public MyViewHolder(View v, OnGameListener onGameListener) {
            super(v);
            imageViewSlika = v.findViewById(R.id.iv_slika);
            textViewPobjedeMi = v.findViewById(R.id.tv_pobjede_mi);
            textViewPobjedeVi = v.findViewById(R.id.tv_pobjede_vi);
            textViewDatum = v.findViewById(R.id.tv_datum);
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
        if(games.get(position).nPobjedeMi > games.get(position).nPobjedeVi){
            holder.imageViewSlika.setImageResource(R.drawable.ic_pobjeda);
        }
        else if(games.get(position).nPobjedeMi < games.get(position).nPobjedeVi){
            holder.imageViewSlika.setImageResource(R.drawable.ic_gubitak);
        }
        else{
            holder.imageViewSlika.setImageResource(R.drawable.ic_izjednaceno);
        }
        holder.textViewPobjedeMi.setText(Integer.toString(games.get(position).nPobjedeMi));
        holder.textViewPobjedeVi.setText(Integer.toString(games.get(position).nPobjedeVi));
        holder.textViewDatum.setText(games.get(position).sDatum);
    }

    @Override
    public int getItemCount() {
        return games.size();
    }
}
