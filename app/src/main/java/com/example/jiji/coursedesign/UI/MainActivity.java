package com.example.jiji.coursedesign.UI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.jiji.coursedesign.R;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //标题栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //侧滑菜单
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //左上方显示侧滑按钮
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);
        }
        navView.setCheckedItem(R.id.nav_text);//默认选中
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //侧滑栏点击更换碎片
                switch (item.getItemId()) {
                    case R.id.nav_text:
                        Snackbar.make(MainActivity.this.getCurrentFocus(), "test"
                                , Snackbar.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_photo:

                        break;
                    case R.id.nav_paint:

                        break;
                    case R.id.nav_weather:
                        replaceFragment(new ChooseAreaFragment());
                        break;
                    case R.id.nav_cal:

                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });


        //悬浮按钮
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击事件

            }
        });
    }

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home://打开侧滑菜单
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.item_backup://上传备份

                break;
            case R.id.item_delete://删除

                Snackbar.make(fab, "成功删除数据", Snackbar.LENGTH_SHORT)
                        .setAction("撤销", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //撤销逻辑

                            }
                        }).show();
                break;
            case R.id.item_setting://设置

                break;

        }
        return true;
    }
}
