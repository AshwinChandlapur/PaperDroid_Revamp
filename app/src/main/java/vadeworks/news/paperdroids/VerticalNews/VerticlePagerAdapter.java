package vadeworks.news.paperdroids.VerticalNews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
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
    private TextView newsno;
    private TextView content;
    private TextView link;


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
        return view == object;
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

    private void verticalNewsDisplay(final int position, final View itemView){

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
