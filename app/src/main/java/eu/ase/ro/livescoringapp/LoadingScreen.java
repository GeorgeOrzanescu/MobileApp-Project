package eu.ase.ro.livescoringapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoadingScreen extends AppCompatActivity {
    // time the loading screen is visible to the user
    private static final int LOADING_SCREEN_SHOW_TIME = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // makes loading screen show on all screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.loading_activity);

        // some fadeout effect
        Animation fadeOut = new AlphaAnimation(1,0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(500);
        fadeOut.setDuration(1800);
        ImageView image =findViewById(R.id.imageView);
        image.setAnimation(fadeOut);

        // make loading screen visible and transition to MainActivity
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(LoadingScreen.this,MainActivity.class);
            startActivity(intent);
            finish();
        },LOADING_SCREEN_SHOW_TIME);
    }
}
