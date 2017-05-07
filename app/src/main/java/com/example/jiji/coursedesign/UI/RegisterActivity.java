package com.example.jiji.coursedesign.UI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiji.coursedesign.R;

/**
 * Created by jiji on 2017/5/7.
 */

public class RegisterActivity extends AppCompatActivity {
    private EditText usernameEt;
    private EditText pwdEt;
    private EditText cpwdEt;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        toolbar = (Toolbar) findViewById(R.id.register_toolbar);
        TextView back = (TextView) findViewById(R.id.register_back);
        TextView reg = (TextView) findViewById(R.id.register_register);
        usernameEt = (EditText) findViewById(R.id.register_username);
        pwdEt = (EditText) findViewById(R.id.register_password);
        cpwdEt = (EditText) findViewById(R.id.register_confirmpwd);

        setSupportActionBar(toolbar);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEt.getText().toString();
                String pwd = pwdEt.getText().toString();
                String cpwd = cpwdEt.getText().toString();
                if (pwd.equals(cpwd)) {
                    SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
                    editor.putString("username", username);
                    editor.putString("password", pwd);
                    editor.apply();
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "两次输入密码不相同", Toast.LENGTH_SHORT).show();
                    cpwdEt.requestFocus();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
