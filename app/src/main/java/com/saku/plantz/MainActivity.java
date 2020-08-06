package com.saku.plantz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.saku.plantz.Fragments.HomeFragment;
import com.saku.plantz.Fragments.NotificationFragment;
import com.saku.plantz.Fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton addNewPlant;
    private BottomNavigationView bottomNavigationMenuView;
    private HomeFragment homeFragment;
    private NotificationFragment notificationFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationMenuView = findViewById(R.id.bottomNavPanel);
        addNewPlant = findViewById(R.id.floatingActionButton);

        homeFragment = new HomeFragment();
        notificationFragment = new NotificationFragment();
        profileFragment = new ProfileFragment();

        bottomNavigationMenuView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.bottum_action_home:
                        replaceFragment(homeFragment);
                        return true;

                    case R.id.bottum_action_notification:
                        replaceFragment(notificationFragment);
                        return true;

                    case R.id.bottum_action_profile:
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
