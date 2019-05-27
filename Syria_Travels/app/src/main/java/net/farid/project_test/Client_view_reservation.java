package net.farid.project_test;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import net.farid.project_test.Adabter.listview_client_reservation_adabter;
import net.farid.project_test.Model.company_flight_item;
import net.farid.project_test.parser.cancel_trip_popup;

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
import java.util.ArrayList;
import java.util.HashMap;
public class Client_view_reservation extends AppCompatActivity {
    cancel_trip_popup cancel;
    SharedPreferences test_name;
    SharedPreferences.Editor editor;
    String name,whoLogin;
    MenuItem bedMenuItem;


    String f_id,client_id,client_national_num,client_phone;
    String client_fname,client_lname,client_country,status;
    URL url;

    private  ListAdapter adapter;
    ArrayList<company_flight_item> trip_item;

    private String TAG = Client_control_panel.class.getSimpleName();
    private ListView listView;
    private ProgressDialog pDialog;
    private ArrayList<HashMap<String, String>> manage_reservation;
    int companylId;
    public void finishThis(){
        this.finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_view_reservation);

        test_name = getSharedPreferences("LOGIN",0);
        editor = test_name.edit();
        name=test_name.getString("name", "").toString();
        whoLogin=test_name.getString("login", "").toString();
        trip_item=new ArrayList<company_flight_item>();
        manage_reservation = new ArrayList<>();
        listView=(ListView)findViewById(R.id.client_reservation_list);
        client_id= String.valueOf(test_name.getInt("id",0));
        new GetConfirmed().execute();
        adapter = new listview_client_reservation_adabter(getApplicationContext(), R.layout.client_view_reservation_item, trip_item);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                f_id=trip_item.get(position).getF_id();
                companylId= Integer.parseInt(trip_item.get(position).getF_company_id());
                FragmentManager manager=getFragmentManager();
                cancel=new cancel_trip_popup();
                cancel.show(manager,null);
            }
        });
        new isNewNotfication().execute();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        test_name = getSharedPreferences("LOGIN",0);
        editor = test_name.edit();
        name=test_name.getString("name", "").toString();
        if (!name.equals("client") ||name.equals("") || name.equals("empty"))
            this.finish();
        else{
            bedMenuItem.setTitle("Log out ");
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
                jsonObject.put("c_id", client_id);
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

        MenuItemCompat.setActionView(bedNot, R.layout.notify_layout);
        View view = MenuItemCompat.getActionView(bedNot);
        ui_hot = (TextView) view.findViewById(R.id.hotlist_hot);
        updateHotCount(hot_number);
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
                Intent i=new Intent(Client_view_reservation.this,search.class);
                startActivity(i);
                return true;
            case R.id.action_profile:
                Intent i3=new Intent(Client_view_reservation.this,Client_control_panel.class);
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

    public void cConfirmFunc(View view) {
        manage_reservation.clear();
        trip_item.clear();
        new GetConfirmed().execute();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                f_id=trip_item.get(position).getF_id();
                companylId= Integer.parseInt(trip_item.get(position).getF_company_id());
                FragmentManager manager=getFragmentManager();
                cancel=new cancel_trip_popup();
                cancel.show(manager,null);
            }
        });

    }
    public void cHoldFunc(View view) {
        manage_reservation.clear();
        trip_item.clear();
        new GetHold().execute();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                f_id=trip_item.get(position).getF_id();
                companylId= Integer.parseInt(trip_item.get(position).getF_company_id());
                FragmentManager manager=getFragmentManager();
                cancel=new cancel_trip_popup();
                cancel.show(manager,null);
            }
        });
    }
    public void cHistoryFunc(View view) {
        manage_reservation.clear();
        trip_item.clear();
        new GetHistory().execute();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    public void cancelReservation(View view) {
        new cancelReservaion().execute();
    }

    private class GetConfirmed extends AsyncTask<Void, Void, Boolean> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(Client_view_reservation.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            trip_item.clear();
            Log.e(TAG, " start: ");
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                //constants
                URL url = new URL("http://10.0.2.2:80/project/client_confirmed_reservation.php");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("c_id", client_id);
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


                    int flight_id = reservation_details.getInt("flight_id");
                    int company_id = reservation_details.getInt("company_id");
                    String company_name = reservation_details.getString("company_name");
                    String f_time = reservation_details.getString("f_time");
                    String f_date = reservation_details.getString("f_date");
                    String f_from = reservation_details.getString("f_from");
                    String f_to = reservation_details.getString("f_to");
                    String c_icon ="http://10.0.2.2:80/Syria-Travels-copy/assets/images/companies/icons/"+ reservation_details.getString("c_icon");
                    String c_type=reservation_details.getString("c_type");
                    company_flight_item item=new company_flight_item();

                    item.setF_id(String.valueOf(flight_id));
                    item.setF_company_id(String.valueOf(company_id));
                    item.setC_name(company_name);
                    item.setF_departure_time(f_time);
                    item.setF_departure_date(f_date);
                    item.setF_from(f_from);
                    item.setF_to(f_to);
                    item.setCompanyIcon(c_icon);
                    item.setC_type(c_type);
                    trip_item.add(item);
                    /*
                    // tmp hash map for single company
                    HashMap<String, String> clientsrequests = new HashMap<>();

                    // adding each child node to HashMap key => value
                    clientsrequests.put("flight_id", String.valueOf(flight_id));
                    clientsrequests.put("company_id", String.valueOf(company_id));
                    clientsrequests.put("company_name", company_name);
                    clientsrequests.put("f_time", f_time);
                    clientsrequests.put("f_date", f_date);
                    clientsrequests.put("f_from", f_from);
                    clientsrequests.put("f_to", f_to);


                    // adding contact to contact list
                    manage_reservation.add(clientsrequests);
*/

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

            adapter = new listview_client_reservation_adabter(getApplicationContext(), R.layout.client_view_reservation_item, trip_item);

            listView.setAdapter(adapter);
            /*
            ListAdapter adapter = new SimpleAdapter(
                    Client_view_reservation.this, manage_reservation,
                    R.layout.client_view_reservation_item, new String[]{"f_from", "f_to", "company_name", "f_date","f_time"}, new int[]{R.id.c_v_l_from,
                    R.id.c_v_l_to, R.id.c_v_l_cname, R.id.c_v_l_date,R.id.c_v_l_time});
            listView.setAdapter(adapter);
            */
        }
    }

    private class GetHold extends AsyncTask<Void, Void, Boolean> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(Client_view_reservation.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            trip_item.clear();
            Log.e(TAG, " start: ");
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                //constants
                URL url = new URL("http://10.0.2.2:80/project/client_hold_reservation.php");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("c_id", client_id);
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


                    int flight_id = reservation_details.getInt("flight_id");
                    int company_id = reservation_details.getInt("company_id");
                    String company_name = reservation_details.getString("company_name");
                    String f_time = reservation_details.getString("f_time");
                    String f_date = reservation_details.getString("f_date");
                    String f_from = reservation_details.getString("f_from");
                    String f_to = reservation_details.getString("f_to");

                    String c_icon ="http://10.0.2.2:80/Syria-Travels-copy/assets/images/companies/icons/"+ reservation_details.getString("c_icon");
                    String c_type=reservation_details.getString("c_type");
                    company_flight_item item=new company_flight_item();

                    item.setF_id(String.valueOf(flight_id));
                    item.setF_company_id(String.valueOf(company_id));
                    item.setC_name(company_name);
                    item.setF_departure_time(f_time);
                    item.setF_departure_date(f_date);
                    item.setF_from(f_from);
                    item.setF_to(f_to);
                    item.setCompanyIcon(c_icon);
                    item.setC_type(c_type);
                    trip_item.add(item);
                    // tmp hash map for single company
                    /*
                    HashMap<String, String> clientsrequests = new HashMap<>();

                    // adding each child node to HashMap key => value
                    clientsrequests.put("flight_id", String.valueOf(flight_id));
                    clientsrequests.put("company_id", String.valueOf(company_id));
                    clientsrequests.put("company_name", company_name);
                    clientsrequests.put("f_time", f_time);
                    clientsrequests.put("f_date", f_date);
                    clientsrequests.put("f_from", f_from);
                    clientsrequests.put("f_to", f_to);


                    // adding contact to contact list
                    manage_reservation.add(clientsrequests);
*/

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

            adapter = new listview_client_reservation_adabter(getApplicationContext(), R.layout.client_view_reservation_item, trip_item);

            listView.setAdapter(adapter);
            /*
            ListAdapter adapter = new SimpleAdapter(
                    Client_view_reservation.this, manage_reservation,
                    R.layout.client_view_reservation_item, new String[]{"f_from", "f_to", "company_name", "f_date","f_time"}, new int[]{R.id.c_v_l_from,
                    R.id.c_v_l_to, R.id.c_v_l_cname, R.id.c_v_l_date,R.id.c_v_l_time});
            listView.setAdapter(adapter);*/
        }
    }

    private class cancelReservaion extends AsyncTask<Void, Void, Boolean> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(Client_view_reservation.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            trip_item.clear();
            Log.e(TAG, " start: ");
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                //constants
                URL url = new URL("http://10.0.2.2:80/project/client_cancel_reservation.php");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("c_id", client_id);;
                jsonObject.put("f_id", f_id);;
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
            Log.d("post", "cancel sent");
            finishThis();
        }
    }

    //client_history_reservation
    private class GetHistory extends AsyncTask<Void, Void, Boolean> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(Client_view_reservation.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            trip_item.clear();
            Log.e(TAG, " start: ");
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                //constants
                URL url = new URL("http://10.0.2.2:80/project/client_history_reservation.php");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("c_id", client_id);
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


                    int flight_id = reservation_details.getInt("flight_id");
                    int company_id = reservation_details.getInt("company_id");
                    String company_name = reservation_details.getString("company_name");
                    String f_time = reservation_details.getString("f_time");
                    String f_date = reservation_details.getString("f_date");
                    String f_from = reservation_details.getString("f_from");
                    String f_to = reservation_details.getString("f_to");

                    String c_icon ="http://10.0.2.2:80/Syria-Travels-copy/assets/images/companies/icons/"+ reservation_details.getString("c_icon");
                    String c_type=reservation_details.getString("c_type");
                    company_flight_item item=new company_flight_item();

                    item.setF_id(String.valueOf(flight_id));
                    item.setF_company_id(String.valueOf(company_id));
                    item.setC_name(company_name);
                    item.setF_departure_time(f_time);
                    item.setF_departure_date(f_date);
                    item.setF_from(f_from);
                    item.setF_to(f_to);
                    item.setCompanyIcon(c_icon);
                    item.setC_type(c_type);
                    trip_item.add(item);
                    // tmp hash map for single company
                    /*
                    HashMap<String, String> clientsrequests = new HashMap<>();

                    // adding each child node to HashMap key => value
                    clientsrequests.put("flight_id", String.valueOf(flight_id));
                    clientsrequests.put("company_id", String.valueOf(company_id));
                    clientsrequests.put("company_name", company_name);
                    clientsrequests.put("f_time", f_time);
                    clientsrequests.put("f_date", f_date);
                    clientsrequests.put("f_from", f_from);
                    clientsrequests.put("f_to", f_to);


                    // adding contact to contact list
                    manage_reservation.add(clientsrequests);
*/

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

            adapter = new listview_client_reservation_adabter(getApplicationContext(), R.layout.client_view_reservation_item, trip_item);

            listView.setAdapter(adapter);
            /*
            ListAdapter adapter = new SimpleAdapter(
                    Client_view_reservation.this, manage_reservation,
                    R.layout.client_view_reservation_item, new String[]{"f_from", "f_to", "company_name", "f_date","f_time"}, new int[]{R.id.c_v_l_from,
                    R.id.c_v_l_to, R.id.c_v_l_cname, R.id.c_v_l_date,R.id.c_v_l_time});
            listView.setAdapter(adapter);*/
        }
    }

}
