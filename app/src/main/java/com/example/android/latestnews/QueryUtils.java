package com.example.android.latestnews;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gikonyo hannah on 23.05.2018.
 */
public final class QueryUtils {

    private QueryUtils() {
    }

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public static List<NewsUpdate> fetchData(String requestURL) {
        URL url = createUrl(requestURL);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
        }
        List<NewsUpdate> news = extractNews(jsonResponse);
        return news;
    }

    private static URL createUrl(String url) {
        URL finalUrl = null;
        try {
            finalUrl = new URL(url);
        } catch (MalformedURLException e) {

        }
        return finalUrl;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = null;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(1000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = getStream(inputStream);
            } else {
                Log.e(LOG_TAG, "no response " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem  retrieving data ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String getStream(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String reads = reader.readLine();
            while (reads != null) {
                builder.append(reads);
                reads = reader.readLine();
            }
        }
        return builder.toString();
    }

    private static List<NewsUpdate> extractNews(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        List<NewsUpdate> newsList = new ArrayList<> ();
        try {
            JSONObject Response = new JSONObject(str);
            JSONObject response = Response.getJSONObject("response");
            JSONArray result = response.getJSONArray("results");
            for (int i = 0; i < result.length(); i++) {
                JSONObject CurrentNews = result.getJSONObject(i);
                String date_time = CurrentNews.optString("webPublicationDate");
                String date = date_time.substring(0,10);
                String title = CurrentNews.getString("webTitle");
                String url = CurrentNews.getString("webUrl");
                String name=CurrentNews.getString ("sectionName");

                JSONObject jsonFieldsObject=CurrentNews.optJSONObject ("fields");
                String byLine=jsonFieldsObject.optString ("byline");

                NewsUpdate news = new NewsUpdate (date, title, url,name,byLine);
                newsList.add(news);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem in parsing data ", e);
        }
        return newsList;
    }
}

