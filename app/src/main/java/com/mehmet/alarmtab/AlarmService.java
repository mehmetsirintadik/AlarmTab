package com.mehmet.alarmtab;

import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Mehmet on 21.02.2017.
 */

public class AlarmService extends Service {
    Ringtone ringtone;
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), alarmUri);
        ringtone.play();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onDestroy() {
        if(ringtone.isPlaying()){
            ringtone.stop();
        }
        Toast.makeText(getApplicationContext(),"Service Durduruldu.",Toast.LENGTH_SHORT).show();
        // TODO Auto-generated method stub
        super.onDestroy();
    }


}
