package com.example.belablok.adapteri;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.belablok.R;
import com.example.belablok.klase.Upis;

import java.util.List;

public class recAdapterUpisi extends RecyclerView.Adapter<recAdapterUpisi.MyViewHolder> {

    List<Upis> upisi;
    private OnUpisListener mOnUpisListener;

    private List<Object> holderi;
    private String title = "Upisi";

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageViewSlika;
        public TextView textViewBodoviMi;
        public TextView textViewBodoviVi;
        OnUpisListener onUpisListener;

        public MyViewHolder(View v, OnUpisListener onUpisListener) {
            super(v);
            imageViewSlika = v.findViewById(R.id.iv_slika);
            textViewBodoviMi = v.findViewById(R.id.tv_bodovi_mi);
            textViewBodoviVi = v.findViewById(R.id.tv_bodovi_vi);
            this.onUpisListener = onUpisListener;

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onUpisListener.onUpisClick(getAdapterPosition());

        }
    }

    public interface OnUpisListener{
        void onUpisClick(int position);
    }


    public recAdapterUpisi(List<Upis> upis, OnUpisListener onUpisListener) {

        upisi = upis;
        this.mOnUpisListener = onUpisListener;
    }

    @NonNull
    @Override
    public recAdapterUpisi.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_upisi, parent, false);
        MyViewHolder vh = new MyViewHolder(v, mOnUpisListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull recAdapterUpisi.MyViewHolder holder, int position) {
        if(upisi.get(position).nBodoviMi + upisi.get(position).nZvanjaMi > upisi.get(position).nBodoviVi + upisi.get(position).nZvanjaVi){
            holder.imageViewSlika.setImageResource(R.drawable.ic_pobjeda);
        }
        else if(upisi.get(position).nBodoviMi + upisi.get(position).nZvanjaMi < upisi.get(position).nBodoviVi + upisi.get(position).nZvanjaVi){
            holder.imageViewSlika.setImageResource(R.drawable.ic_gubitak);
        }
        else{
            holder.imageViewSlika.setImageResource(R.drawable.ic_izjednaceno);
        }
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
