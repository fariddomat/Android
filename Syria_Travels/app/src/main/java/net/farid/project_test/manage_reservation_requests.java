package net.farid.project_test;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class manage_reservation_requests extends AppCompatActivity {

    SharedPreferences test_name;
    SharedPreferences.Editor editor;
    String name,whoLogin;
    MenuItem bedMenuItem;

    String option;
    String employee_id,employeeCompanyId;
    URL url;
    int a=0;
    private String TAG = manage_reservation_requests.class.getSimpleName();
    private ListView listView;
    private ArrayList<HashMap<String, String>> manage_hold_requests;
    private ProgressDialog pDialog;
    hold_clients_requests_popup manage_popup;
    String f_id,client_id,client_national_num,client_phone;
    String client_fname,client_lname,client_country,status;
    String f_first_seats,f_econ_seats,r_children_num,r_seats_num,r_type;

    String refuseResone;
    EditText refuseResonEt;
    cancel_manage_popup manage_popup2;

    BroadcastReceiver mr=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Message message=intent.getParcelableExtra("msg");
            if(message!=null){
                Intent i=new Intent(manage_reservation_requests.this,manage_reservation_requests.class);
                i.putExtra("e_id",employee_id);
                i.putExtra("employeeCompanyId",employeeCompanyId);
                i.putExtra("option",option);
                startActivity(i);
                finishThis();

            }
        }
    };
    public void finishThis(){
        this.finish();
    }
    @Override
    protected void onResume(){
        super.onResume();
        registerReceiver(mr,new IntentFilter("updateEmployeeReservationView"));
    }
    @Override
    protected void onStop(){
        super.onStop();
        unregisterReceiver(mr);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_reservation_requests);
        test_name = getSharedPreferences("LOGIN",0);
        editor = test_name.edit();
        name=test_name.getString("name", "").toString();
        whoLogin=test_name.getString("login", "").toString();

        //firebase
        FirebaseMessaging.getInstance().subscribeToTopic("room");
        Log.e("app topic is"," topic for employee");
        //

        employee_id=getIntent().getStringExtra("e_id");
        employeeCompanyId=getIntent().getStringExtra("employeeCompanyId");
        option=getIntent().getStringExtra("option");
        listView = (ListView) findViewById(R.id.list_requests_hold);

        manage_hold_requests = new ArrayList<>();
        if(!option.equals("cancel")){
            new GetHoldRequests().execute();

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    //Collection<String> str=manage_hold_requests.get(position).values();

                    f_id =manage_hold_requests.get(position).get("flightid");
                    client_id = manage_hold_requests.get(position).get("client_id");
                    client_fname = manage_hold_requests.get(position).get("fname");
                    client_lname = manage_hold_requests.get(position).get("lname");
                    client_national_num =manage_hold_requests.get(position).get("client_national_N");
                    client_phone = manage_hold_requests.get(position).get("client_phoneNum");

                    client_country = manage_hold_requests.get(position).get("c_country");
                    status = manage_hold_requests.get(position).get("request_status");


                    r_type = manage_hold_requests.get(position).get("r_type");
                    r_seats_num = manage_hold_requests.get(position).get("r_seats_num");
                    r_children_num = manage_hold_requests.get(position).get("r_children_num");
                    f_econ_seats = manage_hold_requests.get(position).get("f_econ_seats");
                    f_first_seats = manage_hold_requests.get(position).get("f_first_seats");
                    String card_num=manage_hold_requests.get(position).get("r_card_number");
                    String card_date=manage_hold_requests.get(position).get("r_expire_date");
                    String card_svv=manage_hold_requests.get(position).get("r_card_svv");

                    FragmentManager F=getFragmentManager();
                    manage_popup=new hold_clients_requests_popup();
                    Bundle args=new Bundle();
                    args.putString("client_fname",client_fname);
                    args.putString("client_lname",client_lname);
                    args.putString("client_national_num",client_national_num);
                    args.putString("client_phone",client_phone);
                    args.putString("client_country",client_country);
                    args.putString("status",status);
                    args.putString("r_type",r_type);
                    args.putString("r_seats_num",r_seats_num);
                    args.putString("r_children_num",r_children_num);
                    args.putString("card_num",card_num);
                    args.putString("card_date",card_date);
                    args.putString("card_svv",card_svv);

                    if(option.equals("hold"))
                        args.putString("option","hold");
                    else if(option.equals("confirm"))
                        args.putString("option","confirm");
                    else
                        args.putString("option","refuse");

                    if(r_type.equals("t_econ_price"))
                        args.putString("f_seats",f_econ_seats);
                    else
                        args.putString("f_seats",f_first_seats);

                    manage_popup.setArguments(args);
                    manage_popup.show(F,null);

                }
            });

        }
        else{
            new GetCancelRequests().execute();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    f_id =manage_hold_requests.get(position).get("flightid");
                    client_id = manage_hold_requests.get(position).get("client_id");

                    r_type = manage_hold_requests.get(position).get("r_type");
                    r_seats_num = manage_hold_requests.get(position).get("r_seats_num");
                    r_children_num = manage_hold_requests.get(position).get("r_children_num");
                    FragmentManager manager=getFragmentManager();
                    manage_popup2=new cancel_manage_popup();
                    manage_popup2.show(manager,null);
                }
            });
        }

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
                Intent i=new Intent(manage_reservation_requests.this,search.class);
                startActivity(i);
                return true;
            case R.id.action_profile:
                Intent i3=new Intent(manage_reservation_requests.this,employee_control_panel.class);
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


    public void confirmClientRequests(View view) throws MalformedURLException {
        a=1;
        url = new URL("http://10.0.2.2:80/project/confirmClientrequests.php");
        new confirm_refuse_requests().execute();

    }
    public void refuseClientRequests(View view) throws MalformedURLException {
        a=2;
        refuseResone=manage_popup.getResone();
        Toast.makeText(getApplicationContext(),refuseResone,Toast.LENGTH_SHORT).show();;
        url = new URL("http://10.0.2.2:80/project/refuseClientrequests.php");
        new confirm_refuse_requests().execute();




    }

    public void acceptCancel(View view) throws MalformedURLException {

        url = new URL("http://10.0.2.2:80/project/acceptCancelRequests.php");
        new acceptCancelRequests().execute();
    }

    public void ignoreCancel(View view) throws MalformedURLException {

        url = new URL("http://10.0.2.2:80/project/ignoreCancelRequest.php");
        new acceptCancelRequests().execute();
    }
