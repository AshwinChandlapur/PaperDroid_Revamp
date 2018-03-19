package vadeworks.news.paperdroids;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.udevel.widgetlab.TypingIndicatorView;

import java.util.ArrayList;

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

    public void firebaseNewsFetcher(final FragmentActivity fragmentActivity, final Context context, View view, final String category) {
        firestoreNews = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        firestoreNews.setFirestoreSettings(settings);

        typingView = view.findViewById(R.id.loader);
        mContext = context;
        mCategory = category;

        firebaseRecyclerview = (RecyclerView) view.findViewById(R.id.firebaseRecyclerview);
        firebaseRecyclerview.setHasFixedSize(true);

        Log.d("Starting Fetch", "Starting Fetch");
        firestoreNews.collection(mCategory)
                .orderBy("imgurl", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                Log.d("Docu", documentSnapshot.getId() + " => " + documentSnapshot.getData());
                                Log.d("AllContent", "all" + documentSnapshot.get("content"));
//                                News news = documentSnapshot.toObject(News.class);

                                News news1 = new News();
                                news1.head = ( documentSnapshot.get("head") != null) ?  documentSnapshot.get("head").toString() : "";
                                news1.link = ( documentSnapshot.get("link") != null) ?  documentSnapshot.get("link").toString() : "";
                                news1.content = ( documentSnapshot.get("content") != null) ?  documentSnapshot.get("content").toString() : "";
                                news1.thumburl = ( documentSnapshot.get("thumburl") != null) ?  documentSnapshot.get("thumburl").toString() : "";
                                news1.imgurl = ( documentSnapshot.get("imgurl") != null) ?  documentSnapshot.get("imgurl").toString() : "";
                                news1.tag = ( documentSnapshot.get("tag") != null) ?  documentSnapshot.get("tag").toString() : "";
                                news1.subtag = ( documentSnapshot.get("subtag") != null) ?  documentSnapshot.get("subtag").toString() : "";
                                news1.showNews();
                                if (!(news1.isEmpty()))
                                    newsList.add(news1);
                            }
                            Log.d("Starting Fetch", "Finishing Fetch");
                        } else {
                            Toast.makeText(context, "Oops, Something went wrong. Could'nt Fetch News :( ", Toast.LENGTH_SHORT).show();
                            Log.w("Docu", "Error getting documents.", task.getException());
                        }
                        // use a linear layout manager
                        mLayoutManager = new LinearLayoutManager(fragmentActivity);
                        firebaseRecyclerview.setLayoutManager(mLayoutManager);
                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(firebaseRecyclerview.getContext(), LinearLayoutManager.VERTICAL);
                        firebaseRecyclerview.addItemDecoration(dividerItemDecoration);
                        mAdapter = new RecyclerAdapter(mContext, newsList);
                        firebaseRecyclerview.setAdapter(mAdapter);
                        typingView.setVisibility(View.GONE);
                    }
                });
    }

}
