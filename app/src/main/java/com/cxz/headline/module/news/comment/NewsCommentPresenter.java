package com.cxz.headline.module.news.comment;

import com.cxz.headline.base.mvp.BasePresenter;
import com.cxz.headline.base.mvp.IModel;

import javax.inject.Inject;

/**
 * Created by chenxz on 2018/1/12.
 */

public class NewsCommentPresenter extends BasePresenter<IModel, NewsCommentContract.View> implements NewsCommentContract.Presenter {

    @Inject
    public NewsCommentPresenter(NewsCommentContract.View view) {
        super(view);
    }

}
