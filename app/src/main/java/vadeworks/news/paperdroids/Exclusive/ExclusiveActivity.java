package vadeworks.news.paperdroids.Exclusive;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import cn.jzvd.JZVideoPlayer;
import vadeworks.news.paperdroids.Articles;
import vadeworks.news.paperdroids.Constants;
import vadeworks.news.paperdroids.MainScreen.MainScreen_Activity;
import vadeworks.news.paperdroids.Utils;
import vadeworks.news.paperdroids.VerticalNews.VerticalViewPager;
import vadeworks.paperdroid.R;

public class ExclusiveActivity extends AppCompatActivity {

    private final Bundle params = new Bundle();
    FirebaseFirestore firestoreNews;
    Query first;
    private ArrayList<DocIdRetrive> articlesList = new ArrayList<>();
    private String notifId, head, imgurl, content;
    private DocIdRetrive todisplay;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View parentLayout = findViewById(android.R.id.content);
        setContentView(R.layout.exclusive_activity);

        Utils utils = new Utils(this);
        if (!(utils.isConnected(getApplicationContext()))) {
            utils.buildDialog(this).show();
        }
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        notifId = getIntent().getStringExtra("exclusiveNotif");
        if (notifId != null)
            Log.d("Notifid is :", notifId);

        firestoreNews = FirebaseFirestore.getInstance();

        first = firestoreNews.collection("EXCLUSIVE")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(41);

        first.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            DocIdRetrive temp = new DocIdRetrive();
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                DocIdRetrive articles = new DocIdRetrive();
                                articles.docid = (documentSnapshot.getId() != null) ? documentSnapshot.getId() : "";
                                articles.type = (documentSnapshot.get("type") != null) ? documentSnapshot.get("type").toString() : "";
                                articles.head = (documentSnapshot.get("head") != null) ? documentSnapshot.get("head").toString() : "";
                                articles.content = (documentSnapshot.get("content") != null) ? documentSnapshot.get("content").toString() : "";
                                articles.imgurl = (documentSnapshot.get("imgurl") != null) ? documentSnapshot.get("imgurl").toString() : Constants.exclusiveBackground;
                                articles.videourl = (documentSnapshot.get("videourl") != null) ? documentSnapshot.get("videourl").toString() : "";
                                articles.audiourl = (documentSnapshot.get("audiourl") != null) ? documentSnapshot.get("audiourl").toString() : "";
                                articles.articlever = (documentSnapshot.get("articlever") != null) ? Integer.parseInt(documentSnapshot.get("articlever").toString()) : null;
                                articles.timestamp = (documentSnapshot.get("timestamp") != null) ? Long.parseLong(documentSnapshot.get("timestamp").toString()) : null;

                                Log.d("Snap1", articles.head + articles.timestamp);

                                if (articles.articlever == 1) {
                                    if (notifId != null)
                                        if (articles.docid.equals(notifId))
                                            temp = articles;

                                    articlesList.add(new DocIdRetrive(articles.docid, articles.type, articles.head, articles.content,
                                            articles.imgurl, articles.videourl, articles.audiourl,
                                            (int) articles.articlever, (long) articles.timestamp));
                                }
                            }
//                            Collections.reverse(articlesList);
                            if (notifId != null)
                                articlesList.add(0, temp);
                            Snackbar.make(parentLayout, "Swipe Up to read more...", Snackbar.LENGTH_LONG).show();
                            initSwipePager();
                        } else {
                            Log.w("Docu", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void initSwipePager() {
        final VerticalViewPager verticalViewPager = findViewById(R.id.vPager);
        verticalViewPager.setAdapter(new Exclusive_Verticle_Pager_Adapter(this, articlesList));
        verticalViewPager.setOffscreenPageLimit(10);


        verticalViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(final int position) {
                // Check if this is the page you want.
                JZVideoPlayer.releaseAllVideos();
                params.putInt("Cards", position);
                Log.d("Position", "" + position);
                String cards_read = "Cards_Read";
                mFirebaseAnalytics.logEvent(cards_read, params);
            }
        });

        verticalViewPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                verticalViewPager.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(ExclusiveActivity.this, MainScreen_Activity.class);
            JZVideoPlayer.releaseAllVideos();
            finish();
            startActivity(intent);
        }


        if (keyCode == KeyEvent.KEYCODE_HOME) {
            JZVideoPlayer.releaseAllVideos();
        }
        return true;
    }


    class DocIdRetrive extends Articles {
        String docid = "";


        DocIdRetrive() {
            this.docid = "";
            this.type = "";
            this.head = "";
            this.content = "";
            this.imgurl = "";
            this.videourl = "";
            this.audiourl = "";
            this.articlever = 1;
            this.timestamp = System.currentTimeMillis();
        }

        DocIdRetrive(String docid, String type, String head, String content, String imgurl, String videourl, String audiourl, int articlever, long timestamp) {
            this.docid = docid;
            this.type = type;
            this.head = head;
            this.content = content;
            this.imgurl = imgurl;
            this.videourl = videourl;
            this.audiourl = audiourl;
            this.articlever = articlever;
            this.timestamp = timestamp;
        }


    }


}





