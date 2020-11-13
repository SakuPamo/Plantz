package com.saku.plantz;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.saku.plantz.Fragments.HomeFragment;
import com.saku.plantz.Fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton addNewPlant;
    private BottomNavigationView bottomNavigationMenuView;
    private HomeFragment homeFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationMenuView = findViewById(R.id.bottomNavPanel);
//        addNewPlant = findViewById(R.id.floatingActionButton);

//        addNewPlant.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, AddPlantActivity.class);
//                startActivity(intent);
//            }
//        });

        homeFragment = new HomeFragment();
        profileFragment = new ProfileFragment();

        replaceFragment(homeFragment);

        bottomNavigationMenuView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.bottom_action_home:
                        replaceFragment(homeFragment);
                        return true;

                    case R.id.bottom_action_profile:
                        replaceFragment(profileFragment);
                        return true;

                    default:
                        return false;
                }
            }
        });


    }

    private void replaceFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_container,fragment);
        fragmentTransaction.commit();

    }
}
