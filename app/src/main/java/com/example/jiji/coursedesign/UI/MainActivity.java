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
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiji.coursedesign.R;
import com.example.jiji.coursedesign.Utils.Utility;
import com.example.jiji.coursedesign.db.User;

import org.litepal.crud.DataSupport;

import de.hdodenhof.circleimageview.CircleImageView;

// TODO: 2017/5/7 添加打开抽屉动画
public class MainActivity extends BaseActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    private CircleImageView icon_image;
    private NavigationView navView;
    private TextView navName;
    private TextView signature;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //默认加载文字备忘碎片
        replaceFragment(new TextFragment());

        //侧滑菜单
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView) findViewById(R.id.nav_view);
        View nav = LayoutInflater.from(this).inflate(R.layout.nav_header, navView);
        navName = (TextView) nav.findViewById(R.id.nav_username);
        icon_image = (CircleImageView) nav.findViewById(R.id.icon_image);
        signature = (TextView) nav.findViewById(R.id.nav_signature);

        SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
        username = sp.getString("username", "");
        initUserData();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //左上方显示侧滑按钮
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
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
                    case R.id.nav_weather:

                        SharedPreferences prefs = PreferenceManager
                                .getDefaultSharedPreferences(MainActivity.this);
                        if (prefs.getString("weather", null) != null) {
                            Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                            startActivity(intent);
                            replaceFragment(new TextFragment());
                        } else {
                            replaceFragment(new ChooseAreaFragment());
                        }
                        break;
                    case R.id.nav_tools:
                        //更多工具正在开发中
                        Toast.makeText(MainActivity.this, "更多功能正在开发中,敬请期待...", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_setting:
                        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                        startActivity(intent);
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

        //打开小游戏
        navName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, GameActivity.class);
                startActivity(i);
            }
        });

        //打开个人页面
        icon_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PersonalActivity.class);
                startActivity(intent);
            }
        });

    }

    //更换碎片
    public void replaceFragment(android.support.v4.app.Fragment fragment) {
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
        Utility.showConfirmation(MainActivity.this, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });
    }

    private void initUserData() {
        if (!username.equals("")) {
            User saveUser = DataSupport.where("username=?", username).findFirst(User.class);
            if (saveUser != null) {
                navName.setText(saveUser.getName());
                signature.setText(saveUser.getSignature());
            }
        }
    }

    @Override
    protected void onResume() {
        initUserData();
        super.onResume();
    }
}
