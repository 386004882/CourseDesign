package com.example.jiji.coursedesign.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiji.coursedesign.R;
import com.example.jiji.coursedesign.db.User;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jiji on 2017/5/7.
 */

// TODO: 2017/5/16 添加图片选择
public class RegisterActivity extends AppCompatActivity {
    private EditText et_username;
    private EditText et_pwd;
    private EditText et_cpwd;
    private Toolbar toolbar;
    private EditText et_name;
    private EditText et_signature;
    private CircleImageView civ_photo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        toolbar = (Toolbar) findViewById(R.id.register_toolbar);
        TextView back = (TextView) findViewById(R.id.register_back);
        TextView reg = (TextView) findViewById(R.id.register_register);
        et_username = (EditText) findViewById(R.id.register_username);
        et_pwd = (EditText) findViewById(R.id.register_password);
        et_cpwd = (EditText) findViewById(R.id.register_confirmpwd);
        et_name = (EditText) findViewById(R.id.register_name);
        et_signature = (EditText) findViewById(R.id.register_signature);
        civ_photo = (CircleImageView) findViewById(R.id.recycler_photo);


        setSupportActionBar(toolbar);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String pwd = et_pwd.getText().toString();
                String name = et_name.getText().toString();
                String signature = et_signature.getText().toString();
                SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());
                String date = formatter.format(curDate);

                if (username.equals("") || pwd.equals("") || name.equals("") || et_cpwd.equals("")) {
                    Toast.makeText(RegisterActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                    et_username.requestFocus();
                } else {
                    if (isUserAvailable(username)) {
                        if (pwd.equals(et_cpwd.getText().toString())) {
                            User user = new User();
                            user.setUsername(username);
                            user.setPassword(pwd);
                            user.setName(name);
                            user.setTime(date);
                            user.setSignature(signature);
                            user.save();
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                            et_cpwd.requestFocus();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "用户已经被注册", Toast.LENGTH_SHORT).show();
                        et_username.requestFocus();
                    }
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

    //判断用户是否存储在
    private boolean isUserAvailable(String username) {
        boolean flag = true;
        User user = DataSupport.where("username=?", username).findFirst(User.class);
        if (user != null && user.getUsername().equals(username)) {
            flag = false;
        }
        return flag;
    }
}
