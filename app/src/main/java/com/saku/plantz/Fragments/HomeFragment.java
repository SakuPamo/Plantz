package com.saku.plantz.Fragments;

import android.os.Bundle;


import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
import com.google.firebase.database.Query;
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
    EditText search_users;


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

        search_users = view.findViewById(R.id.search_plants);
        search_users.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchPlants(charSequence.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    private void searchPlants(String s) {
        final FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        Query query = FirebaseDatabase.getInstance().getReference("Plants").orderByChild("search")
                .startAt(s)
                .endAt(s + "\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                plantList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Plant plants = snapshot.getValue(Plant.class);


                    assert plants != null;

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