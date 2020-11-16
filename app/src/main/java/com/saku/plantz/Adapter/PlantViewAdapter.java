package com.saku.plantz.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.saku.plantz.Model.Favourite;
import com.saku.plantz.Model.Plant;
import com.saku.plantz.PlantViewActivity;
import com.saku.plantz.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class PlantViewAdapter extends RecyclerView.Adapter<PlantViewAdapter.ViewHolder> {

    private Context mContext;
    private List<Plant> plantList;
    private List<Favourite> favouriteList;

    public PlantViewAdapter(Context mContext, List<Plant> plantList, List<Favourite> favouriteList) {
        this.mContext = mContext;
        this.plantList = plantList;
        this.favouriteList = favouriteList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup holder, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.plant_card, holder, false);
        return new PlantViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Plant plantGetList = plantList.get(position);
        holder.plantName.setText(plantGetList.getPlantName());
        holder.familyName.setText(plantGetList.getFamily());
        holder.sciName.setText(plantGetList.getSciName());
        if(plantGetList.getPlantImageUrl().equals("default")){
            holder.plant_image.setImageResource(R.mipmap.ic_launcher);
        }else {
            Picasso.with(mContext).load(plantGetList.getPlantImageUrl()).into(holder.plant_image);
        }
        if (favouriteList != null) {
            for (Favourite addId : favouriteList) {
                if (favouriteList != null && addId.getAdd_Id().equals(plantGetList.getAdd_Id()) && addId.getFlag().equals("1")) {
                    holder.faved_Icn.setVisibility(View.VISIBLE);
                    holder.favIcn.setVisibility(View.GONE);
                }
            }
        }

        holder.faved_Icn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid()).child("Favourite").child(plantGetList.getAdd_Id());
                if (favouriteList != null) {
                    for (Favourite addId : favouriteList) {
                        if (favouriteList != null && addId.getAdd_Id().equals(plantGetList.getAdd_Id()) && addId.getFlag().equals("1")) {
//                            favouriteList.remove(addId);
                            removeAt(position);
                            userRef.removeValue();


                        }
                    }
                }
                holder.favIcn.setVisibility(View.VISIBLE);
                holder.faved_Icn.setVisibility(View.GONE);
            }
        });

        holder.favIcn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("favourites ===> " + favouriteList);
                final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid()).child("Favourite").child(plantGetList.getAdd_Id());
//                DatabaseReference favRef = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid()).child("Favourite");
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("add_Id", plantGetList.getAdd_Id());
                hashMap.put("flag", "1");

                userRef.setValue(hashMap);
                holder.favIcn.setVisibility(View.GONE);
                holder.faved_Icn.setVisibility(View.VISIBLE);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(mContext, PlantViewActivity.class);
                intent.putExtra("plantId", plantGetList.getAdd_Id());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return plantList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView plantName, sciName, familyName;
        public ImageView plant_image, favIcn, faved_Icn;

        public ViewHolder(View itemView){
            super(itemView);

            plantName = itemView.findViewById(R.id.plant_name);
            sciName = itemView.findViewById(R.id.sifi_name);
            familyName = itemView.findViewById(R.id.family_name);
            plant_image = itemView.findViewById(R.id.plant_image);
            favIcn = itemView.findViewById(R.id.fav_icn);
            faved_Icn = itemView.findViewById(R.id.faved_icn);
        }
    }

    public void removeAt(int position) {
        plantList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, plantList.size());
    }
}
