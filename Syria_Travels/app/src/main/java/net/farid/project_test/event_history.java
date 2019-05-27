package net.farid.project_test;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import net.farid.project_test.Adabter.listview_event_history_adabter;
import net.farid.project_test.Model.event_history_item;

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

public class event_history extends AppCompatActivity {

    String c_id="";
    String option="";
    private ListAdapter adapter;
    ArrayList<event_history_item> event_item;

    private String TAG = event_history.class.getSimpleName();
    private ListView listView;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_history);


        event_item=new ArrayList<event_history_item>();

        listView=(ListView)findViewById(R.id.list_event_history);
        option=getIntent().getStringExtra("option");
        if(option.equals("employee_history")||option.equals("reservation_history"))
            c_id=getIntent().getStringExtra("c_id");
        new GetHistory().execute();
        adapter = new listview_event_history_adabter(getApplicationContext(), R.layout.event_history_item, event_item);

        listView.setAdapter(adapter);

    }

    private class GetHistory extends AsyncTask<Void, Void, Boolean> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(event_history.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

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
        protected Boolean doInBackground(Void... params) {
            URL url = null;
            try {
                url = new URL("http://10.0.2.2:80/project/get_history.php");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;


                try {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("option", option);
                    if(option.equals("employee_history")||option.equals("reservation_history"))
                        jsonObject.put("c_id", c_id);
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

                    Log.e(TAG, "response: "+response);
                    JSONObject jsonObj = new JSONObject(response);

                    JSONArray jsonArray = jsonObj.getJSONArray("posts");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject companies_details =jsonArray.getJSONObject(i);

                        String name = companies_details.getString("name");
                        String action = companies_details.getString("action");
                        String date = companies_details.getString("date");
                        String time = companies_details.getString("time");
                        String details = companies_details.getString("details");
                        String icon ="http://10.0.2.2:80/project/uploads/"+ companies_details.getString("icon");


                        event_history_item item=new event_history_item();
                        item.setName(name);
                        item.setAction(action);
                        item.seteDate(date);
                        item.seteTime(time);
                        item.setDetails(details);
                        item.setIcon(icon);
                        event_item.add(item);
                        // tmp hash map for single company
                        /*HashMap<String, String> company = new HashMap<>();

                        // adding each child node to HashMap key => value
                        company.put("id", String.valueOf(id));
                        company.put("name", name);
                        company.put("username", usernam);
                        company.put("pass", pass);
                        company.put("details", details);
                        company.put("icon", icon);

                        // adding contact to contact list
                        manage_company_item.add(company);
*/

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
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (pDialog.isShowing())
                pDialog.dismiss();

            Log.d("post", "post");
        }
    }
}
