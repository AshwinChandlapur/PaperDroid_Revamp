package vadeworks.news.paperdroids.Splash_Screen;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.analytics.FirebaseAnalytics;

import vadeworks.news.paperdroids.AsiaNet.AsiaNet_MainActivity;
import vadeworks.news.paperdroids.MainScreen.MainScreen_Activity;
import vadeworks.news.paperdroids.Prajavani.PrajaVaani_MainActivity;
import vadeworks.news.paperdroids.VerticalNews.Vertical_News;
import vadeworks.news.paperdroids.VijayaKarnataka.VijayaKarnataka_MainActivity;
import vadeworks.paperdroid.R;

public class Splash_Main_Activity extends AppCompatActivity {


    LinearLayout l1;
    Button read;
    ImageView space;
    Animation uptodown,downtoup;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Bundle bundle = new Bundle();
        bundle.putInt("SplashActivity-Read-Button",R.id.read);
        mFirebaseAnalytics.logEvent("App_Open", bundle);


        if (Build.VERSION.SDK_INT >= 21) {
            // Call some material design APIs here
            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusbar)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusbar)); //status bar or the time bar at the top

        } else {
            Log.d("Lesser than 21 SDK","Lesser than 21 SDK");
            Log.d("Lesser than 21 SDK","Lesser than 21 SDK");
            // Implement this feature without material design
        }



        read = (Button) findViewById(R.id.read);
        l1 =  (LinearLayout) findViewById(R.id.l1);
        space = (ImageView) findViewById(R.id.space);

        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        l1.setAnimation(uptodown);
        read.setAnimation(downtoup);
        space.setAnimation(downtoup);

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Splash_Main_Activity.this, PrajaVaani_MainActivity.class);
                startActivity(i);
            }
        });


    }
}
