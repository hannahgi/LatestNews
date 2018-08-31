package com.example.android.latestnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Gikonyo hannah on 26.05.2018.
 */

public class NewsAdapter extends ArrayAdapter {
    public NewsAdapter(Context context, List<NewsUpdate> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(
                    R.layout.custom_xml, parent, false);
        }

        NewsUpdate current_News = (NewsUpdate) getItem(position);
        TextView date_view = (TextView) listView.findViewById(R.id.date);
        String date = current_News.getDate();
        date_view.setText(date);

        TextView title_view = (TextView) listView.findViewById(R.id.title);
        String title = current_News.getTitle();
        title_view.setText(title);


        TextView name_view = (TextView) listView.findViewById(R.id.sectionName);
        String name = current_News.getName ();
        name_view.setText(name);

        TextView byLine_view = (TextView) listView.findViewById(R.id.byLine);
        String byLine = current_News.getmByLine ();
        byLine_view.setText(byLine);

        return listView;

    }


}

