package com.cxz.headline.util;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;

import com.cxz.headline.app.App;
import com.cxz.headline.R;

/**
 * Created by chenxz on 2017/11/25.
 */

public class SettingUtil {

    private SharedPreferences mSp = PreferenceManager.getDefaultSharedPreferences(App.getInstance());

    private static SettingUtil instance = null;

    public static SettingUtil getInstance() {
        if (instance == null)
            instance = new SettingUtil();
        return instance;
    }

    /**
     * 获取是否开启夜间模式
     */
    public boolean getIsNightMode() {
        return mSp.getBoolean("switch_nightMode", false);
    }

    /**
     * 设置夜间模式
     */
    public void setIsNightMode(boolean flag) {
        mSp.edit().putBoolean("switch_nightMode", flag).apply();
    }

    /**
     * 获取主题颜色
     *
     * @return
     */
    public int getColor() {
        int defaultColor = App.getInstance().getResources().getColor(R.color.colorPrimary);
        int color = mSp.getInt("color", defaultColor);
        if ((color != 0) && Color.alpha(color) != 255) {
            return defaultColor;
        }
        return color;
    }

    /**
     * 设置主题颜色
     */
    public void setColor(int color) {
        mSp.edit().putInt("color", color).apply();
    }

}
