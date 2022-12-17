package eu.ase.ro.livescoringapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.livescoringapp.async.CallbackFunction;
import eu.ase.ro.livescoringapp.classes.Comment;
import eu.ase.ro.livescoringapp.classes.CommentCategory;
import eu.ase.ro.livescoringapp.database.CategoryWithCommentsService;
import eu.ase.ro.livescoringapp.fragments.ChatFragment;
import eu.ase.ro.livescoringapp.fragments.SportFixturesFragment;
import eu.ase.ro.livescoringapp.fragments.SportResultsFragment;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Fragment currentFragment;

    private List<Comment> comments = new ArrayList<>();
    private CategoryWithCommentsService categoryWithCommentsService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // default when starting
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        currentFragment = SportResultsFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutFragment,currentFragment).commit();
        categoryWithCommentsService = new CategoryWithCommentsService(getApplicationContext());
        createChatCategories();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.bottomNavTips:
                    currentFragment = ChatFragment.newInstance((ArrayList<Comment>) comments);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutFragment,currentFragment).commit();
                    return true;
                case R.id.bottomNavUpcoming:
                    currentFragment = SportFixturesFragment.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutFragment,currentFragment).commit();
                    return true;
                case R.id.bottomNavHome:
                    currentFragment = SportResultsFragment.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutFragment,currentFragment).commit();
                    return true;
            }
            return true;
        });
    }

    // categories must be created when the application is first started for the db one to many relationship
    private void createChatCategories() {
        categoryWithCommentsService.insert(new CommentCategory(1,"Football"),insertCommentCategoryCallback());
        categoryWithCommentsService.insert(new CommentCategory(2,"Basketball"),insertCommentCategoryCallback());
    }

    private CallbackFunction<CommentCategory> insertCommentCategoryCallback() {
        return new CallbackFunction<CommentCategory>() {
            @Override
            public void runResultOnUiThread(CommentCategory result) {
                if(result != null) {
                    Log.i("Chat category", "insert successfully");
                }
            }
        };
    }

}