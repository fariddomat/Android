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

import net.farid.project_test.Model.manage_compnay_items;
import net.farid.project_test.R;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Farid on 4/1/2017.
 */

public class listview_manage_company_adabter extends ArrayAdapter<manage_compnay_items> {
    private Context mContext;
    private ArrayList<manage_compnay_items> mData;


    LayoutInflater vi;
    int Resource;
    public listview_manage_company_adabter(Context mContext, int activity_manage_company_list, ArrayList<manage_compnay_items> mData) {
        super(mContext, R.layout.activity_manage_company_list, mData);
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
        final manage_compnay_items s = getItem(position);

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


            convertView = mInflater.inflate(R.layout.activity_manage_company_list, null);
        }
        ImageView imageview = (ImageView) convertView.findViewById(R.id.ivCompany);

        TextView cId = (TextView) convertView.findViewById(R.id.company_list_id);
            cId.setText(mData.get(position).getcID2());

            TextView cName = (TextView) convertView.findViewById(R.id.company_list_name);
            cName.setText(mData.get(position).getcName());

            TextView cDetails = (TextView) convertView.findViewById(R.id.company_list_details);
            cDetails.setText(mData.get(position).getcDetails());

        imageview.setImageResource(R.drawable.user_ic);
        new DownloadImageTask(imageview).execute(mData.get(position).getcIcon());
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
