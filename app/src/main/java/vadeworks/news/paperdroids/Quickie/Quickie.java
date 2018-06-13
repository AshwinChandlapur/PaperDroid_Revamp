package vadeworks.news.paperdroids.Quickie;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
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
import com.squareup.picasso.Picasso;
import com.udevel.widgetlab.TypingIndicatorView;

import java.util.ArrayList;

import jp.shts.android.storiesprogressview.StoriesProgressView;
import vadeworks.news.paperdroids.MainScreen.MainScreen_Activity;
import vadeworks.news.paperdroids.News;
import vadeworks.paperdroid.R;

public class Quickie extends AppCompatActivity implements StoriesProgressView.StoriesListener {


    int counter_date=0;
    int counter_event=0;

    FrameLayout quickie_container;
    RelativeLayout quickie_parent;
    TextView headlines;
    ImageView image;
    FirebaseFirestore firestoreNews;
    private StoriesProgressView storiesProgressView;
    TypingIndicatorView typingIndicatorView;
    private ArrayList<News> news_60 = new ArrayList<>();
    String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quickie);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("id");
            //The key argument here must match that used in the other activity
        }


        quickie_container = findViewById(R.id.quickie_container);
        quickie_parent = findViewById(R.id.quickie_parent);
        headlines = findViewById(R.id.headlines);
        image = findViewById(R.id.image);
        storiesProgressView = (StoriesProgressView) findViewById(R.id.stories);
        typingIndicatorView = findViewById(R.id.loader);


        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("60News","60News");
                Quickie_Parser quickie_parser = new Quickie_Parser();
                news_60 = quickie_parser.MainPage(value);


                firestoreNews = FirebaseFirestore.getInstance();
                FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                        .setPersistenceEnabled(true)
                        .build();
                firestoreNews.setFirestoreSettings(settings);
                firestoreNews.collection(value)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                        Log.d("Docu", documentSnapshot.getId() + " => " + documentSnapshot.getData());
                                        Log.d("AllContent", "all" + documentSnapshot.get("content"));
//
                                        News news1 = new News();
                                        news1.head= (documentSnapshot.get("head") != null) ? documentSnapshot.get("head").toString() : "";
                                        news1.link = (documentSnapshot.get("imgurl") != null) ? documentSnapshot.get("imgurl").toString() : "";
                                        if (!(news1.head.isEmpty()) && !(news1.link.isEmpty()))
                                        {
                                            news_60.add(news1);
                                        }


                                    }
                                    Log.d("Starting Fetch", "Finishing Fetch");
                                } else {
                                    Toast.makeText(getApplicationContext(), "Oops, Something went wrong. Could'nt Fetch News :( ", Toast.LENGTH_SHORT).show();
                                    Log.w("Docu", "Error getting documents.", task.getException());
                                }
                            }
                        });



            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    storiesProgressView.setStoriesCount(10);
                    storiesProgressView.setStoryDuration(4000L); // <- set a story duration
                    storiesProgressView.setStoriesListener(new StoriesProgressView.StoriesListener() {
                        @Override
                        public void onNext() {
                            headlines.setText(news_60.get(++counter_date).head);
                            Picasso.with(getApplicationContext()).load(news_60.get(++counter_event).link).fit().centerCrop().into(image);
                        }

                        @Override
                        public void onPrev() {
                            headlines.setText(news_60.get(--counter_date).head);
                            Picasso.with(getApplicationContext()).load(news_60.get(--counter_event).link).fit().centerCrop().into(image);

                        }

                        @Override
                        public void onComplete() {
                            Intent i = new Intent(Quickie.this, Feedback.class);
                            startActivity(i);
                        }
                    }); // <- set listener
                    typingIndicatorView.setVisibility(View.GONE);
                    storiesProgressView.startStories(); // <- start progress

                    quickie_parent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            storiesProgressView.skip();
//                      storiesProgressView.reverse();
                        }
                    });

                    quickie_parent.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            storiesProgressView.pause();
                            return false;
                        }
                    });

                    headlines.setText(news_60.get(counter_date).head);
                    Picasso.with(getApplicationContext()).load(news_60.get(counter_event).link).fit().centerCrop().into(image);
                }
            });
            }
        }).start();


//            final Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            }, 200);

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

    @Override
    protected void onDestroy() {
        // Very important !
        storiesProgressView.destroy();
        super.onDestroy();
    }
}
