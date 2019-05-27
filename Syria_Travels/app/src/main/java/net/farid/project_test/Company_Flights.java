package net.farid.project_test;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import net.farid.project_test.Model.company_flight_item;
import net.farid.project_test.Adabter.listview_company_flights_adabter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Company_Flights extends AppCompatActivity {

    SharedPreferences test_name;
    SharedPreferences.Editor editor;
    String name,whoLogin;
    MenuItem bedMenuItem;


    private String TAG = Company_Flights.class.getSimpleName();
    private ListView listView;//list_company_flights
    private ArrayList<HashMap<String, String>> flights_item;
    private ProgressDialog pDialog;
    private ListAdapter adapter;
    private String company_id;

    company_flight_popup manage_pop;


    String f_id;
    String f_from;
    String f_to;
    String f_departure_date;
    String f_departure_time;
    String f_duration;
    private String f_econ_seats;
    private String f_first_seats;
    private String f_seats;
    private String f_econ_price;
    private String f_first_price;
    private String f_child_price;
    private String f_status;
    private String f_total_weight_value;
    private String f_extra_weight_price;

    public void finishT(){
        this.finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company__flights);

        test_name = getSharedPreferences("LOGIN",0);
        editor = test_name.edit();
        name=test_name.getString("name", "").toString();
        whoLogin=test_name.getString("login", "").toString();

        listView = (ListView)findViewById(R.id.list_company_flights);
        flights_item= new ArrayList<>();
        company_id=getIntent().getStringExtra("c_id");
        new GetCflights().execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                //Toast.makeText(getBaseContext(), manage_company_item.get(position).get("name"), Toast.LENGTH_LONG).show();
                f_id=flights_item.get(position).get("f_id");

                Log.e(TAG, "fffff" + f_id);
                f_from=flights_item.get(position).get("f_from");
                f_to=flights_item.get(position).get("f_to");
                f_departure_date=flights_item.get(position).get("f_departure_date");
                f_departure_time=flights_item.get(position).get("f_departure_time");
                f_duration=flights_item.get(position).get("f_duration");
                f_econ_seats=flights_item.get(position).get("f_econ_seats");
                f_first_seats=flights_item.get(position).get("f_first_seats");
                f_econ_price=flights_item.get(position).get("f_econ_price");
                f_first_price=flights_item.get(position).get("f_first_price");
                f_child_price=flights_item.get(position).get("f_child_price");
                f_status=flights_item.get(position).get("f_status");
                f_total_weight_value=flights_item.get(position).get("f_total_weight_value");
                f_extra_weight_price=flights_item.get(position).get("f_extra_weight_price");
                FragmentManager manager=getFragmentManager();
                manage_pop=new company_flight_popup();
                manage_pop.show(manager,null);

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
                Intent i=new Intent(Company_Flights.this,search.class);
                startActivity(i);
                return true;
            case R.id.action_profile:
                Intent i3=new Intent(Company_Flights.this,Company_control_panel.class);
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

    public void del_flight(View view) {
        new delFlight().execute();
    }

    public void update_flight(View view) {
        Intent br=new Intent(this,Add_flights.class);
        br.putExtra("manage","2");
        br.putExtra("f_id",f_id);
        br.putExtra("f_from",f_from);
        br.putExtra("f_to",f_to);
        br.putExtra("f_departure_date",f_departure_date);
        br.putExtra("f_departure_time",f_departure_time);
        br.putExtra("f_duration",f_duration);
        br.putExtra("f_econ_seats",f_econ_seats);
        br.putExtra("f_first_seats",f_first_seats);
        br.putExtra("f_econ_price",f_econ_price);
        br.putExtra("f_first_price",f_first_price);
        br.putExtra("f_child_price",f_child_price);
        br.putExtra("f_status",f_status);
        br.putExtra("f_total_weight_value",f_total_weight_value);
        br.putExtra("f_extra_weight_price",f_extra_weight_price);
        br.putExtra("c_id",company_id);

        br.putExtra("f_duration",f_duration);
        startActivity(br);
        this.finish();
    }

    public void arrive_flight(View view) {
        new arriveFlight().execute();
    }

    private class GetCflights extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            Log.e(TAG, " start: ");
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                //constants
                URL url = new URL("http://10.0.2.2:80/project/read_company_flights.php");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("c_id", company_id);
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
                JSONObject jsonObj = new JSONObject(response);

                JSONArray jsonArray = jsonObj.getJSONArray("posts");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject flight_details = jsonArray.getJSONObject(i);


                    int f_id = flight_details.getInt("f_id");
                    f_from = flight_details.getString("f_from");
                    f_to = flight_details.getString("f_to");
                    f_departure_date = flight_details.getString("f_departure_date");
                    f_departure_time = flight_details.getString("f_departure_time");
                    f_duration = flight_details.getString("f_duration");

                    f_econ_seats = flight_details.getString("f_econ_seats");
                    f_first_seats = flight_details.getString("f_first_seats");
                    f_econ_price = flight_details.getString("f_econ_price");
                    f_first_price = flight_details.getString("f_first_price");
                    f_child_price = flight_details.getString("f_child_price");
                    f_status = flight_details.getString("f_status");
                    f_total_weight_value = flight_details.getString("f_total_weight_value");
                    f_extra_weight_price = flight_details.getString("f_extra_weight_price");

                    // tmp hash map for single company
                    HashMap<String, String> flight = new HashMap<>();

                    // adding each child node to HashMap key => value
                    flight.put("f_id", String.valueOf(f_id));
                    flight.put("f_from", f_from);
                    flight.put("f_to", f_to);
                    flight.put("f_departure_date", f_departure_date);
                    flight.put("f_departure_time", f_departure_time);
                    flight.put("f_duration", f_duration);
                    flight.put("f_status", f_status);
                    flight.put("f_econ_seats", f_econ_seats);
                    flight.put("f_first_seats", f_first_seats);
                    flight.put("f_econ_price", f_econ_price);
                    flight.put("f_first_price", f_first_price);
                    flight.put("f_child_price", f_child_price);
                    flight.put("f_total_weight_value", f_total_weight_value);
                    flight.put("f_extra_weight_price", f_extra_weight_price);

                    // adding contact to contact list
                    flights_item.add(flight);
            } }
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
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Company_Flights.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }
        // onPostExecute displays the results of the AsyncTask.


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (pDialog.isShowing())
                pDialog.dismiss();

            adapter = new SimpleAdapter(
                    Company_Flights.this, flights_item,
                    R.layout.activity_company__flights_item, new String[]{"f_id", "f_from", "f_to","f_departure_date","f_departure_time",
            "f_duration"}, new int[]{R.id.flight_list_id,
                    R.id.flight_list_from, R.id.flight_list_to,R.id.flight_list_date,R.id.flight_list_time,R.id.flight_list_duration});
            listView.setAdapter(adapter);

        }
    }

    private class delFlight extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            Log.e(TAG, " start: ");
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            URL url = null;
            try {
                url = new URL("http://10.0.2.2:80/project/del_flight.php");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                //constants

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("id", f_id);

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
                //is = conn.getInputStream();
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

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Company_Flights.this);
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
            finishT();
        }
    }

    private class arriveFlight extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            Log.e(TAG, " start: ");
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            URL url = null;
            try {
                url = new URL("http://10.0.2.2:80/project/arrive_flight.php");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                //constants

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("id", f_id);

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
                //is = conn.getInputStream();
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

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Company_Flights.this);
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
            finishT();
        }
    }
}
