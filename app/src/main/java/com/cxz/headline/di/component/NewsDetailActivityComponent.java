package com.cxz.headline.di.component;

import com.cxz.headline.di.module.NewsDetailActivityModule;
import com.cxz.headline.di.scope.ActivityScope;
import com.cxz.headline.module.news.detail.NewsDetailActivity;

import dagger.Component;

/**
 * Created by chenxz on 2018/1/6.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = NewsDetailActivityModule.class)
public interface NewsDetailActivityComponent {
    void inject(NewsDetailActivity activity);
}
