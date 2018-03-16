package vadeworks.news.paperdroids.VerticalNews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vadeworks.news.paperdroids.MainScreen.MainScreen_Activity;
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
    private News fullnews;
    private TextView link;


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
    public Object instantiateItem(ViewGroup container, int position) {
        final View itemView = mLayoutInflater.inflate(R.layout.vertical_news_display, container, false);

        fullnews = new News(mnews.get(position).head,mnews.get(position).link,mnews.get(position).imgurl,mnews.get(position).content);
        verticalNewsDisplay(fullnews,itemView);
        container.addView(itemView);

        return itemView;
    }



    private void verticalNewsDisplay (final News singleNews, View itemView) {
        headline = itemView.findViewById(R.id.headline);
        content = itemView.findViewById(R.id.content);
        link = itemView.findViewById(R.id.link);
        image = itemView.findViewById(R.id.image);


        headline.setText(singleNews.head);
        content.setText(singleNews.content);
        Picasso.with(mContext).load(singleNews.imgurl).into(image);

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(singleNews.link));
                mContext.startActivity(browserIntent);
            }
        });
    }

}
