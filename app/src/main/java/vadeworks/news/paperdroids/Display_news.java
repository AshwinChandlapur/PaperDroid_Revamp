package vadeworks.news.paperdroids;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import vadeworks.paperdroid.R;

public class Display_news extends ActionBarActivity {



    TextView headlines_textview,content_textview,link_textview;
    ImageView imageView;

    String head,link,content,imgurl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_news_activity);
        views_init();


//        android.support.v7.app.ActionBar AB = getSupportActionBar();
//        AB.hide();



        head = getIntent().getStringExtra("singleHead");
        link = getIntent().getStringExtra("singleLink");
        imgurl = getIntent().getStringExtra("singleImg");
        content = getIntent().getStringExtra("singleContent");




//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                try {
//
//                    Document doc = Jsoup.connect(link).get();
//                    Elements article = doc.getElementsByClass("article-wrap new-article-desc").select("p");
//                    content = article.toString();
//                    content = Jsoup.parse(content).text();
//
//                    Log.d("parser","parser"+head);
//                    Log.d("parser","parser"+link);
//                    Log.d("parser","parser"+imgurl);
//                    Log.d("parser","parser"+ content);
//
//
//
//                } catch (IOException e) {
//
//                }
//            }
//        }).start();



        headlines_textview.setText(head);
        content_textview.setText(content);
        if(!imgurl.isEmpty())
        {
            Picasso.with(getApplicationContext())
                    .load(imgurl)
                    .placeholder(R.drawable.spaceullustration)
                    .error(R.drawable.spaceullustration)
                    .into(imageView);
        }else{
            Toast.makeText(getApplicationContext(),"Image Not Loading",Toast.LENGTH_LONG).show();
        }


        link_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(link));
                startActivity(i);
            }
        });


    }

    public void views_init(){

        headlines_textview = (TextView)findViewById(R.id.headline);
        content_textview = (TextView)findViewById(R.id.content);
        link_textview = (TextView)findViewById(R.id.link);
        imageView = (ImageView)findViewById(R.id.imageView);


    }

}
