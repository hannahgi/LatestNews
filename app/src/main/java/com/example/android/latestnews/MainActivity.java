package com.example.android.latestnews;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gikonyo hannah on 26.05.2018.
 */


public class MainActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<List<NewsUpdate>> {


    private static String news_URL ="https://content.guardianapis.com/search?q=android&api-key=dcc1ce04-e31f-464b-bd14-699f30106cd1&show-fields=byline,thumbnails";
    private TextView emptyTextView;
    private static final int newsloader = 0;
    private NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        ListView listNews = (ListView) findViewById (R.id.listNews);
         emptyTextView = (TextView) findViewById (R.id.empty_textView);
        listNews.setEmptyView (emptyTextView);
        adapter = new NewsAdapter (this, new ArrayList<NewsUpdate> ());
        listNews.setAdapter (adapter);
        android.app.LoaderManager loader = null;
        listNews.setOnItemClickListener (new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                NewsUpdate current_news = (NewsUpdate) adapter.getItem (position);
                Uri news_uri = Uri.parse (current_news.getmURL ());
                Intent internetpage = new Intent (Intent.ACTION_VIEW, news_uri);
                startActivity (internetpage);
            }
        });
           ConnectivityManager connMgr = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo ();
        if (networkInfo != null && networkInfo.isConnected ()) {
            loader = getLoaderManager ();
            loader.initLoader (newsloader, null, this);
        } else {
            View indicator = findViewById (R.id.Indicator);
            indicator.setVisibility (View.GONE);
            emptyTextView.setText ("Internet not available ");
        }
    }

    @Override
    public Loader<List<NewsUpdate>> onCreateLoader(int i, Bundle bundle) {
        return new NewsLoader (this, news_URL);
    }


    @Override
    public void onLoadFinished(Loader<List<NewsUpdate>> loader, List<NewsUpdate> News) {
        View loadingIndicator = findViewById (R.id.Indicator);
        loadingIndicator.setVisibility (View.GONE);
        emptyTextView.setText ("No news found");
        adapter.clear ();
        if (News != null && !News.isEmpty ()) {
            adapter.addAll (News);
        } else {
            emptyTextView.setVisibility (View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<NewsUpdate>> loader) {
        adapter.clear ();
    }}


