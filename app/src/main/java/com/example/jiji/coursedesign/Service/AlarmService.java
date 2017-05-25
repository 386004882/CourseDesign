package com.example.jiji.coursedesign.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.jiji.coursedesign.R;
import com.example.jiji.coursedesign.UI.MainActivity;
import com.example.jiji.coursedesign.db.TextRecord;

/**
 * Created by jiji on 2017/5/23.
 */

public class AlarmService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final TextRecord record = (TextRecord) intent.getSerializableExtra("record");
        new Thread(new Runnable() {
            @Override
            public void run() {
                //通知推送
                if (record != null) {
                    Intent i = new Intent(AlarmService.this, MainActivity.class);
                    i.putExtra("record", record);
                    PendingIntent p = PendingIntent.getActivity(getApplicationContext(), 0, i, 0);
                    NotificationManager manage = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    Notification notification = new Notification.Builder(getApplicationContext())
                            .setContentTitle("提醒")
                            .setContentText("您设置的提示时间到啦！点击查看内容")
                            .setWhen(System.currentTimeMillis())
                            .setSmallIcon(R.drawable.ic_action_alarm)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources()
                                    , R.drawable.ic_action_guitar))
                            .setContentIntent(p)
                            .setPriority(Notification.PRIORITY_MAX)
                            .setAutoCancel(true)
                            .build();
                    manage.notify(record.getId(), notification);
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }
}
