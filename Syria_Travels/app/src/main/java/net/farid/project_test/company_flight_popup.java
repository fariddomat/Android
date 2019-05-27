package net.farid.project_test;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
/**
 * Created by Farid on 4/29/2017.
 */

public class company_flight_popup extends DialogFragment implements View.OnClickListener {
        Button update,delete;
        View form;

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        form=inflater.inflate(R.layout.company_flight_popup,container,false);

        update=(Button)form.findViewById(R.id.updateFlight);
        delete=(Button)form.findViewById(R.id.deleteFlight);

        //update.setOnClickListener(this);
        //delete.setOnClickListener(this);
        return form;
        }
@Override
public void onClick(View v) {
        Button bu=(Button)v;
        if(bu.getText().toString().equals("update flight info")){
        this.dismiss();
        }
        else{

        }
}
}
