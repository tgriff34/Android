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
 * Created by Tristan on 2/26/18.
 */

public class NewsAdapter extends ArrayAdapter<Articles> {
    public NewsAdapter(@NonNull Context context, int resource, @NonNull List<Articles> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Articles articles = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_view, parent, false);

        TextView textViewTitle = (TextView) convertView.findViewById(R.id.titleView);
        TextView textViewDescription = (TextView) convertView.findViewById(R.id.descriptionView);
        TextView textViewPublished = (TextView) convertView.findViewById(R.id.publishedView);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.newsPicture);

        textViewDescription.setText(articles.description);
        textViewPublished.setText(articles.published_at);
        textViewTitle.setText(articles.title);
        Picasso.with(getContext()).load(articles.image).into(imageView);

        return convertView;
    }
}
