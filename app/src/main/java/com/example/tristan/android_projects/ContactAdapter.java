package com.example.tristan.android_projects;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Tristan on 2/6/18.
 */

public class ContactAdapter extends ArrayAdapter<Contact> {
    public ContactAdapter(Context context, ArrayList<Contact> contacts) {
        super(context, 0, contacts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contact contact = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_contact, parent, false);
        }

        TextView textViewFirst = (TextView) convertView.findViewById(R.id.firstListView);
        TextView textViewLast = (TextView) convertView.findViewById(R.id.lastListView);
        TextView textViewPhone = (TextView) convertView.findViewById(R.id.phoneListView);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.contactAvatar);

        textViewFirst.setText(contact.user_firstName);
        textViewLast.setText(contact.user_lastName);
        textViewPhone.setText(contact.user_phone);
        if (contact.user_contactAvatar != null) {
            imageView.setImageBitmap(contact.user_contactAvatar);
        } else {
            imageView.setImageResource(R.drawable.default_image);
        }

        return convertView;
    }
}
