package com.example.jiji.coursedesign.UI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jiji.coursedesign.R;
import com.example.jiji.coursedesign.db.User;

import org.litepal.crud.DataSupport;

/**
 * Created by jiji on 2017/5/10.
 */

public class PersonalActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_username;
    private EditText et_name;
    private EditText et_signature;
    private Button btn_back;
    private Button btn_editable;
    private Button btn_changepwd;
    private User user;
    private boolean EDITABLE = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        //获取登录信息
        SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
        String saveUsername = sp.getString("username", "");
        if (!saveUsername.equals("")) {
            user = DataSupport.where("username=?", saveUsername).findFirst(User.class);
        } else {
            finish();
        }

        //初始化控件
        initView();

    }

    private void initView() {
        et_username = (EditText) findViewById(R.id.personal_username);
        et_name = (EditText) findViewById(R.id.personal_name);
        et_signature = (EditText) findViewById(R.id.personal_signature);
        btn_back = (Button) findViewById(R.id.personal_back);
        btn_editable = (Button) findViewById(R.id.personal_editable);
        btn_changepwd = (Button) findViewById(R.id.personal_changepwd);

        if (user != null) {
            et_username.setText(user.getUsername());
            et_name.setText(user.getName());
            et_signature.setText(user.getSignature());
        } else {
            finish();
        }

        btn_back.setOnClickListener(this);
        btn_editable.setOnClickListener(this);
        btn_changepwd.setOnClickListener(this);
    }

    private boolean isDataChanged() {
        boolean flag = false;
        if (!et_name.equals(user.getName()) || !et_signature.equals(user.getSignature())) {
            flag = true;
        }
        return flag;
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_back://退出逻辑
                finish();
                break;
            case R.id.personal_editable://修改逻辑
                if (EDITABLE) {
                    if (isDataChanged()) {
                        //可编辑状态，点击保存信息
                        User newUser = new User();
                        newUser.setName(et_name.getText().toString());
                        newUser.setSignature(et_signature.getText().toString());
                        newUser.updateAll("username=?", user.getUsername());
                        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                    }
                    //控件不可编辑
                    et_name.setFocusable(false);
                    et_name.setFocusableInTouchMode(false);
                    et_signature.setFocusable(false);
                    et_signature.setFocusableInTouchMode(false);
                    //动画
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext()
                            , R.anim.anim_function_disappear);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            btn_editable.setBackgroundResource(R.drawable.ic_action_font_faces);
                            Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext()
                                    , R.anim.anim_alpha);
                            btn_editable.startAnimation(animation1);
                            EDITABLE = false;
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    btn_editable.startAnimation(animation);
                } else {
                    //不可编辑状态，点击更换可编辑
                    et_name.setFocusable(true);
                    et_name.setFocusableInTouchMode(true);
                    et_signature.setFocusable(true);
                    et_signature.setFocusableInTouchMode(true);
                    //动画
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext()
                            , R.anim.anim_function_disappear);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            btn_editable.setBackgroundResource(R.drawable.ic_action_tick);
                            Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext()
                                    , R.anim.anim_alpha);
                            btn_editable.startAnimation(animation1);
                            EDITABLE = true;
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    btn_editable.startAnimation(animation);
                }
                break;
            case R.id.personal_changepwd://更改密码

                break;
        }
    }
}
