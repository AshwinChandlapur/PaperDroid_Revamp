package vadeworks.news.paperdroids;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import vadeworks.paperdroid.R;

public class Display_news extends ActionBarActivity {

    String link,head,TAG,img_url,content;


    TextView headlines_textview,content_textview,link_textview;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_news_activity);


//        android.support.v7.app.ActionBar AB = getSupportActionBar();
//        AB.hide();


        head = getIntent().getStringExtra("singleHead");
        link = getIntent().getStringExtra("singleLink");
        content = getIntent().getStringExtra("singleContent");
        img_url = getIntent().getStringExtra("singleimg");
        TAG = getIntent().getStringExtra("vk_headlines");
        Log.d("Single","Single  "+head+link+content+img_url);


        headlines_textview = (TextView)findViewById(R.id.headline);
        content_textview = (TextView)findViewById(R.id.content);
        link_textview = (TextView)findViewById(R.id.link);
        imageView = (ImageView)findViewById(R.id.imageView);




        headlines_textview.setText(head);
        content_textview.setText(content);
//        Picasso.with(getApplicationContext())
//                .load(head)
//                .placeholder(R.drawable.spaceullustration)
//                .error(R.drawable.spaceullustration)
//                .into(imageView);
        link_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(link));
                startActivity(i);
            }
        });













    }


}
