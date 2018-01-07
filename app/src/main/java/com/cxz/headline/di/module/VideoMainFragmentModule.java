package com.cxz.headline.di.module;

import com.cxz.headline.di.scope.FragmentScope;
import com.cxz.headline.module.video.VideoMainContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by chenxz on 2018/1/7.
 */
@Module
public class VideoMainFragmentModule {

    private VideoMainContract.View view;

    public VideoMainFragmentModule(VideoMainContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    VideoMainContract.View provideVideoMainView() {
        return this.view;
    }

}
