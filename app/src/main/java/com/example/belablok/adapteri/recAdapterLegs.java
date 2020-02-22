package com.example.belablok.adapteri;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.belablok.R;
import com.example.belablok.baze.DatabaseUpisi;
import com.example.belablok.klase.Leg;

import java.util.List;

public class recAdapterLegs extends RecyclerView.Adapter<recAdapterLegs.MyViewHolder> {

    List<Leg> legs;
    private OnLegListener mOnLegListener;

    //private List<Upis> upisi = UpisStorage.getInstance().readUpise();
    private List<Object> holderi;
    private String title = "legs";

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageViewSlika;
        public TextView textViewRezMi;
        public TextView textViewRezVi;
        OnLegListener onLegListener;

        public MyViewHolder(View v, OnLegListener onLegListener) {
            super(v);
            imageViewSlika = v.findViewById(R.id.iv_slika);
            textViewRezMi = v.findViewById(R.id.tv_rezultat_mi);
            textViewRezVi = v.findViewById(R.id.tv_rezultat_vi);
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
        if(legs.get(position).nBodoviMi > legs.get(position).nBodoviVi){
            holder.imageViewSlika.setImageResource(R.drawable.ic_pobjeda);
        }
        else if(legs.get(position).nBodoviMi < legs.get(position).nBodoviVi){
            holder.imageViewSlika.setImageResource(R.drawable.ic_gubitak);
        }
        else{
            holder.imageViewSlika.setImageResource(R.drawable.ic_izjednaceno);
        }
        holder.textViewRezMi.setText(Integer.toString(legs.get(position).nBodoviMi));
        holder.textViewRezVi.setText(Integer.toString(legs.get(position).nBodoviVi));
    }

    @Override
    public int getItemCount() {
        return legs.size();
    }
}
