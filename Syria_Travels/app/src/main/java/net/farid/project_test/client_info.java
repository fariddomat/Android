package net.farid.project_test;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

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
import java.util.Date;
import java.util.Locale;

import static com.basgeekball.awesomevalidation.ValidationStyle.COLORATION;

public class client_info extends AppCompatActivity {

    private ProgressDialog pDialog;

    SharedPreferences test_name;
    SharedPreferences.Editor editor;
    MenuItem bedMenuItem;
    String whoLogin,name;

    Calendar myCalendar;
    private String TAG = client_info.class.getSimpleName();

    String from,to,departure_date,departure_time,duration,type,adult_num,child_num;
    String fid,fid2,cid,price,child_price,client_id;

    EditText fnameEt,lnameEt,national_numEt,countryEt,cityEt,phoneEt,card_numEt,expire_dateEt,card_cvvEt;
    String fname,lname,national_num,country,city,phone,card_num,expire_date,card_cvv;
    int a;
    String manage;
    boolean bool;
    AwesomeValidation mAwesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_info);

        mAwesomeValidation = new AwesomeValidation(COLORATION);
        mAwesomeValidation.setColor(Color.YELLOW);  // optional, default color is RED if not set
        mAwesomeValidation.addValidation(client_info.this, R.id.client_fname, "[a-zA-Z\\s]+", R.string.err_name);
        mAwesomeValidation.addValidation(client_info.this, R.id.client_lname, "[a-zA-Z\\s]+", R.string.err_name);
        mAwesomeValidation.addValidation(client_info.this, R.id.client_country, "[a-zA-Z\\s]+", R.string.err_name);
        mAwesomeValidation.addValidation(client_info.this, R.id.client_city, "[a-zA-Z\\s]+", R.string.err_name);
        mAwesomeValidation.addValidation(client_info.this, R.id.client_phone_num, RegexTemplate.TELEPHONE, R.string.err_phone);
        mAwesomeValidation.addValidation(client_info.this, R.id.client_national_num, "[0-9]+", R.string.err_num);
        mAwesomeValidation.addValidation(client_info.this, R.id.card_num, "[0-9]+", R.string.err_num);
        mAwesomeValidation.addValidation(client_info.this, R.id.card_cvv, "[0-9][0-9][0-9]", R.string.err_cvv);


        test_name = getSharedPreferences("LOGIN",0);
        editor = test_name.edit();
        name=test_name.getString("name", "").toString();
        whoLogin=test_name.getString("login", "").toString();

        fnameEt=(EditText)findViewById(R.id.client_fname);
        lnameEt=(EditText)findViewById(R.id.client_lname);
        national_numEt=(EditText)findViewById(R.id.client_national_num);
        countryEt=(EditText)findViewById(R.id.client_country);
        cityEt=(EditText)findViewById(R.id.client_city);
        phoneEt=(EditText)findViewById(R.id.client_phone_num);
        card_numEt=(EditText)findViewById(R.id.card_num);
        expire_dateEt=(EditText)findViewById(R.id.card_expire_date);

        myCalendar = Calendar.getInstance();
        expire_dateEt.setOnClickListener(new View.OnClickListener() {
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
                        expire_dateEt.setText(sdf.format(myCalendar.getTime()));
                    }

                };
                new DatePickerDialog(client_info.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        card_cvvEt=(EditText)findViewById(R.id.card_cvv);

        manage=getIntent().getStringExtra("manage");
        if (manage.equals("2")){
            fid2=getIntent().getStringExtra("fid2");
        }
        fid=getIntent().getStringExtra("fid");
        cid=getIntent().getStringExtra("cid");
        client_id=getIntent().getStringExtra("client_id");
        from=getIntent().getStringExtra("from");
        to=getIntent().getStringExtra("to");
        departure_date=getIntent().getStringExtra("departure_date");
        departure_time=getIntent().getStringExtra("departure_time");
        duration=getIntent().getStringExtra("duration");
        adult_num=getIntent().getStringExtra("adult_num");
        child_num=getIntent().getStringExtra("child_num");
        price=getIntent().getStringExtra("price");
        type=getIntent().getStringExtra("type");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        test_name = getSharedPreferences("LOGIN",0);
        editor = test_name.edit();
        name=test_name.getString("name", "").toString();
        if (name.equals("") || name.equals("empty"))
            bedMenuItem.setTitle("Log in ");
        else{
            bedMenuItem.setTitle("Log out ");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        bedMenuItem = menu.findItem(R.id.action_log_out);
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
                Intent i=new Intent(client_info.this,search.class);
                startActivity(i);
                return true;
            case R.id.action_profile:
                whoLogin=test_name.getString("login", "").toString();
                Toast.makeText(getBaseContext(), "profile ", Toast.LENGTH_LONG).show();
                if(whoLogin.equals("admin")){
                    Toast.makeText(getBaseContext(), "Hello admin ", Toast.LENGTH_LONG).show();
                    Intent i3=new Intent(client_info.this,Admin_control_panel.class);
                    startActivity(i3);
                }
                if(whoLogin.equals("company")){
                    Toast.makeText(getBaseContext(), "Hello company ", Toast.LENGTH_LONG).show();
                    Intent i3=new Intent(client_info.this,Company_control_panel.class);
                    startActivity(i3);
                }
                if(whoLogin.equals("employee")){
                    Toast.makeText(getBaseContext(), "Hello employee ", Toast.LENGTH_LONG).show();
                    Intent i3=new Intent(client_info.this,employee_control_panel.class);
                    startActivity(i3);
                }
                if(whoLogin.equals("client")){
                    Toast.makeText(getBaseContext(), "Hello client ", Toast.LENGTH_LONG).show();
                    Intent i3=new Intent(client_info.this,Client_control_panel.class);
                    startActivity(i3);
                }
                return true;
            case R.id.action_log_out:
                if (bedMenuItem.getTitle().equals("Log In")){

                    Intent i2=new Intent(client_info.this,login.class);
                    startActivity(i2);
                }else {
                    whoLogin="empty";
                    editor.putString("name","empty");
                    editor.putString("login","empty");
                    editor.commit();
                    bedMenuItem.setTitle("Log In");
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void submit_info_func(View view) {
        bool= mAwesomeValidation.validate();
        if(bool==true) {
            fname = fnameEt.getText().toString();
            lname = lnameEt.getText().toString();
            national_num = national_numEt.getText().toString();
            country = countryEt.getText().toString();
            city = cityEt.getText().toString();
            phone = phoneEt.getText().toString();
            card_num = card_numEt.getText().toString();
            expire_date = expire_dateEt.getText().toString();
            card_cvv = card_cvvEt.getText().toString();
            Toast.makeText(getApplicationContext(), "go ..", Toast.LENGTH_LONG).show();
            a = 1;
            new addReservation().execute();
        }
    }


    private class addReservation extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            Log.e(TAG, " start: ");
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                //constants
                URL url = new URL("http://10.0.2.2:80/project/add_reservation.php");
                JSONObject jsonObject = new JSONObject();
                if (manage.equals("2")) {
                    if (a == 1) {
                        jsonObject.put("r_flight_id", fid);
                        jsonObject.put("r_round_trip", "departure");
                    } else {
                        jsonObject.put("r_flight_id", fid2);
                        jsonObject.put("r_round_trip", "return");
                    }
                }else{
                    jsonObject.put("r_flight_id", fid);
                    jsonObject.put("r_round_trip", "");
                }
                jsonObject.put("r_client_id", client_id);
                jsonObject.put("r_company_id", String.valueOf(cid));
                jsonObject.put("r_client_fname", fname);
                jsonObject.put("r_client_lname", lname);
                jsonObject.put("r_client_national_number", national_num);
                jsonObject.put("r_client_country", country);
                jsonObject.put("r_client_city", city);
                jsonObject.put("r_client_phone", phone);
                Date cDate = new Date();
                String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
                jsonObject.put("r_date", fDate);
                Calendar c = Calendar.getInstance();
                String ctime = new SimpleDateFormat("HH:mm:ss").format(c.getTime());
                jsonObject.put("r_time",ctime);
                jsonObject.put("r_seats_num", adult_num);
                jsonObject.put("r_children_num", child_num);
                jsonObject.put("r_extra_weight_value", "00");
                if(type.equals("Economy"))
                    jsonObject.put("r_type", "t_econ_price");
                else
                    jsonObject.put("r_type", "t_first_price");
                jsonObject.put("r_card_number", card_num);
                jsonObject.put("r_expire_date", expire_date);
                jsonObject.put("r_card_svv", card_cvv);

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
                //String contentAsString = readIt(is,len);

            } catch (IOException e) {
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

            return null;
        }

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
        protected void onPreExecute() {
            super.onPreExecute();
            if(a==1) {
                pDialog = new ProgressDialog(client_info.this);
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(false);
                pDialog.show();
            }

        }
        // onPostExecute displays the results of the AsyncTask.


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
            if (manage.equals("2"))
            if(a==1) {
                a=2;
                new addReservation().execute();
            }
                if (pDialog.isShowing())
                    pDialog.dismiss();

        }
    }

}
