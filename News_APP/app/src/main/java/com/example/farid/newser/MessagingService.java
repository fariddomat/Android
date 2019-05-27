package com.example.farid.newser;

/**
 * Created by Farid on 8/11/2016.
 */

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MessagingService extends FirebaseMessagingService {


    private static final String NOTIFICATION_TAG = "NewMessage";
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());


        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody());

            notify2(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle());
        }


        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }


    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_news)
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void notify2(String messageBody,String messageTitle){
        NotificationManager nm=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification not=new Notification(android.R.drawable.stat_notify_more,"this is important",System.currentTimeMillis());

        CharSequence title="you have been notified";
        CharSequence details="Continue with what you are doing";
        Intent i =new Intent(this,MainActivity.class);
        PendingIntent p=PendingIntent.getActivity(this,0,i,0);
        //not.setLatestEventInfo(this, title, details, p);

        Notification.Builder builder = new Notification.Builder(this)
                .setContentIntent(p)
                .setSmallIcon(R.drawable.ic_news_in)
                .setContentTitle(messageTitle)
                .setContentText(messageBody);
        builder.setAutoCancel(false);
        builder.setTicker("this is important");
        builder.setSmallIcon(R.drawable.ic_news_in);
        //builder.setOngoing(true); //can't remove notification
        //builder.setSubText("This is subtext...");   //API level 16
        builder.setNumber(10);

        not = builder.build();


        nm.notify(0, not);
    }
}
