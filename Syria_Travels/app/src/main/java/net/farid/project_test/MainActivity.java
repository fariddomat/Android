package net.farid.project_test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    SharedPreferences test_name;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test_name = getSharedPreferences("LOGIN",0);
        editor = test_name.edit();
        editor.putString("notIsGet","no");
        editor.commit();
        FirebaseMessaging.getInstance().subscribeToTopic("room");

        final Handler handler = new Handler();
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {

                        Intent br=new Intent(MainActivity.this,search.class);
                        startActivity(br);

                    }
                });
            }
        }, 2000);
        startService(new Intent(this, getNewNot.class));
    this.finish();
    }

}
