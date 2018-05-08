package vadeworks.news.paperdroids;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vadeworks.news.paperdroids.HorizontalNews.Horizontal_Display_News;
import vadeworks.paperdroid.R;

/**
 * Created by ashwinchandlapur on 12/03/18.
 */


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<News> newsList;
    int pos;

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerAdapter(Context context, ArrayList<News> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_custom_layout, parent, false);

        return new ViewHolder(itemView);
    }

    public void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(400);
        view.startAnimation(anim);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        setFadeAnimation(holder.itemView);
        pos = position;

        holder.news_textview.setText(newsList.get(position).head);
        Picasso.with(context).load(newsList.get(position).thumburl).resize(106,66).centerCrop().into(holder.news_imageview);
        newsList.get(position).showNews();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Horizontal_Display_News.class);
                i.putExtra("newsObject", newsList);
                i.putExtra("position", position);
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (newsList.size() == 0)
            return 0;
        return newsList.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView news_textview;
        ImageView news_imageview;

        public ViewHolder(View v) {
            super(v);
            news_imageview = v.findViewById(R.id.newsImage);
            news_textview = v.findViewById(R.id.newsHeadlines);
        }
    }
}


