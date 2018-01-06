package com.cxz.headline.di.component;

import com.cxz.headline.di.module.NewsChannelActivityModule;
import com.cxz.headline.di.scope.ActivityScope;
import com.cxz.headline.module.news.channel.NewsChannelActivity;

import dagger.Component;

/**
 * Created by chenxz on 2018/1/6.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = NewsChannelActivityModule.class)
public interface NewsChannelActivityComponent {
    void inject(NewsChannelActivity activity);
}
