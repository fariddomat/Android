package net.farid.project_test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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

import net.farid.project_test.Adabter.listview_one_way_result_adabter;
import net.farid.project_test.Model.company_flight_item;

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
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class round_result_list extends AppCompatActivity {

    int x=0;
    int y=0;
    SharedPreferences test_name;
    SharedPreferences.Editor editor;
    String name,whoLogin;
    MenuItem bedMenuItem;
    String c_type;

    String from1,to1,departure_date1,departure_time1,duration1,type1,adult_num1,child_num1,return_date1,f_extra_weight_price,f_total_weight_value;
    int fid1,cid1,price1,child_price;


    String departure_date2,departure_time2,duration2,f_extra_weight_price2,f_total_weight_value2;
    int fid2,cid2,price2,child_price2;
    int response_code;
    private ProgressDialog pDialog;
    private ArrayList<HashMap<String, String>> flights_item1;
    private ArrayList<HashMap<String, String>> flights_item2;
    private String TAG = login.class.getSimpleName();

    int a;
    ListView listView1,listView2;
    private ListAdapter adapter;

    private listview_one_way_result_adabter adabter,adabter2;
    ArrayList<company_flight_item> flight_item,flight_item2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_result_list);

        flight_item=new ArrayList<company_flight_item>();
        flight_item2=new ArrayList<company_flight_item>();

        test_name = getSharedPreferences("LOGIN",0);
        editor = test_name.edit();
        name=test_name.getString("name", "").toString();
        whoLogin=test_name.getString("login", "").toString();

        flights_item1= new ArrayList<>();
        flights_item2= new ArrayList<>();
        listView1 = (ListView) findViewById(R.id.round1_list);
        listView2 = (ListView) findViewById(R.id.round2_list);
        from1=getIntent().getStringExtra("from");
        to1=getIntent().getStringExtra("to");
        departure_date1=getIntent().getStringExtra("departure_date");
        return_date1=getIntent().getStringExtra("return_date");
        adult_num1=getIntent().getStringExtra("adult_num");
        child_num1=getIntent().getStringExtra("child_num");
        type1=getIntent().getStringExtra("type");
        c_type=getIntent().getStringExtra("c_type");
        a=1;
        new Search_round().execute();


        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                fid1=Integer.parseInt(flight_item.get(position).getF_id());
                departure_time1=flight_item.get(position).getF_departure_time();
                duration1=flight_item.get(position).getF_duration();
                price1=Integer.parseInt(flight_item.get(position).getPrice());
                cid1=Integer.parseInt(flight_item.get(position).getF_company_id());

                child_price=Integer.parseInt(flight_item.get(position).getF_child_price());
                f_total_weight_value=flight_item.get(position).getF_total_weight_value();
                f_extra_weight_price=flight_item.get(position).getF_extra_weight_price();

                x=1;
            }
        });
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                fid2=Integer.parseInt(flight_item2.get(position).getF_id());
                departure_time2=flight_item2.get(position).getF_departure_time();
                duration2=flight_item2.get(position).getF_duration();
                price2=Integer.parseInt(flight_item2.get(position).getPrice());
                cid2=Integer.parseInt(flight_item2.get(position).getF_company_id());

                child_price2=Integer.parseInt(flight_item2.get(position).getF_child_price());
                f_total_weight_value2=flight_item2.get(position).getF_total_weight_value();
                f_extra_weight_price2=flight_item2.get(position).getF_extra_weight_price();

                y=1;
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
                Intent i=new Intent(round_result_list.this,search.class);
                startActivity(i);
                return true;
            case R.id.action_profile:
                whoLogin=test_name.getString("login", "").toString();
                Toast.makeText(getBaseContext(), "profile ", Toast.LENGTH_LONG).show();
                if(whoLogin.equals("admin")){
                    Toast.makeText(getBaseContext(), "Hello admin ", Toast.LENGTH_LONG).show();
                    Intent i3=new Intent(round_result_list.this,Admin_control_panel.class);
                    startActivity(i3);
                }
                if(whoLogin.equals("company")){
                    Toast.makeText(getBaseContext(), "Hello company ", Toast.LENGTH_LONG).show();
                    Intent i3=new Intent(round_result_list.this,Company_control_panel.class);
                    startActivity(i3);
                }
                if(whoLogin.equals("employee")){
                    Toast.makeText(getBaseContext(), "Hello employee ", Toast.LENGTH_LONG).show();
                    Intent i3=new Intent(round_result_list.this,employee_control_panel.class);
                    startActivity(i3);
                }
                if(whoLogin.equals("client")){
                    Toast.makeText(getBaseContext(), "Hello client ", Toast.LENGTH_LONG).show();
                    Intent i3=new Intent(round_result_list.this,Client_control_panel.class);
                    startActivity(i3);
                }
                return true;
            case R.id.action_log_out:
                if (bedMenuItem.getTitle().equals("Log In")){

                    Intent i2=new Intent(round_result_list.this,login.class);
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

    public void book_round_func(View view) {
        if (x == 1 && y == 1) {
            Intent i=new Intent(round_result_list.this,flight_details.class);
            i.putExtra("manage","2");
            i.putExtra("fid",String.valueOf(fid1));
            i.putExtra("fid2",String.valueOf(fid2));
            i.putExtra("cid",String.valueOf(cid1));
            i.putExtra("from",from1);
            i.putExtra("to",to1);
            i.putExtra("departure_date",departure_date1);
            i.putExtra("departure_time",departure_time1);
            i.putExtra("duration",duration1);
            i.putExtra("return_date",return_date1);
            i.putExtra("return_time",departure_time2);
            i.putExtra("duration2",duration2);
            i.putExtra("adult_num",adult_num1);
            i.putExtra("child_num",child_num1);
            i.putExtra("price",String.valueOf(price1));
            i.putExtra("price2",String.valueOf(price2));

            i.putExtra("child_price",String.valueOf(child_price));
            i.putExtra("f_extra_weight_price",f_extra_weight_price);
            i.putExtra("f_total_weight_value",f_total_weight_value);


            i.putExtra("child_price2",String.valueOf(child_price2));
            i.putExtra("f_extra_weight_price2",f_extra_weight_price2);
            i.putExtra("f_total_weight_value2",f_total_weight_value2);
            i.putExtra("type",type1);
            startActivity(i);
        }else{
            Toast.makeText(getBaseContext(),"please select 2 trips",Toast.LENGTH_SHORT).show();
        }


    }

    private class Search_round extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            Log.e(TAG, " start: ");
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                //constants
                URL url = new URL("http://10.0.2.2:80/project/search_one_way.php");
                JSONObject jsonObject = new JSONObject();
                if(a==1) {
                    jsonObject.put("f_from", from1);
                    jsonObject.put("f_to", to1);
                    jsonObject.put("f_departure_date", departure_date1);
                }
                else{
                    jsonObject.put("f_from", to1);
                    jsonObject.put("f_to", from1);
                    jsonObject.put("f_departure_date", return_date1);
                }
                jsonObject.put("adult_num", adult_num1);
                jsonObject.put("child_num", child_num1);
                jsonObject.put("type", type1);
                jsonObject.put("c_type", c_type);
                String message = jsonObject.toString();

                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /*milliseconds*/);
                conn.setConnectTimeout(15000 /* milliseconds */);
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
                Log.e(TAG, "response: " + response);
                JSONObject jsonObj = new JSONObject(response);
                //JSONArray jsonArray = jsonObj.getJSONArray("posts");

                response_code = jsonObj.getInt("success");
                if (response_code == 1) {
                    JSONArray jsonArray = jsonObj.getJSONArray("posts");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject flight_details = jsonArray.getJSONObject(i);


                        int f_id = flight_details.getInt("f_id");
                        int f_company_id = flight_details.getInt("f_company_id");
                        String c_name = flight_details.getString("c_name");
                        String f_departure_time = flight_details.getString("f_departure_time");
                        String f_duration = flight_details.getString("f_duration");
                        String f_status = flight_details.getString("f_status");
                        String price=flight_details.getString("price");

                        String f_child_price=flight_details.getString("f_child_price");
                        String f_total_weight_value=flight_details.getString("f_total_weight_value");
                        String f_extra_weight_price=flight_details.getString("f_extra_weight_price");
                        String icon="http://10.0.2.2:80/Syria-Travels-copy/assets/images/companies/icons/"+ flight_details.getString("icon");
                        // tmp hash map for single company
                        company_flight_item item=new company_flight_item();
                        item.setC_name(c_name);
                        item.setF_id(String.valueOf(f_id));
                        item.setF_company_id(String.valueOf(f_company_id));
                        item.setF_departure_time(f_departure_time);
                        item.setF_duration(f_duration);
                        item.setF_status(f_status);
                        item.setPrice(price);
                        item.setF_total_weight_value(f_total_weight_value);
                        item.setF_extra_weight_price(f_extra_weight_price);
                        item.setF_child_price(f_child_price);
                        item.setCompanyIcon(icon);
                        if(a==1)
                        flight_item.add(item);
                        else
                            flight_item2.add(item);
                        /*HashMap<String, String> flight = new HashMap<>();

                        // adding each child node to HashMap key => value
                        flight.put("f_id", String.valueOf(f_id));
                        flight.put("f_company_id", String.valueOf(f_company_id));
                        flight.put("f_departure_time", f_departure_time);
                        flight.put("f_duration", f_duration);
                        flight.put("f_status", f_status);
                        flight.put("price",price);
                        flight.put("f_total_weight_value",f_total_weight_value);
                        flight.put("f_extra_weight_price",f_extra_weight_price);
                        flight.put("child_price",f_child_price);
                        // adding contact to contact list
                        if(a==1)
                            flights_item1.add(flight);
                        else
                            flights_item2.add(flight);*/
                    }
                }

            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally{
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
                pDialog = new ProgressDialog(round_result_list.this);
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(false);
                pDialog.show();
            }
        }
        // onPostExecute displays the results of the AsyncTask.


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);


            if(a==1) {
                adapter = new listview_one_way_result_adabter(getApplicationContext(), R.layout.activity_one_way_result_list_items, flight_item);

                listView1.setAdapter(adapter);




                a=2;
                new Search_round().execute();
            }else{
                adabter2 = new listview_one_way_result_adabter(getApplicationContext(), R.layout.activity_one_way_result_list_items, flight_item2);

                listView2.setAdapter(adabter2);

                if (pDialog.isShowing())
                    pDialog.dismiss();
            }

        }
    }
}
