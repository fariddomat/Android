package com.example.farid.newser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.farid.newser.java.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Farid on 8/8/2016.
 */
public class AddNewsActivity  extends AppCompatActivity {



    private EditText mNewsTitleET;
    private EditText mNewsET;


    JSONParser jsonParser = new JSONParser();

    private String ADD_URL =
            "http://syrian.orgfree.com/newsapp/addnews.php";



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_news);

        mNewsTitleET = (EditText) findViewById(R.id.title_news);
        mNewsET      = (EditText) findViewById(R.id.news_box);

    }

    private void attempAdding()
    {
        if (!mNewsTitleET.getText().toString().equals("") &&
                !mNewsET.getText().toString().equals(""))
        {
            new AddNewsTask().execute();
        }
        else
        {
            Toast.makeText(getApplicationContext(),
                    "All fields are requires", Toast.LENGTH_LONG).show();
        }
    }

    public void buPublish(View view) {
        attempAdding();
    }

    private class AddNewsTask extends AsyncTask<Void, Void, Boolean>
    {


        private JSONObject jsonObjectResult = null;

        private String error;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();

            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("news_date", dateFormat.format(date).toString()));
            pairs.add(new BasicNameValuePair("news_title", mNewsTitleET.getText().toString()));
            pairs.add(new BasicNameValuePair("news_body", mNewsET.getText().toString()));

            jsonObjectResult = jsonParser.makeHttpRequest(ADD_URL, pairs);

            if (jsonObjectResult == null)
            {
                error = "Error int the connection";
                return false;
            }

            try
            {
                if (jsonObjectResult.getInt("success") == 1)
                {
                    error = jsonObjectResult.getString("message");
                    return true;
                }
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
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();

            finish();
        }
    }

}
