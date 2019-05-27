package net.farid.project_test;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class hold_clients_requests_popup extends DialogFragment implements View.OnClickListener {
    View form;
    TextView client_national_num,client_phone;
    TextView client_fname,client_lname,client_country,status,typec,setAdult,setChild,seatsAvailable;
    TextView card_num,card_date,card_svv;
    EditText refuseReasoneEt;
    String refuse;
    String option;
    Button a,b;
    EditText e;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        form=inflater.inflate(R.layout.activity_hold_clients_requests_popup,container,false);


        card_num=(TextView)form.findViewById(R.id.card_num);
        card_num.setText(getArguments().getString("card_num"));
        card_date=(TextView)form.findViewById(R.id.expire_date);
        card_date.setText(getArguments().getString("card_date"));
        card_svv=(TextView)form.findViewById(R.id.card_svv);
        card_svv.setText(getArguments().getString("card_svv"));

        client_fname=(TextView)form.findViewById(R.id.fn);
        client_fname.setText(getArguments().getString("client_fname"));
        client_lname=(TextView)form.findViewById(R.id.ln);
        client_lname.setText(getArguments().getString("client_lname"));
        client_country=(TextView)form.findViewById(R.id.countryclient);
        client_country.setText(getArguments().getString("client_country"));
        status=(TextView)form.findViewById(R.id.statusc);
        status.setText(getArguments().getString("status"));
        client_national_num=(TextView)form.findViewById(R.id.nn);
        client_national_num.setText(getArguments().getString("client_national_num"));
        client_phone=(TextView)form.findViewById(R.id.phonec);
        client_phone.setText(getArguments().getString("client_phone"));

        typec=(TextView)form.findViewById(R.id.typec);
        typec.setText(getArguments().getString("r_type"));
        setAdult=(TextView)form.findViewById(R.id.setAdult);
        setAdult.setText(getArguments().getString("r_seats_num"));
        setChild=(TextView)form.findViewById(R.id.setChild);
        setChild.setText(getArguments().getString("r_children_num"));
        seatsAvailable=(TextView)form.findViewById(R.id.seatsAvailable);
        seatsAvailable.setText(getArguments().getString("f_seats"));

        refuseReasoneEt=(EditText)form.findViewById(R.id.refuse_reason);
        option=getArguments().getString("option");
        a=(Button)form.findViewById(R.id.confirm_client_requests);
        b=(Button)form.findViewById(R.id.refuse_client_requests);
        e=(EditText) form.findViewById(R.id.refuse_reason);
        if(option.equals("refuse"))
        {
            b.setVisibility(View.GONE);
            e.setVisibility(View.GONE);
        }
        else if(option.equals("confirm")){
            a.setVisibility(View.GONE);
        }


        return form;
    }

    public String getResone(){

        refuse=refuseReasoneEt.getText().toString();
        return refuse;
    }



    @Override
    public void onClick(View v) {
        this.dismiss();
    }
}