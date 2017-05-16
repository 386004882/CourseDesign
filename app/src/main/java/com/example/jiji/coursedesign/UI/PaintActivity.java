package com.example.jiji.coursedesign.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jiji.coursedesign.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static org.litepal.LitePalApplication.getContext;

/**
 * Created by jiji on 2017/5/7.
 */

public class PaintActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView paintView;
    private Bitmap srcBitmap;
    private Bitmap copyBitmap;
    private Paint paint;
    private Canvas canvas;
    private RelativeLayout tools;
    private LinearLayout colors;
    private LinearLayout thickness;
    private Toolbar toolbar;
    private TextView color;
    private TextView thick;
    private TextView more;
    private Button back;
    private Button save;
    private RelativeLayout thickness10;
    private RelativeLayout thickness20;
    private RelativeLayout thickness30;
    private CircleImageView red;
    private CircleImageView green;
    private CircleImageView blue;

    // TODO: 2017/5/7 添加图片可涂鸦
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvty_paint);

        paintView = (ImageView) findViewById(R.id.paint_iv);
        tools = (RelativeLayout) findViewById(R.id.paint_tool);
        colors = (LinearLayout) findViewById(R.id.paint_tool_color);
        thickness = (LinearLayout) findViewById(R.id.paint_tool_thickness);
        toolbar = (Toolbar) findViewById(R.id.paint_toolbar);
        color = (TextView) findViewById(R.id.paint_color);
        thick = (TextView) findViewById(R.id.paint_thickness);
        more = (TextView) findViewById(R.id.paint_more);
        back = (Button) findViewById(R.id.paint_back);
        save = (Button) findViewById(R.id.paint_save);
        thickness10 = (RelativeLayout) findViewById(R.id.paint_thickness_10);
        thickness20 = (RelativeLayout) findViewById(R.id.paint_thickness_20);
        thickness30 = (RelativeLayout) findViewById(R.id.paint_thickness_30);
        red = (CircleImageView) findViewById(R.id.paint_color_red);
        green = (CircleImageView) findViewById(R.id.paint_color_green);
        blue = (CircleImageView) findViewById(R.id.paint_color_blue);

        color.setOnClickListener(this);
        thick.setOnClickListener(this);
        more.setOnClickListener(this);
        back.setOnClickListener(this);
        save.setOnClickListener(this);
        thickness10.setOnClickListener(this);
        thickness20.setOnClickListener(this);
        thickness30.setOnClickListener(this);
        red.setOnClickListener(this);
        green.setOnClickListener(this);
        blue.setOnClickListener(this);


        Glide.with(this).load(R.drawable.bg).into(paintView);
        srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        //创建原图副本
        copyBitmap = Bitmap.createBitmap(srcBitmap.getWidth()
                , srcBitmap.getHeight(), srcBitmap.getConfig());
        //画笔
        paint = new Paint();
        //画布
        canvas = new Canvas(copyBitmap);
        canvas.drawBitmap(srcBitmap, new Matrix(), paint);

        paintView.setOnTouchListener(new View.OnTouchListener() {
            int startX;
            int startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN://按下
                        startX = (int) event.getX();
                        startY = (int) event.getY();

                        break;
                    case MotionEvent.ACTION_MOVE://移动
                        int endX = (int) event.getX();
                        int endY = (int) event.getY();
                        //画线
                        canvas.drawLine(startX, startY, endX, endY, paint);
                        paintView.setImageBitmap(copyBitmap);
                        //更新开始坐标和结束坐标
                        startX = endX;
                        startY = endY;

                        break;
                    case MotionEvent.ACTION_UP://抬起

                        break;
                }
                return true;//时间处理完成
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 4://判断是否在编辑页面返回
                if (resultCode == 20) {
                    finish();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        AlertDialog isExit = new AlertDialog.Builder(this).create();
        isExit.setTitle("系统提示");
        isExit.setMessage("是否不保存涂鸦?");
        isExit.setButton(DialogInterface.BUTTON_POSITIVE, "确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        isExit.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        isExit.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.paint_save://保存
                try {
                    File outputImage = new File(getApplicationContext().getExternalCacheDir()
                            , System.currentTimeMillis() + ".jpg");
                    FileOutputStream fos = new FileOutputStream(outputImage);
                    copyBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    Uri imageUri;
                    if (Build.VERSION.SDK_INT >= 24) {
                        imageUri = FileProvider.getUriForFile(getContext()
                                , "com.example.jiji.coursedesign.fileprovider", outputImage);
                    } else {
                        imageUri = Uri.fromFile(outputImage);
                    }
                    Intent intent = new Intent(this, PhotoEditActivity.class);
                    intent.putExtra("imageUri", imageUri.toString());
                    //打开编辑页面
                    startActivityForResult(intent, 4);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.paint_back://返回
                AlertDialog isExit = new AlertDialog.Builder(this).create();
                isExit.setTitle("系统提示");
                isExit.setMessage("是否不保存涂鸦?");
                isExit.setButton(DialogInterface.BUTTON_POSITIVE, "确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                isExit.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                isExit.show();
                break;
            case R.id.paint_color://选择颜色
                if (tools.getVisibility() == View.INVISIBLE) {
                    thickness.setVisibility(View.INVISIBLE);
                    tools.setVisibility(View.VISIBLE);
                    colors.setVisibility(View.VISIBLE);
                } else {
                    thickness.setVisibility(View.INVISIBLE);
                    colors.setVisibility(View.INVISIBLE);
                    tools.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.paint_thickness://选择画笔
                if (tools.getVisibility() == View.INVISIBLE) {
                    colors.setVisibility(View.INVISIBLE);
                    tools.setVisibility(View.VISIBLE);
                    thickness.setVisibility(View.VISIBLE);
                } else {
                    colors.setVisibility(View.INVISIBLE);
                    thickness.setVisibility(View.INVISIBLE);
                    tools.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.paint_more://更多
                Toast.makeText(this, "更多功能正在开发中...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.paint_color_red:
                paint.setColor(Color.RED);
                thickness.setVisibility(View.INVISIBLE);
                colors.setVisibility(View.INVISIBLE);
                tools.setVisibility(View.INVISIBLE);
                break;
            case R.id.paint_color_blue:
                paint.setColor(Color.BLUE);
                thickness.setVisibility(View.INVISIBLE);
                colors.setVisibility(View.INVISIBLE);
                tools.setVisibility(View.INVISIBLE);
                break;
            case R.id.paint_color_green:
                paint.setColor(Color.GREEN);
                thickness.setVisibility(View.INVISIBLE);
                colors.setVisibility(View.INVISIBLE);
                tools.setVisibility(View.INVISIBLE);
                break;
            case R.id.paint_thickness_10:
                paint.setStrokeWidth(10);
                colors.setVisibility(View.INVISIBLE);
                thickness.setVisibility(View.INVISIBLE);
                tools.setVisibility(View.INVISIBLE);
                break;
            case R.id.paint_thickness_20:
                paint.setStrokeWidth(15);
                colors.setVisibility(View.INVISIBLE);
                thickness.setVisibility(View.INVISIBLE);
                tools.setVisibility(View.INVISIBLE);
                break;
            case R.id.paint_thickness_30:
                paint.setStrokeWidth(20);
                colors.setVisibility(View.INVISIBLE);
                thickness.setVisibility(View.INVISIBLE);
                tools.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
