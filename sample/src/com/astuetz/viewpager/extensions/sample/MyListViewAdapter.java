package com.astuetz.viewpager.extensions.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

// MyListViewAdapter.java
public class MyListViewAdapter extends ArrayAdapter<HashMap<String, String>> {
    private LayoutInflater mInflater;
    private TextView mTitle;
    private TextView mDesc;

    public MyListViewAdapter(Context context, List<HashMap<String, String>> objects)
    {
        super(context, 0, objects);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_eachtopic, null);
        }

        HashMap<String, String> item = this.getItem(position);
        if (item != null) {
            mTitle = (TextView)convertView.findViewById(R.id.site_name);
            mTitle.setText(item.get("title"));

            mDesc = (TextView)convertView.findViewById(R.id.title);
            mDesc.setText(item.get("desc"));
        }
        return convertView;
    }
}
