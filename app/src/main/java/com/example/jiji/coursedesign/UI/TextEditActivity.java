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
import android.widget.Toast;

import com.example.jiji.coursedesign.R;
import com.example.jiji.coursedesign.Service.AlarmService;
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
    private EditText et_content;
    private EditText et_time;
    private EditText et_title;
    private EditText et_date;
    SimpleDateFormat formatDate = new SimpleDateFormat("MM-dd");
    SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
    private Calendar calendar = java.util.Calendar.getInstance(Locale.CHINA);

    private String oldTitle;
    private String oldContent;
    private String dateTime;
    private String oldTime;
    private String oldDate;
    private int oldId;
    private TextRecord record;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textedit);
        toolbar = (Toolbar) findViewById(R.id.text_edit_toolbar);
        back = (Button) findViewById(R.id.text_edit_back);
        ok = (Button) findViewById(R.id.text_edit_ok);
        et_title = (EditText) findViewById(R.id.text_edit_title);
        et_content = (EditText) findViewById(R.id.text_edit_content);
        et_time = (EditText) findViewById(R.id.text_edit_timetext);
        et_date = (EditText) findViewById(R.id.text_edit_datetext);

        setSupportActionBar(toolbar);

        //获取传输对象
        Intent data = getIntent();
        record = (TextRecord) data.getSerializableExtra("record");
        if (isRecordObjectExist()) {
            oldId = record.getId();
            oldTitle = record.getTitle().toString();
            if (oldTitle.equals("无标题便签")) {
                et_title.setText("");
            } else {
                et_title.setText(oldTitle);
            }
            oldContent = record.getContent().toString();
            et_content.setText(oldContent);
            if (record.getAlertTime() != null) {
                dateTime = record.getAlertTime().toString();
                oldTime = dateTime.split(" ")[0];
                oldDate = dateTime.split(" ")[1];
                et_date.setText(oldTime);
                et_time.setText(oldDate);
            }
        }

        //单击选择日期和时间
        et_date.setOnClickListener(new View.OnClickListener() {
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

        et_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(TextEditActivity.this, t,
                        calendar.get(Calendar.HOUR_OF_DAY)
                        , calendar.get(Calendar.MINUTE), true);
                dialog.show();
            }
        });

        //长按取消时间和日期
        et_date.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                et_date.setText("");
                return true;
            }
        });

        et_time.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                et_time.setText("");
                return true;
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //数据存入数据库中
                String date = et_date.getText().toString().trim();
                String time = et_time.getText().toString().trim();
                final String content = et_content.getText().toString();
                final String title = et_title.getText().toString();
                if (!content.equals("")) {
                    if (date.equals("") || time.equals("")) {

                        AlertDialog dialog = new AlertDialog.Builder(TextEditActivity.this).create();
                        dialog.setTitle("提示");
                        dialog.setMessage("是否不设置提醒时间？");
                        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确认"
                                , new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //没有传输的对象
                                        if (!isRecordObjectExist()) {
                                            TextRecord textRecord = new TextRecord();
                                            textRecord.setContent(content);
                                            //获取时间
                                            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
                                            Date curDate = new Date(System.currentTimeMillis());
                                            String now = formatter.format(curDate);
                                            textRecord.setTime(now);
                                            if (title.equals("")) {
                                                textRecord.setTitle("无标题便签");
                                            } else {
                                                textRecord.setTitle(title);
                                            }
                                            textRecord.save();
                                            setResult(RESULT_OK, new Intent());
                                            finish();
                                        } else {
                                            //内容是否相同
                                            if (!isContentSave()) {
                                                TextRecord textRecord = new TextRecord();
                                                textRecord.setContent(content);
                                                //获取时间
                                                SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
                                                Date curDate = new Date(System.currentTimeMillis());
                                                String now = formatter.format(curDate);
                                                textRecord.setTime(now);
                                                if (title.equals("")) {
                                                    textRecord.setTitle("无标题便签");
                                                } else {
                                                    textRecord.setTitle(title);
                                                }
                                                textRecord.updateAll("id=?", oldId + "");
                                                finish();
                                            } else {
                                                finish();
                                            }
                                        }
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
                        //有提醒时间
                        //对象不存在,插入数据库
                        if (!isRecordObjectExist()) {
                            String dateTime = date + " " + time;
                            TextRecord textRecord = new TextRecord();
                            textRecord.setContent(content);
                            //获取时间
                            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
                            Date curDate = new Date(System.currentTimeMillis());
                            String now = formatter.format(curDate);
                            textRecord.setTime(now);
                            textRecord.setAlertTime(dateTime);
                            if (title.equals("")) {
                                textRecord.setTitle("无标题便签");
                            } else {
                                textRecord.setTitle(title);
                            }
                            textRecord.save();
                            sendAlarmBroadcast(textRecord);
                            setResult(RESULT_OK, new Intent());
                            finish();
                        } else {//对象存在，更新数据库
                            //内容是否相同
                            if (!isContentSave()) {
                                String dateTime = date + " " + time;
                                TextRecord textRecord = new TextRecord();
                                textRecord.setContent(content);
                                //获取时间
                                SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
                                Date curDate = new Date(System.currentTimeMillis());
                                String now = formatter.format(curDate);
                                textRecord.setTime(now);
                                textRecord.setAlertTime(dateTime);
                                if (title.equals("")) {
                                    textRecord.setTitle("无标题便签");
                                } else {
                                    textRecord.setTitle(title);
                                }
                                textRecord.updateAll("id=?", oldId + "");
                                sendAlarmBroadcast(textRecord);
                                finish();
                            } else {
                                finish();
                            }
                        }

                    }
                } else {
                    Toast.makeText(TextEditActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
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
        et_date.setText(formatDate.format(calendar.getTime()));
    }

    private void updateTime() {
        et_time.setText(formatTime.format(calendar.getTime()));
    }

    //判断是否存在传输的对象
    private boolean isRecordObjectExist() {
        boolean flag = false;
        if (record != null) {
            flag = true;
        }
        return flag;
    }

    //检测内容是否相同
    private boolean isContentSave() {
        boolean flag = false;
        if (dateTime != null && oldTitle != null && oldContent != null) {
            if (oldTitle.equals(et_title.getText().toString())
                    && oldContent.equals(et_content.getText().toString())
                    && oldTime.equals(et_time.getText().toString())
                    && oldDate.equals(et_date.getText().toString())) {
                flag = true;
            }
        }
        return flag;
    }

    private void sendAlarmBroadcast(TextRecord textRecord) {
        if (textRecord.getAlertTime() != null && !textRecord.getAlertTime().equals("")) {
            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
            long alertTime = -1;
            try {
                Date date = formatter.parse(textRecord.getAlertTime());
                alertTime = date.getTime();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (alertTime != -1) {
                // TODO: 2017/5/20  将时间戳发送给服务，由服务处理alarm
                Intent i = new Intent(getApplicationContext(), AlarmService.class);
                i.putExtra("alertTime", alertTime);
                startService(i);
            }
        }
    }
}
