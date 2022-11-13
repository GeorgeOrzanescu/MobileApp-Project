package eu.ase.ro.livescoringapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


import eu.ase.ro.livescoringapp.fragments.TipsFragment;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    TipsFragment tipsFragment = new TipsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        //getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutFragment,tipsFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottomNavTips:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutFragment,tipsFragment).commit();
                        return true;
                }
                return true;
            }
        });
    };


}