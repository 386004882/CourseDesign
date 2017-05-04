package com.example.jiji.coursedesign.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.jiji.coursedesign.R;
import com.example.jiji.coursedesign.db.Photo;

import java.io.IOException;

/**
 * Created by jiji on 2017/5/4.
 */

public class PhotoEditActivity extends AppCompatActivity {
    private ImageView imageView;
    private EditText editText;
    private Button submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoedit);

        imageView = (ImageView) findViewById(R.id.photoedit_img);
        editText = (EditText) findViewById(R.id.photoedit_edit);
        submit = (Button) findViewById(R.id.photoedit_submit);

        Intent intent = getIntent();
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
                //把数据存入数据库中
                String desc = editText.getText().toString().trim();
                Photo photo = new Photo();
                photo.setDescribe(desc);
                photo.setImageUrl(imageUrl);
                photo.setTime(SystemClock.elapsedRealtime() + "");
                photo.save();

                setResult(20, new Intent());
                finish();
            }
        });

    }
}
