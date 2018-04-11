package vadeworks.news.paperdroids;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Map;

import vadeworks.paperdroid.R;

public class Special_Card extends AppCompatActivity {

    static int match_id;
    CardView ipl_parent;
    TextView mchDesc;
    TextView mchStatus;
    ImageView battingTeamImage;
    TextView battingTeamText;
    TextView scoreCard;
    ImageView cricketImage;
    FirebaseFirestore firestoreNews;


    TextView
            batsman1_name, batsman1_balls, batsman1_4s, batsman1_6s, batsman1_sr, batsman1_runs,
            batsman2_name, batsman2_balls, batsman2_4s, batsman2_6s, batsman2_sr, batsman2_runs,
            bowler1_name, bowler1_overs, bowler1_maidens, bowler1_runs, bowler1_wickets, bowler1_economy,
            bowler2_name, bowler2_overs, bowler2_maidens, bowler2_runs, bowler2_wickets, bowler2_economy;
    CardView team_score_card, batting_score_card, bowling_score_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special__card);

        initializeNewViews();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshScores(true);
            }
        }, 2000);


    }



    private void initializeNewViews() {

        final SharedPreferences sharedPreferences = getSharedPreferences("ipl_sp", Context.MODE_PRIVATE);
        match_id = sharedPreferences.getInt("match1", 0);
        firestoreNews = FirebaseFirestore.getInstance();

        DocumentReference docRef = firestoreNews.collection("ipl").document("match_id");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {

                        match_id = Integer.parseInt(document.get("match_id").toString());
                        Log.d("DocumentSnapshot data", "DocumentSnapshot data: " + match_id);
                        Log.d("DocumentSnapshot data", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("DocumentSnapshot", "No such document");
                    }
                } else {
                    Log.d("DocumentSnapshot", "get failed with ", task.getException());
                }
            }
        });


        ipl_parent = findViewById(R.id.specialCards);
        cricketImage = findViewById(R.id.cricketImage);
        
        mchDesc = findViewById(R.id.mchDesc);
        mchStatus = findViewById(R.id.mchStatus);

        battingTeamImage = findViewById(R.id.battingTeamImage);
        battingTeamText = findViewById(R.id.battingTeamText);
        scoreCard = findViewById(R.id.scoreCard);




        batsman1_name = findViewById(R.id.batsman1_name);
        batsman1_runs = findViewById(R.id.batsman1_runs);
        batsman1_balls = findViewById(R.id.batsman1_balls);
        batsman1_4s = findViewById(R.id.batsman1_4s);
        batsman1_6s = findViewById(R.id.batsman1_6s);
        batsman1_sr = findViewById(R.id.batsman1_sr);

        batsman2_name = findViewById(R.id.batsman2_name);
        batsman2_runs = findViewById(R.id.batsman2_runs);
        batsman2_balls = findViewById(R.id.batsman2_balls);
        batsman2_4s = findViewById(R.id.batsman2_4s);
        batsman2_6s = findViewById(R.id.batsman2_6s);
        batsman2_sr = findViewById(R.id.batsman2_sr);


        bowler1_name = findViewById(R.id.bowler1_name);
        bowler1_overs = findViewById(R.id.bowler1_overs);
        bowler1_maidens = findViewById(R.id.bowler1_maidens);
        bowler1_wickets = findViewById(R.id.bowler1_wickets);
        bowler1_economy = findViewById(R.id.bowler1_economy);
        bowler1_runs = findViewById(R.id.bowler1_runs);

        bowler2_name = findViewById(R.id.bowler2_name);
        bowler2_overs = findViewById(R.id.bowler2_overs);
        bowler2_maidens = findViewById(R.id.bowler2_maidens);
        bowler2_wickets = findViewById(R.id.bowler2_wickets);
        bowler2_economy = findViewById(R.id.bowler2_economy);
        bowler2_runs = findViewById(R.id.bowler2_runs);

        team_score_card = findViewById(R.id.team_score_card);
        batting_score_card = findViewById(R.id.batting_score_card);
        bowling_score_card = findViewById(R.id.bowling_score_card);

        ipl_parent.setVisibility(View.GONE);
    }

    private void refreshScores(boolean auto_refresh_){
        if(auto_refresh_){
            Log.d("DocumentSnapshot data", "DocumentSnapshot datas: " + match_id);
            if(match_id != 0){
                ipl_parent.setVisibility(View.VISIBLE);
                try{
                    Cricbuzz cricbuzz = new Cricbuzz();
                    Map<String,Map> score = cricbuzz.livescore(match_id+"");
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    String data = gson.toJson(score);

                    JSONObject _data = new JSONObject(data);
                    Log.d("data",data.toString());

                    JSONObject matchinfo = _data.getJSONObject("matchinfo");
                    Log.d("matchinfo",matchinfo.toString());


                    String match = matchinfo.get("mchdesc").toString();
                    String status = matchinfo.get("status").toString();
                    mchDesc.setText(match);
                    mchStatus.setText(status);

                    JSONObject batting = _data.getJSONObject("batting");
                    JSONArray team = batting.getJSONArray("team");
                    JSONObject _team = team.getJSONObject(0);

                    battingTeamText.setText(_team.getString("team"));
                    switch (_team.getString("team")) {
                        case "CSK":
                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.chennai));
                            battingTeamText.setVisibility(View.GONE);
                            break;
                        case "KKR":
                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.kolkatta));
                            battingTeamText.setVisibility(View.GONE);
                            break;
                        case "RCB":
                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.bangalore));
                            battingTeamText.setVisibility(View.GONE);
                            break;
                        case "RR":
                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.rajasthan));
                            battingTeamText.setVisibility(View.GONE);
                            break;
                        case "DD":
                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.dehli));
                            battingTeamText.setVisibility(View.GONE);
                            break;
                        case "SRH":
                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.hyderabad));
                            battingTeamText.setVisibility(View.GONE);
                            break;
                        case "MI":
                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.mumbai));
                            battingTeamText.setVisibility(View.GONE);
                            break;
                        case "K11P":
                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.punjab));
                            battingTeamText.setVisibility(View.GONE);
                            break;
                        case "IND":
                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.ind));
                            battingTeamText.setVisibility(View.GONE);
                            break;
                        case "AUS":
                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.aus));
                            battingTeamText.setVisibility(View.GONE);
                            break;
                        case "BAN":
                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.bangladesh));
                            battingTeamText.setVisibility(View.GONE);
                            break;
                        case "ENG":
                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.eng));
                            battingTeamText.setVisibility(View.GONE);
                            break;
                        case "NZ":
                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.nz));
                            battingTeamText.setVisibility(View.GONE);
                            break;
                        case "PAK":
                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.pakistan));
                            battingTeamText.setVisibility(View.GONE);
                            break;
                        case "RSA":
                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.sf));
                            battingTeamText.setVisibility(View.GONE);
                            break;
                        case "SL":
                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.sl));
                            battingTeamText.setVisibility(View.GONE);
                            break;
                        case "WI":
                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.wi));
                            battingTeamText.setVisibility(View.GONE);
                            break;
                        default:
                            battingTeamImage.setVisibility(View.GONE);
                            battingTeamText.setVisibility(View.VISIBLE);
                            break;
                    }

                    JSONArray _score = batting.getJSONArray("score");
                    Log.d("_score",_score.toString());
                    JSONObject __score = _score.getJSONObject(0);
                    scoreCard.setText(__score.getString("runs") + "-" + __score.getString("wickets") + " (" + __score.getString("overs") + ")");



                    DecimalFormat format = new DecimalFormat("###.##");
                    JSONArray batsmen = batting.getJSONArray("batsman");
                    JSONObject bat1 = batsmen.getJSONObject(0);
                    batsman1_name.setText(bat1.getString("name"));
                    batsman1_4s.setText(bat1.getString("fours"));
                    batsman1_6s.setText(bat1.getString("six"));
                    batsman1_balls.setText(bat1.getString("balls"));
                    batsman1_runs.setText(bat1.getString("runs"));
                    double bat1_sr = (float)Integer.parseInt(bat1.getString("runs")) * 100 / Integer.parseInt(bat1.getString("balls"));
                    batsman1_sr.setText(format.format(bat1_sr));

                    JSONObject bowling = _data.getJSONObject("bowling");
                    JSONArray bowlers = bowling.getJSONArray("bowler");
                    JSONObject ball1 = bowlers.getJSONObject(0);
                    bowler1_name.setText(ball1.getString("name"));
                    bowler1_overs.setText(ball1.getString("overs"));
                    bowler1_maidens.setText(ball1.getString("maidens"));
                    bowler1_wickets.setText(ball1.getString("wickets"));
                    bowler1_runs.setText(ball1.getString("runs"));
                    double ball1_eco = Integer.parseInt(ball1.getString("runs")) / Double.parseDouble(ball1.getString("overs"));
                    bowler1_economy.setText(format.format(ball1_eco));

                    JSONObject bat2 = batsmen.getJSONObject(1);
                    batsman2_name.setText(bat2.getString("name"));
                    batsman2_4s.setText(bat2.getString("fours"));
                    batsman2_6s.setText(bat2.getString("six"));
                    batsman2_balls.setText(bat2.getString("balls"));
                    batsman2_runs.setText(bat2.getString("runs"));
                    double bat2_sr = (float)Integer.parseInt(bat2.getString("runs")) * 100 / Integer.parseInt(bat2.getString("balls"));
                    batsman2_sr.setText(format.format(bat2_sr));

                    JSONObject ball2 = bowlers.getJSONObject(1);
                    bowler2_name.setText(ball2.getString("name"));
                    bowler2_overs.setText(ball2.getString("overs"));
                    bowler2_maidens.setText(ball2.getString("maidens"));
                    bowler2_wickets.setText(ball2.getString("wickets"));
                    bowler2_runs.setText(ball2.getString("runs"));
                    double ball2_eco = Integer.parseInt(ball2.getString("runs")) / Double.parseDouble(ball2.getString("overs"));
                    bowler2_economy.setText(format.format(ball2_eco));
                }catch (Exception e){

                }finally {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            refreshScores(true);
                        }
                    }, 10000);
                }
            }
        }
    }
}
