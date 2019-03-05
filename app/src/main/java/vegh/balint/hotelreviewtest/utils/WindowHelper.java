package vegh.balint.hotelreviewtest.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Functions for activity window
 */
public class WindowHelper {

    /**
     * Change the activity statusbar color to transparent
     */
    public static void changeStatusBarColor(Activity myActivityReference) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = myActivityReference.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * Change the activity to fullscreen
     */
    public static void createFullScreenActivity(Activity myActivityReference){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = myActivityReference.getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

}