//ignoreCancelRequest

    private class GetHoldRequests extends AsyncTask<Void, Void, Boolean> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(manage_reservation_requests.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Log.e(TAG, " start: ");
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                //constants
                URL url;
                if(option.equals("hold"))
                    url = new URL("http://10.0.2.2:80/project/show_reservation_requests.php");
                else if(option.equals("confirm"))
                    url = new URL("http://10.0.2.2:80/project/show_reservation_confirmed.php");
                else
                    url = new URL("http://10.0.2.2:80/project/show_reservation_refused.php");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("e_id", employee_id);
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

                        int f_id = reservation_details.getInt("r_flight_id");
                        int client_id = reservation_details.getInt("r_client_id");
                        client_fname = reservation_details.getString("r_client_fname");
                        client_lname = reservation_details.getString("r_client_lname");
                        int client_national_num = reservation_details.getInt("r_client_national_number");
                        String client_phone = reservation_details.getString("r_client_phone");
                        client_country = reservation_details.getString("r_client_country");
                        status = reservation_details.getString("r_status");

                        String r_type = reservation_details.getString("r_type");
                        String r_seats_num = reservation_details.getString("r_seats_num");
                        String r_children_num = reservation_details.getString("r_children_num");
                        String f_econ_seats = reservation_details.getString("f_econ_seats");
                        String f_first_seats = reservation_details.getString("f_first_seats");

                    String card_svv = reservation_details.getString("r_card_svv");
                    String card_date = reservation_details.getString("r_expire_date");
                    String card_num = reservation_details.getString("r_card_number");
                        String r_round_trip = reservation_details.getString("r_round_trip");
                        if(r_round_trip.equals("null"))
                            r_round_trip="";

                        // tmp hash map for single company
                        HashMap<String, String> clientsrequests = new HashMap<>();

                        // adding each child node to HashMap key => value
                        clientsrequests.put("flightid", String.valueOf(f_id));
                        clientsrequests.put("client_id", String.valueOf(client_id));
                        clientsrequests.put("fname", client_fname);
                        clientsrequests.put("lname", client_lname);
                        clientsrequests.put("client_national_N", String.valueOf(client_national_num));
                        clientsrequests.put("client_phoneNum", client_phone);

                    clientsrequests.put("r_card_number", card_num);
                    clientsrequests.put("r_expire_date", card_date);
                    clientsrequests.put("r_card_svv", card_svv);

                        clientsrequests.put("c_country", client_country);
                        clientsrequests.put("request_status", status);


                        clientsrequests.put("r_type", r_type);
                        clientsrequests.put("r_seats_num", r_seats_num);
                        clientsrequests.put("r_children_num", r_children_num);
                        clientsrequests.put("f_econ_seats", f_econ_seats);
                        clientsrequests.put("f_first_seats", f_first_seats);

                        clientsrequests.put("r_round_trip", r_round_trip);

                        // adding contact to contact list
                        manage_hold_requests.add(clientsrequests);
                    }

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

            if (pDialog.isShowing())
                pDialog.dismiss();

            Log.d("post", "post");
            ListAdapter adapter = new SimpleAdapter(
                    manage_reservation_requests.this, manage_hold_requests,
                    R.layout.manage_reservation_requests_list, new String[]{"flightid", "fname", "lname", "c_country","r_round_trip"}, new int[]{R.id.flight_id,
                    R.id.c_fname, R.id.c_lname, R.id.c_country,R.id.is_return});
            listView.setAdapter(adapter);
        }
    }

    private class confirm_refuse_requests extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(manage_reservation_requests.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Log.e(TAG, " start: ");
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                //constants
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("f_id", f_id);
                jsonObject.put("c_id", client_id);
                jsonObject.put("e_id", employee_id);
                jsonObject.put("company_id", employeeCompanyId);
                if(a==2){

                    jsonObject.put("m_details", refuseResone);
                }
                    Date cDate = new Date();
                    String mDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
                    jsonObject.put("m_date", mDate);
                    Calendar c = Calendar.getInstance();
                    String mtime = new SimpleDateFormat("HH:mm:ss").format(c.getTime());
                    jsonObject.put("m_time",mtime);


                jsonObject.put("type", r_type);
                int num=Integer.parseInt(r_seats_num)+Integer.parseInt(r_children_num);
                jsonObject.put("number", num);
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

            if (pDialog.isShowing())
                pDialog.dismiss();
            finishThis();
        }
    }


    private class GetCancelRequests extends AsyncTask<Void, Void, Boolean> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(manage_reservation_requests.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Log.e(TAG, " start: ");
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                //constants
                URL url;
                    url = new URL("http://10.0.2.2:80/project/show_cancel_request.php");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("e_id", employee_id);
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

                        int f_id = reservation_details.getInt("r_flight_id");
                        int client_id = reservation_details.getInt("r_client_id");
                        status = reservation_details.getString("r_status");
                        String r_date = reservation_details.getString("r_date");
                        String r_time = reservation_details.getString("r_time");
                        String t_departure_date = reservation_details.getString("t_departure_date");
                        String t_departure_time = reservation_details.getString("t_departure_time");
                    String r_type = reservation_details.getString("r_type");
                    String r_seats_num = reservation_details.getString("r_seats_num");
                    String r_children_num = reservation_details.getString("r_children_num");

                        // tmp hash map for single company
                        HashMap<String, String> clientsrequests = new HashMap<>();

                        // adding each child node to HashMap key => value
                        clientsrequests.put("flightid", String.valueOf(f_id));
                        clientsrequests.put("client_id", String.valueOf(client_id));

                        clientsrequests.put("request_status", status);
                        clientsrequests.put("r_date", r_date);
                        clientsrequests.put("r_time", r_time);
                        clientsrequests.put("t_departure_date", t_departure_date);
                        clientsrequests.put("t_departure_time", t_departure_time);


                    clientsrequests.put("r_type", r_type);
                    clientsrequests.put("r_seats_num", r_seats_num);
                    clientsrequests.put("r_children_num", r_children_num);
                        // adding contact to contact list
                        manage_hold_requests.add(clientsrequests);


                }
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

            if (pDialog.isShowing())
                pDialog.dismiss();

            Log.d("post", "post");
            ListAdapter adapter = new SimpleAdapter(
                    manage_reservation_requests.this, manage_hold_requests,
                    R.layout.cancel_request_item, new String[]{"flightid", "client_id","t_departure_date","t_departure_time", "r_date", "r_time"}, new int[]{R.id.c_r_i_t_id,
                    R.id.c_r_i_c_id, R.id.c_r_i_t_date, R.id.c_r_i_t_time,R.id.c_r_i_r_date,R.id.c_r_i_r_time});
            listView.setAdapter(adapter);
        }
    }

    private class acceptCancelRequests extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(manage_reservation_requests.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Log.e(TAG, " start: ");
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                //constants
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("f_id", f_id);
                jsonObject.put("c_id", client_id);
                jsonObject.put("e_id", employee_id);
                jsonObject.put("company_id", employeeCompanyId);
                jsonObject.put("type", r_type);
                int num=Integer.parseInt(r_seats_num)+Integer.parseInt(r_children_num);
                jsonObject.put("number", num);

                Date cDate = new Date();
                String mDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
                jsonObject.put("m_date", mDate);
                Calendar c = Calendar.getInstance();
                String mtime = new SimpleDateFormat("HH:mm:ss").format(c.getTime());
                jsonObject.put("m_time",mtime);

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

            if (pDialog.isShowing())
                pDialog.dismiss();
            finishThis();

        }
    }


}
