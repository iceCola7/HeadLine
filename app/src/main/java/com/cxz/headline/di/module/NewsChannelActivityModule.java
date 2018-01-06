package com.cxz.headline.di.module;

import com.cxz.headline.di.scope.ActivityScope;
import com.cxz.headline.module.news.channel.NewsChannelContract;
import com.cxz.headline.module.news.channel.NewsChannelModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by chenxz on 2018/1/6.
 */
@Module
public class NewsChannelActivityModule {

    private NewsChannelContract.View view;

    public NewsChannelActivityModule(NewsChannelContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    NewsChannelContract.View provideNewsChannelView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    NewsChannelContract.Model provideNewsChannelModel(NewsChannelModel model) {
        return model;
    }

}
