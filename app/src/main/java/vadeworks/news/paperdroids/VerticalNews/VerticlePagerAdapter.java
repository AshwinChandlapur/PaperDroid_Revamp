package vadeworks.news.paperdroids.VerticalNews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vadeworks.news.paperdroids.News;
import vadeworks.paperdroid.R;

/**
 * Created by ashwinchandlapur on 16/02/18.
 */

class VerticlePagerAdapter extends PagerAdapter {
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private ArrayList<News> mnews = new ArrayList<>();


    private TextView headline;
    private ImageView image;
    private TextView content;
    private TextView link;
    private int pos;
    News news;


    public VerticlePagerAdapter(Context context, ArrayList<News> news) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mnews = news;
        Log.d("Mnews size", "Mnews Size" + mnews.size());
    }


    @Override
    public int getCount() {
        return mnews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }



    @Override
    public Object instantiateItem(ViewGroup container,  int position) {
        View itemView = mLayoutInflater.inflate(R.layout.vertical_news_display, container, false);

//
//        for(position=0;position<mnews.size();position++){
//            news = mnews.get(position);
//            verticalNewsDisplayNews(position,itemView,news);
//        }




        if (position == 0) {
            verticalNewsDisplay(position, itemView);
        } else if (position == 1) {
            verticalNewsDisplay(position, itemView);
        } else if (position == 2) {
            verticalNewsDisplay(position, itemView);
        }
        else if (position == 3) {
            verticalNewsDisplay(position, itemView);
        } else if (position == 4) {
            verticalNewsDisplay(position, itemView);
        } else if (position == 5) {
            verticalNewsDisplay(position, itemView);
        } else if (position == 6) {
            verticalNewsDisplay(position, itemView);
        } else if (position == 7) {
            verticalNewsDisplay(position, itemView);
        } else if (position == 8) {
            verticalNewsDisplay(position, itemView);
        } else if (position == 9) {
            verticalNewsDisplay(position, itemView);
        }

        container.addView(itemView);
        return itemView;
    }



    private void verticalNewsDisplayNews( int position, View itemView,final News news) {
        pos = position;

        headline = itemView.findViewById(R.id.headline);
        content = itemView.findViewById(R.id.content);
        link = itemView.findViewById(R.id.link);
        image = itemView.findViewById(R.id.image);



        headline.setText(news.head);
        content.setText(news.content);
        Picasso.with(mContext).load(news.imgurl).into(image);

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.content));
                mContext.startActivity(browserIntent);

            }
        });


    }


    private void verticalNewsDisplay( int position, View itemView) {
        pos = position;

        headline = itemView.findViewById(R.id.headline);
        content = itemView.findViewById(R.id.content);
        link = itemView.findViewById(R.id.link);
        image = itemView.findViewById(R.id.image);



        headline.setText(mnews.get(position).head);
        content.setText(mnews.get(position).content);
        Picasso.with(mContext).load(mnews.get(position).imgurl).into(image);

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mnews.get(pos).content));
                mContext.startActivity(browserIntent);

            }
        });


    }


}
