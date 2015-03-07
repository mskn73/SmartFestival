package com.underphones.smartfestival;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.makeramen.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Juan on 07/03/2015.
 */
public class ContactsAdapter extends ArrayAdapter<ContactBean> {

    private final Activity context;
    private ArrayList<ContactBean> contactList;

    public ContactsAdapter(Activity context, ArrayList<ContactBean> list) {
        super(context, R.layout.contacts_item, list);
        this.context = context;
        contactList = list;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {

            ContactBean cb = getItem(position);
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.contacts_item, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.contact_image = (RoundedImageView) rowView.findViewById(R.id.contact_image);
            viewHolder.contact_name = (TextView) rowView.findViewById(R.id.contact_name);
            //viewHolder.contact_image.setImageDrawable();
            viewHolder.contact_name.setText(cb.getName());

            Picasso.with(context).load(cb.getImage()).into(viewHolder.contact_image);
        }

        return rowView;
    }

    static class ViewHolder {
        RoundedImageView contact_image;
        TextView contact_name;

    }

    @Override
    public int getCount() {
        if (contactList==null) return 0;
        return super.getCount();
    }
}
