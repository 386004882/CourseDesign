package com.example.jiji.coursedesign.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by jiji on 2017/5/19.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "你好:" + intent.getStringExtra("test"), Toast.LENGTH_SHORT).show();
    }
}
