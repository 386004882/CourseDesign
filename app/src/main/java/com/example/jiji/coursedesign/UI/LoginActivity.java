package com.example.jiji.coursedesign.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jiji.coursedesign.R;
import com.example.jiji.coursedesign.db.User;

import org.litepal.crud.DataSupport;


/**
 * Created by jiji on 2017/5/7.
 */
// TODO: 2017/5/7 为登录注册模块添加联网操作
public class LoginActivity extends BaseActivity {
    private EditText usernameTv;
    private EditText pwdTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //是否清除所有activity
        Intent destroyIntent = getIntent();
        boolean isDestroy = destroyIntent.getBooleanExtra("destroyAll", false);
        if (isDestroy) {
            ActivityCollector.DestroyExcept(LoginActivity.this);
        }

        SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
        String saveUsername = sp.getString("username", "");
        if (!saveUsername.equals("")) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        usernameTv = (EditText) findViewById(R.id.login_username);
        pwdTv = (EditText) findViewById(R.id.login_password);
        Button reg = (Button) findViewById(R.id.login_res);
        Button login = (Button) findViewById(R.id.login_login);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameTv.getText().toString();
                String pwd = pwdTv.getText().toString();
                if (!username.equals("") && !pwd.equals("")) {
                    User user = DataSupport.where("username=?", username).findFirst(User.class);
                    if (user != null && user.getPassword().equals(pwd)) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("username", username);
                        editor.apply();
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "密码不正确或用户不存", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
