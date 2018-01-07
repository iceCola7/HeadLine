package com.cxz.headline.di.module;

import com.cxz.headline.di.scope.FragmentScope;
import com.cxz.headline.module.news.main.NewsMainContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by chenxz on 2017/12/10.
 */
@Module
public class NewsMainFragmentModule {

    private NewsMainContract.View view;

    public NewsMainFragmentModule(NewsMainContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    NewsMainContract.View provideNewsMainView() {
        return this.view;
    }

}
