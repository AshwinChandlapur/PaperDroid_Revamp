package vadeworks.news.paperdroids.Quickie;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import vadeworks.news.paperdroids.Display_news;
import vadeworks.news.paperdroids.MainScreen.MainScreen_Activity;
import vadeworks.paperdroid.R;

public class Feedback extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);



        Button suggestions = findViewById(R.id.suggestions);
        Button giveusa5 = findViewById(R.id.rateus);

        suggestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "vadeworks@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Suggestions to improve " + getString(R.string.app_name));
                startActivity(intent);
            }
        });


        giveusa5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //replaces the default 'Back' button action
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Feedback.this, MainScreen_Activity.class);
            finish();
            startActivity(intent);
        }
        return true;
    }
}
