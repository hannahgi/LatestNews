package com.example.android.latestnews;

/**
 * Created by Gikonyo hannah on 23.05.2018.
 */

public class NewsUpdate {

    private String mTitle;
    private String mDate;
    private String mName;
    private String mURL;
    private String mByLine;

    public NewsUpdate(String Date, String Title, String url,String Name , String ByLine) {
        mDate = Date;
        mTitle = Title;
        mName=Name;
        mURL = url;
        mByLine=ByLine;


    }

    public String getTitle() {
        return mTitle;
    }

    public String getDate() {
        return mDate;
    }

    public String getmURL() {
        return mURL;
    }

    public String getName(){return mName;}

    public String getmByLine(){return mByLine;}}


