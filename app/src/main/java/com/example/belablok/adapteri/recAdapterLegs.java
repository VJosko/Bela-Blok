package com.example.belablok.adapteri;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.belablok.R;
import com.example.belablok.klase.Leg;

import java.util.List;

public class recAdapterLegs extends RecyclerView.Adapter<recAdapterLegs.MyViewHolder> {

    List<Leg> legs;
    private OnLegListener mOnLegListener;

    //private List<Upis> upisi = UpisStorage.getInstance().readUpise();
    private List<Object> holderi;
    private String title = "legs";

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textViewGameId;
        public TextView textViewRbr;
        OnLegListener onLegListener;

        public MyViewHolder(View v, OnLegListener onLegListener) {
            super(v);
            textViewGameId = v.findViewById(R.id.tv_game_id);
            textViewRbr = v.findViewById(R.id.tv_rbr);
            this.onLegListener = onLegListener;

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onLegListener.onLegClick(getAdapterPosition());

        }
    }

    public interface OnLegListener{
        void onLegClick(int position);
    }


    public recAdapterLegs(List<Leg> leg, OnLegListener onLegListener) {

        legs = leg;
        this.mOnLegListener = onLegListener;
    }

    @NonNull
    @Override
    public recAdapterLegs.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_legs, parent, false);
        MyViewHolder vh = new MyViewHolder(v, mOnLegListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull recAdapterLegs.MyViewHolder holder, int position) {
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
        return legs.size();
    }
}
