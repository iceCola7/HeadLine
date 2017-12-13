package com.cxz.headline.di.component;

import com.cxz.headline.di.module.ArticleFragmentModule;
import com.cxz.headline.di.scope.FragmentScope;
import com.cxz.headline.module.news.article.ArticleFragment;

import dagger.Component;

/**
 * Created by chenxz on 2017/12/10.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = ArticleFragmentModule.class)
public interface ArticleFragmentComponent {
    void inject(ArticleFragment fragment);
}
