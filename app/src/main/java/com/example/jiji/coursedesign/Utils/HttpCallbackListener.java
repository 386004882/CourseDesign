package com.example.jiji.coursedesign.Utils;

/**
 * Created by jiji on 2017/4/14.
 */
public interface HttpCallbackListener {
    public void onFinish(String reponse);

    public void onError(Exception e);
}
