package com.example.jiji.coursedesign.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jiji on 2017/5/25.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ActivityCollector.ActivityCreate(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        ActivityCollector.ActivityDestroy(this);
        super.onDestroy();
    }
}
