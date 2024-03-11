package com.example.b3tempo_nawfel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b3tempo_nawfel.databinding.TempoDateItemBinding;

import java.util.List;

public class TempoDateAdapter extends RecyclerView.Adapter<TempoDateAdapter.TempoDateViewHolder> {

    private List<TempoDate> tempoDates;
    private Context context;
    public TempoDateAdapter(List<TempoDate> tempoDates, Context context){
        this.tempoDates = tempoDates;
        this.context = context;
    }

    @NonNull
    @Override
    public TempoDateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TempoDateViewHolder(TempoDateItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tempo_date_item, parent, false);
        //return new TempoDateViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TempoDateViewHolder holder, int position) {
        holder.binding.dateTv.setText(tempoDates.get(position).getDate());
        holder.binding.colorFl.setBackgroundColor(ContextCompat.getColor(context, tempoDates.get(position).getCouleur().getColorResId()));

        //holder.dateTv.setText(tempoDates.get(position).getDate());
        //holder.colorFl.setBackgroundColor(ContextCompat.getColor(context, tempoDates.get(position).getCouleur().getColorResId()));
    }

    @Override
    public int getItemCount() {
        return tempoDates.size();
    }

    public class TempoDateViewHolder extends RecyclerView.ViewHolder {
        TempoDateItemBinding binding;

        public TempoDateViewHolder(TempoDateItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


   /*
    * Old way of proceeding with 'findViewById'
    *
    public class TempoDateViewHolder extends RecyclerView.ViewHolder {

        TextView dateTv;
        FrameLayout colorFl;

        public TempoDateViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTv = itemView.findViewById(R.id.date_tv);
            colorFl = itemView.findViewById(R.id.color_fl);
        }
    } */
}
