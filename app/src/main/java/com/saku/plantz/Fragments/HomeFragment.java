package com.saku.plantz.Fragments;

import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.saku.plantz.Adapter.PlantViewAdapter;
import com.saku.plantz.Model.Plant;
import com.saku.plantz.R;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private List<Plant> plantList;
    private RecyclerView recyclerView;
    private PlantViewAdapter plantAdapter;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.plant_recycler, container, false);

        recyclerView = view.findViewById(R.id.plant_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        plantList = new ArrayList<>();

        readPlants();

        return view;
    }

    private void readPlants() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Plants");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                plantList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Plant plants = snapshot.getValue(Plant.class);

                    assert plants != null;
                    assert firebaseUser != null;
                    plantList.add(plants);
                }

                plantAdapter = new PlantViewAdapter(getContext(), plantList);
                recyclerView.setAdapter(plantAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}