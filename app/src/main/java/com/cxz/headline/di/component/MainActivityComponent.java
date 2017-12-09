package com.cxz.headline.di.component;

import com.cxz.headline.di.module.MainActivityModule;
import com.cxz.headline.di.scope.ActivityScope;
import com.cxz.headline.module.main.MainActivity;

import dagger.Component;

/**
 * Created by chenxz on 2017/12/2.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = MainActivityModule.class)
public interface MainActivityComponent {
    void inject(MainActivity activity);
}
