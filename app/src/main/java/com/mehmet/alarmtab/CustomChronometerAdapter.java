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
 * Created by Mehmet on 22.02.2017.
 */

public class CustomChronometerAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private List<Chronometers> mChronometers;

    public CustomChronometerAdapter(Activity activity, List<Chronometers> mChronometers) {
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mChronometers = mChronometers;
    }
    @Override
    public int getCount() {
        return mChronometers.size();
    }
    public Object getItem(int position) {
        return mChronometers.get(position);
    }
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View satirView;
        satirView = mInflater.inflate(R.layout.satir_layout, null);
        TextView textView = (TextView) satirView.findViewById(R.id.date);
        ImageView imageView =(ImageView) satirView.findViewById(R.id.simge);

        Chronometers alarms=mChronometers.get(position);
        textView.setText(alarms.getChronometers());
        imageView.setImageResource(R.drawable.chronometer);
        return satirView;
    }

}
