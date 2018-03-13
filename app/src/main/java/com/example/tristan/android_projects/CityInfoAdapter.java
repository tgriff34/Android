package com.example.tristan.android_projects;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Tristan on 3/12/18.
 */

public class CityInfoAdapter extends ArrayAdapter<CityInfo> {

    public CityInfoAdapter(@NonNull Context context, int resource, @NonNull List<CityInfo> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CityInfo cityInfo = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.new_view, parent, false);

        TextView cityName = (TextView) convertView.findViewById(R.id.cityName);
        TextView countryName = (TextView) convertView.findViewById(R.id.countryName);

        cityName.setText(cityInfo.name);
        countryName.setText(cityInfo.country);

        return convertView;
    }
}
