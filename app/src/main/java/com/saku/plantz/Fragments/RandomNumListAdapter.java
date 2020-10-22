package com.saku.plantz.Fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.saku.plantz.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Random;

public class RandomNumListAdapter extends RecyclerView.Adapter<PlantsViewHolder> {
    private Random random;

    public RandomNumListAdapter(int seed) {
        this.random = new Random(seed);
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.plant_card;
    }

    @NonNull
    @Override
    public PlantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new PlantsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 100;
    }
}
