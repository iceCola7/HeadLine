package com.cxz.headline.di.module;

import com.cxz.headline.di.scope.ActivityScope;
import com.cxz.headline.module.news.detail.NewsDetailContract;
import com.cxz.headline.module.news.detail.NewsDetailModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by chenxz on 2018/1/6.
 */

@Module
public class NewsDetailActivityModule {

    private NewsDetailContract.View view;

    public NewsDetailActivityModule(NewsDetailContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    NewsDetailContract.View provideNewsDetailView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    NewsDetailContract.Model provideNewsDetailModel(NewsDetailModel model){
        return model;
    }

}
