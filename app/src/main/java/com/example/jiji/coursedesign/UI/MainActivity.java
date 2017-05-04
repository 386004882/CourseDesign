package com.example.jiji.coursedesign.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jiji.coursedesign.R;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //侧滑菜单
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //左上方显示侧滑按钮
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);
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

                        break;
                    case R.id.nav_paint:

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
                    case R.id.nav_cal:

                        break;
                }
                drawerLayout.closeDrawers();
                return true;
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


}
