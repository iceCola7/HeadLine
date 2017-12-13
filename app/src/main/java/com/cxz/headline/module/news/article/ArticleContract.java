package com.cxz.headline.module.news.article;

import com.cxz.headline.base.mvp.IModel;
import com.cxz.headline.base.mvp.IPresenter;
import com.cxz.headline.base.mvp.IView;
import com.cxz.headline.bean.news.NewsMultiArticleBean;

import io.reactivex.Observable;

/**
 * Created by chenxz on 2017/12/10.
 */

public interface ArticleContract {

    interface View extends IView {
        void updateNewsArticleList();
    }

    interface Presenter extends IPresenter {
        void loadNewsArticleList(String category, String minBehotTime);
    }

    interface Model extends IModel {
        Observable<NewsMultiArticleBean> loadNewsArticleList(String category, String minBehotTime);
    }

}
