package vadeworks.news.paperdroids.HoroScopes;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.udevel.widgetlab.TypingIndicatorView;

import java.util.ArrayList;
import java.util.Random;

import vadeworks.news.paperdroids.Constants;
import vadeworks.news.paperdroids.News;
import vadeworks.news.paperdroids.RecyclerAdapter;
import vadeworks.news.paperdroids.Utils;
import vadeworks.paperdroid.R;

public class HoroScopes extends AppCompatActivity {


    KenBurnsView kenBurnsView;
    CardView c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12;
    TextView horoscope,sign;
    FirebaseFirestore firestoreHoroscope;
    ArrayList<News> horoscopes = new ArrayList<>();
    TypingIndicatorView typingIndicatorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horo_scopes);


        kenBurnsView = findViewById(R.id.kens);
        Utils utils = new Utils(this);
        if(!utils.isConnected(getApplicationContext())){
            utils.buildDialog(this).show();
        }

        firestoreHoroscope = FirebaseFirestore.getInstance();
        Random r = new Random();
        int  n = r.nextInt(100) + 1;

        if(n>50){
            kenBurnsView.setImageDrawable(getResources().getDrawable(R.drawable.horoscope1));
        }else {
            kenBurnsView.setImageDrawable(getResources().getDrawable(R.drawable.horoscope2));
        }

        horoscope = findViewById(R.id.horoscope);
        sign = findViewById(R.id.sign);
        typingIndicatorView  = findViewById(R.id.horoscope_loader);
        c1 = findViewById(R.id.aries);
        c2 = findViewById(R.id.taurus);
        c3 = findViewById(R.id.gemini);
        c4 = findViewById(R.id.cancer);
        c5 = findViewById(R.id.leo);
        c6 = findViewById(R.id.virgo);
        c7 = findViewById(R.id.libra);
        c8 = findViewById(R.id.scorpio);
        c9 = findViewById(R.id.sagittarius);
        c10 = findViewById(R.id.capricorn);
        c11 = findViewById(R.id.aquarius);
        c12 = findViewById(R.id.pisces);



        new Thread(new Runnable() {
            @Override
            public void run() {

                firestoreHoroscope.collection("HOROSCOPE")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot documentSnapshot : task.getResult()) {

                                        News news1 = new News();
                                        news1.head = (documentSnapshot.get("zodiac_sign") != null) ? documentSnapshot.get("zodiac_sign").toString() : "";
                                        news1.link = (documentSnapshot.get("horoscope") != null) ? documentSnapshot.get("horoscope").toString() : "";
                                        news1.showNews();
                                        horoscopes.add(news1);
                                        typingIndicatorView.setVisibility(View.GONE);
                                        sign.setVisibility(View.VISIBLE);

                                        c1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                sign.setText(horoscopes.get(0).head);
                                                horoscope.setText(horoscopes.get(0).link);
                                            }
                                        });


                                        c2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                sign.setText(horoscopes.get(1).head);
                                                horoscope.setText(horoscopes.get(1).link);
                                            }
                                        });

                                        c3.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                sign.setText(horoscopes.get(2).head);
                                                horoscope.setText(horoscopes.get(2).link);
                                            }
                                        });

                                        c4.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                sign.setText(horoscopes.get(3).head);
                                                horoscope.setText(horoscopes.get(3).link);
                                            }
                                        });


                                        c5.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                sign.setText(horoscopes.get(4).head);
                                                horoscope.setText(horoscopes.get(4).link);
                                            }
                                        });


                                        c6.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                sign.setText(horoscopes.get(5).head);
                                                horoscope.setText(horoscopes.get(5).link);
                                            }
                                        });

                                        c7.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                sign.setText(horoscopes.get(6).head);
                                                horoscope.setText(horoscopes.get(6).link);
                                            }
                                        });

                                        c8.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                sign.setText(horoscopes.get(7).head);
                                                horoscope.setText(horoscopes.get(7).link);
                                            }
                                        });


                                        c9.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                sign.setText(horoscopes.get(8).head);
                                                horoscope.setText(horoscopes.get(8).link);
                                            }
                                        });


                                        c10.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                sign.setText(horoscopes.get(9).head);
                                                horoscope.setText(horoscopes.get(9).link);
                                            }
                                        });

                                        c11.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                sign.setText(horoscopes.get(10).head);
                                                horoscope.setText(horoscopes.get(10).link);
                                            }
                                        });

                                        c12.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                sign.setText(horoscopes.get(11).head);
                                                horoscope.setText(horoscopes.get(11).link);
                                            }
                                        });

                                    }
                                    Log.d("Starting Fetch", "Finishing Fetch");
                                } else {
                                    Toast.makeText(getApplicationContext(), "Oops, Something went wrong. Could'nt Fetch Horoscopes :( ", Toast.LENGTH_SHORT).show();
                                    Log.w("Docu", "Error getting documents.", task.getException());
                                }

                            }
                        });
            }
        }).start();













    }
}
