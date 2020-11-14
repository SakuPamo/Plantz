package com.saku.plantz.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.saku.plantz.Adapter.PlantViewAdapter;
import com.saku.plantz.Model.Favourite;
import com.saku.plantz.Model.Plant;
import com.saku.plantz.R;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment {

    private List<Plant> plantList;
    private List<Favourite> favouriteList;
    private RecyclerView recyclerView;
    private PlantViewAdapter plantAdapter;

    public FavouriteFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        recyclerView = view.findViewById(R.id.favourite_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        plantList = new ArrayList<>();
        favouriteList = new ArrayList<>();

        readPlants();

        return view;
    }

    private void readPlants() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid()).child("Favourite");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Plants");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                favouriteList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    assert snapshot != null;
                    Favourite favourite = snapshot.getValue(Favourite.class);

                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            plantList.clear();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Plant plants = snapshot.getValue(Plant.class);

                                assert plants != null;
                                assert firebaseUser != null;
                                if (plants.getAdd_Id().equals(favourite.getAdd_Id()) && favourite.getFlag().equals("1")){
                                    plantList.add(plants);
                                }

                            }

//                            favouriteViewAdapter = new FavouriteViewAdapter(getContext(), plantList);
//                            recyclerView.setAdapter(favouriteViewAdapter);
                            favouriteList.add(favourite);

                            plantAdapter = new PlantViewAdapter(getContext(), plantList, favouriteList);
                            recyclerView.setAdapter(plantAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}