package com.cxz.headline.app;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;

import com.cxz.headline.database.RealmHelper;
import com.cxz.headline.di.component.AppComponent;
import com.cxz.headline.di.component.DaggerAppComponent;
import com.cxz.headline.di.module.AppModule;
import com.cxz.headline.util.SettingUtil;
import com.squareup.leakcanary.LeakCanary;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by chenxz on 2017/11/25.
 */

public class App extends MultiDexApplication {

    private static App instance;
    public static AppComponent appComponent = null;
    private static Context mContext;

    public static synchronized App getInstance() {
        return instance;
    }

    public static synchronized Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = getApplicationContext();
        init();
    }

    private void init() {
        initLeakCanary();
        initTheme();
        initDatabase();
    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    private void initDatabase() {
        Realm.init(this);
        // 使用默认配置
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(RealmHelper.DB_NAME)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
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
