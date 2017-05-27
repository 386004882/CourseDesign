package com.example.jiji.coursedesign.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jiji.coursedesign.R;
import com.example.jiji.coursedesign.Utils.Utility;


/**
 * Created by jiji on 2017/5/25.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private Button btn_personal;
    private Button btn_accomplished;
    private Button btn_game;
    private Button btn_support;
    private Button btn_about;
    private Button btn_logout;

    private ImageView iv_personalArrow;
    private ImageView iv_accomplishedArrow;
    private ImageView iv_gameArrow;
    private ImageView iv_supportArrow;
    private ImageView iv_aboutArrow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initComponent();
        setOnClick();
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //左上方显示侧滑按钮
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        hiddenArrow();
        showArrow();

    }

    @Override
    protected void onResume() {
        hiddenArrow();
        showArrow();
        super.onResume();
    }

    private void showArrow() {
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_function_show);
        anim.setDuration(800);
        btn_personal.startAnimation(anim);
        btn_accomplished.startAnimation(anim);
        btn_game.startAnimation(anim);
        btn_about.startAnimation(anim);
        btn_support.startAnimation(anim);
        iv_personalArrow.startAnimation(anim);
        iv_accomplishedArrow.startAnimation(anim);
        iv_gameArrow.startAnimation(anim);
    }

    private void hiddenArrow() {
        btn_personal.setVisibility(View.INVISIBLE);
        btn_accomplished.setVisibility(View.INVISIBLE);
        btn_game.setVisibility(View.INVISIBLE);
        iv_personalArrow.setVisibility(View.INVISIBLE);
        iv_accomplishedArrow.setVisibility(View.INVISIBLE);
        iv_gameArrow.setVisibility(View.INVISIBLE);
    }

    //初始化控件
    private void initComponent() {
        toolbar = (Toolbar) findViewById(R.id.setting_toolbar);
        btn_personal = (Button) findViewById(R.id.setting_personal);
        btn_accomplished = (Button) findViewById(R.id.setting_accomplished);
        btn_game = (Button) findViewById(R.id.setting_game);
        btn_support = (Button) findViewById(R.id.setting_support);
        btn_about = (Button) findViewById(R.id.setting_about);
        btn_logout = (Button) findViewById(R.id.setting_logout);

        iv_personalArrow = (ImageView) findViewById(R.id.setting_personal_arrow);
        iv_accomplishedArrow = (ImageView) findViewById(R.id.setting_accomplished_arrow);
        iv_gameArrow = (ImageView) findViewById(R.id.setting_game_arrow);
        iv_supportArrow = (ImageView) findViewById(R.id.setting_support_arrow);
        iv_aboutArrow = (ImageView) findViewById(R.id.setting_about_arrow);

    }

    //设置控件点击事件
    private void setOnClick() {
        btn_personal.setOnClickListener(this);
        btn_accomplished.setOnClickListener(this);
        btn_game.setOnClickListener(this);
        btn_support.setOnClickListener(this);
        btn_about.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
    }

    //点击事件
    @Override
    public void onClick(View v) {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_setting_arrow_hidden);
        switch (v.getId()) {
            case R.id.setting_personal:
                btn_personal.startAnimation(animation);
                iv_personalArrow.startAnimation(animation);
                Intent intent = new Intent(SettingActivity.this, PersonalActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_accomplished:
                btn_accomplished.startAnimation(animation);
                iv_accomplishedArrow.startAnimation(animation);
                Intent i1 = new Intent(SettingActivity.this, AccomplishedRecordActivity.class);
                startActivity(i1);
                break;
            case R.id.setting_game:
                btn_game.startAnimation(animation);
                iv_gameArrow.startAnimation(animation);
                Intent i = new Intent(SettingActivity.this, GameActivity.class);
                startActivity(i);
                break;
            case R.id.setting_support:
                Toast.makeText(SettingActivity.this, "感谢您的支持", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting_about:
                AlertDialog aboutDialog = new AlertDialog.Builder(this).create();
                aboutDialog.setMessage("    如果您对本软件有任何建议或意见，可以联系：\n" +
                        "    QQ:386004882\n" +
                        "    微博:陳_肇_基\n" +
                        "感谢您的支持与使用!!！");
                aboutDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                aboutDialog.show();
                break;
            case R.id.setting_logout:
                Utility.showConfirmation(SettingActivity.this, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getSharedPreferences("user", Context.MODE_PRIVATE)
                                .edit().clear().apply();
                        Toast.makeText(SettingActivity.this, "成功注销", Toast.LENGTH_SHORT).show();
                        Intent loginIntent = new Intent(SettingActivity.this, LoginActivity.class);
                        loginIntent.putExtra("destroyAll", true);
                        startActivity(loginIntent);
                    }
                });
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
