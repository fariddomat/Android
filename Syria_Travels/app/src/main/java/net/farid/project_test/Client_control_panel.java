package net.farid.project_test;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.basgeekball.awesomevalidation.ValidationStyle.COLORATION;

public class Client_control_panel extends AppCompatActivity {


    private ProgressDialog pDialog;

    private String TAG = Client_control_panel.class.getSimpleName();
    SharedPreferences test_name;
    SharedPreferences.Editor editor;
    String name,whoLogin;
    int id;
    String username,password,passwordCon;
    MenuItem bedMenuItem;
    EditText usernameEt,passEt,passconfirmEt;

    LinearLayout l;
    AwesomeValidation mAwesomeValidation;
    boolean bool;


    @Override
    protected void onResume(){
        super.onResume();
        registerReceiver(mr,new IntentFilter("updateControlPanel"));

    }
    BroadcastReceiver mr=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Message message=intent.getParcelableExtra("msg");
            if(message!=null){
                Toast.makeText(getApplicationContext(),"notifyyy",Toast.LENGTH_LONG).show();

            }
        }
    };
    @Override
    protected void onStop(){
        super.onStop();
        unregisterReceiver(mr);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_control_panel);



                test_name = getSharedPreferences("LOGIN",0);
        editor = test_name.edit();
        name=test_name.getString("name", "").toString();
        whoLogin=test_name.getString("login", "").toString();
        id=test_name.getInt("id",0);

        //firebase
        FirebaseMessaging.getInstance().subscribeToTopic("room");
        Log.e("app topic is"," topic"+String.valueOf(id));
        //

        mAwesomeValidation = new AwesomeValidation(COLORATION);
        mAwesomeValidation.setColor(Color.YELLOW);  // optional, default color is RED if not set

        l=(LinearLayout)findViewById(R.id.layout_clinet_manage_profile);
        l.setVisibility(View.GONE);
        usernameEt=(EditText)findViewById(R.id.client_uname);
        passEt=(EditText)findViewById(R.id.client_pass);
        passconfirmEt=(EditText)findViewById(R.id.client_pass2);


        mAwesomeValidation.addValidation(Client_control_panel.this, R.id.client_uname, "[a-zA-Z\\s]+", R.string.err_name);
        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        mAwesomeValidation.addValidation(Client_control_panel.this, R.id.client_pass, regexPassword, R.string.err_password);
        // to validate a confirmation field (don't validate any rule other than confirmation on confirmation field)
        mAwesomeValidation.addValidation(Client_control_panel.this, R.id.client_pass2, R.id.client_pass, R.string.err_password_confirmation);

        new isNewNotfication().execute();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        test_name = getSharedPreferences("LOGIN",0);
        editor = test_name.edit();
        name=test_name.getString("name", "").toString();

        whoLogin=test_name.getString("login", "").toString();
        if(!whoLogin.equals("client"))
            bedNot.setVisible(false);
        else
            bedNot.setVisible(true);
        if (!name.equals("client") ||name.equals("") || name.equals("empty"))
            this.finish();
        else{
            bedMenuItem.setTitle("Log out ");
        }
        if(whoLogin.equals("client")) {
            bedNot.setVisible(true);
            MenuItemCompat.setActionView(bedNot, R.layout.notify_layout);
            View view = MenuItemCompat.getActionView(bedNot);
            ui_hot = (TextView) view.findViewById(R.id.hotlist_hot);
            updateHotCount(hot_number);

            final ImageView iconButtonMessages = (ImageView) view.findViewById(R.id.hotlist_bell);
            iconButtonMessages.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getBaseContext(), "not ", Toast.LENGTH_LONG).show();
                    Intent i5 = new Intent(Client_control_panel.this, notification.class);
                    startActivity(i5);
                }
            });
        }
    }

    String isNewNotification="no";
    MenuItem bedNot;
    private int hot_number = 0;
    private TextView ui_hot = null;
    public void updateHotCount(final int new_hot_number) {

        hot_number = new_hot_number;

        Log.e(TAG, " hot is: " + String.valueOf(hot_number));
        if (ui_hot == null) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (new_hot_number == 0)
                    ui_hot.setVisibility(View.INVISIBLE);
                else {
                    ui_hot.setVisibility(View.VISIBLE);
                    ui_hot.setText(Integer.toString(new_hot_number));
                }
            }
        });
    }
    private class isNewNotfication extends AsyncTask<Void, Void, Boolean> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Boolean doInBackground(Void... params) {

            Log.e(TAG, " start: ");
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {

                hot_number=0;
                //constants
                URL url = new URL("http://10.0.2.2:80/project/isNewNotification.php");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("c_id", id);
                String message = jsonObject.toString();
                Log.e(TAG, " start:2 ");
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout( 10000 /*milliseconds*/ );
                conn.setConnectTimeout( 15000 /* milliseconds */ );
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(message.getBytes().length);
                Log.e(TAG, " start:3 ");
                //make some HTTP header nicety
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                Log.e(TAG, " start:4 ");
                //open
                conn.connect();

                Log.e(TAG, " start:5 ");
                //setup send
                os = new BufferedOutputStream(conn.getOutputStream());
                os.write(message.getBytes());
                //clean up
                os.flush();

                //do somehting with response
                is = new BufferedInputStream(conn.getInputStream());
                String response = null;
                response = convertStreamToString(is);
                //String contentAsString = is.toString();
                Log.e(TAG, "response: "+response);

                JSONObject jsonObjj = new JSONObject(response);

                JSONArray jsonArray = jsonObjj.getJSONArray("posts");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject reservation_details = jsonArray.getJSONObject(i);




                    Log.e(TAG, " hot is: " + String.valueOf(hot_number));
                    isNewNotification = reservation_details.getString("new");
                    if(isNewNotification.equals("no"))
                        hot_number=0;
                    else
                        hot_number+=1;

                }
                updateHotCount(hot_number);
            }
            catch (IOException e) {
                Log.e(TAG, " error: " + e.getMessage());
                e.printStackTrace();
            } catch (JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                e.printStackTrace();
            } finally {
                //clean up
                try {
                    os.close();
                    is.close();
                } catch (IOException e) {
                    Log.e(TAG, " error: " + e.getMessage());
                    e.printStackTrace();
                }

                conn.disconnect();
            }

            return null;}

        private String convertStreamToString(InputStream is) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return sb.toString();
        }


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        bedMenuItem = menu.findItem(R.id.action_log_out);
        bedNot=menu.findItem(R.id.action_notify);

        if(whoLogin.equals("client")) {
            bedNot.setVisible(true);
            MenuItemCompat.setActionView(bedNot, R.layout.notify_layout);
            View view = MenuItemCompat.getActionView(bedNot);
            ui_hot = (TextView) view.findViewById(R.id.hotlist_hot);
            updateHotCount(hot_number);

            final ImageView iconButtonMessages = (ImageView) view.findViewById(R.id.hotlist_bell);
            iconButtonMessages.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i5 = new Intent(Client_control_panel.this, notification.class);
                    startActivity(i5);
                }
            });
        }

        if (name.equals("") || name.equals("empty"))
            this.finish();
        else{
            bedMenuItem.setTitle("Log out ");
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        switch (item.getItemId())
        {
            case R.id.action_home:
                Intent i=new Intent(Client_control_panel.this,search.class);
                startActivity(i);
                return true;
            case R.id.action_profile:
                Intent i3=new Intent(Client_control_panel.this,Client_control_panel.class);
                startActivity(i3);
                return true;
            case R.id.action_log_out:

                whoLogin="empty";
                editor.putString("name","empty");
                editor.putString("login","empty");
                editor.putInt("id",0);
                editor.commit();
                bedMenuItem.setTitle("Log In");
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void client_browsingFunc(View view) {
        this.finish();
        Intent i=new Intent(Client_control_panel.this,search.class);
        startActivity(i);
    }
    public void client_view_reservationFunc(View view) {
        Intent i=new Intent(Client_control_panel.this,Client_view_reservation.class);
        startActivity(i);
    }

    public void client_view_historyFunc(View view) {
    }

    public void client_manage_profileFunc(View view) {
        l.setVisibility(View.VISIBLE);
    }

    public void update_clientFunc(View view) {
        bool= mAwesomeValidation.validate();
        if(bool==true) {
            username = usernameEt.getText().toString();
            password = passEt.getText().toString();
            passwordCon = passconfirmEt.getText().toString();
            new updateClient().execute();
        }
    }
    private class updateClient extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            Log.e(TAG, " start: ");
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                //constants
                URL url = new URL("http://10.0.2.2:80/project/update_client.php");
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("id", id);
                jsonObject.put("username", username);
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
                String pass= Base64.encodeToString(hash, Base64.DEFAULT);


                jsonObject.put("password", SHA256.GenerateHash(password));
                String message = jsonObject.toString();

                Log.e(TAG, " start:2 ");
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout( 10000 /*milliseconds*/ );
                conn.setConnectTimeout( 15000 /* milliseconds */ );
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(message.getBytes().length);

                Log.e(TAG, " start:3 ");
                //make some HTTP header nicety
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                Log.e(TAG, " start:4 ");
                //open
                conn.connect();

                Log.e(TAG, " start:5 ");
                //setup send
                os = new BufferedOutputStream(conn.getOutputStream());
                os.write(message.getBytes());
                //clean up
                os.flush();

                //do somehting with response
                is = conn.getInputStream();
                Log.e(TAG, " start: "+is);
                //String contentAsString = readIt(is,len);

            } catch (IOException e) {
                Log.e(TAG, " error: " + e.getMessage());
                e.printStackTrace();
            } catch (JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                e.printStackTrace();
            }catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } finally {
                //clean up
                try {
                    os.close();
                    is.close();
                } catch (IOException e) {
                    Log.e(TAG, " error: " + e.getMessage());
                    e.printStackTrace();
                }

                conn.disconnect();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(Client_control_panel.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }
        // onPostExecute displays the results of the AsyncTask.


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
            if (pDialog.isShowing())
                pDialog.dismiss();
        }
    }



}
