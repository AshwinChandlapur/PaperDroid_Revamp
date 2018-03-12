package vadeworks.news.paperdroids.HorizontalNews;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.udevel.widgetlab.TypingIndicatorView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

import vadeworks.news.paperdroids.News;
import vadeworks.paperdroid.R;


/**
 * Created by ashwinchandlapur on 27/02/18.
 */

class CustomPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<News> mNews = new ArrayList<>();
    News fullnews;
    int mPos;
    String fileName;

    private FloatingActionButton share;

     TextView content_textview;
     TextView link_textview;
     ImageView imageView;
     TypingIndicatorView typingView;
     TextView headlines_textview;



    public CustomPagerAdapter(Context context, ArrayList<News> news, int position ){
        mContext = context;
        mNews = news;
        mPos = position;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mNews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final View itemView = mLayoutInflater.inflate(R.layout.horizontal_pager_item, container, false);
        mPos = position;

        fullnews = new News(mNews.get(mPos).head, mNews.get(mPos).link, mNews.get(mPos).imgurl,mNews.get(mPos).content);
        display_news(fullnews,itemView,mPos);
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((CoordinatorLayout) object);
    }

    private void getScreenShot(final int mPos) {

        Picasso.with(mContext.getApplicationContext())
                .load(mNews.get(mPos).imgurl)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        try{
                            Date now = new Date();
                            android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
                            fileName = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";
                            File imageFile = new File(fileName);
                            FileOutputStream out = new FileOutputStream(imageFile);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, out);
                            out.flush();
                            out.close();
                            shareImage(imageFile, mNews.get(mPos).head);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
    }


    private void shareImage(File file, String head) {
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        final String appPackageName = mContext.getPackageName();
//        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, head);
        intent.putExtra(android.content.Intent.EXTRA_TEXT, head + "\n\nDownload News Duniya - Karnataka's Best Newspaper App\n" + "https://play.google.com/store/apps/details?id=" + appPackageName);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            mContext.startActivity(Intent.createChooser(intent, "Share Screenshot"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(mContext.getApplicationContext(), "No App Available", Toast.LENGTH_SHORT).show();
        }
    }



    private void display_news(News fullnews, final View itemView, final int mPos) {

        headlines_textview = itemView.findViewById(R.id.headline);
        content_textview = itemView.findViewById(R.id.content);
        link_textview = itemView.findViewById(R.id.link);
        imageView = itemView.findViewById(R.id.imageView);
        typingView = itemView.findViewById(R.id.loader);
        headlines_textview.setText(fullnews.head);
        share = itemView.findViewById(R.id.share);


        if (!fullnews.content.isEmpty()) {
            content_textview.setText(fullnews.content);
        }
        if (!fullnews.imgurl.isEmpty()) {
            Picasso.with(mContext)
                    .load(fullnews.imgurl)
                    .placeholder(R.drawable.image3)
                    .error(R.drawable.image3)
                    .into(imageView);
        }


        link_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share.setVisibility(View.GONE);
                WebView webView = itemView.findViewById(R.id.webView);
                webView.setVisibility(View.VISIBLE);
                webView.setWebViewClient(new WebViewClient());
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl(mNews.get(mPos).link);
            }
        });
        typingView.setVisibility(View.GONE);


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getScreenShot(mPos);
            }
        });
    }


}