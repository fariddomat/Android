package net.farid.project_test;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Farid on 4/19/2017.
 */

public class manage_company_popup extends DialogFragment implements View.OnClickListener {

    Button update,delete;
    View form;
    TextView company_name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        form=inflater.inflate(R.layout.activity_manage_company_popup,container,false);

        update=(Button)form.findViewById(R.id.updateComp);
        delete=(Button)form.findViewById(R.id.deleteComp);

        company_name=(TextView)form.findViewById(R.id.company_name);
        company_name.setText(getArguments().getString("company"));

        //update.setOnClickListener(this);
        //delete.setOnClickListener(this);
        return form;
    }
    @Override
    public void onClick(View v) {
            this.dismiss();

    }
}
