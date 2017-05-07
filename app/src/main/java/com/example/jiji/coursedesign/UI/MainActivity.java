package com.example.jiji.coursedesign.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jiji.coursedesign.R;

// TODO: 2017/5/7 添加打开抽屉动画
public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //默认加载文字备忘碎片
        replaceFragment(new TextFragment());


        //侧滑菜单
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //左上方显示侧滑按钮
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        navView.setCheckedItem(R.id.nav_text);//默认选中
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                //侧滑栏点击更换碎片
                switch (item.getItemId()) {
                    case R.id.nav_text:

                        replaceFragment(new TextFragment());
                        break;
                    case R.id.nav_photo:

                        replaceFragment(new PhotoFragment());
                        break;
                    case R.id.nav_paint:

                        Intent paintIntent = new Intent(getApplicationContext(), PaintActivity.class);
                        startActivity(paintIntent);
                        break;
                    case R.id.nav_weather:

                        SharedPreferences prefs = PreferenceManager
                                .getDefaultSharedPreferences(MainActivity.this);
                        if (prefs.getString("weather", null) != null) {
                            Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                            startActivity(intent);
                        } else {
                            replaceFragment(new ChooseAreaFragment());
                        }
                        break;
                    case R.id.nav_tools:
                        //更多工具正在开发中
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

    }

    //更换碎片
    private void replaceFragment(android.support.v4.app.Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_layout, fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_toolbar, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        AlertDialog isExit = new AlertDialog.Builder(this).create();
        isExit.setTitle("系统提示");
        isExit.setMessage("是否退出程序");
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
        // super.onBackPressed();
    }


}
