package com.example.farid.newser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.farid.newser.adapter.ListViewNewsAdapter;
import com.example.farid.newser.java.JSONParser;
import com.example.farid.newser.model.ListViewNewsItem;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private FirebaseAnalytics mFirebaseAnalytics;
    private Menu menu;

    SharedPreferences sharedp;

    private ListView mListView;
    private ListViewNewsAdapter listViewNewsAdapter;
    private ArrayList<ListViewNewsItem> listViewNewsItems;

    private JSONParser jsonParser = new JSONParser();
    SwipeRefreshLayout swipeLayout;

    public int getStartfrom() {
        return startfrom;
    }

    public int getEndto() {
        return endto;
    }

    public void setStartfrom(int startfrom) {
        this.startfrom = startfrom;
    }

    public void setEndto(int endto) {
        this.endto = endto;
    }

    int startfrom=0;
    int endto=10;

    Boolean ONAttime=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        sharedp=getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        if(sharedp.getString("data","empty").equals("empty"))
        {
            Intent i=new Intent(MainActivity.this,login.class);
            startActivity(i);
            finish();
            System.exit(0);
        }


        mListView = (ListView) findViewById(R.id.list_view);
        listViewNewsItems = new ArrayList<ListViewNewsItem>();

        listViewNewsAdapter = new ListViewNewsAdapter(getApplicationContext(),
                listViewNewsItems);
        mListView.setAdapter(listViewNewsAdapter);

        new GetNewsTask().execute();

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeLayout.setOnRefreshListener(this);

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if((firstVisibleItem+visibleItemCount)==totalItemCount)
                    if( (totalItemCount>1)&& (ONAttime==true)){
                        setStartfrom(getStartfrom()+10);

                        new GetNewsTask().execute();
                    }
            }
        });



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem bedMenuItem = menu.findItem(R.id.action_log_out);
        bedMenuItem.setTitle("Log out ( "+sharedp.getString("data","empty")+" )");
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
            case R.id.action_add:
                Intent intent=new Intent(this,AddNewsActivity.class);
                startActivity(intent);

                return true;
            case R.id.action_refresh:
                onRefresh();
                return true;
            case R.id.action_log_out:
                SharedPreferences.Editor myedit=sharedp.edit();
                myedit.putString("data","empty");
                myedit.commit();
                Intent mIntent = new Intent(MainActivity.this, login.class);
                startActivity(mIntent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        finish();
        System.exit(0);
    }

    @Override
    public void onRefresh() {
        listViewNewsItems.clear();
        setStartfrom(0);
        setEndto(10);
        listViewNewsAdapter.notifyDataSetChanged();
        new GetNewsTask().execute();

        swipeLayout.setRefreshing(false);
    }

    public void buAdd(View view) {
        Intent intent=new Intent(MainActivity.this,AddNewsActivity.class);
        startActivity(intent);
    }

    private class GetNewsTask extends AsyncTask<Void, Void, Boolean>
    {


        private JSONObject jsonObjectResult = null;

        private String error;

        String READNEWS_URL =
                "http://syrian.orgfree.com/newsapp/getnews.php?startfrom="+getStartfrom()+"&endto="+getEndto();

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            listViewNewsAdapter.notifyDataSetChanged();
            ONAttime=false;
            listViewNewsItems.add(new ListViewNewsItem(0,"progress",null,null));
            listViewNewsAdapter.notifyDataSetChanged();



        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            jsonObjectResult = jsonParser.makeHttpRequest(READNEWS_URL, null);

            if (jsonObjectResult == null)
            {
                error = "Error int the connection";
                return false;
            }

            try
            {
                if (jsonObjectResult.getInt("success") == 1)
                {
                    listViewNewsItems.remove(listViewNewsItems.size()-1);

                    JSONArray jsonArray = jsonObjectResult.getJSONArray("posts");
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject news = jsonArray.getJSONObject(i);

                        ListViewNewsItem listViewNewsItem = new ListViewNewsItem
                                (
                                        news.getInt("news_id"),
                                        news.getString("news_title"),
                                        news.getString("news_date"),
                                        news.getString("news_body")
                                );
                        listViewNewsItems.add(listViewNewsItem);

                    }
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


            if (aBoolean)
            {
                listViewNewsAdapter.notifyDataSetChanged();

                ONAttime=true;
            }
            else {
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                listViewNewsItems.remove(listViewNewsItems.size()-1);
                listViewNewsAdapter.notifyDataSetChanged();
            }

        }
    }
}
