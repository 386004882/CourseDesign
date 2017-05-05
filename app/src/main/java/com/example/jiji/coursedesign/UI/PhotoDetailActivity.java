package com.example.jiji.coursedesign.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.jiji.coursedesign.R;
import com.example.jiji.coursedesign.db.Photo;

import org.litepal.crud.DataSupport;

/**
 * Created by jiji on 2017/5/5.
 */

public class PhotoDetailActivity extends AppCompatActivity {
    private String photoUrl;
    private String photoDesc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photodetail);

        //获取传递的对象数据
        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra("imageUrl");
        Photo photo = DataSupport.where("imageUrl=?", imageUrl).findFirst(Photo.class);
        photoUrl = photo.getImageUrl();
        photoDesc = photo.getDescribe();



    }
}
