package eu.ase.ro.livescoringapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import eu.ase.ro.livescoringapp.async.AsyncTaskRunner;
import eu.ase.ro.livescoringapp.async.CallbackFunction;
import eu.ase.ro.livescoringapp.classes.Comment;
import eu.ase.ro.livescoringapp.fragments.ChatFragment;
import eu.ase.ro.livescoringapp.network.HttpManager;

public class MainActivity extends AppCompatActivity {

    private static final String SPORTS_URL = "https://www.jsonkeeper.com/b/LP9I";

    private BottomNavigationView bottomNavigationView;
    private Fragment currentFragment;

    private List<Comment> comments = new ArrayList<>();

    // ASYNC OPERATION RESOURCES
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        getSportEventsFromNetwork();

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

    private void getSportEventsFromNetwork() {
        Callable<String> asyncTask = new HttpManager(SPORTS_URL);
        CallbackFunction<String> mainThreadTask = mainThreadTaskHttpJson();

        asyncTaskRunner.executeAsync(asyncTask,mainThreadTask);
    }

    private CallbackFunction<String> mainThreadTaskHttpJson() {
        return new CallbackFunction<String>() {
            @Override
            public void runResultOnUiThread(String result) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }
        };
    }
}