package com.example.jiji.coursedesign.UI;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiji on 2017/5/25.
 */

public class ActivityCollector {
    private static List<Activity> activityList = new ArrayList<>();

    public static void ActivityCreate(Activity activity) {
        activityList.add(activity);
    }

    public static void ActivityDestroy(Activity activity) {
        activityList.remove(activity);
    }

    public static void DestroyAll() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        activityList.clear();
    }

    public static void DestroyExcept(Activity except) {
        for (Activity activity : activityList) {
            if (!activity.equals(except)) {
                activity.finish();
            }
        }
        activityList.clear();
        activityList.add(except);
    }

}
