package net.farid.project_test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class Admin_control_panel extends AppCompatActivity {

    SharedPreferences test_name;
    SharedPreferences.Editor editor;
    MenuItem bedMenuItem;
    String whoLogin,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_control_panel);

        test_name = getSharedPreferences("LOGIN",0);
        editor = test_name.edit();
        name=test_name.getString("name", "").toString();
        whoLogin=test_name.getString("login", "").toString();


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
                Intent i=new Intent(Admin_control_panel.this,search.class);
                startActivity(i);
                return true;
            case R.id.action_profile:
                whoLogin=test_name.getString("login", "").toString();
                Toast.makeText(getBaseContext(), "profile ", Toast.LENGTH_LONG).show();

                    Toast.makeText(getBaseContext(), "Hello admin ", Toast.LENGTH_LONG).show();
                    Intent i3=new Intent(Admin_control_panel.this,Admin_control_panel.class);
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
    public void manageClients(View view) {
        Intent i=new Intent(this,Manage_Clients.class);
        startActivity(i);
    }

    public void manageCompanies(View view) {
        Intent i=new Intent(this,Manage_Companies.class);
        startActivity(i);
    }

    public void viewClient_Log(View view) {
        Intent i=new Intent(Admin_control_panel.this,event_history.class);
        i.putExtra("option","client_history");
        startActivity(i);
    }

    public void viewC_Log(View view) {
    Intent i=new Intent(Admin_control_panel.this,event_history.class);
        i.putExtra("option","company_history");
        startActivity(i);
    }

}
