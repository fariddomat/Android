package net.farid.project_test;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;

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
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.basgeekball.awesomevalidation.ValidationStyle.COLORATION;

public class Add_flights extends AppCompatActivity {


    SharedPreferences test_name;
    SharedPreferences.Editor editor;
    String name,whoLogin;
    MenuItem bedMenuItem;


    private ProgressDialog pDialog;

    private String TAG = Add_flights.class.getSimpleName();
    Button b;

    URL url;
    EditText fromEt,toEt,departure_dateEt,departure_timeEt,durationET,econ_setsEt,
            first_setsEt,econ_priceEt,first_priceEt,child_priceEt,statusEt,
            total_weightEt,extra_weight_valueEt;
    String from,to,departure_date,departure_time,duration,status;
    int econ_sets,first_sets,total_sets,econ_price,first_price,child_price,total_weight,extra_weight_value;
    Calendar myCalendar;
    int c_id;
    String f_id;
    String s;

    AwesomeValidation mAwesomeValidation;
    boolean bool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flights);

        test_name = getSharedPreferences("LOGIN",0);
        editor = test_name.edit();
        name=test_name.getString("name", "").toString();
        whoLogin=test_name.getString("login", "").toString();

        mAwesomeValidation = new AwesomeValidation(COLORATION);
        mAwesomeValidation.setColor(Color.YELLOW);  // optional, default color is RED if not set

        c_id=Integer.parseInt(getIntent().getStringExtra("c_id"));
        fromEt=(EditText)findViewById(R.id.f_from);
        toEt=(EditText)findViewById(R.id.f_to);
        departure_dateEt=(EditText)findViewById(R.id.depDate);
        departure_timeEt=(EditText)findViewById(R.id.depTime);
        durationET=(EditText)findViewById(R.id.f_duration);
        econ_setsEt=(EditText)findViewById(R.id.f_econ_sets);
        first_setsEt=(EditText)findViewById(R.id.f_first_sets);
        econ_priceEt=(EditText)findViewById(R.id.f_eprice);
        first_priceEt=(EditText)findViewById(R.id.f_fprice);
        child_priceEt=(EditText)findViewById(R.id.f_childprice);
        statusEt=(EditText)findViewById(R.id.f_status);
        total_weightEt=(EditText)findViewById(R.id.f_total_weight);
        extra_weight_valueEt=(EditText)findViewById(R.id.f_extraWeightprice);
        b=(Button)findViewById(R.id.newFlight);
        mAwesomeValidation.addValidation(Add_flights.this, R.id.f_from, "[a-zA-Z\\s]+", R.string.err_string);
        mAwesomeValidation.addValidation(Add_flights.this, R.id.f_to, "[a-zA-Z\\s]+", R.string.err_string);
        mAwesomeValidation.addValidation(Add_flights.this, R.id.f_econ_sets, "[0-9]+", R.string.err_num);
        mAwesomeValidation.addValidation(Add_flights.this, R.id.f_first_sets, "[0-9]+", R.string.err_num);
        mAwesomeValidation.addValidation(Add_flights.this, R.id.f_eprice, "[0-9]+", R.string.err_num);
        mAwesomeValidation.addValidation(Add_flights.this, R.id.f_fprice, "[0-9]+", R.string.err_num);
        mAwesomeValidation.addValidation(Add_flights.this, R.id.f_childprice, "[0-9]+", R.string.err_num);
        mAwesomeValidation.addValidation(Add_flights.this, R.id.f_status, "[a-zA-Z\\s]+", R.string.err_string);
        mAwesomeValidation.addValidation(Add_flights.this, R.id.f_total_weight, "[0-9]+", R.string.err_num);
        mAwesomeValidation.addValidation(Add_flights.this, R.id.f_extraWeightprice, "[0-9]+", R.string.err_num);

        myCalendar = Calendar.getInstance();
        s=getIntent().getStringExtra("manage");
        if(s.equals("2")){
            f_id=getIntent().getStringExtra("f_id");

            Log.e(TAG, " f_id " + f_id);
            fromEt.setText(getIntent().getStringExtra("f_from"));
            toEt.setText(getIntent().getStringExtra("f_to"));
            departure_dateEt.setText(getIntent().getStringExtra("f_departure_date"));
            departure_timeEt.setText(getIntent().getStringExtra("f_departure_time"));
            durationET.setText(getIntent().getStringExtra("f_duration"));
            econ_setsEt.setText(getIntent().getStringExtra("f_econ_seats"));
            first_setsEt.setText(getIntent().getStringExtra("f_first_seats"));
            econ_priceEt.setText(getIntent().getStringExtra("f_econ_price"));
            first_priceEt.setText(getIntent().getStringExtra("f_first_price"));
            child_priceEt.setText(getIntent().getStringExtra("f_child_price"));
            statusEt.setText(getIntent().getStringExtra("f_status"));
            total_weightEt.setText(getIntent().getStringExtra("f_total_weight_value"));
            extra_weight_valueEt.setText(getIntent().getStringExtra("f_extra_weight_price"));
            b.setText("UPDATE trip");
        }

        departure_dateEt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabel();
                    }

                };
                new DatePickerDialog(Add_flights.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        departure_timeEt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Add_flights.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        departure_timeEt.setText( selectedHour + ":" + selectedMinute+":00");
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        durationET.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Add_flights.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        durationET.setText( selectedHour + ":" + selectedMinute+":00");
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        test_name = getSharedPreferences("LOGIN",0);
        editor = test_name.edit();
        name=test_name.getString("name", "").toString();
        if (name.equals("") || name.equals("empty"))
            this.finish();
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
                Intent i=new Intent(Add_flights.this,search.class);
                startActivity(i);
                return true;
            case R.id.action_profile:
                Intent i3=new Intent(Add_flights.this,Company_control_panel.class);
                startActivity(i3);
                return true;
            case R.id.action_log_out:

                whoLogin="empty";
                editor.putString("name","empty");
                editor.putString("login","empty");
                editor.commit();
                bedMenuItem.setTitle("Log In");
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateLabel() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        departure_dateEt.setText(sdf.format(myCalendar.getTime()));
    }

    public void addFlights(View view) throws MalformedURLException {
        bool= mAwesomeValidation.validate();
        if(bool==true) {
            from = fromEt.getText().toString();
            to = toEt.getText().toString();
            departure_date = departure_dateEt.getText().toString();
            departure_time = departure_timeEt.getText().toString();
            duration = durationET.getText().toString();
            econ_sets = Integer.parseInt(econ_setsEt.getText().toString());
            first_sets = Integer.parseInt(first_setsEt.getText().toString());
            total_sets = econ_sets + first_sets;
            econ_price = Integer.parseInt(econ_priceEt.getText().toString());
            first_price = Integer.parseInt(first_priceEt.getText().toString());
            child_price = Integer.parseInt(child_priceEt.getText().toString());
            status = statusEt.getText().toString();
            total_weight = Integer.parseInt(total_weightEt.getText().toString());
            extra_weight_value = Integer.parseInt(extra_weight_valueEt.getText().toString());

            if (s.equals("2")) {

                url = new URL("http://10.0.2.2:80/project/update_flight.php");
                new addFlight().execute();
                //update_flight
                // call AsynTask to perform network operation on separate thread
                new addFlight().execute();
            } else {
                url = new URL("http://10.0.2.2:80/project/add_flight.php");
                new addFlight().execute();
            }
        }
    }
    private class addFlight extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            Log.e(TAG, " start: ");
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                //constants

                JSONObject jsonObject = new JSONObject();
                if(s.equals("2")){
                    jsonObject.put("id", f_id);
                }
                jsonObject.put("from", from);
                jsonObject.put("to", to);
                jsonObject.put("departure_date", departure_date);
                jsonObject.put("departure_time", departure_time);
                jsonObject.put("duration", duration);
                jsonObject.put("econ_sets", econ_sets);
                jsonObject.put("first_sets", first_sets);
                jsonObject.put("total_sets", total_sets);
                jsonObject.put("econ_price", econ_price);
                jsonObject.put("first_price", first_price);
                jsonObject.put("child_price", child_price);
                jsonObject.put("status", status);
                jsonObject.put("total_weight", total_weight);
                jsonObject.put("extra_weight_value", extra_weight_value);
                jsonObject.put("c_id", c_id);

                Date cDate = new Date();
                String mDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
                jsonObject.put("c_date", mDate);
                Calendar c = Calendar.getInstance();
                String mtime = new SimpleDateFormat("HH:mm:ss").format(c.getTime());
                jsonObject.put("c_time",mtime);

                String message = jsonObject.toString();

                Log.e(TAG, " start:2 "+message);
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
                Log.e(TAG, " finish ");
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
//                    is.close();
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
            pDialog = new ProgressDialog(Add_flights.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        // onPostExecute displays the results of the AsyncTask.


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();

            if (pDialog.isShowing()){
                pDialog.dismiss();}
            finish();
        }
    }

}
