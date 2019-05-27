package com.example.farid.newser.model;

/**
 * Created by Farid
 */
public class ListViewNewsItem {

    private int    mNewsID;
    private String mNewsTitle;
    private String mNewsDate;
    private String mNewsBody;

    public ListViewNewsItem(int mNewsID, String mNewsTitle, String mNewsDate, String mNewsBody) {
        this.mNewsID = mNewsID;
        this.mNewsTitle = mNewsTitle;
        this.mNewsDate = mNewsDate;
        this.mNewsBody = mNewsBody;
    }

    public int getmNewsID() {
        return mNewsID;
    }

    public void setmNewsID(int mNewsID) {
        this.mNewsID = mNewsID;
    }

    public String getmNewsTitle() {
        return mNewsTitle;
    }

    public void setmNewsTitle(String mNewsTitle) {
        this.mNewsTitle = mNewsTitle;
    }

    public String getmNewsDate() {
        return mNewsDate;
    }

    public void setmNewsDate(String mNewsDate) {
        this.mNewsDate = mNewsDate;
    }

    public String getmNewsBody() {
        return mNewsBody;
    }

    public void setmNewsBody(String mNewsBody) {
        this.mNewsBody = mNewsBody;
    }
}
