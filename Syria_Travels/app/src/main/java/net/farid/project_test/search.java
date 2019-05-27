package net.farid.project_test;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.google.common.collect.Range;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.basgeekball.awesomevalidation.ValidationStyle.COLORATION;

public class search extends AppCompatActivity {


    private Menu menu;

    int x=0;
    int y=0;
    JSONArray jsonArray;

    String fromArr[];
    String toArr[];
    SharedPreferences test_name;
    SharedPreferences.Editor editor;
    String id;
    String isNewNotification="no";
    private int hot_number = 0;
    MenuItem bedMenuItem,bedNot;
    String c_type;//trip type
    RadioButton radioT,radioF;

    Calendar myCalendar;
    Button b,b2,b3;
    private TabHost myTabHost;
    Button search_one_way,search_round;
    EditText adult1,children1;
    EditText adult2,children2;
    AutoCompleteTextView from1,to1,from2,to2;

    Spinner type1,type2;
    String name,whoLogin;
    String from,to,departure_date,departure_time,duration,type,adult_num,child_num,return_date;
    int fid,cid,price,child_price;


    private String TAG = search.class.getSimpleName();
    boolean bool,bool2;
    AwesomeValidation mAwesomeValidation,mAwesomeValidation2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        new getFrom().execute();
        radioT=(RadioButton)findViewById(R.id.radioT);
        radioF=(RadioButton)findViewById(R.id.radioF);


        final TextView login=(TextView) findViewById(R.id.a_b_login) ;
        Button back=(Button)findViewById(R.id.a_b_back);
        Button home=(Button)findViewById(R.id.a_b_home);
        Button controlPanle=(Button)findViewById(R.id.a_b_control_panel);
        test_name = getSharedPreferences("LOGIN",0);
        editor = test_name.edit();
        name=test_name.getString("name", "").toString();
        whoLogin=test_name.getString("login", "").toString();
        id= String.valueOf(test_name.getInt("id",0));
        Log.e("id ",id);

        // Recuperation du TabHost
        myTabHost =(TabHost) findViewById(R.id.TabHost01);
        // Before adding tabs, it is imperative to call the method setup()
        myTabHost.setup();
        // Adding tabs
        // tab1 settings
        TabHost.TabSpec spec = myTabHost.newTabSpec("tab_creation");
        // text and image of tab
        spec.setIndicator("round trip",getResources().getDrawable(android.R.drawable.ic_menu_add));
        // specify layout of tab
        spec.setContent(R.id.onglet1);
        // adding tab in TabHost
        myTabHost.addTab(spec);
        // otherwise :
        myTabHost.addTab(myTabHost.newTabSpec("tab_inser").setIndicator("one way",getResources().getDrawable(android.R.drawable.ic_menu_edit)).setContent(R.id.onglet2));


