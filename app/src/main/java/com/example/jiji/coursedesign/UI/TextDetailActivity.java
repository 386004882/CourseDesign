package com.example.jiji.coursedesign.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiji.coursedesign.R;
import com.example.jiji.coursedesign.db.TextRecord;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jiji on 2017/5/6.
 */

public class TextDetailActivity extends AppCompatActivity {
    private Button back;
    private Button edit;
    private Toolbar toolbar;
    private EditText content;
    private TextView time;
    private TextView alertTime;
    private TextRecord text;
    private int isEDITABLE = 0;//默认不可编辑

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textdetail);

        final Intent intent = getIntent();
        final int textId = intent.getIntExtra("textId", -1);

        back = (Button) findViewById(R.id.text_detail_back);
        edit = (Button) findViewById(R.id.text_detail_edit);
        toolbar = (Toolbar) findViewById(R.id.photodetail_toolbar);
        content = (EditText) findViewById(R.id.text_detail_content);
        time = (TextView) findViewById(R.id.text_detail_time);
        alertTime = (TextView) findViewById(R.id.text_detail_alerttime);

        setSupportActionBar(toolbar);

        if (textId != -1) {
            text = DataSupport.where("id=?", textId + "").findFirst(TextRecord.class);
            content.setText(text.getContent());
            time.setText(text.getTime());
            alertTime.setText(text.getAlertTime());
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEDITABLE == 1) {
                    //如果当前是可编辑状态，点击保存
                    TextRecord textRecord = new TextRecord();
                    SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm:ss");
                    Date curDate = new Date(System.currentTimeMillis());
                    String now = formatter.format(curDate);
                    textRecord.setAlertTime(text.getAlertTime());
                    textRecord.setTime(now);
                    textRecord.setContent(content.getText().toString());
                    textRecord.updateAll("id=?", textId + "");
                    Toast.makeText(TextDetailActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent();
                    intent.setAction("com.example.jiji.coursedesign.textdatechange");
                    sendBroadcast(intent);
                    isEDITABLE = 0;
                } else if (isEDITABLE == 0) {
                    //不可编辑变可编辑
                    content.setFocusable(true);
                    content.requestFocus();
                    content.setFocusableInTouchMode(true);
                    Toast.makeText(TextDetailActivity.this, "已更改为可编辑,再次点击按钮保存", Toast.LENGTH_SHORT).show();
                    // TODO: 2017/5/6 添加按钮更改
                    isEDITABLE = 1;
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEDITABLE == 0) {
                    finish();
                } else if (isEDITABLE == 1) {
                    Intent intent1 = new Intent();
                    intent.setAction("com.example.jiji.coursedesign.textdatechange");
                    sendBroadcast(intent);
                    finish();
                }
            }
        });


    }
}
