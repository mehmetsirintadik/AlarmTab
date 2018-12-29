package com.mehmet.alarmtab;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class ChronometerFragment extends Fragment {
    Chronometer chronometer;
    Button btnStartChronometer,btnStopChronometer,btnRestartChronometer, btnClear;
    ListView listView;
    List<Chronometers> chronometers=new ArrayList<Chronometers>();
    CustomChronometerAdapter adapter;
    ListView listemiz;

    public ChronometerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chronometer, container, false);

        chronometer=(Chronometer)view.findViewById(R.id.chronometer);
        btnStartChronometer=(Button)view.findViewById(R.id.btnStartChronometer);
        btnStopChronometer=(Button)view.findViewById(R.id.btnStopChronometer);
        btnRestartChronometer=(Button)view.findViewById(R.id.btnRestartChronometer);
        listView = (ListView)view.findViewById(R.id.chronometerList);
        btnClear=(Button)view.findViewById(R.id.btnClear);

        btnStartChronometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                btnStartChronometer.setEnabled(false);
            }
        });

        btnStopChronometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStartChronometer.setEnabled(true);
                //chronometer.stop();
                long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                int h   = (int)(time /3600000);
                int m = (int)(time - h*3600000)/60000;
                int s= (int)(time - h*3600000- m*60000)/1000 ;
                int ms= (int)time - h*3600000-m*60000-s*1000 ;
                String hh = h < 10 ? "0"+h: h+"";
                String mm = m < 10 ? "0"+m: m+"";
                String ss = s < 10 ? "0"+s: s+"";
                String mss = String.valueOf(ms);

                chronometers.add(new Chronometers(mm+":"+ss+":"+mss));
                createListView2();
            }
        });

        btnRestartChronometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.setBase(SystemClock.elapsedRealtime());
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometers.clear();
                adapter.notifyDataSetChanged();
            }
        });

        return view;
    }
    private void createListView2() {


        listemiz = (ListView)getView().findViewById(R.id.chronometerList);
        adapter =new CustomChronometerAdapter(getActivity(),chronometers);
        listemiz.setAdapter(adapter);
    }

}
