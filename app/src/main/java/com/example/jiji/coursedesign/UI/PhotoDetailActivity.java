package com.example.jiji.coursedesign.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
    private TextView photoText;
    private TextView time;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photodetail);

        //获取传递的对象数据
        final Intent intent = getIntent();
        final String imageUrl = intent.getStringExtra("imageUrl");
        Photo photo = DataSupport.where("imageUrl=?", imageUrl).findFirst(Photo.class);
        photoUrl = photo.getImageUrl();
        photoDesc = photo.getDescribe();
        photoText = (TextView) findViewById(R.id.photo_describe_text);
        photoView = (ImageView) findViewById(R.id.photo_image_view);
        fab = (FloatingActionButton) findViewById(R.id.photo_view_fab);
        time = (TextView) findViewById(R.id.photo_show_time);

        photoText.setText(photoDesc);
        Glide.with(this).load(imageUrl).into(photoView);
        time.setText(photo.getTime());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PhotoDetailActivity.this, ShowPhotoActivity.class);
                i.putExtra("imageUrl", imageUrl);
                startActivity(i);
            }
        });


    }
}
