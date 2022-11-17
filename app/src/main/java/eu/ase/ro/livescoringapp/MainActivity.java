package eu.ase.ro.livescoringapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.livescoringapp.classes.Comment;
import eu.ase.ro.livescoringapp.fragments.ChatFragment;

public class MainActivity extends AppCompatActivity {



    private BottomNavigationView bottomNavigationView;
    private Fragment currentFragment;

    private List<Comment> comments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.bottomNavTips:
                    currentFragment = ChatFragment.newInstance((ArrayList<Comment>) comments);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutFragment,currentFragment).commit();
                    return true;
            }
            return true;
        });
    }
}