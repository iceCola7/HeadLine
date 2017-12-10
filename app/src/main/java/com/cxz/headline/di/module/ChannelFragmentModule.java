package com.cxz.headline.di.module;

import com.cxz.headline.di.scope.FragmentScope;
import com.cxz.headline.module.news.channel.ChannelContract;
import com.cxz.headline.module.news.channel.ChannelModel;
import com.cxz.headline.module.news.main.NewsMainContract;
import com.cxz.headline.module.news.main.NewsMainModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by chenxz on 2017/12/10.
 */
@Module
public class ChannelFragmentModule {

    private ChannelContract.View view;

    public ChannelFragmentModule(ChannelContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    ChannelContract.View provideChannelView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    ChannelContract.Model provideChannelModel(ChannelModel model) {
        return model;
    }


}
