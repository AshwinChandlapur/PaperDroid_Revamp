package vadeworks.news.paperdroids.HorizontalNews;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.List;

import hotchemi.android.rate.AppRate;
import hotchemi.android.rate.OnClickButtonListener;
import vadeworks.news.paperdroids.News;
import vadeworks.paperdroid.R;

public class Horizontal_Display_News extends AppCompatActivity {

    ViewPager mViewPager;
    private ArrayList<News> news = new ArrayList<>();
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horizontal_display_news);

        askPermission();

//
//        AppRate.with(this)
//                .setInstallDays(0) // default 10, 0 means install day.
//                .setLaunchTimes(7) // default 10
//                .setRemindInterval(2) // default 1
//                .setShowLaterButton(true) // default true
//                .setDebug(true) // default false
//                .setTitle("Enable Auto-Start")
//                .setTextRateNow("Enable Auto-Start")
//                .setMessage("Please make sure that News Duniya is enabled on the list. It helps us to deliver latest news notifications.")
//                .setOnClickButtonListener(new OnClickButtonListener() { // callback listener.
//                    @Override
//                    public void onClickButton(int which) {
//                        Log.d("OKOK"+Horizontal_Display_News.class.getName(), Integer.toString(which));
//                        if(which == (-1)){
//                            try {
//                                Intent intent = new Intent();
//                                String manufacturer = android.os.Build.MANUFACTURER;
//                                if ("xiaomi".equalsIgnoreCase(manufacturer)) {
//                                    intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
//                                } else if ("oppo".equalsIgnoreCase(manufacturer)) {
//                                    intent.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity"));
//                                } else if ("vivo".equalsIgnoreCase(manufacturer)) {
//                                    intent.setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"));
//                                } else if("oneplus".equalsIgnoreCase(manufacturer)) {
//                                    intent.setComponent(new ComponentName("com.oneplus.security", "com.oneplus.security.chainlaunch.view.ChainLaunchAppListAct‌​ivity")); }
//
//                                List<ResolveInfo> list = getApplicationContext().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
//                                if  (list.size() > 0) {
//                                    getApplicationContext().startActivity(intent);
//                                }
//                            } catch (Exception e) {
//                                Crashlytics.logException(e);
//                            }
//                        }
//                    }
//                })
//                .monitor();
//
//        // Show a dialog if meets conditions
//        AppRate.showRateDialogIfMeetsConditions(this);


        Intent i = getIntent();
        if (i != null) {
            news = (ArrayList<News>) i.getSerializableExtra("newsObject");
            position = i.getIntExtra("position", 0);
        }

        CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(this, news, position);
        mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);
        mViewPager.setCurrentItem(position);
        mViewPager.setOffscreenPageLimit(3);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (!(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, "Permission denied!.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void askPermission() {
        if (Build.VERSION.SDK_INT > 22) {
            requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
        }
    }


}
