package net.farid.project_test.Adabter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.farid.project_test.Model.manage_client_item;
import net.farid.project_test.R;

import java.util.ArrayList;

/**
 * Created by Farid on 4/1/2017.
 */

public class listview_manage_client_adabter extends ArrayAdapter<manage_client_item> {


    private Context mContext;
    private ArrayList<manage_client_item> mData;

    public listview_manage_client_adabter(Context mContext, ArrayList<manage_client_item> mData) {
        super(mContext, R.layout.manage_client_list, mData);
        this.mContext = mContext;
        this.mData = mData;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final manage_client_item s = getItem(position);


        LayoutInflater mInflater = (LayoutInflater)
                mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        String Name = mData.get(position).getClientName();
        if (Name == "progress") {
            //convertView = mInflater.inflate(R.layout.new_news_loading, null);
            return convertView;
        } else {

            convertView = mInflater.inflate(R.layout.manage_client_list, null);
            TextView clientName = (TextView) convertView.findViewById(R.id.client_list_username);
            clientName.setText(Name);



            TextView clientemail = (TextView) convertView.findViewById(R.id.client_list_email);
            clientemail.setText(mData.get(position).getClientEmail());


            return convertView;

        }
    }
}