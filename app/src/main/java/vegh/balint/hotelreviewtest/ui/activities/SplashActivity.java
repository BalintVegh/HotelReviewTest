package vegh.balint.hotelreviewtest.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vegh.balint.hotelreviewtest.utils.WindowHelper;

/**
 * Splash screen to make the loading screen nicer
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowHelper.createFullScreenActivity(this);
        WindowHelper.changeStatusBarColor(this);
        startActivity(new Intent(SplashActivity.this, ReviewActivity.class));
        finish();
    }
}
