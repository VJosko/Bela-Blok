package com.example.belablok;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class recAdapterRezultati extends RecyclerView.Adapter<recAdapterRezultati.MyViewHolder> {
    private List<Upis> upisi = UpisStorage.getInstance().readUpise();
    private List<Object> holderi;
    private String title = "Upisi";

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewRbr;
        public TextView textViewBodoviMi;
        public TextView textViewBodoviVi;

        public MyViewHolder(View v) {
            super(v);
            textViewRbr = v.findViewById(R.id.tv_rbr);
            textViewBodoviMi = v.findViewById(R.id.tv_bodovi_mi);
            textViewBodoviVi = v.findViewById(R.id.tv_bodovi_vi);
        }
    }


    public recAdapterRezultati() {
    }

    @NonNull
    @Override
    public recAdapterRezultati.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_rezultati, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull recAdapterRezultati.MyViewHolder holder, int position) {
        String sPosition = Integer.toString(position + 1);
        holder.textViewRbr.setText(sPosition);
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
