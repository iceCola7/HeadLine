package com.cxz.headline.module.news.main;

import com.cxz.headline.base.mvp.BasePresenter;

import javax.inject.Inject;

/**
 * Created by chenxz on 2017/12/10.
 */

public class NewsMainPresenter extends BasePresenter<NewsMainContract.Model, NewsMainContract.View> implements NewsMainContract.Presenter {

    @Inject
    public NewsMainPresenter(NewsMainContract.Model model, NewsMainContract.View view) {
        super(model, view);
    }

}
