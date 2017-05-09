package com.example.jiji.coursedesign.UI;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jiji.coursedesign.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jiji on 2017/5/7.
 */
// TODO: 2017/5/7 修改图片分辨率

public class GameActivity extends AppCompatActivity {
    private ImageView src;
    private ImageView after;
    private Bitmap alertBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        src = (ImageView) findViewById(R.id.game_src);
        after = (ImageView) findViewById(R.id.game_after);

        String srcRes;
        String afterRes;

        int number = (int) (Math.random() * 25);
        Toast.makeText(this, number + "", Toast.LENGTH_SHORT).show();
        if (number > 19) {
            srcRes = "src/mnsrc16.jpg";
            int mnlsNum = (int) (Math.random() * 10);
            afterRes = "mnls/mnls" + mnlsNum + ".jpg";
            Toast.makeText(this, mnlsNum + "", Toast.LENGTH_SHORT).show();
        } else if (number == 15 || number == 16 || number == 0 || number == 1) {
            srcRes = "src/mnsrc15.jpg";
            int ekNum = (int) (Math.random() * 10);
            afterRes = "ek/ek" + ekNum + ".jpg";
            Toast.makeText(this, ekNum + "", Toast.LENGTH_SHORT).show();
        } else if (number == 2 || number == 17 || number == 18 || number == 19) {
            srcRes = "src/mnsrc2.jpg";
            int dfNum = (int) (Math.random() * 20);
            afterRes = "df/df" + dfNum + ".jpg";
            Toast.makeText(this, dfNum + "", Toast.LENGTH_SHORT).show();
        } else {
            srcRes = "src/mnsrc" + number + ".jpg";
            int mnNum = (int) (Math.random() * 19);
            afterRes = "mn/mn" + mnNum + ".jpg";
            Toast.makeText(this, mnNum + "", Toast.LENGTH_SHORT).show();

        }

        InputStream inafter = null;
        try {
            inafter = this.getAssets().open(afterRes);
        } catch (IOException e) {

        }
        Bitmap afterBitmap = BitmapFactory.decodeStream(inafter);
        after.setImageBitmap(afterBitmap);


        InputStream in = null;
        try {
            in = this.getAssets().open(srcRes);
        } catch (IOException e) {

        }
        Bitmap srcBitmap = BitmapFactory.decodeStream(in);

        //副本
        alertBitmap = Bitmap.createBitmap(srcBitmap.getWidth()
                , srcBitmap.getHeight(), srcBitmap.getConfig());

        Paint paint = new Paint();

        Canvas canvas = new Canvas(alertBitmap);

        canvas.drawBitmap(srcBitmap, new Matrix(), paint);

        src.setImageBitmap(alertBitmap);

        src.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        for (int i = -20; i < 20; i++) {
                            for (int j = -20; j < 20; j++) {
                                //圆形
                                if (Math.sqrt(i * i + j * j) < 20) {
                                    try {
                                        alertBitmap.setPixel((int) event.getX() + i
                                                , (int) event.getY() + j, Color.TRANSPARENT);
                                    } catch (Exception e) {
                                    }
                                }
                            }
                        }
                        src.setImageBitmap(alertBitmap);

                        break;
                }
                return true;
            }
        });
    }
}
