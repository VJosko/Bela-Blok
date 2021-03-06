package com.vudrag.belablok.adapteri;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vudrag.belablok.R;
import com.vudrag.belablok.klase.Upis;

import java.util.List;

public class recAdapterRezultati extends RecyclerView.Adapter<recAdapterRezultati.MyViewHolder> {

    List<Upis> upisi;
    private OnUpisListener mOnUpisListener;

    private List<Object> holderi;
    private String title = "Upisi";

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textViewBodoviMi;
        public TextView textViewBodoviVi;
        public ImageButton ibtnIzbrisi;
        OnUpisListener onUpisListener;

        public MyViewHolder(View v, final OnUpisListener onUpisListener) {
            super(v);
            textViewBodoviMi = v.findViewById(R.id.tv_bodovi_mi);
            textViewBodoviVi = v.findViewById(R.id.tv_bodovi_vi);
            ibtnIzbrisi = v.findViewById(R.id.ibtn_izbrisi);
            this.onUpisListener = onUpisListener;

            v.setOnClickListener(this);
            ibtnIzbrisi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onUpisListener.onIzbrisiClick(getAdapterPosition());
                }
            });
        }

        @Override
        public void onClick(View v) {
            onUpisListener.onUpisClick(getAdapterPosition());

        }
    }

    public interface OnUpisListener{
        void onUpisClick(int position);
        void onIzbrisiClick(int position);
    }


    public recAdapterRezultati(List<Upis> upis, OnUpisListener onUpisListener) {

        upisi = upis;
        this.mOnUpisListener = onUpisListener;
    }

    @NonNull
    @Override
    public recAdapterRezultati.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_rezultati, parent, false);
        MyViewHolder vh = new MyViewHolder(v, mOnUpisListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull recAdapterRezultati.MyViewHolder holder, int position) {
        int nBodoviMi = upisi.get(position).nBodoviMi;
        int nZvanjaMi = upisi.get(position).nZvanjaMi;
        int nBodoviVi = upisi.get(position).nBodoviVi;
        int nZvanjaVi = upisi.get(position).nZvanjaVi;
        String sBodoviMi = Integer.toString(nBodoviMi + nZvanjaMi);
        String sBodoviVi = Integer.toString(nBodoviVi + nZvanjaVi);
        holder.textViewBodoviMi.setText(sBodoviMi);
        holder.textViewBodoviVi.setText(sBodoviVi);
    }

    @Override
    public int getItemCount() {
        return upisi.size();
    }
}
