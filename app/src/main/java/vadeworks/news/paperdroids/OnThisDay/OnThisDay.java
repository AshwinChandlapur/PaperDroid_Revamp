package vadeworks.news.paperdroids.OnThisDay;

import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.udevel.widgetlab.TypingIndicatorView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Random;

import jp.shts.android.storiesprogressview.StoriesProgressView;
import vadeworks.news.paperdroids.MainScreen.MainScreen_Activity;
import vadeworks.news.paperdroids.News;
import vadeworks.news.paperdroids.Quickie.Feedback;
import vadeworks.news.paperdroids.RecyclerAdapter;
import vadeworks.paperdroid.R;

public class OnThisDay extends AppCompatActivity implements StoriesProgressView.StoriesListener {


    int counter_date=0;
    int counter_event=0;
    int counter_color=0;

    LinearLayout onthisday_container;
    RelativeLayout onthisday_parent;
    TextView date;
    TextView event;
    FirebaseFirestore firestoreNews;
    private StoriesProgressView storiesProgressView;
    private ArrayList<News> newsList = new ArrayList<>();
    TypingIndicatorView typingIndicatorView;

//    int Low=0;
//    int High = colors.length;
//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_this_day);

        final int[] colors = {getApplicationContext().getResources().getColor(R.color.material0),
                getApplicationContext().getResources().getColor(R.color.material1),
                getApplicationContext().getResources().getColor(R.color.material2),
                getApplicationContext().getResources().getColor(R.color.material3),
                getApplicationContext().getResources().getColor(R.color.material4),
                getApplicationContext().getResources().getColor(R.color.material5),
                getApplicationContext().getResources().getColor(R.color.material6),
                getApplicationContext().getResources().getColor(R.color.material7),
                getApplicationContext().getResources().getColor(R.color.material8),
                getApplicationContext().getResources().getColor(R.color.material9),
                getApplicationContext().getResources().getColor(R.color.material10),
                getApplicationContext().getResources().getColor(R.color.material11),
                getApplicationContext().getResources().getColor(R.color.material12),
                getApplicationContext().getResources().getColor(R.color.material13),
        };


        onthisday_parent = findViewById(R.id.onthisday_parent);
        onthisday_container = findViewById(R.id.onthisday_container);
        date = findViewById(R.id.date);
        event = findViewById(R.id.event);
        typingIndicatorView = findViewById(R.id.loader);
        storiesProgressView = (StoriesProgressView) findViewById(R.id.stories);
        firestoreNews = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        firestoreNews.setFirestoreSettings(settings);
        firestoreNews.collection("DAY")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                Log.d("Docu", documentSnapshot.getId() + " => " + documentSnapshot.getData());
                                Log.d("AllContent", "all" + documentSnapshot.get("content"));
//
                                String fire_date,fire_event;
                                News news1 = new News();
                                news1.head= (documentSnapshot.get("date") != null) ? documentSnapshot.get("date").toString() : "";
                                news1.link = (documentSnapshot.get("event") != null) ? documentSnapshot.get("event").toString() : "";
                                if (!(news1.head.isEmpty()) && !(news1.link.isEmpty()))
                                {
                                    newsList.add(news1);
                                }
                                Log.d("OnThisDay Size",String.valueOf(newsList.size()));

                            }
                            Log.d("Starting Fetch", "Finishing Fetch");
                        } else {
                            Toast.makeText(getApplicationContext(), "Oops, Something went wrong. Could'nt Fetch News :( ", Toast.LENGTH_SHORT).show();
                            Log.w("Docu", "Error getting documents.", task.getException());
                        }
                    }
                });



            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    storiesProgressView.setStoriesCount(10);
                    storiesProgressView.setStoryDuration(4000L); // <- set a story duration
                    storiesProgressView.setStoriesListener(new StoriesProgressView.StoriesListener() {
                        @Override
                        public void onNext() {

                            Random r = new Random();
                            int Low = 0;
                            int High = colors.length;
                            int Result = r.nextInt(High-Low) + Low;


                            onthisday_parent.setBackgroundColor(colors[Result]);
                            date.setText(newsList.get(++counter_date).head);
                            event.setText(newsList.get(++counter_event).link);
                        }

                        @Override
                        public void onPrev() {

                            Random r = new Random();
                            int Low = 0;
                            int High = colors.length;
                            int Result = r.nextInt(High-Low) + Low;

                            onthisday_parent.setBackgroundColor(colors[Result]);
                            date.setText(newsList.get(--counter_date).head);
                            event.setText(newsList.get(--counter_event).link);
                        }

                        @Override
                        public void onComplete() {
                            Intent i = new Intent(OnThisDay.this, Feedback.class);
                            startActivity(i);
                        }
                    }); // <- set listener
                    typingIndicatorView.setVisibility(View.GONE);
                    storiesProgressView.startStories(); // <- start progress

                    onthisday_parent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        storiesProgressView.skip();
//                      storiesProgressView.reverse();
                        }
                    });

                    onthisday_parent.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            storiesProgressView.pause();
                            return false;
                        }
                    });


                    date.setText(newsList.get(counter_date).head);
                    event.setText(newsList.get(counter_event).link);
                }
            }, 2000);


    }


    @Override
    protected void onDestroy() {
        // Very important !
        storiesProgressView.destroy();
        super.onDestroy();
    }

    @Override
    public void onNext() {
    }

    @Override
    public void onPrev() {
    }

    @Override
    public void onComplete() {

    }
}
