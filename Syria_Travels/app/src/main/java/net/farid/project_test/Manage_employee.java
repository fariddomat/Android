package net.farid.project_test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;

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
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static com.basgeekball.awesomevalidation.ValidationStyle.COLORATION;

public class Manage_employee extends AppCompatActivity {

    SharedPreferences test_name;
    SharedPreferences.Editor editor;
    String name,whoLogin;
    MenuItem bedMenuItem;


    private String TAG = Manage_employee.class.getSimpleName();
    private ArrayList<HashMap<String, String>> employee_item;
    private ProgressDialog pDialog;
    private ListAdapter adapter;
    String company_id;
    String e_id;
    String e_username;
    String e_password;
    Spinner mySpinner;
    EditText empname,emppass,confpass;

    Button b,updBtn,histBtn,eventBtn,delBtn;
    LinearLayout l;
    ArrayList<String> worldlist;
    AwesomeValidation mAwesomeValidation;
    boolean bool;

    public void finishT(){
        this.finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_employee);

        test_name = getSharedPreferences("LOGIN",0);
        editor = test_name.edit();
        name=test_name.getString("name", "").toString();
        whoLogin=test_name.getString("login", "").toString();

        b=(Button)findViewById(R.id.addbtnv);
        updBtn=(Button)findViewById(R.id.updateEmp);
        eventBtn=(Button)findViewById(R.id.viewEventHisbtn);
        delBtn=(Button)findViewById(R.id.deleteEbtn);

        l=(LinearLayout)findViewById(R.id.newEmpLinear);
        company_id=getIntent().getStringExtra("c_id");
        mySpinner = (Spinner) findViewById(R.id.employeeSpinner);
        worldlist = new ArrayList<String>();
        employee_item=new ArrayList<>();
        empname=(EditText) findViewById(R.id.newEet);
        emppass=(EditText)findViewById(R.id.newEpet) ;
        confpass=(EditText)findViewById(R.id.newEcpet) ;

        mAwesomeValidation = new AwesomeValidation(COLORATION);
        mAwesomeValidation.setColor(Color.YELLOW);  // optional, default color is RED if not set
        mAwesomeValidation.addValidation(Manage_employee.this, R.id.newEet, "[a-zA-Z\\s]+", R.string.err_name);

        new GetEmp().execute();


        mySpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                updBtn.setVisibility(View.VISIBLE);
                delBtn.setVisibility(View.VISIBLE);

