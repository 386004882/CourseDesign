package com.example.jiji.coursedesign.UI;

import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.jiji.coursedesign.R;

/**
 * Created by jiji on 2017/5/5.
 */

public class ShowPhotoActivity extends BaseActivity implements GestureDetector.OnGestureListener {
    private ImageView imageView;
    private GestureDetector detector;
    private Matrix matrix;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showphoto);

        imageView = (ImageView) findViewById(R.id.showphoto);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra("imageUrl");
        Glide.with(this).load(imageUrl).into(imageView);

        detector = new GestureDetector(this, this);
        matrix = new Matrix();


    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
