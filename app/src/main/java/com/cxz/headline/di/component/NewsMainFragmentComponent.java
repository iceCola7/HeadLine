package com.cxz.headline.di.component;

import com.cxz.headline.di.module.NewsMainFragmentModule;
import com.cxz.headline.di.scope.FragmentScope;
import com.cxz.headline.module.news.main.NewsMainFragment;

import dagger.Component;

/**
 * Created by chenxz on 2017/12/10.
 */
@FragmentScope
@Component(dependencies = AppComponent.class, modules = NewsMainFragmentModule.class)
public interface NewsMainFragmentComponent {
    void inject(NewsMainFragment fragment);
}
