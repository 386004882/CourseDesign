package com.example.jiji.coursedesign.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Menu;

import com.example.jiji.coursedesign.db.City;
import com.example.jiji.coursedesign.db.County;
import com.example.jiji.coursedesign.db.Province;
import com.example.jiji.coursedesign.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jiji on 2017/5/3.
 */

public class Utility {
    /**
     * 解析和处理服务器返回数据
     */
    public static boolean handleProvinceRespone(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allProvinces = new JSONArray(response);
                for (int i = 0; i < allProvinces.length(); i++) {
                    JSONObject provinceObject = allProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleCityRespone(String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCities = new JSONArray(response);
                for (int i = 0; i < allCities.length(); i++) {
                    JSONObject cityObject = allCities.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleConutyeRespone(String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCounties = new JSONArray(response);
                for (int i = 0; i < allCounties.length(); i++) {
                    JSONObject countyObjcet = allCounties.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObjcet.getString("name"));
                    county.setWeatherId(countyObjcet.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 将返回的json数据解析成weather实体类
     */
    public static Weather handleWeatherResponse(String resopnse) {
        try {
            JSONObject jsonObject = new JSONObject(resopnse);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Weather.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 隐藏和显示菜单
     */
    public static void hiddneMenu(Menu menu) {
        if (menu != null) {
            for (int i = 0; i < menu.size(); i++) {
                menu.getItem(i).setVisible(false);
            }
        }
    }

    /**
     * 显示菜单
     */
    public static void showMenu(Menu menu) {
        if (menu != null) {
            for (int i = 0; i < menu.size(); i++) {
                menu.getItem(i).setVisible(true);
            }
        }
    }

    /**
     * 显示确认框
     */
    public static void showConfirmation(Context context, DialogInterface.OnClickListener clickListener) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("提示");
        dialog.setMessage("确认退出？");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定"
                , clickListener);
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消"
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        dialog.show();
    }

    public static void showConfirmation(Context context, String msg, DialogInterface.OnClickListener clickListener) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("提示");
        dialog.setMessage(msg);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定"
                , clickListener);
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消"
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        dialog.show();
    }
}
