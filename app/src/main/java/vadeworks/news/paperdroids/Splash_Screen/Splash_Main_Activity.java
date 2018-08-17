package vadeworks.news.paperdroids.Splash_Screen;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import vadeworks.news.paperdroids.MainScreen.MainScreen_Activity;
import vadeworks.paperdroid.R;

public class Splash_Main_Activity extends AppCompatActivity {


    private TextView logo,tagline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        if (Build.VERSION.SDK_INT >= 21) {
            // Call some material design APIs here
            getWindow().setNavigationBarColor(getResources().getColor(R.color.background_white)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series
            getWindow().setStatusBarColor(getResources().getColor(R.color.background_white)); //status bar or the time bar at the top

        } else {
            Log.d("Lesser than 21 SDK", "Lesser than 21 SDK");
            Log.d("Lesser than 21 SDK", "Lesser than 21 SDK");
            // Implement this feature without material design
        }

        logo = findViewById(R.id.logo);
        tagline = findViewById(R.id.tagline);
        logo.animate().alpha(1);
        tagline.animate().alpha(1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash_Main_Activity.this, MainScreen_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Splash_Main_Activity.this.finish();
            }
        }, 2000);
    }


    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);

    }



}
