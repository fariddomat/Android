package com.example.farid.newser;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farid.newser.java.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class login extends AppCompatActivity {

    SharedPreferences sharedp;
    private EditText username;
    private EditText password;
    private Button signinBtn;
    private Button registerBtn;
    private JSONParser jsonParser=new JSONParser();
    private String LOGIN_URL="http://syrian.orgfree.com/newsapp/db.php";
    private String REGISTER_URL="http://syrian.orgfree.com/newsapp/register.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedp=getSharedPreferences("myPrefs", Context.MODE_PRIVATE);




        username=(EditText)findViewById(R.id.usernameId);
        username.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
                    password.requestFocus();
                }
                return false;
            }
        });

        password=(EditText)findViewById(R.id.passwordId);
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
                    attemLogin(0);
                }
                return false;
            }
        });

        signinBtn=(Button)findViewById(R.id.singningBtn);
        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemLogin(0);
            }
        });

        registerBtn=(Button)findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemLogin(1);
            }
        });
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
    private void attemLogin(int btn){
        String Username=username.getText().toString();
        String Password=password.getText().toString();
        if (TextUtils.isEmpty(Username)){
            username.setError(getString(R.string.error_empty_field));
            return;
        }
        else if (TextUtils.isEmpty(Password)){
            password.setError(getString(R.string.error_empty_field));
            return;
        }

        if (btn==0)
            new LoginUserTask(Username,Password).execute();
        else
            new RegisterUserTask(Username,Password).execute();
    }
    private class LoginUserTask extends AsyncTask<Void, Void, Boolean>
    {
        private ProgressDialog mProgressDialog;

        private JSONObject jsonObjectResult = null;

        private String Username;
        private String Password;

        private String error;

        private LoginUserTask(String username, String password)
        {
            Username = username;
            Password = password;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(login.this,
                    "Processing...", "Check username and password", false, false);
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("username", Username));
            pairs.add(new BasicNameValuePair("password", Password));

            jsonObjectResult = jsonParser.makeHttpRequest(LOGIN_URL, pairs);


            try
            {
                if (jsonObjectResult.getInt("success") == 1)
                    return true;
                else
                    error = jsonObjectResult.getString("message");

            }
            catch (Exception ex)
            {

            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean)
        {
            super.onPostExecute(aBoolean);
            mProgressDialog.dismiss();
            if (aBoolean)
            {
                SharedPreferences.Editor myedit=sharedp.edit();
                myedit.putString("data",Username);
                myedit.commit();

                Intent mIntent = new Intent(login.this, MainActivity.class);
                mIntent.putExtra("username", Username);
                startActivity(mIntent);
                finish();
            }
            else
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
        }
    }

    private class RegisterUserTask extends AsyncTask<Void, Void, Boolean>
    {
        private ProgressDialog mProgressDialog;

        private JSONObject jsonObjectResult = null;

        private String Username;
        private String Password;

        private String error;

        private RegisterUserTask(String username, String password)
        {
            Username = username;
            Password = password;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(login.this,
                    "Processing...", "create new user", false, false);
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("username", Username));
            pairs.add(new BasicNameValuePair("password", Password));

            jsonObjectResult = jsonParser.makeHttpRequest(REGISTER_URL, pairs);


            try
            {
                if (jsonObjectResult.getInt("success") == 1)
                    return true;
                else
                    error = jsonObjectResult.getString("message");

            }
            catch (Exception ex)
            {

            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean)
        {
            super.onPostExecute(aBoolean);
            mProgressDialog.dismiss();
            if (aBoolean)
            {

                SharedPreferences.Editor myedit=sharedp.edit();
                myedit.putString("data",Username);
                myedit.commit();

                Intent mIntent = new Intent(login.this, MainActivity.class);
                mIntent.putExtra("username", Username);
                startActivity(mIntent);
                finish();
            }
            else
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
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
}
