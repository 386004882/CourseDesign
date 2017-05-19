package com.example.jiji.coursedesign.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by jiji on 2017/5/20.
 */

public class AlarmService extends Service {
    private long alertTime;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        alertTime = intent.getLongExtra("alertTime", -1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent1 = new Intent("ALARM_TO_NOTIFICATION");
                intent1.putExtra("test", alertTime);
                //activity关闭会影响
                PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, intent1, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP
                        , alertTime, pi);
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }
}
