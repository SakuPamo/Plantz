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

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.saku.plantz.Model.Plant;
import com.saku.plantz.PlantViewActivity;
import com.saku.plantz.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlantViewAdapter extends RecyclerView.Adapter<PlantViewAdapter.ViewHolder> {

    private Context mContext;
    private List<Plant> plantList;

    public PlantViewAdapter(Context mContext, List<Plant> plantList) {
        this.mContext = mContext;
        this.plantList = plantList;
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
        holder.sciName.setText(plantGetList.getScientificName());
        if(plantGetList.getPlantImageUrl().equals("default")){
            holder.plant_image.setImageResource(R.mipmap.ic_launcher);
        }else {
            Picasso.with(mContext).load(plantGetList.getPlantImageUrl()).into(holder.plant_image);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(mContext, PlantViewActivity.class);
//                intent.putExtra("grpId", chatList.getId());
//                intent.putExtra("displayName", chatList.getDisplayName());
//                intent.putExtra("imageUrl", chatList.getThumbnail());
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
        public ImageView plant_image;

        public ViewHolder(View itemView){
            super(itemView);

            plantName = itemView.findViewById(R.id.plant_name);
            sciName = itemView.findViewById(R.id.sifi_name);
            familyName = itemView.findViewById(R.id.family_name);
            plant_image = itemView.findViewById(R.id.plant_image);
        }
    }
}
