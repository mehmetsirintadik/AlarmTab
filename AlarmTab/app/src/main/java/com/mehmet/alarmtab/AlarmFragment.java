package com.mehmet.alarmtab;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AlarmFragment extends Fragment {
    List<Alarms> alarms=new ArrayList<Alarms>();
    private ImageButton startAlarmBtn;
    private TimePickerDialog timePickerDialog;
    final static int REQUEST_CODE = 1;


    public AlarmFragment() {
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


        View view= inflater.inflate(R.layout.fragment_alarm, container, false);

        startAlarmBtn = (ImageButton)view.findViewById(R.id.imageButton);
        startAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPickerDialog(false);
            }
        });
        return view;
    }
    private void openPickerDialog(boolean is24hour) {

        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(
                getActivity(),
                onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                is24hour);
        timePickerDialog.setTitle("Alarm Ayarla");

        timePickerDialog.show();
    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener
            = new TimePickerDialog.OnTimeSetListener(){

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();

            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            if(calSet.compareTo(calNow) <= 0){

                calSet.add(Calendar.DATE, 1);
            }

            setAlarm(calSet);
            createListView();
            addAlarm(hourOfDay,minute);

        }

        private void addAlarm(int hourOfDay, int minute) {
            alarms.add(new Alarms(hourOfDay+":"+minute));
        }

        private void createListView() {
            final ListView listemiz = (ListView) getView().findViewById(R.id.a);
            CustomAdapter adapter =new CustomAdapter(getActivity(),alarms);
            listemiz.setAdapter(adapter);
        }
    };

    private void setAlarm(Calendar alarmCalender){


        Toast.makeText(getContext(),"Alarm Ayarlandı!",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity().getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity().getBaseContext(), REQUEST_CODE, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmCalender.getTimeInMillis(), pendingIntent);

    }



}
