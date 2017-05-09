package com.example.jiji.coursedesign.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jiji.coursedesign.R;

/**
 * Created by jiji on 2017/5/7.
 */
// TODO: 2017/5/7 为登录注册模块添加联网操作及相关判断
public class LoginActivity extends AppCompatActivity {
    private EditText usernameTv;
    private EditText pwdTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
        String user = sp.getString("username", "");
        if (!user.equals("")) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
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
                SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
                String username = sp.getString("username", "");
                String pwd = sp.getString("password", "");

                if (username.equals(usernameTv.getText().toString())
                        && pwd.equals(pwdTv.getText().toString())) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "请输入正确的用户名和密码", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
