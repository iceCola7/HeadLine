package com.cxz.headline.di.component;

import com.cxz.headline.di.module.VideoMainFragmentModule;
import com.cxz.headline.di.scope.FragmentScope;
import com.cxz.headline.module.video.main.VideoMainFragment;

import dagger.Component;

/**
 * Created by chenxz on 2018/1/7.
 */
@FragmentScope
@Component(dependencies = AppComponent.class, modules = VideoMainFragmentModule.class)
public interface VideoMainFragmentComponent {
    void inject(VideoMainFragment fragment);
}
