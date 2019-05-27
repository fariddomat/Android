package net.farid.project_test;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class login extends AppCompatActivity {


    Context mContext;
    EditText usernameEt,passwordEt;
    String username,password;
    String c_type;//company type
    int c_id,e_id,client_id,employeeCompanyId;
    int response_code;
    private ProgressDialog pDialog;

    SharedPreferences test_name;
    SharedPreferences.Editor editor;
    static Boolean l=false;
    private String TAG = login.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEt=(EditText)findViewById(R.id.usernameId);
        passwordEt=(EditText)findViewById(R.id.passwordId);
        test_name = getSharedPreferences("LOGIN", 0);
        editor = test_name.edit();
        mContext=getApplicationContext();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
        // inflate your menu here
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    public void bLogin(View view) throws InterruptedException {
        username=usernameEt.getText().toString();
        password=passwordEt.getText().toString();
        new newLogin().execute();

    }
    public void loginfinish(){
        this.finish();
    }

    public void registerPage(View view) {
        Intent i=new Intent(login.this,register.class);
        startActivity(i);
    }

    private class newLogin extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            Log.e(TAG, " start: ");
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                //constants
                URL url = new URL("http://10.0.2.2:80/project/login.php");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("username", username);
/*
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(password.getBytes(Charset.forName("UTF-8")));
                String pass=Base64.encodeToString(hash, Base64.DEFAULT);

*/
                jsonObject.put("password", SHA256.GenerateHash(password));

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
                is = new BufferedInputStream(conn.getInputStream());
                String response = null;
                response = convertStreamToString(is);
                //String contentAsString = is.toString();
                Log.e(TAG, "response: "+response);
                JSONObject jsonObj = new JSONObject(response);
                //JSONArray jsonArray = jsonObj.getJSONArray("posts");

                response_code=jsonObj.getInt("success");
                if(response_code==2) {
                    c_id = jsonObj.getInt("c_id");
                    c_type=jsonObj.getString("c_type");
                }
                else if(response_code==3)
                    client_id=jsonObj.getInt("client_id");
                else if(response_code==4){
                    e_id=jsonObj.getInt("e_id");
                    employeeCompanyId=jsonObj.getInt("company_id");}
            } catch (IOException e) {
                Log.e(TAG, " error: " + e.getMessage());
                e.printStackTrace();
            } catch (JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
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
            pDialog = new ProgressDialog(login.this);
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
            if(response_code==0)
                Toast.makeText(getBaseContext(), "faild to login", Toast.LENGTH_LONG).show();
            else if(response_code==1){
                editor.putString("login","admin");
                editor.putString("name",username);
                editor.commit();
                Toast.makeText(getBaseContext(), "Hello Admin "+username, Toast.LENGTH_LONG).show();
                loginfinish();
                /*Intent i=new Intent(login.this,Admin_control_panel.class);
                startActivity(i);*/
            }
            else if(response_code==2){
                editor.putString("login","company");
                editor.putInt("id",c_id);
                editor.putString("name",username);
                editor.putString("c_type",c_type);
                editor.commit();
                Toast.makeText(getBaseContext(), "Hello Company admin "+username, Toast.LENGTH_LONG).show();
                loginfinish();
                /*Intent i=new Intent(login.this,Company_control_panel.class);
                i.putExtra("c_id",String.valueOf(c_id));
                startActivity(i);*/
            }
            else if(response_code==3){
                editor.putString("login","client");
                editor.putInt("id",client_id);
                editor.putString("name",username);
                editor.commit();
                loginfinish();


            }
            else if(response_code==4){
                editor.putString("login","employee");
                editor.putInt("id",e_id);
                editor.putInt("employeeCompanyId",employeeCompanyId);
                editor.putString("name",username);
                editor.commit();
                Toast.makeText(getBaseContext(), "Hello employee "+username, Toast.LENGTH_LONG).show();
                loginfinish();
                /*Intent i=new Intent(login.this,employee_control_panel.class);
                i.putExtra("e_id",String.valueOf(e_id));
                startActivity(i);*/
            }
        }
    }

}
