package vadeworks.news.paperdroids.MainScreen;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import vadeworks.news.paperdroids.AsiaNet.AsiaNet_MainActivity;
import vadeworks.news.paperdroids.Esanje.Esanje_MainActivity;
import vadeworks.news.paperdroids.Esanje.Esanje_Parser;
import vadeworks.news.paperdroids.Prajavani.PrajaVaani_MainActivity;
import vadeworks.news.paperdroids.Splash_Screen.Splash_Main_Activity;
import vadeworks.news.paperdroids.UdayaVaani.UdayaVaani_MainActivity;
import vadeworks.news.paperdroids.VijayaKarnataka.VijayaKarnataka_MainActivity;
import vadeworks.news.paperdroids.VijayaVaani.VijayaVaani_MainActivity;
import vadeworks.paperdroid.R;

public class MainScreen_Activity extends AppCompatActivity {


    CardView prajavani, vijayavani, vijayakarnataka, udayavani, suvarna, esanje;
    View parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainscreen_activity);
        parentLayout = findViewById(android.R.id.content);

        parentLayout.setFocusableInTouchMode(true);
        parentLayout.requestFocus();
        parentLayout.setFocusableInTouchMode(false);

        prajavani = findViewById(R.id.prajavani);
        vijayavani = findViewById(R.id.vijayavani);
        vijayakarnataka =findViewById(R.id.vijayakarnataka);
        udayavani = findViewById(R.id.udayavani);
        suvarna = findViewById(R.id.suvarna);
        esanje =findViewById(R.id.esanje);




        prajavani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainScreen_Activity.this, PrajaVaani_MainActivity.class);
                startActivity(i);
            }
        });

        vijayavani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainScreen_Activity.this, VijayaVaani_MainActivity.class);
                startActivity(i);
            }
        });

        vijayakarnataka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainScreen_Activity.this, VijayaKarnataka_MainActivity.class);
                startActivity(i);
            }
        });

        udayavani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainScreen_Activity.this, UdayaVaani_MainActivity.class);
                startActivity(i);
            }
        });


        suvarna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainScreen_Activity.this, AsiaNet_MainActivity.class);
                startActivity(i);
            }
        });


        esanje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainScreen_Activity.this, Esanje_MainActivity.class);
                startActivity(i);
            }
        });

    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        return true;
    }

}