                return false;
            }
        });
        mySpinner
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                    @Override
                    public void onItemSelected(AdapterView<?> arg0,
                                               View arg1, int position, long arg3) {

                            e_id = employee_item.get(position).get("e_id");
                            e_username = employee_item.get(position).get("e_username");
                            empname.setText(e_username);
                            e_password = employee_item.get(position).get("e_password");
                            emppass.setText(e_password);
                            confpass.setText(e_password);
                            Log.e(TAG, e_id + "   " + e_username + "    " + e_password);


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
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
                Intent i=new Intent(Manage_employee.this,search.class);
                startActivity(i);
                return true;
            case R.id.action_profile:
                Intent i3=new Intent(Manage_employee.this,Company_control_panel.class);
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

    public void add_employee_func (View view) {
        b.setText("ADD");
        l.setVisibility(View.VISIBLE);

        updBtn.setVisibility(View.GONE);
        delBtn.setVisibility(View.GONE);
    }

    public void upd_employee_func(View view) {
        b.setText("UPDATE");
        l.setVisibility(View.VISIBLE);
    }

    public void manage_emp_func(View view) {

        if (b.getText().toString().equals("ADD")) {
            String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d]).{8,}";
            mAwesomeValidation.addValidation(Manage_employee.this, R.id.newEpet, regexPassword, R.string.err_password);
            // to validate a confirmation field (don't validate any rule other than confirmation on confirmation field)
            mAwesomeValidation.addValidation(Manage_employee.this, R.id.newEcpet, R.id.newEpet, R.string.err_password_confirmation);
        }
        bool= mAwesomeValidation.validate();
        if(bool==true) {
            e_username = empname.getText().toString();
            e_password = emppass.getText().toString();
            if (b.getText().toString().equals("ADD"))
                new AddEmployee().execute();
            else
                new UpdEmployee().execute();
        }
    }

    public void del_emp_func(View view) {
        new DelEmp().execute();
    }

    public void e_eh_func(View view) {
        Intent i=new Intent(Manage_employee.this,event_history.class);
        i.putExtra("option","employee_history");
        i.putExtra("c_id",company_id);
        startActivity(i);
    }

    private class UpdEmployee extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            Log.e(TAG, " start update "+e_id+" "+e_username+"  "+e_password);
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                //constants
                URL url = new URL("http://10.0.2.2:80/project/update_employee.php");
                JSONObject jsonObject = new JSONObject();


                jsonObject.put("e_id", e_id);

                jsonObject.put("e_username", e_username);
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(e_password.getBytes(StandardCharsets.UTF_8));
                String pass= Base64.encodeToString(hash, Base64.DEFAULT);


                jsonObject.put("e_password", SHA256.GenerateHash(e_password));
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
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }finally {
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
            pDialog = new ProgressDialog(Manage_employee.this);
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

    private class AddEmployee extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            Log.e(TAG, " start: ");
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                //constants
                URL url = new URL("http://10.0.2.2:80/project/add_employee.php");
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("username", e_username);
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(e_password.getBytes(StandardCharsets.UTF_8));
                String pass=Base64.encodeToString(hash, Base64.DEFAULT);


                jsonObject.put("password", SHA256.GenerateHash(e_password));
                jsonObject.put("companyid", company_id);
                Date cDate = new Date();
                String mDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
                jsonObject.put("c_date", mDate);
                Calendar c = Calendar.getInstance();
                String mtime = new SimpleDateFormat("HH:mm:ss").format(c.getTime());
                jsonObject.put("c_time",mtime);

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
            }catch (NoSuchAlgorithmException e) {
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
            pDialog = new ProgressDialog(Manage_employee.this);
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

    private class GetEmp extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            Log.e(TAG, " start: ");
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                //constants
                URL url = new URL("http://10.0.2.2:80/project/read_employee.php");
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
                    JSONObject emp_obj = jsonArray.getJSONObject(i);

                    e_id = emp_obj.getString("e_id");
                    e_username = emp_obj.getString("e_username");
                    e_password = emp_obj.getString("e_password");
                    worldlist.add(e_username);

                    HashMap<String,String> emp=new HashMap<>();
                    emp.put("e_id",e_id);
                    emp.put("e_username",e_username);
                    emp.put("e_password",e_password);


                    employee_item.add(emp);
                } }
            catch (IOException e) {
                Log.e(TAG, " error: " + e.getMessage());
                e.printStackTrace();
            } catch (JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                e.printStackTrace();
            }
            catch (Exception e) {
                Log.e(TAG, "fail " + e.getMessage());
            }finally
             {
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
            pDialog = new ProgressDialog(Manage_employee.this);
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

            mySpinner
                    .setAdapter(new ArrayAdapter<String>(Manage_employee.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            worldlist));

        }
    }


    private class DelEmp extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            Log.e(TAG, " start: ");
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                //constants
                URL url = new URL("http://10.0.2.2:80/project/del_employee.php");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("e_id",e_id);

                jsonObject.put("companyid", company_id);
                Date cDate = new Date();
                String mDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
                jsonObject.put("c_date", mDate);
                Calendar c = Calendar.getInstance();
                String mtime = new SimpleDateFormat("HH:mm:ss").format(c.getTime());
                jsonObject.put("c_time",mtime);
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


        }
        // onPostExecute displays the results of the AsyncTask.


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            finishT();
        }
    }

}
