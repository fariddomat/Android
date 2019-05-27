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
import android.widget.TextView;

import net.farid.project_test.Model.company_flight_item;
import net.farid.project_test.R;

import java.io.InputStream;
import java.util.ArrayList;
/**
 * Created by Farid on 6/28/2017.
 */

public class listview_client_reservation_adabter extends ArrayAdapter<company_flight_item> {
    private Context mContext;
    private ArrayList<company_flight_item> mData;


    LayoutInflater vi;
    int Resource;
    public listview_client_reservation_adabter(Context mContext, int client_view_reservation_item, ArrayList<company_flight_item> mData) {
        super(mContext, R.layout.client_view_reservation_item, mData);
        this.mContext = mContext;
        //vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mData = mData;
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


            convertView = mInflater.inflate(R.layout.client_view_reservation_item, null);
        }
        ImageView imageview = (ImageView) convertView.findViewById(R.id.c_v_l_imageView);
        ImageView imageview2 = (ImageView) convertView.findViewById(R.id.c_v_l_image2);

        TextView from = (TextView) convertView.findViewById(R.id.c_v_l_from);
        from.setText(mData.get(position).getF_from());

        TextView to = (TextView) convertView.findViewById(R.id.c_v_l_to);
        to.setText(mData.get(position).getF_to());

        TextView cDate = (TextView) convertView.findViewById(R.id.c_v_l_date);
        cDate.setText(mData.get(position).getF_departure_date());

        TextView cTime = (TextView) convertView.findViewById(R.id.c_v_l_time);
        cTime.setText(mData.get(position).getF_departure_time());

        TextView cName = (TextView) convertView.findViewById(R.id.c_v_l_cname);
        cName.setText(mData.get(position).getC_name());

        if(mData.get(position).getC_type().equals("t"))
            imageview2.setImageResource(R.drawable.train);
        else
            imageview2.setImageResource(R.drawable.plane);
        imageview.setImageResource(R.drawable.user_ic);
        new DownloadImageTask2(imageview).execute(mData.get(position).getCompanyIcon());
        return convertView;

    }

    private class DownloadImageTask2 extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask2(ImageView bmImage) {
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
