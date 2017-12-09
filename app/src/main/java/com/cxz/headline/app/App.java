package com.cxz.headline.app;

import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;

import com.cxz.headline.di.component.AppComponent;
import com.cxz.headline.di.component.DaggerAppComponent;
import com.cxz.headline.di.module.AppModule;
import com.cxz.headline.util.SettingUtil;

/**
 * Created by chenxz on 2017/11/25.
 */

public class App extends MultiDexApplication {

    private static App instance;
    public static AppComponent appComponent = null;

    public static synchronized App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initTheme();
    }

    private void initTheme() {
        SettingUtil settingUtil = SettingUtil.getInstance();

        // 获取当前主题
        if (settingUtil.getIsNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

    }

    public static AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(instance))
                    .build();
        }
        return appComponent;
    }
}
