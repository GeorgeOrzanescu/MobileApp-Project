package eu.ase.ro.livescoringapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.livescoringapp.classes.Comment;
import eu.ase.ro.livescoringapp.fragments.ChatFragment;
import eu.ase.ro.livescoringapp.fragments.SportsFragment;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Fragment currentFragment;

    private List<Comment> comments = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // default when starting
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        currentFragment = SportsFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutFragment,currentFragment).commit();


        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.bottomNavTips:
                    currentFragment = ChatFragment.newInstance((ArrayList<Comment>) comments);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutFragment,currentFragment).commit();
                    return true;
                case R.id.bottomNavHome:
                    currentFragment = SportsFragment.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutFragment,currentFragment).commit();
                    return true;
            }
            return true;
        });
    }

}