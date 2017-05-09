package com.example.jiji.coursedesign.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jiji.coursedesign.R;
import com.example.jiji.coursedesign.db.Photo;

import org.litepal.crud.DataSupport;

/**
 * Created by jiji on 2017/5/5.
 */

public class PhotoDetailActivity extends AppCompatActivity {
    private String photoUrl;
    private String photoDesc;
    private ImageView photoView;
    private EditText photoText;
    private TextView time;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private Button save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photodetail);

        //获取传递的对象数据
        final Intent intent = getIntent();
        final String imageUrl = intent.getStringExtra("imageUrl");
        final Photo photo = DataSupport.where("imageUrl=?", imageUrl).findFirst(Photo.class);
        photoUrl = photo.getImageUrl();
        photoDesc = photo.getDescribe();

        photoText = (EditText) findViewById(R.id.photo_describe_text);
        photoView = (ImageView) findViewById(R.id.photo_image_view);
        fab = (FloatingActionButton) findViewById(R.id.photo_view_fab);
        time = (TextView) findViewById(R.id.photo_show_time);
        toolbar = (Toolbar) findViewById(R.id.photodetail_toolbar);
        save = (Button) findViewById(R.id.photo_describe_save);

        //设置标题栏
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        actionBar.setTitle(photo.getDescribe());

        //设置控件属性
        photoText.setText(photoDesc);
        Glide.with(this).load(imageUrl).into(photoView);
        time.setText(photo.getTime());

        photoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!photoText.isFocusable()) {
                    Toast.makeText(PhotoDetailActivity.this, "长按重新编辑", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //长按文字描述框编辑
        photoText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //变为可编辑状态
                photoText.setFocusable(true);
                photoText.setFocusableInTouchMode(true);
                photoText.requestFocus();
                save.setVisibility(View.VISIBLE);

                return true;
            }
        });

        //保存
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newText = photoText.getText().toString();
                if (!newText.equals(photo.getDescribe())) {
                    photo.setDescribe(newText);
                    photo.updateAll("imageUrl=?", photo.getImageUrl());
                    Toast.makeText(PhotoDetailActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                }
                photoText.setFocusableInTouchMode(false);
                photoText.setFocusable(false);
                save.setVisibility(View.INVISIBLE);
            }
        });

        //点击悬浮按钮查看图片
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PhotoDetailActivity.this, ShowPhotoActivity.class);
                i.putExtra("imageUrl", imageUrl);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
