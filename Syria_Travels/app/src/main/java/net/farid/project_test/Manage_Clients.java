package net.farid.project_test;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import net.farid.project_test.Adabter.listview_manage_client_adabter;
import net.farid.project_test.Model.manage_client_item;
import net.farid.project_test.parser.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Manage_Clients extends AppCompatActivity {

    SharedPreferences test_name;
    SharedPreferences.Editor editor;
    MenuItem bedMenuItem;
    String whoLogin,name;


    manage_client_popup manage_pop_c;
    manage_client_popup_block manage_pop_c_block;
    private String TAG = Manage_Clients.class.getSimpleName();

    private ListView listView2;
    private listview_manage_client_adabter manage_client_adabter;
    private ArrayList<HashMap<String, String>> manage_client_items;

    private ProgressDialog pDialog;
    private ListAdapter adapter;
    private int client_id;
    private String client_name,client_username,client_password,client_confirm;

    public String getClient_confirm(){return client_confirm;}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage__clients);

        test_name = getSharedPreferences("LOGIN",0);
        editor = test_name.edit();
        name=test_name.getString("name", "").toString();
        whoLogin=test_name.getString("login", "").toString();

        listView2 = (ListView) findViewById(R.id.list_manage_client);
        manage_client_items = new ArrayList<>();

        new Get_clients().execute();
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                client_id=Integer.parseInt(manage_client_items.get(position).get("client_id"));
                client_name=manage_client_items.get(position).get("client_name");
                client_username=manage_client_items.get(position).get("client_username");
                client_password=manage_client_items.get(position).get("client_pass");
                client_confirm=manage_client_items.get(position).get("confirm");
                FragmentManager m=getFragmentManager();


                if (Objects.equals("active", client_confirm))
                {manage_pop_c_block=new manage_client_popup_block();
                    manage_pop_c_block.show(m,null);}

                else {
                    if (Objects.equals(client_confirm, "blocked"))

                    {
                        manage_pop_c = new manage_client_popup();
                        manage_pop_c.show(m, null);
                    }
                }



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
                Intent i=new Intent(Manage_Clients.this,search.class);
                startActivity(i);
                return true;
            case R.id.action_profile:
                Intent i3=new Intent(Manage_Clients.this,Admin_control_panel.class);
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
    public void unblockclient(View view){


        new UnblockClient().execute();
        this.finish();




    }
    public void blockclient(View view){

        new BlockClient().execute();
        this.finish();



    }


    private class UnblockClient extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            Log.e(TAG, " start: ");
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                //constants
                URL url = new URL("http://10.0.2.2:80/project/unblock_client.php");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", client_id);
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
            pDialog = new ProgressDialog(Manage_Clients.this);
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

    private class BlockClient extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            Log.e(TAG, " start: ");
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                //constants
                URL url = new URL("http://10.0.2.2:80/project/block_client.php");
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("id", client_id);

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

            pDialog = new ProgressDialog(Manage_Clients.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }
        // onPostExecute displays the results of the AsyncTask.


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Toast.makeText(getBaseContext(), "data update", Toast.LENGTH_LONG).show();
            if (pDialog.isShowing())
                pDialog.dismiss();
        }
    }

    private class Get_clients extends AsyncTask<Void, Void, Boolean> {
        String Mange_client_URL =
                "http://10.0.2.2:80/project/read_clients.php";



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(Manage_Clients.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(Mange_client_URL);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {

                try {


                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray jsonArray = jsonObj.getJSONArray("posts");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject client_details = jsonArray.getJSONObject(i);


                        int id = client_details.getInt("client_id");
                        String name = client_details.getString("client_email-phone");
                        String usernam = client_details.getString("client_username");
                        String pass = client_details.getString("client_password");
                        String details = client_details.getString("client_confirm");


                        // tmp hash map for single company
                        HashMap<String, String> client = new HashMap<>();

                        // adding each child node to HashMap key => value
                        client.put("client_id", String.valueOf(id));
                        client.put("client_name", name);
                        client.put("client_username", usernam);
                        client.put("client_pass", pass);
                        client.put("confirm", details);


                        // adding contact to contact list
                        manage_client_items.add(client);


                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {

                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (pDialog.isShowing())
                pDialog.dismiss();

            Log.d("post", "post");
            adapter = new SimpleAdapter(
                    Manage_Clients.this, manage_client_items,
                    R.layout.manage_client_list, new String[]{"client_username", "client_name", "client_pass"}, new int[]{R.id.client_list_username,
                    R.id.client_list_email, R.id.client_list_pass});
            listView2.setAdapter(adapter);
        }
    }
    }

