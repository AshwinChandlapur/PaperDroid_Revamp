package vadeworks.news.paperdroids;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

import vadeworks.news.paperdroids.Exclusive.ExclusiveActivity;
import vadeworks.news.paperdroids.Levels.Tab1;
import vadeworks.news.paperdroids.MainScreen.MainScreen_Activity;
import vadeworks.news.paperdroids.VerticalNews.Vertical_News;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by ashwinchandlapur on 15/02/18.
 */

public class MainApplication extends Application {
    Bundle params = new Bundle();
    private FirebaseAnalytics mFirebaseAnalytics;
    private Articles todisplay;
    private String exclusiveId;


//    private RefWatcher refWatcher;
//    public static RefWatcher getRefWatcher(Context context) {
//        MainApplication application = (MainApplication) context.getApplicationContext();
//        return application.refWatcher;
//    }

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
//        refWatcher = LeakCanary.install(this);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean("firstTime", true)) {
            // run your one time code here
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", false);
            editor.putBoolean("isunlocked", false);
            editor.putLong("firstlaunch", System.currentTimeMillis() / 1000L);
//            editor.putLong("firstlaunch", 1520620801);
            Log.d("launch at", "first at: " + System.currentTimeMillis() / 1000L);
            editor.apply();
        }
        Log.d("launch at", "from pref: " + prefs.getLong("firstlaunch", System.currentTimeMillis() / 1000L));
        // Call syncHashedEmail anywhere in your app if you have the user's email.
        // This improves the effectiveness of OneSignal's "best-time" notification scheduling feature.
        // OneSignal.syncHashedEmail(userEmail);
    }

    private class onNotificationReceived implements OneSignal.NotificationReceivedHandler {

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
            Log.d("Incoming_Data", "Json Object Data" + data);
            String tag, singleLink, singleHead, singleImg, promotionLink, verticalLink;

            if (data != null) {
                tag = data.optString("documentid", "");
                singleHead = data.optString("singleHead", "No Headline");
                singleLink = data.optString("singleLink", "");
                singleImg = data.optString("singleImg", "No ImgUrl");
                promotionLink = data.optString("promotionLink", "");
                verticalLink = data.optString("verticalLink", "");
                exclusiveId = data.optString("exclusiveId", "");
                Log.d("Incoming_Data", "All Values" + tag + singleHead + singleLink + singleImg);

                if (!(tag.isEmpty())) {
                    Intent intent = new Intent(getApplicationContext(), Display_news.class);
                    Log.d("Incoming_Data", "Passing Intent to Display News");
                    intent.putExtra("singleLink", singleLink);
                    intent.putExtra("singleImg", singleImg);
                    intent.putExtra("singleHead", singleHead);
                    intent.putExtra("documentid", tag);
                    Log.d("notificationOpened", "document id: " + tag);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else if (!(promotionLink.isEmpty())) {
                    Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(promotionLink));
                    i.setFlags(FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                } else if (!(verticalLink.isEmpty())) {
                    Intent intent = new Intent(getApplicationContext(), Vertical_News.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("verticalLink", verticalLink);
                    startActivity(intent);
                } else if (!(exclusiveId.isEmpty())) {
                    Log.d("Inside ExclusiveID", "TodisplayIntent");
                    Intent intent = new Intent(getApplicationContext(), ExclusiveActivity.class);
                    Log.d("Inside ExclusiveID", "Intent");
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("exclusiveNotif", exclusiveId);
                    startActivity(intent);
                }
            } else {
                Intent intent = new Intent(getApplicationContext(), MainScreen_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }

}
