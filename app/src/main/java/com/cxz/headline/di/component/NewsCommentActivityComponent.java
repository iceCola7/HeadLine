package com.cxz.headline.di.component;

import com.cxz.headline.di.module.NewsCommentActivityModule;
import com.cxz.headline.di.scope.ActivityScope;
import com.cxz.headline.module.news.comment.NewsCommentActivity;

import dagger.Component;

/**
 * Created by chenxz on 2018/1/16.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = NewsCommentActivityModule.class)
public interface NewsCommentActivityComponent {
    void inject(NewsCommentActivity activity);
}
