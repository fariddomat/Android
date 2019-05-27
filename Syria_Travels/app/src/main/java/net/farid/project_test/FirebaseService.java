package net.farid.project_test;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;

/**
 * Created by Farid on 6/23/2017.
 */

public class FirebaseService extends FirebaseMessagingService {

    SharedPreferences test_name;
    SharedPreferences.Editor editor;
    String name,whoLogin,e_id,c_id,client_id;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        super.onMessageReceived(remoteMessage);

        Log.e("message received from",remoteMessage.getFrom());
        Log.e("message content",remoteMessage.getData().get("message"));
        Log.e("message type",remoteMessage.getData().get("messageType"));

        test_name = getSharedPreferences("LOGIN",0);
        editor = test_name.edit();
        name=test_name.getString("name", "").toString();
        whoLogin=test_name.getString("login", "").toString();
        if(whoLogin.equals("employee")){
            //work if an employee accept or refuse request for client and another employee almost to change request status
            c_id= String.valueOf(test_name.getInt("employeeCompanyId",0));
            e_id= String.valueOf(test_name.getInt("id",0));
            if (!remoteMessage.getData().get("e_id").equals(e_id) && remoteMessage.getData().get("company_id").equals(c_id)){
                if(remoteMessage.getData().size()>0){

                    Log.e("message received from",remoteMessage.getFrom());
                    Log.e("message content",remoteMessage.getData().get("message"));
                    Log.e("message type",remoteMessage.getData().get("messageType"));

                    String messageContent=remoteMessage.getData().get("message");
                    String client_id=remoteMessage.getData().get("client_id");
                    String messageType=remoteMessage.getData().get("messageType");

                    Message message=new Message();
                    message.setClient_id(client_id);
                    message.setContent(messageContent);
                    message.setType(messageType);
                    if (isAppIsInBackground(this)){
                        sendNontification(message);
                    }else{
                        Intent intent=new Intent("updateEmployeeReservationView");
                        intent.putExtra("msg",message);
                        sendBroadcast(intent);

                    }
                }
            }
        }else if(whoLogin.equals("client")) {
            //send notification to client when refuse his request for reservation
            client_id = String.valueOf(test_name.getInt("id", 0));
            if (remoteMessage.getData().get("client_id").equals(client_id)) {
                if (remoteMessage.getData().size() > 0) {

                    Log.e("message received from", remoteMessage.getFrom());
                    Log.e("message content", remoteMessage.getData().get("message"));
                    Log.e("message type", remoteMessage.getData().get("messageType"));

                    String messageContent = remoteMessage.getData().get("message");
                    String client_id = remoteMessage.getData().get("client_id");
                    String messageType = remoteMessage.getData().get("messageTpe");

                    Message message = new Message();
                    message.setClient_id(client_id);
                    message.setContent(messageContent);
                    message.setType(messageType);
                    if (isAppIsInBackground(this)) {
                        sendNontification(message);
                    } else {
                        Intent intent = new Intent("updateControlPanel");
                        intent.putExtra("msg", message);
                        sendBroadcast(intent);

                    }
                }
            }else if(remoteMessage.getData().get("update").equals("update")){
                Intent intent=new Intent(this,Client_control_panel.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pi=PendingIntent.getActivity(this,0,intent,
                        PendingIntent.FLAG_ONE_SHOT);
                Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                android.support.v4.app.NotificationCompat.Builder nb=new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.home)
                        .setContentTitle("Syria Travel")
                        .setContentText(remoteMessage.getData().get("message"))
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pi);
                NotificationManager nm=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                nm.notify(0,nb.build());
            }
        }
    }


    private void sendNontification(Message message){
        Intent intent=new Intent(this,Client_control_panel.class);
        intent.putExtra("mas",message);
        intent.putExtra("client_id",message.getClient_id());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi=PendingIntent.getActivity(this,0,intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        android.support.v4.app.NotificationCompat.Builder nb=new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.home)
                .setContentTitle("Syria Travel")
                .setContentText(message.getContent())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pi);
        NotificationManager nm=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(0,nb.build());

    }
    private boolean isAppIsInBackground(Context context){
        boolean isInbackground=true;
        ActivityManager am=(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.KITKAT_WATCH){
            List<ActivityManager.RunningAppProcessInfo> rp=am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo pi:rp){
            if (pi.importance==ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND){
                for (String ap:pi.pkgList){
                    if (ap.equals(context.getPackageName()))
                        isInbackground=false;
                }
            }
                        }
        }
        else{
            List<ActivityManager.RunningTaskInfo> ti=am.getRunningTasks(1);
            ComponentName ci=ti.get(0).topActivity;
            if (ci.getPackageName().equals(context.getPackageName()))
                isInbackground=false;
        }

        return isInbackground;
    }

}
