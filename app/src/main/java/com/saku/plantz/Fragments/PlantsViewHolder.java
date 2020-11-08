package com.saku.plantz.Fragments;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saku.plantz.R;

public class PlantsViewHolder extends RecyclerView.ViewHolder {

    private TextView view;

    public PlantsViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView.findViewById(R.id.randomText);
    }

    public TextView getView() {
        return view;
    }
}