        myCalendar = Calendar.getInstance();
        b=(Button)findViewById(R.id.oneway_date);
        b2=(Button)findViewById(R.id.round_from_date);
        b3=(Button)findViewById(R.id.round_to_date);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String myFormat = "yyyy-MM-dd"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        b.setText(sdf.format(myCalendar.getTime()));
                    }

                };
                new DatePickerDialog(search.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String myFormat = "yyyy-MM-dd"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        b2.setText(sdf.format(myCalendar.getTime()));
                    }

                };
                new DatePickerDialog(search.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String myFormat = "yyyy-MM-dd"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        b3.setText(sdf.format(myCalendar.getTime()));
                    }

                };
                new DatePickerDialog(search.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        mAwesomeValidation = new AwesomeValidation(COLORATION);
        mAwesomeValidation.setColor(Color.YELLOW);  // optional, default color is RED if not set

        mAwesomeValidation2 = new AwesomeValidation(COLORATION);
        mAwesomeValidation2.setColor(Color.YELLOW);  // optional, default color is RED if not set

        search_one_way=(Button)findViewById(R.id.search_one_way);
        from1=(AutoCompleteTextView)findViewById(R.id.from_one_way);
        to1=(AutoCompleteTextView)findViewById(R.id.to_one_way);
        adult1=(EditText)findViewById(R.id.adult_one_way);
        children1=(EditText)findViewById(R.id.child_one_way);
        type1=(Spinner)findViewById(R.id.type_one_way);

        search_round=(Button)findViewById(R.id.search_round);
        from2=(AutoCompleteTextView)findViewById(R.id.from_round);
        to2=(AutoCompleteTextView)findViewById(R.id.to_round);
        adult2=(EditText)findViewById(R.id.adult_round);
        children2=(EditText)findViewById(R.id.child_round);
        type2=(Spinner)findViewById(R.id.type_round);

        new isNewNotfication().execute();
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.e(TAG, "resume");
    }
    @Override
    protected void onRestart() {
        super.onRestart();

        new isNewNotfication().execute();
        test_name = getSharedPreferences("LOGIN",0);
        editor = test_name.edit();
        name=test_name.getString("name", "").toString();

        whoLogin=test_name.getString("login", "").toString();
        if(!whoLogin.equals("client"))
            bedNot.setVisible(false);
        if (name.equals("") || name.equals("empty"))
            bedMenuItem.setTitle("Log in ");
        else{
            bedMenuItem.setTitle("Log out ");
        }
        Log.e(TAG, "restart");
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

                    Intent i5 = new Intent(search.this, notification.class);
                    startActivity(i5);
                }
            });
        }
    }

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        this.menu = menu;
        new isNewNotfication().execute();
        test_name = getSharedPreferences("LOGIN",0);
        editor = test_name.edit();
        name=test_name.getString("name", "").toString();
        bedMenuItem = menu.findItem(R.id.action_log_out);
        bedNot=menu.findItem(R.id.action_notify);
        Log.e(TAG, "optiin o");
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

                    Intent i5 = new Intent(search.this, notification.class);
                    startActivity(i5);
                }
            });
        }

        if (name.equals("") || name.equals("empty"))
        bedMenuItem.setTitle("Log in ");

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
                Intent i=new Intent(search.this,search.class);
                startActivity(i);
                return true;
            case R.id.action_notify:
                Intent i5=new Intent(search.this,notification.class);
                startActivity(i5);
                return true;
            case R.id.action_profile:
                whoLogin=test_name.getString("login", "").toString();
                if(whoLogin.equals("admin")){
                    Toast.makeText(getBaseContext(), "Hello Admin ", Toast.LENGTH_LONG).show();
                    Intent i3=new Intent(search.this,Admin_control_panel.class);
                    startActivity(i3);
                }
                if(whoLogin.equals("company")){
                Toast.makeText(getBaseContext(), "Hello company admin", Toast.LENGTH_LONG).show();
                Intent i3=new Intent(search.this,Company_control_panel.class);
                startActivity(i3);
                }
                if(whoLogin.equals("employee")){
                    Toast.makeText(getBaseContext(), "Hello employee ", Toast.LENGTH_LONG).show();
                    Intent i3=new Intent(search.this,employee_control_panel.class);
                    startActivity(i3);
                }
                if(whoLogin.equals("client")){
                    Toast.makeText(getBaseContext(), "Hello client ", Toast.LENGTH_LONG).show();
                    Intent i3=new Intent(search.this,Client_control_panel.class);
                    startActivity(i3);
                }
                return true;
            case R.id.action_log_out:
                if (bedMenuItem.getTitle().equals("Log In")){

                    Intent i2=new Intent(search.this,login.class);
                    startActivity(i2);
                }else {
                    whoLogin="empty";
                    editor.putString("name","empty");
                    editor.putString("login","empty");
                    editor.commit();
                    bedMenuItem.setTitle("Log In");
                    isNewNotification="";
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void search_one_way_func(View view) {

        mAwesomeValidation2.addValidation(search.this, R.id.from_one_way, "[a-zA-Z\\s]+", R.string.err_name);
        mAwesomeValidation2.addValidation(search.this, R.id.to_one_way, "[a-zA-Z\\s]+", R.string.err_name);
        mAwesomeValidation2.addValidation(search.this, R.id.adult_one_way, Range.closed(1, 5), R.string.adulrnum);
        mAwesomeValidation2.addValidation(search.this, R.id.child_one_way, Range.closed(0, 5), R.string.childnum);
        bool2= mAwesomeValidation2.validate();
        if(bool2==true) {
            from = from1.getText().toString();
            to = to1.getText().toString();
            departure_date = b.getText().toString();
            adult_num = adult1.getText().toString();
            try {
                child_num = children1.getText().toString();
            }catch (Exception e){
                child_num="0";
            }
            type = type1.getSelectedItem().toString();
            Log.e("intent", from + " " + to + " " + departure_date + " " + adult_num + " " + child_num + " " + type);
            Intent i = new Intent(search.this, one_way_result_list.class);
            if(radioF.isChecked())
                c_type="f";
            else
                c_type="t";
            i.putExtra("c_type", c_type);
            i.putExtra("from", from);
            i.putExtra("to", to);
            i.putExtra("departure_date", departure_date);
            i.putExtra("adult_num", adult_num);
            i.putExtra("child_num", child_num);
            i.putExtra("type", type);
            startActivity(i);
            //new Search_one_way().execute();
        }
    }


    public void search_round_func(View view) {

        mAwesomeValidation.addValidation(search.this, R.id.from_round, "[a-zA-Z\\s]+", R.string.err_name);
        mAwesomeValidation.addValidation(search.this, R.id.to_round, "[a-zA-Z\\s]+", R.string.err_name);
        mAwesomeValidation.addValidation(search.this, R.id.adult_round, Range.closed(1, 5), R.string.adulrnum);

        mAwesomeValidation.addValidation(search.this, R.id.child_round, Range.closed(0, 5), R.string.childnum);
        bool= mAwesomeValidation.validate();
        if(bool==true) {
            from = from2.getText().toString();
            to = to2.getText().toString();
            departure_date = b2.getText().toString();
            return_date = b3.getText().toString();
            adult_num = adult2.getText().toString();try {
                child_num = children2.getText().toString();
            }catch (Exception e){
                child_num="0";
            }
            type = type2.getSelectedItem().toString();
            Log.e("intent", from + " " + to + " " + departure_date + " " + adult_num + " " + child_num + " " + type);
            Intent i = new Intent(search.this, round_result_list.class);
            if(radioF.isChecked())
                c_type="f";
            else
                c_type="t";
            i.putExtra("c_type", c_type);
            i.putExtra("from", from);
            i.putExtra("to", to);
            i.putExtra("departure_date", departure_date);
            i.putExtra("return_date", return_date);
            i.putExtra("adult_num", adult_num);
            i.putExtra("child_num", child_num);
            i.putExtra("type", type);
            startActivity(i);
        }
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
                    Log.e(TAG, "is: "+isNewNotification);
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

    private class getFrom extends AsyncTask<Void, Void, Boolean> {



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
                URL url = new URL("http://10.0.2.2:80/project/from.php");
                JSONObject jsonObject = new JSONObject();;
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

                jsonArray = jsonObjj.getJSONArray("posts");

                fromArr=new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject reservation_details = jsonArray.getJSONObject(i);
                    fromArr[i]=reservation_details.getString("t_from");
                    x=1;

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
            /*if(x==1){

                autoComplete();
            }*/

            new getTo().execute();
        }
    }

    private class getTo extends AsyncTask<Void, Void, Boolean> {



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
                URL url = new URL("http://10.0.2.2:80/project/to.php");
                JSONObject jsonObject = new JSONObject();;
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
                Log.e(TAG, "response to: "+response);
                JSONObject jsonObjj = new JSONObject(response);

                jsonArray = jsonObjj.getJSONArray("posts");

                toArr=new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject reservation_details = jsonArray.getJSONObject(i);
                    toArr[i]=reservation_details.getString("t_to");
                    y=1;

                }
                Log.e("to",toArr.toString());
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
            if(y==1){

                autoComplete();
            }

        }
    }


    public void autoComplete(){

        if(fromArr.length!=0 && toArr.length!=0)
        try {

            ArrayAdapter<String> adapterfrom = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.select_dialog_item, fromArr);
            ArrayAdapter<String> adapterto = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.select_dialog_item, toArr);


            Log.e("error","123");

            from1.setThreshold(1);//will start working from first character
            from1.setAdapter(adapterfrom);//setting the adapter data into the AutoCompleteTextView  
            from1.setTextColor(Color.RED);

            from2.setThreshold(1);//will start working from first character
            from2.setAdapter(adapterfrom);//setting the adapter data into the AutoCompleteTextView  
            from2.setTextColor(Color.RED);

            to1.setThreshold(1);//will start working from first character
            to1.setAdapter(adapterto);//setting the adapter data into the AutoCompleteTextView  
            to1.setTextColor(Color.RED);

            to2.setThreshold(1);//will start working from first character
            to2.setAdapter(adapterto);//setting the adapter data into the AutoCompleteTextView  
            to2.setTextColor(Color.RED);
        }
        catch (Exception e){
            Log.e("error",e.toString());
        }

    }
}