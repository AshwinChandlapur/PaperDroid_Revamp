package vadeworks.news.paperdroids;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by ashwinchandlapur on 10/02/18.
 */

public abstract class ListView_Adapter<News> extends BaseAdapter {

    private final LayoutInflater layoutInflater;
    private final Context context;
    private final ArrayList<News> news;

    public ListView_Adapter(Context context, ArrayList<News> news) {
        this.context = context;
        this.news = news;
        layoutInflater = LayoutInflater.from(context);
    }

        @Override
        public int getCount(){
            if(news.size()==0)
                return 0;
            return news.size();
        }

        @Override
        public Object getItem(int i){
            return news.get(i);
        }

        @Override
        public long getItemId(int i){
            return 0;
        }


        public abstract View getMyView(int i, View view, ViewGroup viewGroup,News news);
            @Override
            public View getView(int i, View convertView, ViewGroup parent) {
                return getMyView(i, convertView, parent,news.get(i));
            }

    }
