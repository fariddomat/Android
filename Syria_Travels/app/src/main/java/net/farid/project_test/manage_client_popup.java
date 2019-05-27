package net.farid.project_test;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class manage_client_popup extends DialogFragment {
    View form;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        form = inflater.inflate(R.layout.activity_manage_client_popup, container, false);



        return form;
    }

    }