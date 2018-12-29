package com.mehmet.alarmtab;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.text.DateFormat;

public class AlarmFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    List<Alarms> alarms=new ArrayList<Alarms>();
    private Button startAlarmBtn, btnStop;
    private TimePickerDialog timePickerDialog;
    private DatePickerDialog datePickerDialog;
    final static int REQUEST_CODE = 1;
    PendingIntent pintent;
    AlarmManager alarm;

    int yil,ay,gun,saat,dakika;
    int yilSon,aySon,gunSon,saatSon,dakikaSon;

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

        startAlarmBtn = (Button) view.findViewById(R.id.btnAlarm);
        //startAlarmBtn.setImageResource(R.drawable.alarm_list);

        btnStop=(Button) view.findViewById(R.id.btnStop);
        //btnStop.setImageResource(R.drawable.stop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAlarm();
            }
        });
        startAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDateDialog();
            }
        });

        return view;
    }

    private void openDateDialog(){
        final Calendar takvim = Calendar.getInstance();
        yil = takvim.get(Calendar.YEAR);
        ay = takvim.get(Calendar.MONTH);
        gun = takvim.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                AlarmFragment.this, yil, ay, gun);
        datePickerDialog.show();

    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        yilSon=i;
        aySon=i1+1;
        gunSon=i2;

        Calendar c = Calendar.getInstance();
        saat = c.get(Calendar.HOUR_OF_DAY);
        dakika = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),AlarmFragment.this,
                saat,dakika, android.text.format.DateFormat.is24HourFormat(getContext()));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        saatSon=i;
        dakikaSon=i1;

        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, yilSon);
        c.set(Calendar.MONTH, aySon);
        c.set(Calendar.DAY_OF_MONTH, gunSon);
        c.set(Calendar.HOUR_OF_DAY, saatSon);
        c.set(Calendar.MINUTE, dakikaSon);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        setAlarm(c);
        createListView();
        addAlarm(saatSon,dakikaSon);

    }
    private void addAlarm(int hourOfDay, int minute) {
        if (minute<10){
            alarms.add(new Alarms(hourOfDay+" : 0"+minute));
        }else{
            alarms.add(new Alarms(hourOfDay+" : "+minute));
        }


    }

    private void createListView() {
        final ListView listemiz = (ListView) getView().findViewById(R.id.a);
        CustomAdapter adapter =new CustomAdapter(getActivity(),alarms);
        listemiz.setAdapter(adapter);
    }


    private void setAlarm(Calendar calendar){

        final Snackbar sb =Snackbar.make(getView().getRootView(), "Alarm Ayarlandı!", Snackbar.LENGTH_LONG);
        sb.setAction("CLOSE", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sb.dismiss();
            }
        })
                .setDuration(3000)
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                .show();

        alarm = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmService.class);
        pintent = PendingIntent.getService(getContext(), 0, intent, 0);

        Calendar cal =calendar;
        cal.setTimeInMillis(cal.getTimeInMillis());
        cal.set(Calendar.HOUR_OF_DAY, saatSon);
        cal.set(Calendar.MINUTE, dakikaSon);

        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                1000 * 60 * 20, pintent);


    }

    private void stopAlarm(){
        getActivity().stopService(new Intent(getContext(), AlarmService.class));
    }
}
