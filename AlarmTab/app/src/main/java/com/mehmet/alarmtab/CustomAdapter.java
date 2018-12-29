package com.mehmet.alarmtab;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Hafta_Sonu on 8.02.2017.
 */

public class CustomAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Alarms> mAlarms;

    public CustomAdapter(Activity activity, List<Alarms> mAlarms) {
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mAlarms = mAlarms;
    }

    @Override
    public int getCount() {
        return mAlarms.size();
    }

    @Override
    public Object getItem(int position) {
        return mAlarms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View satirView;
        satirView = mInflater.inflate(R.layout.satir_layout, null);
        TextView textView = (TextView) satirView.findViewById(R.id.date);
        ImageView imageView =(ImageView) satirView.findViewById(R.id.simge);

        Alarms alarms=mAlarms.get(position);
        textView.setText(alarms.getSaat());
        imageView.setImageResource(R.drawable.alarm_list);
        return satirView;
    }
}
