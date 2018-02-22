package vadeworks.news.paperdroids;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;


import vadeworks.news.paperdroids.Prajavani.PrajaVaani_MainActivity;
import vadeworks.news.paperdroids.VerticalNews.Vertical_News;
import vadeworks.paperdroid.R;

/**
 * Created by ashwinchandlapur on 15/02/18.
 */

public class MainApplication extends Application {
    private FirebaseAnalytics mFirebaseAnalytics;
    Bundle params = new Bundle();

    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationReceivedHandler(new onNotificationReceived())
                .setNotificationOpenedHandler(new onNotificationOpened())
                .init();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getApplicationContext());




        // Call syncHashedEmail anywhere in your app if you have the user's email.
        // This improves the effectiveness of OneSignal's "best-time" notification scheduling feature.
        // OneSignal.syncHashedEmail(userEmail);
    }

    private  class onNotificationReceived implements OneSignal.NotificationReceivedHandler{

        @Override
        public void notificationReceived(OSNotification notification) {
            //Will be used to log No of Notifications Received.
            //Automatically done by OneSignal.
        }
    }



    private class onNotificationOpened implements OneSignal.NotificationOpenedHandler {
        // This fires when a notification is opened by tapping on it.
        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            OSNotificationAction.ActionType actionType = result.action.type;
            JSONObject data = result.notification.payload.additionalData;
            Log.d("Incoming_Data","Json Object Data"+data);
            String tag,singleLink,singleHead,singleImg,promotionLink;

            if (data != null) {
                tag = data.optString("tag", null);
                singleHead = data.optString("singleHead","No Headline");
                singleLink = data.optString("singleLink",null);
                singleImg = data.optString("singleImg","No ImgUrl");

                promotionLink = data.optString("promotionLink",null);
                Log.d("Incoming_Data","All Values"+tag+singleHead+singleLink+singleImg);


                if(!(singleLink.isEmpty()) && !(tag.isEmpty())){
                    Intent intent = new Intent(getApplicationContext(), Display_news.class);
                    Log.d("Incoming_Data","Passing Intent to Display News");
                    intent.putExtra("singleLink",singleLink);
                    intent.putExtra("singleImg",singleImg);
                    intent.putExtra("singleHead",singleHead);
                    intent.putExtra("tag",tag);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    }
                else if(!(promotionLink.isEmpty())){
                    Intent i = new Intent(android.content.Intent.ACTION_VIEW,Uri.parse(promotionLink));
                    i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }


                }else{
                Log.d("Inside Else","inside Else");
                Intent intent = new Intent(getApplicationContext(), Vertical_News.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

        }
    }


}
