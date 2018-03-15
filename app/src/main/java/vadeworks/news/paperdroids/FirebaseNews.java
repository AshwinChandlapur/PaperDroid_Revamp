package vadeworks.news.paperdroids;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.udevel.widgetlab.TypingIndicatorView;

import java.util.ArrayList;

import vadeworks.news.paperdroids.HorizontalNews.Horizontal_Display_News;
import vadeworks.paperdroid.R;

/**
 * Created by ashwinchandlapur on 11/03/18.
 */

public class FirebaseNews {

    FirebaseFirestore firestoreNews;
    private TypingIndicatorView typingView;
    private String mCategory;
    private Context mContext;

    private RecyclerView firebaseRecyclerview;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<News> newsList = new ArrayList<>();

    public void firebaseNewsFetcher(final FragmentActivity fragmentActivity, final Context context, View view, final String category)
    {
        firestoreNews = FirebaseFirestore.getInstance();
        typingView = view.findViewById(R.id.loader);
        mContext = context;
        mCategory = category;

        firebaseRecyclerview = (RecyclerView) view.findViewById(R.id.firebaseRecyclerview);
        firebaseRecyclerview.setHasFixedSize(true);

        Log.d("Starting Fetch","Starting Fetch");
        firestoreNews.collection(mCategory)
                .orderBy("imgurl", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                Log.d("Docu", documentSnapshot.getId() + " => " + documentSnapshot.getData());
                                Log.d("AllContent","all"+documentSnapshot.get("content"));
                                News news  = documentSnapshot.toObject(News.class);
                                news.showNews();
                                if(!(news.isEmpty()))
                                    newsList.add(news);
                            }
                            Log.d("Starting Fetch","Finishing Fetch");
                        } else {
                            Toast.makeText(context,"Oops, Something went wrong :( ",Toast.LENGTH_SHORT).show();
                            Log.w("Docu", "Error getting documents.", task.getException());
                        }
                        // use a linear layout manager
                        mLayoutManager = new LinearLayoutManager(fragmentActivity);
                        firebaseRecyclerview.setLayoutManager(mLayoutManager);
                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(firebaseRecyclerview.getContext(), LinearLayoutManager.VERTICAL);
                        firebaseRecyclerview.addItemDecoration(dividerItemDecoration);
                        mAdapter = new RecyclerAdapter(mContext,newsList);
                        firebaseRecyclerview.setAdapter(mAdapter);
                        typingView.setVisibility(View.GONE);
                    }
                });
    }

 }
