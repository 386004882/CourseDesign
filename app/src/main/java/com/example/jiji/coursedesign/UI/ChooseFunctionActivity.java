package com.example.jiji.coursedesign.UI;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.jiji.coursedesign.R;

import java.io.File;
import java.io.IOException;

import static org.litepal.LitePalApplication.getContext;

/**
 * Created by jiji on 2017/5/18.
 */

public class ChooseFunctionActivity extends AppCompatActivity {
    private static final int TEXT = 1;
    private static final int PHOTO = 2;
    private static final int CAMERA = 3;
    private static final int PAINT = 4;
    private static final int PHOTO_RESULT = 20;

    private Uri imageUri;

    private RelativeLayout content;
    private RelativeLayout text;
    private RelativeLayout photo;
    private RelativeLayout camera;
    private RelativeLayout paint;
    private FloatingActionButton fab_text;
    private FloatingActionButton fab_photo;
    private FloatingActionButton fab_camera;
    private FloatingActionButton fab_paint;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosefunction);

        //找控件
        content = (RelativeLayout) findViewById(R.id.function_content);
        text = (RelativeLayout) findViewById(R.id.function_text);
        photo = (RelativeLayout) findViewById(R.id.function_photo);
        camera = (RelativeLayout) findViewById(R.id.function_camera);
        paint = (RelativeLayout) findViewById(R.id.function_paint);
        fab_text = (FloatingActionButton) findViewById(R.id.function_fab_text);
        fab_photo = (FloatingActionButton) findViewById(R.id.function_fab_photo);
        fab_camera = (FloatingActionButton) findViewById(R.id.function_fab_camera);
        fab_paint = (FloatingActionButton) findViewById(R.id.function_fab_paint);

        //初始不可见
        text.setVisibility(View.INVISIBLE);
        camera.setVisibility(View.INVISIBLE);
        photo.setVisibility(View.INVISIBLE);
        paint.setVisibility(View.INVISIBLE);
        //动画
        Animation set = AnimationUtils.loadAnimation(this, R.anim.anim_function_show);
        AlphaAnimation alpha = new AlphaAnimation(0.0f, 1.0f);
        text.startAnimation(set);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation set1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_function_show);
                camera.startAnimation(set1);
                set1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Animation set2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_function_show);
                        photo.startAnimation(set2);
                        set2.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                Animation set3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_function_show);
                                paint.startAnimation(set3);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //事件
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openText();
            }
        });
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPhoto();
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
        paint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPaint();
            }
        });
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //动画
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_function_disappear);
                paint.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Animation a = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_function_disappear);
                        photo.startAnimation(a);
                        a.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                Animation a1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_function_disappear);
                                camera.startAnimation(a1);
                                a1.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        Animation a2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_function_disappear);
                                        text.startAnimation(a2);
                                        finish();
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

    }

    //创建便签
    private void openText() {
        //打开便签编辑页面
        Intent intent = new Intent(getApplicationContext(), TextEditActivity.class);
        startActivityForResult(intent, TEXT);
    }

    //打开相册
    private void openPhoto() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission
                .WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ChooseFunctionActivity.this
                    , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            openAlbum();
        }
    }

    //打开相机
    private void openCamera() {
        File outputImage = new File(getContext().getExternalCacheDir()
                , System.currentTimeMillis() + ".jpg");
        try {
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(getContext()
                    , "com.example.jiji.coursedesign.fileprovider", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }
        //启动相机
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAMERA);
    }

    //打开涂鸦
    private void openPaint() {
        //打开涂鸦页面
        Intent paintIntent = new Intent(getApplicationContext(), PaintActivity.class);
        startActivity(paintIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TEXT:
                if (resultCode == RESULT_OK) {
                    setResult(10);//TEXT_OK
                    finish();
                }
                break;
            case PHOTO://调用相册结果处理
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);//这里打开图片编辑并请求返回结果
                    } else {
                        handleImageBeforeKitKat(data);//这里打开图片编辑并请求返回结果
                    }
                }
                break;
            case CAMERA://调用相机结果处理
                if (resultCode == RESULT_OK) {
                    Intent i = new Intent(ChooseFunctionActivity.this, PhotoEditActivity.class);
                    i.putExtra("imageUri", imageUri.toString());
                    startActivityForResult(i, PHOTO_RESULT);
                }
                break;
            case PHOTO_RESULT://图片编辑是否成功
                if (resultCode == RESULT_OK) {
                    // TODO: 2017/5/18:先销毁mian，返回时在重新打开（为了更新）
                    setResult(20);//PHOTO_OK
                    finish();
                }
                break;

        }

    }

    //调用相册
    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK
                , MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO);
    }

    //Android6.0以上添加动态申请权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1://打开相册申请sd卡读写权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //打开相册
                    openAlbum();
                } else {
                    Toast.makeText(getApplicationContext(), "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    //获取图片sdk>=4.4
    // TODO: 2017/5/12
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(getContext(), uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content:" +
                        "//downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        //获取图片路径后打开activity
        Intent i = new Intent(ChooseFunctionActivity.this, PhotoEditActivity.class);
        i.putExtra("imageUri", imagePath);
        startActivityForResult(i, PHOTO_RESULT);
    }

    //获取图片sdk<4.4
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        Intent i = new Intent(ChooseFunctionActivity.this, PhotoEditActivity.class);
        i.putExtra("imageUri", imagePath.toString());
        startActivityForResult(i, PHOTO_RESULT);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore
                        .Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

}
