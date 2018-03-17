package vadeworks.news.paperdroids.Exclusive;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import cn.jzvd.JZVideoPlayer;
import vadeworks.news.paperdroids.Articles;
import vadeworks.news.paperdroids.Constants;
import vadeworks.news.paperdroids.MainScreen.MainScreen_Activity;
import vadeworks.news.paperdroids.News;
import vadeworks.news.paperdroids.VerticalNews.VerticalViewPager;
import vadeworks.paperdroid.R;

public class ExclusiveActivity extends AppCompatActivity {

    FirebaseFirestore firestoreNews;
    private ArrayList<Articles> articlesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exclusive_activity);


        firestoreNews = FirebaseFirestore.getInstance();


        if (!isConnected(this)) {
            buildDialog(this).show();

        } else {
            Log.d("Internet Working", "Internet Working");
//            Toast.makeText(this,"Welcome", Toast.LENGTH_SHORT).show();
        }

        Log.d("Starting Fetch", "Starting Fetch");
        firestoreNews.collection("EXCLUSIVE")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                Log.d("Docu", documentSnapshot.getId() + " => " + documentSnapshot.getData());
                                Log.d("AllContent", "all" + documentSnapshot.get("content"));
                                Articles articles = documentSnapshot.toObject(Articles.class);
//                                if(articles.articlever == 1){
                                    articlesList.add( new Articles(articles.type,articles.head,articles.content,
                                            articles.imgurl,articles.videourl,articles.audiourl,
                                            (int)articles.articlever,(long)articles.timestamp));
//                                }
                                initSwipePager();
                            }
                            Log.d("Starting Fetch", "Finishing Fetch");
                        } else {
                            Log.w("Docu", "Error getting documents.", task.getException());
                        }

                    }
                });

    }


    private void initSwipePager() {
        VerticalViewPager verticalViewPager = findViewById(R.id.vPager);
        verticalViewPager.setOffscreenPageLimit(2);
        verticalViewPager.setAdapter(new Exclusive_Verticle_Pager_Adapter(this, articlesList));
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(ExclusiveActivity.this, MainScreen_Activity.class);
            JZVideoPlayer.releaseAllVideos();
            finish();
            startActivity(intent);
        }
        return true;
    }


    private boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            return (mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting());
        } else
            return false;
    }

    private AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        LayoutInflater factory = LayoutInflater.from(c);
        final View view = factory.inflate(R.layout.no_internet, null);
        Button wifi = view.findViewById(R.id.switchWifi);
        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });


        Button data = view.findViewById(R.id.switchData);
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$DataUsageSummaryActivity"));
                startActivity(intent);
            }
        });


        builder.setView(view);
        return builder;
    }

}
