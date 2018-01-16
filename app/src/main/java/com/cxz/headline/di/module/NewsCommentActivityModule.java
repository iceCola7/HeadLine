package com.cxz.headline.di.module;

import com.cxz.headline.di.scope.ActivityScope;
import com.cxz.headline.module.news.comment.NewsCommentContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by chenxz on 2018/1/16.
 */
@Module
public class NewsCommentActivityModule {

    private NewsCommentContract.View view;

    public NewsCommentActivityModule(NewsCommentContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    NewsCommentContract.View provideNewsCommentView(){
        return this.view;
    }

}
