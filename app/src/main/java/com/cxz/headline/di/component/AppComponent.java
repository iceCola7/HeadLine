package com.cxz.headline.di.component;

import com.cxz.headline.app.App;
import com.cxz.headline.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by chenxz on 2017/12/2.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    App getContext();

}
