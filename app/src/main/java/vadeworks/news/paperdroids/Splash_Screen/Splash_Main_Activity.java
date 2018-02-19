package vadeworks.news.paperdroids.Splash_Screen;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
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
import vadeworks.news.paperdroids.VijayaVaani.VijayaVaani_MainActivity;
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



        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }




        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Bundle bundle = new Bundle();
        bundle.putInt("SplashActivity_Read_Button",R.id.read);
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
                Intent i = new Intent(Splash_Main_Activity.this, MainScreen_Activity.class);
                startActivity(i);
            }
        });
    }

    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);

    }

}
