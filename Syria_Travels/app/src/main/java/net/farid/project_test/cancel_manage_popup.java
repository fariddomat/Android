package net.farid.project_test;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Farid on 6/29/2017.
 */

public class cancel_manage_popup extends DialogFragment {
    View form;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        form = inflater.inflate(R.layout.cancel_manage_popup, container, false);



        return form;
    }
}