package net.farid.project_test.parser;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import net.farid.project_test.R;

/**
 * Created by Farid on 6/28/2017.
 */

public class cancel_trip_popup extends DialogFragment implements View.OnClickListener {

    View form;
    TextView company_name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        form=inflater.inflate(R.layout.cancel_trip_popup,container,false);


        //update.setOnClickListener(this);
        //delete.setOnClickListener(this);
        return form;
    }
    @Override
    public void onClick(View v) {

    }
}
