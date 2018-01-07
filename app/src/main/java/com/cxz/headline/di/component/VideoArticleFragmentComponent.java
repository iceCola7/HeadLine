package com.cxz.headline.di.component;

import com.cxz.headline.di.module.VideoArticleFragmentModule;
import com.cxz.headline.di.scope.FragmentScope;
import com.cxz.headline.module.video.article.VideoArticleFragment;

import dagger.Component;

/**
 * Created by chenxz on 2017/12/10.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = VideoArticleFragmentModule.class)
public interface VideoArticleFragmentComponent {
    void inject(VideoArticleFragment fragment);
}
