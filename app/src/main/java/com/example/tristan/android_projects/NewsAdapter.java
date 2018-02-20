package com.example.tristan.android_projects;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Tristan on 2/19/18.
 */

public class NewsAdapter extends ArrayAdapter<NewArticles> {

    public NewsAdapter(Context context, int resource, List<NewArticles> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NewArticles newArticles = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.new_view, parent, false);

        TextView textViewAuthor = (TextView) convertView.findViewById(R.id.authorView);
        TextView textViewDate = (TextView) convertView.findViewById(R.id.dateView);
        TextView textViewTitle = (TextView) convertView.findViewById(R.id.titleView);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);

        textViewAuthor.setText(newArticles.author);
        textViewDate.setText(newArticles.date);
        textViewTitle.setText(newArticles.title);
        Picasso.with(getContext()).load(newArticles.image).into(imageView);

        return convertView;
    }
}
