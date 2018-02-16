package vadeworks.news.paperdroids;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

import vadeworks.news.paperdroids.VerticalNews.Vertical_News;

/**
 * Created by ashwinchandlapur on 15/02/18.
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(new onNotificationOpened())
                .init();
        // Call syncHashedEmail anywhere in your app if you have the user's email.
        // This improves the effectiveness of OneSignal's "best-time" notification scheduling feature.
        // OneSignal.syncHashedEmail(userEmail);
    }


    private class onNotificationOpened implements OneSignal.NotificationOpenedHandler {
        // This fires when a notification is opened by tapping on it.
        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            OSNotificationAction.ActionType actionType = result.action.type;
            JSONObject data = result.notification.payload.additionalData;
            Log.d("Incoming_Data","Json Object Data"+data);
            String tag,singleLink,singleHead,singleImg;

            if (data != null) {
                tag = data.optString("tag", null);
                singleHead = data.optString("singleHead","No Headline");
                singleLink = data.optString("singleLink",null);
                singleImg = data.optString("singleImg","No ImgUrl");
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

                }else{
                Log.d("Inside Else","inside Else");
                Intent intent = new Intent(getApplicationContext(), Vertical_News.class);
                startActivity(intent);
            }

        }
    }




}
