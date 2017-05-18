package com.example.jiji.coursedesign.UI;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.jiji.coursedesign.R;
import com.example.jiji.coursedesign.db.TextRecord;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by jiji on 2017/5/6.
 */
// TODO: 2017/5/7 添加可以往输入位置插入其他元素(复选，单选...)及文字样式
// TODO: 2017/5/7 加入提醒通知
public class TextEditActivity extends AppCompatActivity {
    private Button back;
    private Button ok;
    private Toolbar toolbar;
    private EditText content;
    private EditText timeText;
    private EditText dateText;
    SimpleDateFormat formatDate = new SimpleDateFormat("MM-dd");
    SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
    private Calendar calendar = java.util.Calendar.getInstance(Locale.CHINA);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textedit);
        toolbar = (Toolbar) findViewById(R.id.text_edit_toolbar);
        back = (Button) findViewById(R.id.text_edit_back);
        ok = (Button) findViewById(R.id.text_edit_ok);
        content = (EditText) findViewById(R.id.text_edit_content);
        timeText = (EditText) findViewById(R.id.text_edit_timetext);
        dateText = (EditText) findViewById(R.id.text_edit_datetext);

        setSupportActionBar(toolbar);


        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出时间选择框
                DatePickerDialog dialog = new DatePickerDialog(TextEditActivity.this, d,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();

            }
        });

        timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(TextEditActivity.this, t,
                        calendar.get(Calendar.HOUR_OF_DAY)
                        , calendar.get(Calendar.MINUTE), true);
                dialog.show();
            }
        });


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //数据存入数据库中
                String date = dateText.getText().toString().trim();
                String time = timeText.getText().toString().trim();
                if (date.equals("") || time.equals("")) {
                    AlertDialog dialog = new AlertDialog.Builder(TextEditActivity.this).create();
                    dialog.setTitle("提示");
                    dialog.setMessage("是否不设置提醒时间？");
                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确认"
                            , new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    TextRecord textRecord = new TextRecord();
                                    textRecord.setContent(content.getText().toString());
                                    //获取时间
                                    SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm:ss");
                                    Date curDate = new Date(System.currentTimeMillis());
                                    String now = formatter.format(curDate);
                                    textRecord.setTime(now);
                                    textRecord.save();
                                    setResult(RESULT_OK, new Intent());
                                    finish();
                                }
                            });
                    dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消"
                            , new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    dialog.show();
                } else {
                    String dateTime = date + " " + time;
                    TextRecord textRecord = new TextRecord();
                    textRecord.setContent(content.getText().toString());
                    //获取时间
                    SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm:ss");
                    Date curDate = new Date(System.currentTimeMillis());
                    String now = formatter.format(curDate);
                    textRecord.setTime(now);
                    textRecord.setAlertTime(dateTime);
                    textRecord.save();
                    setResult(RESULT_OK, new Intent());
                    finish();
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    DatePickerDialog.OnDateSetListener d
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate();
        }
    };
    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            updateTime();
        }
    };


    private void updateDate() {
        dateText.setText(formatDate.format(calendar.getTime()));
    }

    private void updateTime() {
        timeText.setText(formatTime.format(calendar.getTime()));
    }
}
