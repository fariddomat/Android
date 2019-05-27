package net.farid.project_test.Adabter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import net.farid.project_test.Model.company_flight_item;
import net.farid.project_test.Model.one_way_result_item;
import net.farid.project_test.R;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Farid on 5/5/2017.
 */

public class listview_one_way_result_adabter  extends ArrayAdapter<company_flight_item> {
    private Context mContext;
    private ArrayList<company_flight_item> mData;

    public listview_one_way_result_adabter (Context mContext,int activity_one_way_result_list_items, ArrayList<company_flight_item> mData) {
        super(mContext, R.layout.activity_one_way_result_list_items, mData);
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


            convertView = mInflater.inflate(R.layout.activity_one_way_result_list_items, null);
        }

        ImageView imageview = (ImageView) convertView.findViewById(R.id.ivOneWay);

        TextView dep_date = (TextView) convertView.findViewById(R.id.one_way_dep_date);
        dep_date.setText(mData.get(position).getF_departure_time());

        TextView duration = (TextView) convertView.findViewById(R.id.one_way_duration);
        duration.setText(mData.get(position).getF_duration());

        TextView cName = (TextView) convertView.findViewById(R.id.one_way_company_name);
        cName.setText(mData.get(position).getC_name());

        TextView price = (TextView) convertView.findViewById(R.id.one_way_price);
        price.setText(mData.get(position).getPrice()+"$");


        imageview.setImageResource(R.drawable.user_ic);
        new DownloadImageTask(imageview).execute(mData.get(position).getCompanyIcon());
        return convertView;

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
