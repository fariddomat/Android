package net.farid.project_test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class employee_control_panel extends AppCompatActivity {

    SharedPreferences test_name;
    SharedPreferences.Editor editor;
    String name,whoLogin;
    MenuItem bedMenuItem;

    String s,employeeCId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_control_panel);

        test_name = getSharedPreferences("LOGIN",0);
        editor = test_name.edit();
        name=test_name.getString("name", "").toString();
        whoLogin=test_name.getString("login", "").toString();
        s=String.valueOf(test_name.getInt("id",0));
        employeeCId=String.valueOf(test_name.getInt("employeeCompanyId",0));
        //employeeCId=getIntent().getStringExtra("employeeCompanyId");
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
                Intent i=new Intent(employee_control_panel.this,search.class);
                startActivity(i);
                return true;
            case R.id.action_profile:
                Intent i3=new Intent(employee_control_panel.this,employee_control_panel.class);
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

    public void show_hold_requests(View view)
    {
        Intent i=new Intent(this,manage_reservation_requests.class);
        i.putExtra("option","hold");
        i.putExtra("e_id",s);
        i.putExtra("employeeCompanyId",employeeCId);
        startActivity(i);
    }


    public void showEmployeeCompanyflights(View view)
    {
        Intent i=new Intent(this,Company_Flights.class);

        i.putExtra("c_id",employeeCId);
        i.putExtra("employeeCompanyId",employeeCId);
        i.putExtra("isAdmin","no");
        startActivity(i);



    }


    public void show_confirmed_requests(View view) {

            Intent i=new Intent(this,manage_reservation_requests.class);
            i.putExtra("option","confirm");
        i.putExtra("employeeCompanyId",employeeCId);
            i.putExtra("e_id",s);
            startActivity(i);

    }

    public void show_refused_requests(View view) {
        Intent i=new Intent(this,manage_reservation_requests.class);
        i.putExtra("option","refuse");
        i.putExtra("employeeCompanyId",employeeCId);
        i.putExtra("e_id",s);
        startActivity(i);
    }

    public void show_cancel_requests(View view) {
        Intent i=new Intent(this,manage_reservation_requests.class);
        i.putExtra("option","cancel");
        i.putExtra("employeeCompanyId",employeeCId);
        i.putExtra("e_id",s);
        startActivity(i);

    }
}
