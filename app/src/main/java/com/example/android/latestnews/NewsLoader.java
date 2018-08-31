package com.example.android.latestnews;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Gikonyo hannah on 23.05.2018.
 */

    public class NewsLoader extends AsyncTaskLoader<List<NewsUpdate>> {
        private String mURL;

        public NewsLoader(Context context, String URL) {
            super(context);
            mURL = URL;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }

        @Override
        public List<NewsUpdate> loadInBackground() {
            if (mURL == null) {
                return null;
            }
            List<NewsUpdate> news = QueryUtils.fetchData(mURL);
            return news;
        }
    }


