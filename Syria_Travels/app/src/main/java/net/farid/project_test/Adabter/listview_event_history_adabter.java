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

import net.farid.project_test.Model.event_history_item;
import net.farid.project_test.R;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Farid on 7/5/2017.
 */

public class listview_event_history_adabter extends ArrayAdapter<event_history_item> {
    private Context mContext;
    private ArrayList<event_history_item> mData;


    LayoutInflater vi;
    int Resource;
    public listview_event_history_adabter(Context mContext, int event_history_item, ArrayList<event_history_item> mData) {
        super(mContext, R.layout.event_history_item, mData);
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
        final event_history_item s = getItem(position);

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


            convertView = mInflater.inflate(R.layout.event_history_item, null);
        }
        ImageView imageview = (ImageView) convertView.findViewById(R.id.not_icon);

        TextView name = (TextView) convertView.findViewById(R.id.not_company_name);
        name.setText(mData.get(position).getName());

        TextView action = (TextView) convertView.findViewById(R.id.ehi_action);
        action.setText(mData.get(position).getAction());

        TextView details = (TextView) convertView.findViewById(R.id.not_details);
        details.setText(mData.get(position).getDetails());


        TextView date = (TextView) convertView.findViewById(R.id.not_date);
        date.setText(mData.get(position).geteDate());

        TextView time = (TextView) convertView.findViewById(R.id.not_time);
        time.setText(mData.get(position).geteTime());

        imageview.setImageResource(R.drawable.user_ic);
        new DownloadImageTask(imageview).execute(mData.get(position).getIcon());
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
