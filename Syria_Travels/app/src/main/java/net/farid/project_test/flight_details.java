package net.farid.project_test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class flight_details extends AppCompatActivity {

    SharedPreferences test_name;
    SharedPreferences.Editor editor;

    MenuItem bedMenuItem;
    String whoLogin;
    String manage;//to know one_way or round trip
    String from,to,departure_date,departure_time,duration,type,adult_num,child_num;
    String fid,cid,price,child_price,f_total_weight_value,f_extra_weight_price;

    String departure_date2,departure_time2,duration2;
    String fid2,cid2,price2,child_price2,f_total_weight_value2,f_extra_weight_price2;
    TextView fromTv,toTv,departure_dateTv,departure_timeTv,durationTv,typeTv,adult_numTv,child_numTv,priceTv,child_priceTv,totalTv,extra_priceTv,weight_availableTv,extra_weightTv;
    TextView returnDateTv,returnTimeTv,duration2Tv,price2Tv,child_price2Tv,extra_price2Tv,weight_available2Tv,extra_weight2Tv;
    LinearLayout returnL;

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_details);

        final TextView login=(TextView) findViewById(R.id.a_b_login) ;
        Button back=(Button)findViewById(R.id.a_b_back);
        Button home=(Button)findViewById(R.id.a_b_home);
        Button controlPanle=(Button)findViewById(R.id.a_b_control_panel);
        test_name = getSharedPreferences("LOGIN",0);
        editor = test_name.edit();
        name=test_name.getString("name", "").toString();
        whoLogin=test_name.getString("login", "").toString();

        typeTv=(TextView)findViewById(R.id.fd_type) ;
        fromTv=(TextView)findViewById(R.id.fd_from) ;
        toTv=(TextView)findViewById(R.id.fd_to) ;
        departure_dateTv=(TextView)findViewById(R.id.fd_depart_date) ;
        departure_timeTv=(TextView)findViewById(R.id.fd_depart_time) ;
        durationTv=(TextView)findViewById(R.id.fd_duration) ;
        adult_numTv=(TextView)findViewById(R.id.fd_set) ;
        priceTv=(TextView)findViewById(R.id.fd_price) ;
        child_numTv=(TextView)findViewById(R.id.fd_child_num);
        child_priceTv=(TextView)findViewById(R.id.fd_child_price);
        extra_priceTv=(TextView)findViewById(R.id.extra_price);
        weight_availableTv=(TextView)findViewById(R.id.weight_dep);
        extra_weightTv=(TextView)findViewById(R.id.extraValue1);


        child_price2Tv=(TextView)findViewById(R.id.fd_child_price2);
        extra_price2Tv=(TextView)findViewById(R.id.extra_price2);
        weight_available2Tv=(TextView)findViewById(R.id.weight_ret);
        extra_weight2Tv=(TextView)findViewById(R.id.extraValue2);

        totalTv=(TextView)findViewById(R.id.fd_total_val) ;

        //return
        returnL=(LinearLayout)findViewById(R.id.return_l) ;
        returnDateTv=(TextView)findViewById(R.id.fd_return_date) ;
        returnTimeTv=(TextView)findViewById(R.id.fd_return_time) ;
        duration2Tv=(TextView)findViewById(R.id.fd_duration2) ;
        price2Tv=(TextView)findViewById(R.id.fd_price2);

        //get intent

        fid=getIntent().getStringExtra("fid");
        cid=getIntent().getStringExtra("cid");
        from=getIntent().getStringExtra("from");
        to=getIntent().getStringExtra("to");
        departure_date=getIntent().getStringExtra("departure_date");
        departure_time=getIntent().getStringExtra("departure_time");
        duration=getIntent().getStringExtra("duration");
        adult_num=getIntent().getStringExtra("adult_num");
        child_num=getIntent().getStringExtra("child_num");
        price=getIntent().getStringExtra("price");
        child_price=getIntent().getStringExtra("child_price");
        f_extra_weight_price=getIntent().getStringExtra("f_extra_weight_price");
        f_total_weight_value=getIntent().getStringExtra("f_total_weight_value");
        type=getIntent().getStringExtra("type");

        manage=getIntent().getStringExtra("manage");
        if(manage.equals("2")){

            fid2=getIntent().getStringExtra("fid2");
            departure_date2=getIntent().getStringExtra("return_date");
            departure_time2=getIntent().getStringExtra("return_time");
            duration2=getIntent().getStringExtra("duration2");
            price2=getIntent().getStringExtra("price2");

            child_price2=getIntent().getStringExtra("child_price2");
            f_extra_weight_price2=getIntent().getStringExtra("f_extra_weight_price2");
            f_total_weight_value2=getIntent().getStringExtra("f_total_weight_value2");

            returnL.setVisibility(View.VISIBLE);
            returnDateTv.setText(departure_date2);
            returnTimeTv.setText(departure_time2);
            duration2Tv.setText(duration2);
            price2Tv.setText(price2+"$");

            weight_available2Tv.setText(f_total_weight_value2);
            extra_price2Tv.setText(f_extra_weight_price2);
            child_price2Tv.setText(child_price2+"$");
            extra_weight2Tv.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(extra_weightTv.getText().toString().equals("")){
                        if(extra_weight2Tv.getText().toString().equals("")){

                            int total=Integer.parseInt(adult_num)*Integer.parseInt(price)+Integer.parseInt(child_num)*Integer.parseInt(child_price)+Integer.parseInt(adult_num)*Integer.parseInt(price2)+Integer.parseInt(child_num)*Integer.parseInt(child_price2);
                            totalTv.setText(String.valueOf(total)+"$");
                        }else{
                            int total = Integer.parseInt(adult_num) * Integer.parseInt(price) + Integer.parseInt(child_num) * Integer.parseInt(child_price)+Integer.parseInt(adult_num)*Integer.parseInt(price2)+Integer.parseInt(child_num)*Integer.parseInt(child_price2) + Integer.parseInt(f_extra_weight_price2) * Integer.parseInt(extra_weight2Tv.getText().toString());
                            totalTv.setText(String.valueOf(total)+"$");
                        }
                    }else{
                        if(extra_weight2Tv.getText().toString().equals("")){

                            int total=Integer.parseInt(adult_num)*Integer.parseInt(price)+Integer.parseInt(child_num)*Integer.parseInt(child_price)+Integer.parseInt(adult_num)*Integer.parseInt(price2)+Integer.parseInt(child_num)*Integer.parseInt(child_price2);
                            totalTv.setText(String.valueOf(total)+"$");
                        }else {
                            int total = Integer.parseInt(adult_num) * Integer.parseInt(price) + Integer.parseInt(child_num) * Integer.parseInt(child_price) + Integer.parseInt(adult_num) * Integer.parseInt(price2) + Integer.parseInt(child_num) * Integer.parseInt(child_price2) + Integer.parseInt(f_extra_weight_price) * Integer.parseInt(extra_weightTv.getText().toString()) + Integer.parseInt(f_extra_weight_price2) * Integer.parseInt(extra_weight2Tv.getText().toString());
                            totalTv.setText(String.valueOf(total) + "$");
                        }
                    }
                }
            });
        }
        else{
            returnL.setVisibility(View.GONE);
        }

        typeTv.setText(type);
        fromTv.setText(from);
        toTv.setText(to);
        departure_dateTv.setText(departure_date);
        departure_timeTv.setText(departure_time);
        durationTv.setText(duration);
        adult_numTv.setText(adult_num);
        child_numTv.setText(child_num);
        priceTv.setText(price+"$");
        weight_availableTv.setText(f_total_weight_value);
        extra_priceTv.setText(f_extra_weight_price);
        child_priceTv.setText(child_price+"$");

        int total=Integer.parseInt(adult_num)*Integer.parseInt(price)+Integer.parseInt(child_num)*Integer.parseInt(child_price);
        totalTv.setText(String.valueOf(total)+"$");
        extra_weightTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(extra_weightTv.getText().toString().equals("")){
                    int total=Integer.parseInt(adult_num)*Integer.parseInt(price)+Integer.parseInt(child_num)*Integer.parseInt(child_price);
                    totalTv.setText(String.valueOf(total)+"$");
                }else {
                    int total = Integer.parseInt(adult_num) * Integer.parseInt(price) + Integer.parseInt(child_num) * Integer.parseInt(child_price) + Integer.parseInt(f_extra_weight_price) * Integer.parseInt(extra_weightTv.getText().toString());
                    totalTv.setText(String.valueOf(total)+"$");
                }


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
            bedMenuItem.setTitle("Log in ");
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
            bedMenuItem.setTitle("Log in ");
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
                Intent i=new Intent(flight_details.this,search.class);
                startActivity(i);
                return true;
            case R.id.action_profile:
                whoLogin=test_name.getString("login", "").toString();
                Toast.makeText(getBaseContext(), "profile ", Toast.LENGTH_LONG).show();
                if(whoLogin.equals("admin")){
                    Toast.makeText(getBaseContext(), "Hello admin ", Toast.LENGTH_LONG).show();
                    Intent i3=new Intent(flight_details.this,Admin_control_panel.class);
                    startActivity(i3);
                }
                if(whoLogin.equals("company")){
                    Toast.makeText(getBaseContext(), "Hello company ", Toast.LENGTH_LONG).show();
                    Intent i3=new Intent(flight_details.this,Company_control_panel.class);
                    startActivity(i3);
                }
                if(whoLogin.equals("employee")){
                    Toast.makeText(getBaseContext(), "Hello employee ", Toast.LENGTH_LONG).show();
                    Intent i3=new Intent(flight_details.this,employee_control_panel.class);
                    startActivity(i3);
                }
                if(whoLogin.equals("client")){
                    Toast.makeText(getBaseContext(), "Hello client ", Toast.LENGTH_LONG).show();
                    Intent i3=new Intent(flight_details.this,Client_control_panel.class);
                    startActivity(i3);
                }
                return true;
            case R.id.action_log_out:
                if (bedMenuItem.getTitle().equals("Log In")){

                    Intent i2=new Intent(flight_details.this,login.class);
                    startActivity(i2);
                }else {
                    whoLogin="empty";
                    editor.putString("name","empty");
                    editor.putString("login","empty");
                    editor.commit();
                    bedMenuItem.setTitle("Log In");
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void enter_info_func(View view) {

        SharedPreferences test_name = getSharedPreferences("LOGIN",0);
        String name=test_name.getString("name", "").toString();

        Toast.makeText(getBaseContext(), name, Toast.LENGTH_LONG).show();
        Log.e("name", name);
        if (name.equals("") || name.equals("empty") ){
            Intent i=new Intent(flight_details.this,login.class);
            startActivity(i);
        }else{
        int client_id=test_name.getInt("id",0);
        Intent i=new Intent(flight_details.this,client_info.class);
        i.putExtra("fid",fid);
            if(manage.equals("2")){
                i.putExtra("manage","2");
                i.putExtra("fid2",fid2);
            }else{
                i.putExtra("manage","1");
            }
        i.putExtra("cid",cid);
        i.putExtra("client_id",String.valueOf(client_id));
        i.putExtra("from",from);
        i.putExtra("to",to);
        i.putExtra("departure_date",departure_date);
        i.putExtra("departure_time",departure_time);
        i.putExtra("duration",duration);
        i.putExtra("adult_num",adult_num);
        i.putExtra("child_num",child_num);
        i.putExtra("price",price);
        i.putExtra("child_price",String.valueOf(child_price));
        i.putExtra("f_extra_weight_price",f_extra_weight_price);
        i.putExtra("f_total_weight_value",f_total_weight_value);
        i.putExtra("type",type);
        startActivity(i);}
    }
}
