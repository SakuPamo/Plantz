package com.saku.plantz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.saku.plantz.Model.User;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {

    MaterialEditText update_username, update_email, update_fName, update_lName;
    Button update_profile;
    CircleImageView image_profile;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        image_profile = findViewById(R.id.profile_image);
        update_username = findViewById(R.id.update_username);
        update_email = findViewById(R.id.update_email);
        update_fName = findViewById(R.id.fName);
        update_lName = findViewById(R.id.lName);
        update_profile = findViewById(R.id.update_profile);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                update_username.setText(user.getUsername());
                update_email.setText(user.getEmail());
                if (user.getFname() != null) {
                    update_fName.setText(user.getFname());
                }
                if (user.getLname() != null) {
                    update_lName.setText(user.getLname());
                }
                if (user.getImageUrl().equals("default")){
                    image_profile.setImageResource(R.mipmap.ic_launcher);
                }else {
                    Glide.with(getApplicationContext()).load(user.getImageUrl()).into(image_profile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_username = update_username.getText().toString();
                String txt_email = update_email.getText().toString();
                String txt_fName = update_fName.getText().toString();
                String txt_lName = update_lName.getText().toString();

                if(TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_fName) || TextUtils.isEmpty(txt_lName)){
                    Toast.makeText(EditProfile.this,"All fileds are required",Toast.LENGTH_SHORT).show();
                } else{
                    register(txt_username,txt_email,txt_fName,txt_lName);
                }
            }
        });

    }

    private void register(final String username, final String email,final String fName,final String lName){

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        assert firebaseUser != null;
        String userid = firebaseUser.getUid();

        reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", userid);
        hashMap.put("username", username);
        hashMap.put("email", email);
        hashMap.put("Fname", fName);
        hashMap.put("Lname", lName);

        reference.updateChildren(hashMap);
        Toast.makeText(EditProfile.this,"Update successful!",Toast.LENGTH_SHORT).show();


    }
}