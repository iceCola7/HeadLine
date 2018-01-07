package com.cxz.headline.di.module;

import com.cxz.headline.di.scope.FragmentScope;
import com.cxz.headline.module.video.article.VideoArticleContract;
import com.cxz.headline.module.video.article.VideoArticleModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by chenxz on 2017/12/10.
 */
@Module
public class VideoArticleFragmentModule {

    private VideoArticleContract.View view;

    public VideoArticleFragmentModule(VideoArticleContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    VideoArticleContract.View provideVideoArticleView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    VideoArticleContract.Model provideVideoArticleModel(VideoArticleModel model) {
        return model;
    }


}
