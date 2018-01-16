package com.cxz.headline.di.component;

import com.cxz.headline.di.module.VideoDetailActivityModule;
import com.cxz.headline.di.scope.ActivityScope;
import com.cxz.headline.module.video.detail.VideoDetailActivity;

import dagger.Component;

/**
 * Created by chenxz on 2018/1/15.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = VideoDetailActivityModule.class)
public interface VideoDetailActivityComponent {
    void inject(VideoDetailActivity activity);
}
