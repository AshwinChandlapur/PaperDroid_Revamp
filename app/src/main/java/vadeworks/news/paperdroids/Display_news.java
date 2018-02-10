package vadeworks.news.paperdroids;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import vadeworks.paperdroid.R;

public class Display_news extends ActionBarActivity {

    String link,head,TAG,img_url,content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_news_activity);


//        android.support.v7.app.ActionBar AB = getSupportActionBar();
//        AB.hide();

//        News news =  (News)getIntent().getSerializableExtra("newsObject");
//        Log.d("news object","news"+news.head);

        head = getIntent().getStringExtra("singleHead");
        link = getIntent().getStringExtra("singleLink");
        content = getIntent().getStringExtra("singleContent");
        img_url = getIntent().getStringExtra("singleimg");
        TAG = getIntent().getStringExtra("vk_headlines");

        Log.d("Single","Single  "+head+link+content+img_url);





    }


}
