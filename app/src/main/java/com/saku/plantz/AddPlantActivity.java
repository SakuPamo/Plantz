package com.saku.plantz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class AddPlantActivity extends AppCompatActivity {

    StorageReference storageReference;
    DatabaseReference reference;
    private Uri imageUri;
    private StorageTask uploadTask;
    FirebaseAuth auth;
    MaterialEditText plantName, sifiName, family, genus, height, spread, flow_period;
    Button addBtn;
    ImageView plantImage;
    FirebaseUser firebaseUser;
    private static final int IMAGE_REQUEST = 1;
    String plantImageUrl = "";
    String plantPushID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);

        plantName = findViewById(R.id.plantName);
        sifiName = findViewById(R.id.sci_name);
        family = findViewById(R.id.family);
        genus = findViewById(R.id.genus);
        height = findViewById(R.id.height);
        spread = findViewById(R.id.spread);
        flow_period = findViewById(R.id.flow_period);
        addBtn = findViewById(R.id.add_plant);
        plantImage = findViewById(R.id.plant_image);

        storageReference = FirebaseStorage.getInstance().getReference("Plant_Uploads");

        plantImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImage();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String plant_name = plantName.getText().toString();
                final String sci_name = sifiName.getText().toString();
                final String family_name = family.getText().toString();
                final String genus_name = genus.getText().toString();
                final String height_value = height.getText().toString();
                final String spread_value = spread.getText().toString();
                final String flowPeriod_value = flow_period.getText().toString();

                if (TextUtils.isEmpty(plant_name)) {
                    Toast.makeText(AddPlantActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                } else if (plantPushID == null) {
                    Toast.makeText(AddPlantActivity.this, "Please upload the Image!", Toast.LENGTH_SHORT).show();
                } else {
                    addNewPlant(plant_name,sci_name,family_name,genus_name,height_value,spread_value,flowPeriod_value);
                }

            }
        });

    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver =this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();

        if(imageUri != null){
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    +"."+getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        throw  task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();


                        reference = FirebaseDatabase.getInstance().getReference("Plants");
                        plantPushID = reference.push().getKey();


                            HashMap<String, Object> map = new HashMap<>();
                            map.put("plantImageUrl",mUri);
                            reference.child(plantPushID).updateChildren(map);

                        pd.dismiss();
                    }else{
                        Toast.makeText(AddPlantActivity.this,"Failed!",Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddPlantActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }else{
            Toast.makeText(AddPlantActivity.this,"No Image selected",Toast.LENGTH_SHORT).show();
        }
    }

    private void addNewPlant(String plant_name, String sciName, String family, String genus, String height, String spread, String flow_period) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        String userid = firebaseUser.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("addId", userid);
        hashMap.put("plantName", plant_name);
        hashMap.put("sciName", sciName);
        hashMap.put("family", family);
        hashMap.put("genus", genus);
        hashMap.put("height", height);
        hashMap.put("spread", spread);
        hashMap.put("flow_period", flow_period);
        hashMap.put("search", plant_name.toLowerCase().trim());

        reference.child("Plants").child(plantPushID).updateChildren(hashMap).addOnCompleteListener((task) -> {
            if (task.isSuccessful()) {
                Toast.makeText(AddPlantActivity.this, "Plant add Successfull!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AddPlantActivity.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            imageUri = data.getData();

            if(uploadTask != null && uploadTask.isInProgress()){
                Toast.makeText(AddPlantActivity.this,"Upload in progress",Toast.LENGTH_SHORT).show();
            }else{
                uploadImage();
            }
        }
    }
}