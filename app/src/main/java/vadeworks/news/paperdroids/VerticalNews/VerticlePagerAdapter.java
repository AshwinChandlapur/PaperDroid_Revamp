package vadeworks.news.paperdroids.VerticalNews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vadeworks.news.paperdroids.Display_news;
import vadeworks.news.paperdroids.News;
import vadeworks.news.paperdroids.Prajavani.PrajaVaani_MainActivity;
import vadeworks.news.paperdroids.Prajavani.Prajavaani_Parser;
import vadeworks.paperdroid.R;

/**
 * Created by ashwinchandlapur on 16/02/18.
 */

public class VerticlePagerAdapter extends PagerAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<News> mnews = new ArrayList<News>();
    News imgNews;
    News news = new News();



    TextView headline,newsno;
    ImageView imageView;
    TextView content,link;


    String mResources[] = {"Spider-Man is a fictional superhero appearing in American comic books published by Marvel Comics. The character was created by writer-editor Stan Lee and writer-artist Steve Ditko, and first appeared in the anthology comic book Amazing Fantasy #15 (Aug. 1962) in the Silver Age of Comic Books. Lee and Ditko conceived the character as an orphan being raised by his Aunt May and Uncle Ben, and as a teenager, having to deal with the normal struggles of adolescence in addition to those of a costumed crime-fighter. Spider-Man's creators gave him super strength and agility, the ability to cling to most surfaces, shoot spider-webs using wrist-mounted devices of his own invention, which he calls \"web-shooters\", and react to danger quickly with his \"spider-sense\", enabling him to combat his foes.",
            "Iron Man is a 2008 American superhero film based on the Marvel Comics character of the same name, produced by Marvel Studios and distributed by Paramount Pictures.1 It is the first film in the Marvel Cinematic Universe. The film was directed by Jon Favreau, with a screenplay by Mark Fergus & Hawk Ostby and Art Marcum & Matt Holloway. It stars Robert Downey Jr., Terrence Howard, Jeff Bridges, Shaun Toub and Gwyneth Paltrow. In Iron Man, Tony Stark, an industrialist and master engineer, builds a powered exoskeleton and becomes the technologically advanced superhero Iron Man.\n" +
                    "\n"};

    public VerticlePagerAdapter(Context context, ArrayList<News> news){
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mnews = news;
        Log.d("Mnews size","Mnews Size"+mnews.size());
    }



    @Override
    public int getCount() {
        return mnews.size()-10;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }



    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final View itemView = mLayoutInflater.inflate(R.layout.vertical_news_display, container, false);

        if(position == 0){
            verticalNewsDisplay(position,itemView);
        }else if(position ==1)
        {
            verticalNewsDisplay(position,itemView);
        }else if(position==2)
        {
            verticalNewsDisplay(position,itemView);
        }else if(position==3)
        {
            verticalNewsDisplay(position,itemView);
        }else if(position==4)
        {
            verticalNewsDisplay(position,itemView);
        }else if(position==5)
        {
            verticalNewsDisplay(position,itemView);
        }else if(position==6)
        {
            verticalNewsDisplay(position,itemView);
        }else if(position==7)
        {
            verticalNewsDisplay(position,itemView);
        }else if(position==8)
        {
            verticalNewsDisplay(position,itemView);
        }else if(position==9)
        {
            verticalNewsDisplay(position,itemView);
        }

        container.addView(itemView);




        return itemView;
    }

    public void verticalNewsDisplay(final int position, final View itemView){

        headline = itemView.findViewById(R.id.headline);
        content = itemView.findViewById(R.id.content);
        link = itemView.findViewById(R.id.link);
        newsno = itemView.findViewById(R.id.newsNo);

        newsno.setText(String.valueOf(position+1));
        headline.setText(mnews.get(position).head);
        content.setText(mnews.get(position).content);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mnews.get(position).link));
                mContext.startActivity(browserIntent);

            }
        });




    }




}
