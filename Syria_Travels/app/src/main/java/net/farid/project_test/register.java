package net.farid.project_test;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.basgeekball.awesomevalidation.ValidationStyle.COLORATION;

public class register extends AppCompatActivity {


    private ProgressDialog pDialog;

    private String TAG = register.class.getSimpleName();
    String username,email,password,passwordCon;
    EditText usernameEt,emailEt,passwordEt,passwordConEt;
    boolean bool;
    AwesomeValidation mAwesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        usernameEt=(EditText)findViewById(R.id.uname);
        emailEt=(EditText)findViewById(R.id.u_email);
        passwordEt=(EditText)findViewById(R.id.u_pass);
        passwordConEt=(EditText)findViewById(R.id.u_pass_confirm);
        mAwesomeValidation = new AwesomeValidation(COLORATION);
        mAwesomeValidation.setColor(Color.YELLOW);  // optional, default color is RED if not set

        mAwesomeValidation.addValidation(register.this, R.id.uname, "[a-zA-Z]+", R.string.err_uname);
        mAwesomeValidation.addValidation(register.this, R.id.u_email, android.util.Patterns.EMAIL_ADDRESS, R.string.err_email);


        // to validate the confirmation of another field
        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d]).{8,}";
        mAwesomeValidation.addValidation(register.this, R.id.u_pass, regexPassword, R.string.err_password);
        // to validate a confirmation field (don't validate any rule other than confirmation on confirmation field)
        mAwesomeValidation.addValidation(register.this, R.id.u_pass_confirm, R.id.u_pass, R.string.err_password_confirmation);


    }


    public void registerFinish(){
        this.finish();
    }

    public void registerFunc(View view) {
        //register_client
        bool= mAwesomeValidation.validate();
        if(bool==true){
            username=usernameEt.getText().toString();
            email=emailEt.getText().toString();
            password=passwordEt.getText().toString();
            new registerC().execute();
        }
    }

    private class registerC extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            Log.e(TAG, " start: ");
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                //constants
                URL url = new URL("http://10.0.2.2:80/project/register_client.php");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("email", email);
                jsonObject.put("username", username);
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
                String pass=Base64.encodeToString(hash, Base64.DEFAULT);
                jsonObject.put("password",SHA256.GenerateHash(password));

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

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(register.this);
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
            registerFinish();
        }
    }

}
