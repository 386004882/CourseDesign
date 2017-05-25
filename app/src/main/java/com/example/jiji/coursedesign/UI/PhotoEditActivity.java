package com.example.jiji.coursedesign.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.jiji.coursedesign.R;
import com.example.jiji.coursedesign.db.Photo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jiji on 2017/5/4.
 */

public class PhotoEditActivity extends BaseActivity {
    private ImageView imageView;
    private EditText editText;
    private Button submit;
    private Toolbar toolbar;
    private Button back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoedit);

        imageView = (ImageView) findViewById(R.id.photoedit_img);
        editText = (EditText) findViewById(R.id.photoedit_edit);
        submit = (Button) findViewById(R.id.photoedit_submit);
        toolbar = (Toolbar) findViewById(R.id.photoedit_toolbar);
        back = (Button) findViewById(R.id.photoedit_back);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //获取传递的图片路径
        final Intent intent = getIntent();
        final String imageUrl = intent.getStringExtra("imageUri");
        final Uri imageUri = Uri.parse(imageUrl);
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                    .openInputStream(imageUri));
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取当前时间
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());
                String date = formatter.format(curDate);
                //把数据存入数据库中
                String desc = editText.getText().toString().trim();
                Photo photo = new Photo();
                photo.setDescribe(desc);
                photo.setImageUrl(imageUrl);
                photo.setTime(date);
                photo.save();
                setResult(RESULT_OK, new Intent());
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED, new Intent());
                finish();
            }
        });
    }
}
