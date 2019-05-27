package com.example.farid.newser.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.farid.newser.R;
import com.example.farid.newser.model.ListViewNewsItem;

import java.util.ArrayList;

/**
 * Created by Farid
 */
public class ListViewNewsAdapter extends ArrayAdapter<ListViewNewsItem> {

    private Context mContext;
    private ArrayList<ListViewNewsItem> mData;

    public ListViewNewsAdapter (Context mContext, ArrayList<ListViewNewsItem> mData) {
        super(mContext, R.layout.news_shape, mData);
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
        final ListViewNewsItem s = getItem(position);


        LayoutInflater mInflater = (LayoutInflater)
                mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        String title=mData.get(position).getmNewsTitle();
        if (title == "progress") {
            convertView = mInflater.inflate(R.layout.new_news_loading, null);
            return convertView;
        } else {
            convertView = mInflater.inflate(R.layout.news_shape, null);
            TextView mNewsTitle = (TextView) convertView.findViewById(R.id.news_title_tv);
            mNewsTitle.setText(title);

            TextView mNewsDate = (TextView) convertView.findViewById(R.id.news_date_tv);
            mNewsDate.setText(mData.get(position).getmNewsDate());

            TextView mNewsBody = (TextView) convertView.findViewById(R.id.news_body);
            mNewsBody.setText(mData.get(position).getmNewsBody());

            return convertView;
        }
    }
}
