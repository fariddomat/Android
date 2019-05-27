package net.farid.project_test.Adabter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.farid.project_test.Model.company_flight_item;
import net.farid.project_test.R;
import java.util.ArrayList;

/**
 * Created by Farid on 4/29/2017.
 */

public class listview_company_flights_adabter extends ArrayAdapter<company_flight_item> {

    private Context mContext;
    private ArrayList<company_flight_item> mData;

    public listview_company_flights_adabter (Context mContext, ArrayList<company_flight_item> mData) {
        super(mContext, R.layout.activity_company__flights_item, mData);
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
        final company_flight_item s = getItem(position);

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


            convertView = mInflater.inflate(R.layout.activity_company__flights_item, null);
        }
        TextView Id = (TextView) convertView.findViewById(R.id.flight_list_id);
        Id.setText(mData.get(position).getF_id());

        TextView from = (TextView) convertView.findViewById(R.id.flight_list_from);
        from.setText(mData.get(position).getF_from());

        TextView to = (TextView) convertView.findViewById(R.id.flight_list_to);
        to.setText(mData.get(position).getF_to());

        TextView fDate = (TextView) convertView.findViewById(R.id.flight_list_date);
        fDate.setText(mData.get(position).getF_departure_date());

        TextView fTime = (TextView) convertView.findViewById(R.id.flight_list_time);
        fTime.setText(mData.get(position).getF_departure_time());

        TextView duration = (TextView) convertView.findViewById(R.id.flight_list_duration);
        duration.setText(mData.get(position).getF_duration());
        return convertView;

    }
}
