package com.saku.plantz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.saku.plantz.Model.Plant;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class PlantViewActivity extends AppCompatActivity {

    StorageReference storageReference;
    DatabaseReference reference;
    private Context mContext;
    private Uri imageUri;
    private StorageTask uploadTask;
    FirebaseAuth auth;
    MaterialEditText plantName, sifiName, family, genus, height, spread, flow_period;
    Button updateBtn;
    ImageView plantImage;
    FirebaseUser firebaseUser;
    Intent intent;
    private static final int IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_view);

        plantName = findViewById(R.id.plantName);
        sifiName = findViewById(R.id.sci_name);
        family = findViewById(R.id.family);
        genus = findViewById(R.id.genus);
        height = findViewById(R.id.height);
        spread = findViewById(R.id.spread);
        flow_period = findViewById(R.id.flow_period);
        updateBtn = findViewById(R.id.update_plant);
        plantImage = findViewById(R.id.plant_image);

        intent = getIntent();
        final String plantId = intent.getStringExtra("plantId");
        System.out.println("PlantId ===> "+ plantId);

        assert plantId != null;
        reference = FirebaseDatabase.getInstance().getReference("Plants").child(plantId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Plant getPlant = dataSnapshot.getValue(Plant.class);
                plantName.setText(getPlant.getPlantName());
                sifiName.setText(getPlant.getSciName());
                family.setText(getPlant.getFamily());
                genus.setText(getPlant.getGenus());
                height.setText(getPlant.getHeight());
                spread.setText(getPlant.getSpread());
                flow_period.setText(getPlant.getFlow_period());
                Picasso.with(mContext).load(getPlant.getPlantImageUrl()).into(plantImage);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String update_plantName = plantName.getText().toString();
                String update_sciName = sifiName.getText().toString();
                String update_family = family.getText().toString();
                String update_genus = genus.getText().toString();
                String update_height = height.getText().toString();
                String update_spread = spread.getText().toString();
                String update_flowPeriod = flow_period.getText().toString();

                if (TextUtils.isEmpty(update_plantName)) {
                    Toast.makeText(PlantViewActivity.this,"All fileds are required",Toast.LENGTH_SHORT).show();
                } else {
                    updatePlant(update_plantName,update_sciName,update_family,update_genus,update_height,update_spread,update_flowPeriod,plantId);
                }
            }
        });
    }

    private void updatePlant(String plantName, String sciName,String family,String genus, String height, String spread, String flowPeriod, String plantPushID){


        reference = FirebaseDatabase.getInstance().getReference("Plants").child(plantPushID);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("add_Id", plantPushID);
        hashMap.put("plantName", plantName);
        hashMap.put("sciName", sciName);
        hashMap.put("family", family);
        hashMap.put("genus", genus);
        hashMap.put("height", height);
        hashMap.put("spread", spread);
        hashMap.put("flow_period", flowPeriod);
        hashMap.put("search", plantName.toLowerCase());

        reference.updateChildren(hashMap);
        Toast.makeText(PlantViewActivity.this,"Update successful!",Toast.LENGTH_SHORT).show();


    }
}