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
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import net.farid.project_test.Adabter.listview_company_flights_adabter;
import net.farid.project_test.Adabter.listview_manage_company_adabter;
import net.farid.project_test.parser.HttpHandler;
import net.farid.project_test.Model.manage_compnay_items;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Manage_Companies extends AppCompatActivity {


    SharedPreferences test_name;
    SharedPreferences.Editor editor;
    MenuItem bedMenuItem;
    String whoLogin,name;



    manage_company_popup manage_pop;
    private String TAG = Manage_Companies.class.getSimpleName();
    private ListView listView;
    private listview_manage_company_adabter adabter;
    ArrayList<manage_compnay_items> company_item;
    private ArrayList<HashMap<String, String>> manage_company_item;


    private ProgressDialog pDialog;
    private  ListAdapter adapter;
    private int company_id;

    private String company_name,company_username,company_password,company_details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage__companies);

        test_name = getSharedPreferences("LOGIN",0);
        editor = test_name.edit();
        name=test_name.getString("name", "").toString();
        whoLogin=test_name.getString("login", "").toString();

        company_item=new ArrayList<manage_compnay_items>();
        new GetCompanies().execute();
        listView = (ListView) findViewById(R.id.list_manage_c);

        for (int i=0;i<100000;i++);
        adapter = new listview_manage_company_adabter(getApplicationContext(), R.layout.activity_manage_company_list, company_item);

        listView.setAdapter(adapter);

        manage_company_item = new ArrayList<>();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                //Toast.makeText(getBaseContext(), manage_company_item.get(position).get("name"), Toast.LENGTH_LONG).show();
                company_id=company_item.get(position).getcID();
                company_name=company_item.get(position).getcName();
                company_username=company_item.get(position).getcEmail();
                company_password=company_item.get(position).getcPassword();
                company_details=company_item.get(position).getcDetails();
                FragmentManager manager=getFragmentManager();
                manage_pop=new manage_company_popup();

                Bundle args = new Bundle();
                args.putString("company", "syria");
                manage_pop.setArguments(args);

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
                Intent i=new Intent(Manage_Companies.this,search.class);
                startActivity(i);
                return true;
            case R.id.action_profile:
                Intent i3=new Intent(Manage_Companies.this,Admin_control_panel.class);
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

    public void add_company(View view) {
        Intent i=new Intent(this,Add_company.class);
        i.putExtra("manage","1");
        startActivity(i);

    }

    public void update_company(View view) {
        Intent br=new Intent(this,Add_company.class);
        br.putExtra("manage","2");
        br.putExtra("id",String.valueOf(company_id));
        br.putExtra("name",company_name);
        br.putExtra("username",company_username);
        br.putExtra("pass",company_password);
        br.putExtra("details",company_details);
        startActivity(br);
        this.finish();
    }

    public void del_company(View view) {

        del_copmany del=new del_copmany(company_id);
        this.finish();

    }

    private class GetCompanies extends AsyncTask<Void, Void, Boolean> {
        String Mange_compny_URL =
                "http://10.0.2.2:80/project/read_companies.php";



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(Manage_Companies.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(Mange_compny_URL);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {

                try {


                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray jsonArray = jsonObj.getJSONArray("posts");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject companies_details = jsonArray.getJSONObject(i);

                        int id = companies_details.getInt("c_id");
                        String name = companies_details.getString("c_name");
                        String usernam = companies_details.getString("c_username");
                        String pass = companies_details.getString("c_password");
                        String details = companies_details.getString("c_details");
                        String icon ="http://10.0.2.2:80/Syria-Travels-copy/assets/images/companies/icons/"+ companies_details.getString("c_icon");


                        manage_compnay_items item=new manage_compnay_items();
                        item.setcID(id);
                        item.setcName(name);
                        item.setcEmail(usernam);
                        item.setcPassword(pass);
                        item.setcDetails(details);
                        item.setcIcon(icon);
                        company_item.add(item);
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
            /*adapter = new SimpleAdapter(
                    Manage_Companies.this, manage_company_item,
                    R.layout.activity_manage_company_list, new String[]{"id", "name", "details"}, new int[]{R.id.company_list_id,
                    R.id.company_list_name, R.id.company_list_details});
            listView.setAdapter(adapter);
            */
        }
    }
}
